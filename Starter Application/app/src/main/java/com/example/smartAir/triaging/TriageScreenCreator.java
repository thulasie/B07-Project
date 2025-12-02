package com.example.smartAir.triaging;

import android.view.LayoutInflater;
import android.view.View;

import com.example.smartAir.databaseLog.DatabaseLogger;
import com.example.smartAir.domain.Zone;
import com.example.smartAir.pefAndRecovery.BreathInformationProvider;

// Facade class for triage screen
public class TriageScreenCreator {

    private BreathInformationProvider breathInformationProvider;
    private HomeController exitController;
    private ZoneStepsProvider zoneStepsProvider = new DefaultZoneSteps();
    private TriageLogger triageLogger;
    private final String userID;

    public void setHomeController(HomeController c) {
        this.exitController = c;
    }

    public void setBreathInformationProvider(BreathInformationProvider p) {
        breathInformationProvider = p;
    }

    public void setZoneStepsProvider(ZoneStepsProvider p) {
        zoneStepsProvider = p;
    }

    public TriageScreenCreator(String userID) {
        this.userID = userID;
        this.triageLogger = new DatabaseTriageLogger(userID);
    }

    public TriageFragment createTriageFragment() {
        TriageFragment frag = new TriageFragment();
        TriageController c = new TriageController(frag);

        DatabaseLogger l = new DatabaseLogger(userID);

        c.setTriageLogger(triageLogger);
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

