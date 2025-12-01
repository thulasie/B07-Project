package com.example.smartAir.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartAir.AppDatabase;
import com.example.smartAir.R;
import com.example.smartAir.adapter.InventoryAdapter;
import com.example.smartAir.firebase.Sync;
import com.example.smartAir.model.InventoryItem;

import java.util.List;

public class InventoryFragment extends Fragment {
    private InventoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inventory, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rv = view.findViewById(R.id.rv_inventory);
        Button btnAdd = view.findViewById(R.id.btn_add_item);

        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new InventoryAdapter();
        rv.setAdapter(adapter);

        loadItems();

        btnAdd.setOnClickListener(v -> {
            InventoryItem it = new InventoryItem();
            it.name = "Inhaler";
            it.purchaseDate = "2025-11-01";
            it.amountLeft = 60;
            it.expiryDate = "2026-03-01";
            it.parentMarked = true;

            long id = AppDatabase.getInstance(getContext()).inventoryDao().insert(it);
            it.id = id;

            new Sync().syncInventoryItem(it);

            loadItems();
        });
    }

    private void loadItems() {
        List<InventoryItem> items = AppDatabase.getInstance(getContext()).inventoryDao().getAll();
        adapter.setItems(items);
    }
}
