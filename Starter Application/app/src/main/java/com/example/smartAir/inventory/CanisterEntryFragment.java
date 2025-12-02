package com.example.smartAir.inventory;

import static com.example.smartAir.inventory.InventoryHelpers.convertToMidnight;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartAir.R;

import java.util.Date;

public class CanisterEntryFragment extends Fragment {

    public interface FragmentSwitcher {
        void goHome();
    }

    static FragmentSwitcher switcher;
    private Canister can;

    public void setCan(Canister can) {
        this.can = can;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.canister_entry, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnAdd = view.findViewById(R.id.canister_add_item);

        btnAdd.setOnClickListener(v -> {
            can.purchaseDate = convertToMidnight(new Date()).getTime();
            can.amountLeft = 60;
            can.expiryDate = convertToMidnight(new Date()).getTime();
            can.whoLastMarked = InventoryMarking.NA; // TODO add bindings here

            InventoryDatabaseAccess.putItem(can);

            switcher.goHome();
        });
    }


}
