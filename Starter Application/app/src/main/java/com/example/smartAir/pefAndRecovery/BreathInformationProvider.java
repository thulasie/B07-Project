package com.example.smartAir.pefAndRecovery;

import com.example.smartAir.domain.Zone;

public interface BreathInformationProvider {
    Float getPB();
    Float getHighestPef();
    Zone getZone();
    void setPEF(Float f);

}
