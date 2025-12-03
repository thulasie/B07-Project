package com.example.smartAir.userinfo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import com.example.smartAir.R;
import com.example.smartAir.databaseLog.DatabaseLogType;
import com.example.smartAir.databaseLog.view.LogFragment;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

public class LogsView extends Fragment {
    public LogsView() {
        super(R.layout.logs_fragment_container);
    }

    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button b =view.findViewById(R.id.logs_exit_button);
        b.setOnClickListener((v) -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, UserBasicInfo.getHomeFragment())
                    .commit();
        });

        HashSet<DatabaseLogType> hashSet = new HashSet<>(Arrays.asList(DatabaseLogType.values()));

        LogFragment frag = LogFragment.makeLogFragment(hashSet,
                new Date(Instant.now().minusSeconds(24 * 60 * 60).toEpochMilli()),
                new Date(),
                username);
        setFragment(frag);
    }

    public void setFragment (Fragment fragment) {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.logs_container, fragment)
                .commit();
    }
}