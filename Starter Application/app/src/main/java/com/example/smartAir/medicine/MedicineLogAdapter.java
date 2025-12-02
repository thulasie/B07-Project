package com.example.smartAir.medicine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartAir.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MedicineLogAdapter extends RecyclerView.Adapter<MedicineLogAdapter.VH> {

    private List<MedicineLog> items = new ArrayList<>();
    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

    public void setItems(List<MedicineLog> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medicine_log, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        MedicineLog m = items.get(position);
        holder.tvType.setText(m.type + ": " + m.rating);
        holder.tvDose.setText(String.valueOf(m.dose));
        holder.tvTime.setText(df.format(new Date(m.timestamp)));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvType, tvDose, tvTime;

        VH(@NonNull View v) {
            super(v);
            tvType = v.findViewById(R.id.tv_type);
            tvDose = v.findViewById(R.id.tv_dose);
            tvTime = v.findViewById(R.id.tv_time);
        }
    }
}
