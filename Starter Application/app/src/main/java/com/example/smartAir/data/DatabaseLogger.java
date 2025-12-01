package com.example.smartAir.data;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class DatabaseLogger {

    String userID;

    public DatabaseLogger(String userID) {
        this.userID = userID;
    }

    public interface LogCallback {
        void finalCallback();
    }

    private static FirebaseDatabase instance = FirebaseDatabase.getInstance();
    private static DatabaseLogger singleton;



    public void addLog(DatabaseLogEntry entry) {
        HashMap<String, Object> values = new HashMap<>();
        values.put(String.valueOf(entry.accessDate().getTime()), entry);

        instance.getReference("logs/" + userID).updateChildren(values);
    }

    public void getLogs(HashSet<DatabaseLogType> typesWanted, ArrayList<DatabaseLogEntry> list, LogCallback callback) {
        System.out.println("DatabaseLogger: Retrieving data...");

        class CallbackEnabler {
            int count = 0;
            final int finalCount = typesWanted.size();
            final LogCallback finalCallback = callback;

            void insertIntoList(Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    System.out.println("hey");
                    for (DataSnapshot s : task.getResult().getChildren()) {
                        try {
                            HashMap<String, Object> a = (HashMap) s.getValue();
                            String type = (String) a.get("type");
                            DatabaseLogEntryFactory creator = DatabaseLogEntryFactory.make(DatabaseLogType.valueOf(type));

                            final DatabaseLogEntryData data = creator.createFromDB((HashMap<String, Object>) a.get("data"));
                            final Date date = new Date(Long.parseLong(s.getKey()));

                            DatabaseLogEntry entry = new DatabaseLogEntry(date, (String) a.get("type"), data);

                            list.add(entry);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                count++;

                if (count == finalCount) {
                    finalCallback.finalCallback();
                }
            }
        }

        CallbackEnabler a = new CallbackEnabler();

        for (DatabaseLogType type: typesWanted) {
            instance.getReference("logs/" + userID).orderByChild("type").equalTo(type.name()).get()
                    .addOnCompleteListener(a::insertIntoList);
        }
    }

    public void getLogs(HashSet<DatabaseLogType> typesWanted, ArrayList<DatabaseLogEntry> list, LogCallback callback, Date startAt) {
        System.out.println("DatabaseLogger: Retrieving data...");

        instance.getReference("logs/" + userID)
                .orderByKey().startAt(String.valueOf(startAt.getTime()))
                .get().addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        for (DataSnapshot s: task.getResult().getChildren()) {
                            HashMap<String, Object> a = (HashMap) s.getValue();
                            String type = (String) a.get("type");
                            if (typesWanted.contains(DatabaseLogType.valueOf(type))) {
                                try {
                                    DatabaseLogEntryFactory creator = DatabaseLogEntryFactory.make(DatabaseLogType.valueOf(type));

                                    final DatabaseLogEntryData data = creator.createFromDB((HashMap<String, Object>) a.get("data"));
                                    final Date date = new Date(Long.parseLong(s.getKey()));

                                    DatabaseLogEntry entry = new DatabaseLogEntry(date, (String) a.get("type"), data);
                                    list.add(entry);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        callback.finalCallback();
                    }
                });
    }
}
