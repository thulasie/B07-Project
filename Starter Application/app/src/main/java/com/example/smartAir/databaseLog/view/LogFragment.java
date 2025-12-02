package com.example.smartAir.databaseLog.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartAir.R;
import com.example.smartAir.databaseLog.DatabaseLogEntry;
import com.example.smartAir.databaseLog.DatabaseLogType;
import com.example.smartAir.databaseLog.DatabaseLogger;
import com.example.smartAir.motivation.BadgeAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import javax.annotation.Nullable;

public class LogFragment extends Fragment {

    private LogAdapter adapter;
    private String username;
    private Date startDate, endDate;
    private HashSet<DatabaseLogType> wanted;

    public static LogFragment makeLogFragment(HashSet<DatabaseLogType> wanted, Date startDate, Date endDate, String username) {
        LogFragment frag = new LogFragment();
        frag.wanted = wanted;
        frag.startDate = startDate;
        frag.endDate = endDate;
        frag.username = username;
        return frag;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.log_spinner_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rv = view.findViewById(R.id.logs_spinner);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LogAdapter();
        rv.setAdapter(adapter);

        loadLogs();
    }

    private void loadLogs() {
        ArrayList<DatabaseLogEntry> buf = new ArrayList<>();

        DatabaseLogger logger = new DatabaseLogger(username);
        logger.getLogs(wanted, buf, ()-> {
            adapter.setItems(buf);
        }, startDate, endDate);
    }
}
