package com.example.smartAir.pefAndRecovery;

public class PersonalBestLog {
    private static PersonalBestLog singletonInstance = new PersonalBestLog();
    private String username;

    private PersonalBestLog() {} // Forces singleton to be accessed

    static PersonalBestLog getSingletonInstance() {
        return singletonInstance;
    }
    static void initializePersonalBestLog(String username, CompletionNotifier callback) {
        singletonInstance.username = username;
        ZoneChangeMonitor.getSingletonInstance().initializeCurrentPersonalBest(10F);

        callback.notifyMonitor();
    }

    void setPersonalBest (Float f) {
        // TODO add changing for children
        ZoneChangeMonitor.getSingletonInstance().setCurrentPersonalBest(f);

        System.out.println(username + "'s personal best: " + f);
    }
}
