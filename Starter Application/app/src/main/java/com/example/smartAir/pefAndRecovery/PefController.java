package com.example.smartAir.pefAndRecovery;

public class PefController implements PEFEntryController {
    private Float pEF;
    private Float pEFPost;
    private boolean hasPEFPost = false;
    private static ZoneChangeLogger logger;

    public static void setLogger(ZoneChangeLogger l) {
        logger = l;
    }

    public interface ZoneChangeLogger {
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
};
