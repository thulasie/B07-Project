package com.example.smartAir.triaging;

public interface AlerterService {
    void notifyOfStart();
    void notifyOfEmergency();
    void notifyOfEscalation();
}


class DefaultAlerter implements AlerterService{

    @Override
    public void notifyOfStart() {
        System.out.println("Fake AlerterterService: Triage started!");
    }

    @Override
    public void notifyOfEmergency() {
        System.out.println("Fake AlerterterService: Emergency started!");
    }

    @Override
    public void notifyOfEscalation() {
        System.out.println("Fake AlerterterService: Escalation started!");
    }
}