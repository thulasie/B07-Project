package com.example.smartAir.pefAndRecovery;

interface PefEntryController {
    boolean hasPrePostMedicinePEF();
    void setPEFValue(Float f);
    void setPEFPostMedicineValue(Float f);
    void togglePEFPostMedicineValue(boolean b);
    void submitChanges();
}