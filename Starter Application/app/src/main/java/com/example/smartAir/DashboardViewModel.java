package com.example.smartAir;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<String> todayZone = new MutableLiveData<>();
    private final MutableLiveData<String> lastRescue = new MutableLiveData<>();
    private final MutableLiveData<Integer> weeklyCount = new MutableLiveData<>();
    private final MutableLiveData<String> trendSummary = new MutableLiveData<>();

    private final R6Repository repository = new R6Repository();

    public DashboardViewModel() {
        loadData();
    }

    private void loadData() {
        todayZone.setValue(repository.getTodayZone());
        lastRescue.setValue(repository.getLastRescueTime());
        weeklyCount.setValue(repository.getWeeklyRescueCount());
        trendSummary.setValue(repository.getTrendSummary());
    }

    public LiveData<String> getTodayZone() { return todayZone; }
    public LiveData<String> getLastRescueTime() { return lastRescue; }
    public LiveData<Integer> getWeeklyRescueCount() { return weeklyCount; }
    public LiveData<String> getTrendSummary() { return trendSummary; }

    public void generateProviderReport(Context context) {
        ProviderReportGenerator.generate(context, repository);
    }
}
