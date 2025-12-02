package com.example.smartAir.motivation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.smartAir.AppDatabase;
import com.example.smartAir.R;

import java.util.ArrayList;
import java.util.List;

public class MotivationFragment extends Fragment {
    private BadgeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_motivation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rv = view.findViewById(R.id.rv_badges);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BadgeAdapter();
        rv.setAdapter(adapter);

        loadBadges();
    }

    private void loadBadges() {
        List<Badge> badges = new ArrayList<>();
        // = AppDatabase.getInstance(getContext()).badgeDao().getAll(); // TODO replace badge loader
        adapter.setItems(badges);
    }
}
