package com.example.smartAir.pefAndRecovery;

import com.example.smartAir.domain.Zone;

public class ZoneEntryFacade {
    public interface CallbackGenerator {
        ExitScreenAction callback (String userID);
    }

    public interface OnInitializeCallback {
        void callback();
    }

    private static final ZoneEntryFacade singleton = new ZoneEntryFacade();
    private String userID;
    private CallbackGenerator gen = (userID) ->
            ( () -> System.out.println("ZoneEntryFacade: Please set the CallbackGenerator before using. Argument: " + userID) );
    private ZoneEntryFacade() {
        userID = "";
    } // Forces a singleton

    public static ZoneEntryFacade getInstance() {
        return singleton;
    }

    public static void changeUser (String userID, OnInitializeCallback action) {
        CompletionMonitor monitor = new CompletionMonitor(2, () -> {
            ZoneChangeMonitor.initialize(userID);
            action.callback();
        });

        singleton.userID = userID;

        PefLog.initializeTodaysPefLog(userID, monitor.getNotifier());
        PersonalBestLog.initializePersonalBestLog(userID, monitor.getNotifier());
    }

    public static void setExitAction (CallbackGenerator g) {
        singleton.gen = g;
    }

    public static PefEntry createPefEntry() {
        PefEntry frag = new PefEntry();
        PefController controller = new PefController();
        frag.setPEFController(controller);
        controller.setLogger(PefLog.getSingletonInstance());
        frag.setScreenAfter(singleton.gen.callback(singleton.userID));

        return frag;
    }

    public static PersonalBestEntry createPersonalBestEntry() {
        PersonalBestEntry frag = new PersonalBestEntry();
        PersonalBestEntryController controller = new PersonalBestEntryController();
        controller.setLogger(PersonalBestLog.getSingletonInstance());
        frag.setController(controller);
        frag.setOnExit(singleton.gen.callback(singleton.userID));

        return frag;
    }

    public static Zone enterPef(Float f) {
        PefLog.getSingletonInstance().logPEF(f);
        return ZoneChangeMonitor.getSingletonInstance().getCurrentZone();
    }

    public static BreathInformationProvider getBreathProvider() {
        return new BreathInformationProvider() {
            private Zone zone = ZoneChangeMonitor.getSingletonInstance().getCurrentZone();
            private Float personalBest = PersonalBestLog.getSingletonInstance().getPersonalBest();

            @Override
            public Float getPB() {
                return personalBest;
            }

            @Override
            public Zone getZone() {
                return zone;
            }

            @Override
            public void setPEF(Float f) {
                PefLog.getSingletonInstance().logPEF(f);
            }

            @Override
            public Float getHighestPef() {
                return PefLog.getSingletonInstance().getHighestPEF();
            }
        };
    }
}
