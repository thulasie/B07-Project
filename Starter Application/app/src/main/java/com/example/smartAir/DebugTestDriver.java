package com.example.smartAir;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartAir.data.DatabaseLogger;
import com.example.smartAir.data.DatabaseLogEntry;
import com.example.smartAir.data.DatabaseLogType;
import com.example.smartAir.pefAndRecovery.Pef;
import com.example.smartAir.pefAndRecovery.PefLog;
import com.example.smartAir.triaging.TriageModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;

public class DebugTestDriver extends Fragment {

    LinearLayout debugMessages;

    public DebugTestDriver () {
        super(R.layout.debug_screen);
    }

    // This functions as a main method...
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        debugMessages = view.findViewById(R.id.debug_messages);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword("t.gu@mail.utoronto.ca", "xrpentxkrt");

        PefLog.initializePefLog("testUserID");
        PefLog.getSingletonInstance().logPEF(10F, 15F);
    }

    void printMessage(CharSequence msg) {
        TextView text = (TextView) getLayoutInflater().inflate(R.layout.debug_message, null);

        text.setText(msg);

        debugMessages.addView(text);
    }
}
