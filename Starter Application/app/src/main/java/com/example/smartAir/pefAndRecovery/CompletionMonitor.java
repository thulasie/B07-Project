package com.example.smartAir.pefAndRecovery;

public class CompletionMonitor {
    public interface Callback {
        void onEnd();
    }

    private int count = 0;
    private int required;
    private Callback callback;

    CompletionMonitor(int required, Callback callback) {
        this.required = required;
        this.callback = callback;
    }

    CompletionNotifier getNotifier() {
        return this::addCount;
    }

    void addCount() {
        if (++count == required) {
            callback.onEnd();
        }
    }
}
