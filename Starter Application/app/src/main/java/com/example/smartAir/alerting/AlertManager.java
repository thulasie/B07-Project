package com.example.smartAir.alerting;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.smartAir.R;
import com.example.smartAir.medicine.InventoryStatus;
import com.example.smartAir.R6model.RescueEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AlertManager {

    private static final String CHANNEL_ID = "SMART_AIR_R6_ALERTS";

    private static void ensureChannel(Context ctx) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel(
                            CHANNEL_ID,
                            "SMART AIR Alerts",
                            NotificationManager.IMPORTANCE_HIGH
                    );
            NotificationManager nm = ctx.getSystemService(NotificationManager.class);
            if (nm != null) nm.createNotificationChannel(channel);
        }
    }

    private static void send(Context ctx, int id, String title, String msg) {
        ensureChannel(ctx);
        NotificationCompat.Builder b = new NotificationCompat.Builder(ctx, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)   // ensure this exists
                .setContentTitle(title)
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat.from(ctx).notify(id, b.build());
    }

    public static void checkRedZoneDay(Context ctx, int rescuesToday) {
        if (rescuesToday >= 4) {
            send(ctx, 100,
                    "Red-zone day",
                    "Your child needed rescue medicine many times today. Follow your red plan.");
        }
    }

    public static void checkRapidRescues(Context ctx, List<RescueEvent> events) {
        for (int i = 0; i < events.size(); i++) {
            long start = events.get(i).getTimestampMillis();
            int count = 1;
            for (int j = i + 1; j < events.size(); j++) {
                long diff = events.get(j).getTimestampMillis() - start;
                if (diff <= TimeUnit.HOURS.toMillis(3)) {
                    count++;
                    if (count >= 3) {
                        send(ctx, 101,
                                "Too many rescue puffs",
                                "3+ rescue uses in 3 hours. Use your red plan and seek help.");
                        return;
                    }
                } else break;
            }
        }
    }

    public static void checkWorseAfterDose(Context ctx, List<RescueEvent> events) {
        for (RescueEvent e : events) {
            if (e.isWorseAfterDose()) {
                send(ctx, 102,
                        "Worse after rescue",
                        "Symptoms were worse after rescue medicine. Contact your provider.");
                return;
            }
        }
    }

    public static void checkInventory(Context ctx, List<InventoryStatus> inventory) {
        long now = System.currentTimeMillis();
        for (InventoryStatus st : inventory) {
            if (st.getRemainingDoses() <= 10) {
                send(ctx, 103,
                        "Medication low",
                        st.getMedicationName() + " is almost out. Please refill soon.");
            }
            long daysToExpiry =
                    TimeUnit.MILLISECONDS.toDays(st.getExpiryDateMillis() - now);
            if (daysToExpiry <= 14) {
                send(ctx, 104,
                        "Medication expiring",
                        st.getMedicationName() + " expires in " + daysToExpiry + " days.");
            }
        }
    }
}
