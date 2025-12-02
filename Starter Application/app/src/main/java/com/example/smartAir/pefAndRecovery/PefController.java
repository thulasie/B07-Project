package com.example.smartAir.pefAndRecovery;

class PefController implements PefEntryController {
    private Float pEF;
    private Float pEFPost;
    private boolean hasPEFPost = false;
    private PefLogger logger;

    void setLogger(PefLogger l) {
        logger = l;
    }

    public interface PefLogger {
        void logPEF(Float pEF);
        void logPEF(Float preMedPEF, Float postMedPEF);
    }

    @Override
    public boolean hasPrePostMedicinePEF() {
        return hasPEFPost;
    }

    @Override
    public void setPEFValue(Float f) {
        pEF = f;
    }

    @Override
    public void setPEFPostMedicineValue(Float f) {
        pEFPost = f;
    }

    @Override
    public void togglePEFPostMedicineValue(boolean b) {
        hasPEFPost = b;
    }

    @Override
    public void submitChanges() {
        if (hasPEFPost && (pEF != null || pEFPost != null)) {
            logger.logPEF(pEF, pEFPost);
        } else if (!hasPEFPost && pEF != null) {
            logger.logPEF(pEF);
        }
    }
}
