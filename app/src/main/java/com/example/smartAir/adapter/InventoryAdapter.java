package com.example.smartAir.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartAir.R;
import com.example.smartAir.model.InventoryItem;

import java.util.ArrayList;
import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.VH> {
    private List<InventoryItem> items = new ArrayList<>();

    public void setItems(List<InventoryItem> items) { this.items = items; notifyDataSetChanged(); }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inventory, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        InventoryItem it = items.get(position);
        holder.tvName.setText(it.name);
        holder.tvAmount.setText(String.valueOf(it.amountLeft));
        holder.tvExpiry.setText(it.expiryDate);
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvAmount, tvExpiry;
        VH(@NonNull View v) {
            super(v);
            tvName = v.findViewById(R.id.tv_item_name);
            tvAmount = v.findViewById(R.id.tv_amount_left);
            tvExpiry = v.findViewById(R.id.tv_expiry_date);
        }
    }
}
