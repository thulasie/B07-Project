package com.example.smartAir;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartAir.alerting.AlertMonitor;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class DebugTestDriver extends Fragment implements AlertMonitor.Alerter {

    LinearLayout debugMessages;

    public DebugTestDriver () {
        super(R.layout.debug_screen);
    }

    // This functions as a main method...
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        debugMessages = view.findViewById(R.id.debug_messages);

        AlertMonitor.registerAlerter(this);
        ArrayList<String> children = new ArrayList<>();
        children.add("testUsername");
        AlertMonitor.setChildren(children);
    }

    void printMessage(CharSequence msg) {
        TextView text = (TextView) getLayoutInflater().inflate(R.layout.debug_message, null);

        text.setText(msg);

        debugMessages.addView(text);
    }

    public void showAlert(String user, String msg) {
        Toast toast = Toast.makeText(getContext(), user + ": " + msg, Toast.LENGTH_LONG);
        toast.show();
        printMessage(msg);
    }
}
