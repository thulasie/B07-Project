package com.example.smartAir.pefAndRecovery;

import com.example.smartAir.databaseLog.DatabaseLogEntryData;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

@IgnoreExtraProperties
public abstract class Pef extends DatabaseLogEntryData {
    protected static final String HAS_PRE_AND_POST_MED_FIELD = "hasPreAndPost";

    public Pef() {}

    public static Pef create (Float pEF) {
        SingularPEF obj = new SingularPEF();
        obj.pEF = pEF;
        return obj;
    }

    public static Pef create (Float preMedPEF, Float postMedPEF) {
        PrePostMedPEF obj = new PrePostMedPEF();
        obj.preMedPEF = preMedPEF;
        obj.postMedPEF = postMedPEF;
        return obj;
    }

    public static Pef createFromDB(HashMap<String, Object> map) {
        Pef a;

        if (map.get(HAS_PRE_AND_POST_MED_FIELD) != null && (Boolean) map.get(HAS_PRE_AND_POST_MED_FIELD)) {
            a = new PrePostMedPEF();
        } else {
            a = new SingularPEF();
        }

        a.setEntriesWithDB(map);

        return a;
    }

    @Exclude
    public abstract Float getHighestPEF();

    public abstract boolean getHasPreAndPost();
}

class SingularPEF extends Pef {
    public Float pEF;

    @Override @Exclude
    public Float getHighestPEF() {
        return pEF;
    }
    @Override
    public void setEntriesWithDB(HashMap<String, Object> map) {
        super.setEntriesWithDB(map);
    }

    @Override @Exclude
    public String getLogEntry() {
        return "PEF set to " + pEF;
    }

    public boolean getHasPreAndPost() {
        return false;
    }
}

class PrePostMedPEF extends Pef {
    public Float preMedPEF;
    public Float postMedPEF;

    @Override @Exclude
    public Float getHighestPEF() {
        if (preMedPEF == null && postMedPEF == null) {
            return -1F; // For the sake of completeness
        } else if (preMedPEF == null) {
            return postMedPEF;
        } else if (postMedPEF == null) {
            return preMedPEF;
        } else {
            return Float.max(preMedPEF, postMedPEF);
        }
    }
    @Override
    public void setEntriesWithDB(HashMap<String, Object> map) {
        super.setEntriesWithDB(map);
    }

    @Override @Exclude
    public String getLogEntry() {
        return "Pre-medicine PEF set to " + preMedPEF + ", Post-medicine set to " + postMedPEF;
    }

    public boolean getHasPreAndPost() {
        return true;
    }
}
