package com.example.smartAir.triaging;

import android.view.View;

import com.example.smartAir.data.DatabaseLogger;
import com.example.smartAir.domain.Zone;
import com.example.smartAir.pefAndRecovery.ZoneEntryFacade;

// Facade class for triage screen
public class TriageScreenCreator {

    private BreathInformationProvider breathInformationProvider;
    private AlerterService alerter = new DefaultAlerter();
    private HomeController exitController;
    private ZoneStepsProvider zoneStepsProvider;
    private TriageLogger triageLogger = new DefaultTriageLogger();
    private final String userID;

    public void setAlerter (AlerterService alerter) {
        this.alerter = alerter;
    }

    public void setHomeController(HomeController c) {
        this.exitController = c;
    }

    public void setBreathInformationProvider(BreathInformationProvider p) {
        breathInformationProvider = p;
    }

    public void setZoneStepsProvider(ZoneStepsProvider p) {
        zoneStepsProvider = p;
    }

    public void setTriageLogger(TriageLogger t) {
        this.triageLogger = t;
    }

    public TriageScreenCreator(String userID) {
        this.userID = userID;
    }

    public TriageFragment createTriageFragment() {
        TriageFragment frag = new TriageFragment();
        TriageController c = new TriageController(frag);
        DatabaseTriageLogger log = new DatabaseTriageLogger();

        DatabaseLogger l = new DatabaseLogger(userID);
        log.setLogger(l);

        c.setTriageLogger(log);
        c.setAlerter(this.alerter);
        c.setInformationProvider(this.breathInformationProvider);

        frag.setZoneStepsProvider(this.zoneStepsProvider);
        frag.setExitController(this.exitController);
        frag.setTriageController(c);


        return frag;
    }
}



interface TriageFragmentSwitcher {

    void startSymptomEntry();
    void startRecentHistoryEntry();
    void startDecisionCard();
    void startCheckBack();

    void showResolutionScreen();
}

interface DecisionCardView {
    void startAtHomeSteps();

    void setRemainingTriageTime(long timeRemaining);

}

interface TriageResolver {
    void callEmergency();
    void showTriageEnd();
}

