package com.example.smartAir.inventory;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartAir.R;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class CanisterEntryFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    public interface FragmentSwitcher {
        void goCanisterHome();
    }

    static FragmentSwitcher switcher;
    private Canister can;
    private int amountLeft = 0;
    private int totalAmount = 0;
    private Date selectedDateOfPurchase = new Date();
    private Date selectedDateOfExpiry = new Date();
    private int selection = 0;
    private InventoryMarking marking = InventoryMarking.NA;


    public void setCan(Canister can) {
        this.can = can;
        this.selectedDateOfExpiry = new Date(can.expiryDate);
        this.selectedDateOfPurchase = new Date(can.purchaseDate);
        this.amountLeft = can.amountLeft;
        this.marking = can.whoLastMarked;
        this.totalAmount = can.fullAmount;
    }
    public void setMarking(InventoryMarking im) {
        this.marking = im;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.canister_entry, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText amountLeft = view.findViewById(R.id.canister_amount);
        EditText fullAmount = view.findViewById(R.id.canister_total_amount);
        Button btnAdd = view.findViewById(R.id.canister_add_item);
        Button btnChangePurchase = view.findViewById(R.id.canister_purchase_date_button);
        Button btnChangeExpiry = view.findViewById(R.id.canister_expiry_date_button);

        amountLeft.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {}

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.isEmpty()) {
                    CanisterEntryFragment.this.amountLeft = Integer.parseInt(s.toString());
                }
            }
        });
        amountLeft.setText(String.valueOf(this.amountLeft));
        fullAmount.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {}
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.isEmpty()) {
                    CanisterEntryFragment.this.totalAmount = Integer.parseInt(s.toString());
                }
            }
        });
        fullAmount.setText(String.valueOf(this.totalAmount));


        btnChangePurchase.setOnClickListener((v) -> {
            selection = 0;
            showDateDialog();
        });
        btnChangeExpiry.setOnClickListener((v) -> {
            selection = 1;
            showDateDialog();
        });

        btnAdd.setOnClickListener(v -> {
            can.purchaseDate = selectedDateOfPurchase.getTime();
            can.amountLeft = this.amountLeft;
            can.expiryDate = selectedDateOfExpiry.getTime();
            can.whoLastMarked = marking;
            can.fullAmount = totalAmount;

            InventoryDatabaseAccess.putItem(can);

            switcher.goCanisterHome();
        });

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault());

        TextView tvPD = requireView().findViewById(R.id.canister_purchase_date_picker_text);
        tvPD.setText(formatter.format(new Date(can.purchaseDate).toInstant()));
        TextView tvED = requireView().findViewById(R.id.canister_expiry_date_picker_text);
        tvED.setText(formatter.format(new Date(can.expiryDate).toInstant()));

        TextView title = requireView().findViewById(R.id.canister_name);
        title.setText(can.getName() + " Inhaler");
    }

    private void showDateDialog() {
        DateDialog d = new DateDialog();
        d.setOnDateSetListener(this);
        d.show(getChildFragmentManager(), "canister_entry");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)  {

        // Create a Calendar instance

        Calendar cal = Calendar.getInstance();

        // Set static variables of Calendar instance

        cal.set(Calendar.YEAR, year);

        cal.set(Calendar.MONTH, month);

        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault());

        if (selection == 0) {
            selectedDateOfPurchase = cal.getTime();
            TextView tv = requireView().findViewById(R.id.canister_purchase_date_picker_text);
            tv.setText(formatter.format(cal.toInstant()));
        } else {
            selectedDateOfExpiry = cal.getTime();
            TextView tv = requireView().findViewById(R.id.canister_expiry_date_picker_text);
            tv.setText(formatter.format(cal.toInstant()));
        }
    }
}
