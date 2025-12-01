package com.example.smartAir.triaging;

import com.example.smartAir.domain.Zone;

public interface BreathInformationProvider {
    Float getPB();
    Zone getZone();
    void setPEF(Float f);

}
