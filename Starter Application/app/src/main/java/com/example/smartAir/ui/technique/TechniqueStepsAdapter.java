package com.example.smartAir.ui.technique;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartAir.R;

import java.util.List;

public class TechniqueStepsAdapter extends RecyclerView.Adapter<TechniqueStepsAdapter.VH> {
    private final List<String> steps;

    public TechniqueStepsAdapter(List<String> steps) { this.steps = steps; }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_technique_step, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.tv.setText(steps.get(position));
    }

    @Override
    public int getItemCount() { return steps.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tv;
        VH(@NonNull View v) { super(v); tv = v.findViewById(R.id.tv_step); }
    }
}
