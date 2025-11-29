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

        TriageModel m = new TriageModel();
        m.startTriageSession();
        m.setRescueCount(3);
        m.setDecision(TriageModel.TriageDecision.UNDECIDED);

        //log.startTriage(m, "ignored");


        HashSet<DatabaseLogType> types = new HashSet<>();
        types.add(DatabaseLogType.MEDICINE);
        types.add(DatabaseLogType.TRIAGE);


        DatabaseLogger databaseLogger = new DatabaseLogger(auth.getUid());

        ArrayList<DatabaseLogEntry> output = new ArrayList<>();
        databaseLogger.getLogs(types, output, () -> {
            printMessage("FINISHED ALL");
            output.sort(Comparator.comparing(DatabaseLogEntry::accessDate));
            for (DatabaseLogEntry e: output) {
                printMessage(e.toString());
            }
        });
    }

    void printMessage(CharSequence msg) {
        TextView text = (TextView) getLayoutInflater().inflate(R.layout.debug_message, null);

        text.setText(msg);

        debugMessages.addView(text);
    }
}
