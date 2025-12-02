package com.example.smartAir.ui.parent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.smartAir.R;
import com.example.smartAir.data.R6Repository;
import com.example.smartAir.logic.AdherenceCalculator;
import com.example.smartAir.logic.R6AlertManager;
import com.example.smartAir.R6model.ControllerDoseEvent;
import com.example.smartAir.R6model.RescueEvent;
import com.example.smartAir.R6model.SymptomLog;
import com.example.smartAir.report.ProviderReportPdfUtil;
import com.example.smartAir.ui.charts.TrendChartView;
import com.google.android.gms.tasks.Tasks;

import java.io.File;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;

public class ParentDashboardFragment extends Fragment {

    private TextView textTodayZone, textLastRescue, textWeeklyRescue;
    private TrendChartView trendChart;
    private Button buttonToggleTrend, buttonExportPdf;

    private boolean show30Days = false;
    private R6Repository repo;

    private final String childId = "demoChildId"; // TODO real child id
    private final String childName = "Child";

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        // FIX 1: Use correct layout
        View v = inflater.inflate(R.layout.fragment_parent_dashboard, container, false);

        // FIX 2: Use correct IDs
        textTodayZone = v.findViewById(R.id.textTodayZone);
        textLastRescue = v.findViewById(R.id.textLastRescue);
        textWeeklyRescue = v.findViewById(R.id.textWeeklyRescue);
        trendChart = v.findViewById(R.id.trendChart);
        buttonToggleTrend = v.findViewById(R.id.buttonToggleTrend);
        buttonExportPdf = v.findViewById(R.id.buttonExportPdf);

        // FIX 3: No ViewModel used
        repo = new R6Repository(childId);

        buttonToggleTrend.setOnClickListener(view -> {
            show30Days = !show30Days;
            buttonToggleTrend.setText(show30Days ? "Show 7 days" : "Show 30 days");
            loadDashboard();
        });

        buttonExportPdf.setOnClickListener(view -> exportPdf());

        loadDashboard();
        return v;
    }

    private void loadDashboard() {
        Executors.newSingleThreadExecutor().execute(() -> {
            long now = System.currentTimeMillis();

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(now);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            long todayStart = cal.getTimeInMillis();

            long daysWindow = show30Days ? 30 : 7;
            long windowStart = todayStart - (daysWindow - 1) * 24L * 60L * 60L * 1000L;
            long weekStart = todayStart - 6 * 24L * 60L * 60L * 1000L;

            try {
                List<RescueEvent> rescues =
                        Tasks.await(repo.getRescueEvents(windowStart, now));
                Collections.sort(rescues, Comparator.comparingLong(RescueEvent::getTimestampMillis));

                int[] dailyCounts = new int[(int) daysWindow];
                int weeklyRescues = 0;
                long lastRescueTime = -1;
                int rescuesToday = 0;

                for (RescueEvent e : rescues) {
                    long t = e.getTimestampMillis();
                    if (t >= weekStart) weeklyRescues++;
                    if (t > lastRescueTime) lastRescueTime = t;
                    if (t >= todayStart) rescuesToday++;

                    int idx = (int) ((t - windowStart) / (24L * 60L * 60L * 1000L));
                    if (idx >= 0 && idx < dailyCounts.length) dailyCounts[idx]++;
                }

                int symptomToday = 0;

                String zone = AdherenceCalculator.calculateTodayZone(rescuesToday, symptomToday);

                if (getContext() != null) {
                    R6AlertManager.checkRedZoneDay(getContext(), rescuesToday);
                    R6AlertManager.checkRapidRescues(getContext(), rescues);
                }

                int finalWeeklyRescues = weeklyRescues;
                long finalLastRescueTime = lastRescueTime;

                requireActivity().runOnUiThread(() -> {
                    textTodayZone.setText(zone);
                    textWeeklyRescue.setText(String.valueOf(finalWeeklyRescues));
                    if (finalLastRescueTime > 0) {
                        textLastRescue.setText(
                                android.text.format.DateFormat.format(
                                        "MMM d, h:mm a", finalLastRescueTime));
                    } else {
                        textLastRescue.setText("—");
                    }
                    trendChart.setData(dailyCounts);
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void exportPdf() {
        Executors.newSingleThreadExecutor().execute(() -> {

            try {
                long to = repo.now();
                long from = repo.getStartMillisMonthsAgo(3);

                List<RescueEvent> rescues =
                        Tasks.await(repo.getRescueEvents(from, to));
                List<ControllerDoseEvent> doses =
                        Tasks.await(repo.getControllerDoses(from, to));
                List<SymptomLog> symptoms =
                        Tasks.await(repo.getSymptomLogs(from, to));

                int expectedDosesPerDay = 2;

                double adherence = AdherenceCalculator.calculateAdherencePercent(
                        doses, expectedDosesPerDay, from, to);

                int totalRescue = rescues.size();
                int problemDays = symptoms.size();
                int[] zoneDistribution = new int[]{120, 5, 2};

                // FIX: this call MUST be inside the try/catch
                File pdf = ProviderReportPdfUtil.generateReport(
                        requireContext(),
                        childName,
                        "Last 3 months",
                        adherence,
                        totalRescue,
                        problemDays,
                        zoneDistribution
                );

                Uri uri = FileProvider.getUriForFile(
                        requireContext(),
                        requireContext().getPackageName() + ".fileprovider",
                        pdf
                );

                Intent send = new Intent(Intent.ACTION_SEND);
                send.setType("application/pdf");
                send.putExtra(Intent.EXTRA_STREAM, uri);
                send.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                startActivity(Intent.createChooser(send, "Share provider report"));

            } catch (Exception e) {   // This catches IOException + Task exceptions
                e.printStackTrace();
            }

        });
    }
}