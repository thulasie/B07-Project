package com.example.smartAir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class TempParentHomeFragment extends Fragment {
    private Button info, addChild, modifyChild;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.temp_parent_home, container, false);

        info = view.findViewById(R.id.parentInfoButton);
        addChild = view.findViewById(R.id.addButton);
        modifyChild = view.findViewById(R.id.modifyButton);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {loadFragment(new InformationSummaryParentFragment());}
        });
        addChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {loadFragment(new AddChildFragment());}
        });
        modifyChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {loadFragment(new ModifyChildFragment());}
        });
        return view;
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
