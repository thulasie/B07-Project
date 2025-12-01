package com.example.smartAir;

import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class DashboardFragment extends Fragment {

    private TextView todayZoneText, lastRescueText, weeklyCountText, trendSummaryText;
    private Button generateReportButton;
    private DashboardViewModel viewModel;

    public DashboardFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_parent_dashboard, container, false);

        todayZoneText = view.findViewById(R.id.textTodayZone);
        lastRescueText = view.findViewById(R.id.textLastRescue);
        weeklyCountText = view.findViewById(R.id.textWeeklyRescue);
        trendSummaryText = view.findViewById(R.id.textTrendSummary);
        generateReportButton = view.findViewById(R.id.buttonGenerateReport);

        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        observeLiveData();

        generateReportButton.setOnClickListener(v -> {
            if (getContext() != null) {
                viewModel.generateProviderReport(getContext());
            }
        });

        return view;
    }

    private void observeLiveData() {
        viewModel.getTodayZone().observe(getViewLifecycleOwner(),
                zone -> todayZoneText.setText("Today's Zone: " + zone));

        viewModel.getLastRescueTime().observe(getViewLifecycleOwner(),
                time -> lastRescueText.setText("Last Rescue Time: " + time));

        viewModel.getWeeklyRescueCount().observe(getViewLifecycleOwner(),
                count -> weeklyCountText.setText("Weekly Rescue Count: " + count));

        viewModel.getTrendSummary().observe(getViewLifecycleOwner(),
                summary -> trendSummaryText.setText("Trend Summary: " + summary));
    }
}
