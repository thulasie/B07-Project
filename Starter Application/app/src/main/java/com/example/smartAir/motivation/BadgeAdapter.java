package com.example.smartAir.motivation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartAir.R;

import java.util.ArrayList;
import java.util.List;

public class BadgeAdapter extends RecyclerView.Adapter<BadgeAdapter.VH> {
    private List<Badge> items = new ArrayList<>();

    public void setItems(List<Badge> items) { this.items = items; notifyDataSetChanged(); }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_badge, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Badge b = items.get(position);
        holder.tvTitle.setText(b.title + (b.earned ? " ✅" : ""));
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvTitle;
        VH(@NonNull View v) { super(v); tvTitle = v.findViewById(R.id.tv_badge_title); }
    }
}
