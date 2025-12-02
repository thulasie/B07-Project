package com.example.smartAir.inventory;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartAir.R;

public class CanisterMainPage extends Fragment {

    public interface FragmentSwitcher {
        void goHome();
        void goEditCanister(Canister c);
    }

    static FragmentSwitcher switcher;

    public CanisterMainPage() {
        super(R.layout.canister_home_page);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Canister controller = Canister.getController();
        Canister rescue = Canister.getRescue();

        TextView controllerDet = view.findViewById(R.id.controller_canister_details);
        Button controllerBut = view.findViewById(R.id.controller_canister_button);
        TextView rescueDet = view.findViewById(R.id.rescue_canister_details);
        Button rescueBut = view.findViewById(R.id.rescue_canister_button);

        if (controller != null) {
            controllerDet.setText(controller.getSummary());
            controllerBut.setText("Edit info");
        }

        controllerBut.setOnClickListener((v) -> {
            if (controller == null) {
                Canister.initializeController();
            }
            switcher.goEditCanister(Canister.getController());
        });

        if (rescue != null) {
            rescueDet.setText(rescue.getSummary());
            rescueBut.setText("Edit info");
        }

        rescueBut.setOnClickListener((v) -> {
            if (controller == null) {
                Canister.initializeRescue();
            }
            switcher.goEditCanister(Canister.getRescue());
        });

    }
}
