package com.example.smartAir.databaseLog.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartAir.R;
import com.example.smartAir.databaseLog.DatabaseLogEntry;
import com.google.type.DateTime;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.VH> {
    private ArrayList<DatabaseLogEntry> logs = new ArrayList<>();
    private static final DateTimeFormatter a = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss").withZone(ZoneId.systemDefault());

    public void setItems(ArrayList<DatabaseLogEntry> items) {
        this.logs = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        DatabaseLogEntry l = logs.get(position);
        holder.setLog(l);
    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    static class VH extends RecyclerView.ViewHolder {

        TextView timestamp, description, title;

        void setLog (DatabaseLogEntry log) {
            timestamp.setText(a.format(log.accessDate().toInstant()));
            description.setText(log.data.getLogEntry());
            title.setText(log.getType().toUpperCase());

        }

        VH(@NonNull View v) {
            super(v);
            timestamp = v.findViewById(R.id.log_item_timestamp);
            description = v.findViewById(R.id.log_item_description);
            title = v.findViewById(R.id.log_item_title);
        }
    }
}
