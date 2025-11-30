package com.example.smartAir.pefAndRecovery;

public interface PEFEntryController {
    boolean hasPrePostMedicinePEF();
    void setPEFValue(Float f);
    void setPEFPostMedicineValue(Float f);
    void togglePEFPostMedicineValue(boolean b);
    void submitChanges();
}