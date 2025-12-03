package com.example.smartAir.userinfo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartAir.R;

public class ProviderSettingsFragment extends Fragment {

    private String name;
    private GoBackProvider provider;

    public ProviderSettingsFragment() {
        super(R.layout.provider_settings);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProvider(GoBackProvider provider) {
        this.provider = provider;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button b = view.findViewById(R.id.buttonUpdateChild);

        TextView text = view.findViewById(R.id.editChildName);

        b.setOnClickListener((v)->{
            provider.goBack();
        });

        text.setText(name);
    }
}
