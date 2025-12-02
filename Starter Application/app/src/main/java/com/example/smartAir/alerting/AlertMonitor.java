package com.example.smartAir.alerting;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class AlertMonitor {
    public interface Alerter{
        void showAlert(String username, String msg);
    }

    private static ArrayList<String> children;
    private static final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private static Alerter alert;
    private static final long THREE_HOURS_IN_SECONDS = 3 * 60 * 60;
    private static final long ALERT_LEEWAY_SECONDS = 10L;

    public static void setChildren(ArrayList<String> arr) {
        AlertMonitor.children = arr;

        for (String username: arr) {
            registerChild(username);
        }
    }

    public static void registerAlerter(Alerter a) {
        alert = a;
    }

    private static void registerChild(String username) {
        registerRapidRescue(username);
        registerEscalation(username);
        registerLowInventory(username);
        registerRedDay(username);
        registerWorseAfterDose(username);
        registerTriage(username);
    }

    private static void registerRapidRescue(String username) {
        firebaseDatabase.getReference("logs")
                .child(username)
                .orderByKey()
                .startAt(String.valueOf(Instant.now().minusSeconds(THREE_HOURS_IN_SECONDS).toEpochMilli()))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int i = 0;
                        for (DataSnapshot d: snapshot.getChildren()) {
                            if (d.child("type").getValue().toString().equals("RESCUE_USE")) {
                                System.out.println("Rescue use...");
                                i++;
                            }
                            if (i > 2) {
                                alert.showAlert(username, "≥3 rescue uses in 3 hours.");
                                return;
                            }
                        }
                    }

                    @Override public void onCancelled(@NonNull DatabaseError error) {}
                });
    }

    private static void registerRedDay(String username) {
        firebaseDatabase.getReference("logs")
                .child(username)
                .orderByKey()
                .startAt(getTodaysMidnight())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot d: snapshot.getChildren()) {
                            Object temp = d.child("type").getValue();
                            if (temp != null && temp.toString().equals("ZONE_CHANGE")) {
                                Object data = d.child("data").getValue();
                                if (data instanceof HashMap) {
                                    HashMap<String, Object> map = (HashMap) data;

                                    String s = map.getOrDefault("newZone", "_").toString();

                                    if (s.equalsIgnoreCase("RED")) {
                                        alert.showAlert(username, "Today's zone changed to Red.");
                                    }
                                }
                            }
                        }
                    }

                    @Override public void onCancelled(@NonNull DatabaseError error) {}
                });
    }

    private static void registerWorseAfterDose(String username) {
        firebaseDatabase.getReference("logs")
                .child(username)
                .orderByKey()
                .startAt(getLeewayDate())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot d: snapshot.getChildren()) {
                            Object temp = d.child("type").getValue();
                            if (temp != null && (temp.toString().equals("RESCUE_USE") || temp.toString().equals("CONTROLLER_USE"))) {
                                Object data = d.child("data").getValue();
                                if (data instanceof HashMap) {
                                    HashMap<String, Object> map = (HashMap) data;

                                    String s = map.getOrDefault("rating", "_").toString();

                                    if (s.equalsIgnoreCase("WORSE")) {
                                        alert.showAlert(username, "Reported worse feeling after medicine.");
                                    }
                                }
                            }
                        }
                    }

                    @Override public void onCancelled(@NonNull DatabaseError error) {}
                });
    }

    private static void registerTriage(String username) {
        firebaseDatabase.getReference("logs")
                .child(username)
                .orderByKey()
                .startAt(getLeewayDate())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot d: snapshot.getChildren()) {
                            Object type = d.child("type").getValue();
                            if (type != null && type.toString().equals("TRIAGE")) {
                                alert.showAlert(username, "Triage session started.");
                            }
                        }
                    }

                    @Override public void onCancelled(@NonNull DatabaseError error) {}
                });

    }

    private static void registerEscalation(String username) {
        firebaseDatabase.getReference("logs")
                .child(username)
                .orderByKey()
                .startAt(getLeewayDate())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot d: snapshot.getChildren()) {
                            Object type = d.child("type").getValue();
                            if (type != null && type.toString().equals("TRIAGE")) {
                                Object data = d.child("data").getValue();
                                if (data instanceof HashMap) {
                                    HashMap<String, Object> map = (HashMap) data;

                                    String s = map.getOrDefault("triageDecision", "_").toString();

                                    if (s.equalsIgnoreCase("EMERGENCY_CALLED")) {
                                        alert.showAlert(username, "Triage session escalated.");
                                    }
                                }
                            }
                        }
                    }

                    @Override public void onCancelled(@NonNull DatabaseError error) {}
                });

    }

    private static void registerLowInventory(String username) {
        registerLowInventory(username, "Controller");
        registerLowInventory(username, "Rescue");
    }

    private static void registerLowInventory(String username, String canistername) {
        firebaseDatabase.getReference("inventory")
                .child(username).child(canistername)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Object amountLeft = snapshot.child("amountLeft").getValue();
                        Object fullAmount = snapshot.child("fullAmount").getValue();

                        if (amountLeft instanceof Long && fullAmount instanceof Long) {
                            Float percent = calculatePercentLeft((Long) amountLeft, (Long) fullAmount);
                            if (!percent.isNaN() && !percent.isInfinite() && percent <= 0.2) {
                                alert.showAlert(username, canistername + " inhaler is running low.");
                            }
                        }

                    }

                    @Override public void onCancelled(@NonNull DatabaseError error) {}
                });
    }

    private static Float calculatePercentLeft(Long p, Long q) {
        return p.floatValue() / q.floatValue();
    }

    private static String getTodaysMidnight() {
        Calendar c = new GregorianCalendar();
        c.setTime(new Date());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return String.valueOf(c.getTime());
    }

    private static String getLeewayDate() {
        return String.valueOf(Instant.now().minusSeconds(ALERT_LEEWAY_SECONDS).toEpochMilli());
    }
}
