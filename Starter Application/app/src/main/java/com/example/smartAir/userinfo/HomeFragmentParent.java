package com.example.smartAir.userinfo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.smartAir.userinfo.addchildren.AddChildrenFragment;
import com.example.smartAir.R;
import com.example.smartAir.alerting.AlertMonitor;

public class HomeFragmentParent extends Fragment implements AlertMonitor.Alerter {

    FragmentLoader loader;
    int selectedChild;

    public HomeFragmentParent () {
        super(R.layout.home_fragment_parent);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.loader = new FragmentLoader() {
            final FragmentManager manager = HomeFragmentParent.this.getParentFragmentManager();
            @Override
            public void load(Fragment f) {
                FragmentTransaction t = manager.beginTransaction();
                t.replace(R.id.fragment_container, f);
                t.addToBackStack(null);
                t.commit();
            }
        };

        Button parentViewProfile = view.findViewById(R.id.parent_view_profile);
        Button parentViewDashboard = view.findViewById(R.id.parent_view_dashboard);
        Button parentViewLogs = view.findViewById(R.id.parent_view_logs);
        Button parentAddChild = view.findViewById(R.id.parent_add_child);
        Button parentManageProvider = view.findViewById(R.id.parent_manage_provider);
        Button parentSignOutButton = view.findViewById(R.id.parent_signout);

        Spinner spinner = view.findViewById(R.id.parent_child_choice_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, UserBasicInfo.getChildren());

        parentViewProfile.setOnClickListener((v) -> {
            String username = UserBasicInfo.getChildren().get(spinner.getSelectedItemPosition());
            goToChildProfile(username);
        });

        parentAddChild.setOnClickListener((v) -> navigateToAddChildFragment());

        parentSignOutButton.setOnClickListener((v) -> signOut());

        spinner.setAdapter(adapter);

        AlertMonitor.registerAlerter(this);
        AlertMonitor.setChildren(UserBasicInfo.getChildren());

        parentManageProvider.setOnClickListener((v) ->
                editProviderSettings(UserBasicInfo
                        .getChildren()
                        .get(spinner.getSelectedItemPosition()))
        );

        parentViewLogs.setOnClickListener((v) ->
                goToLogs(UserBasicInfo
                        .getChildren()
                        .get(spinner.getSelectedItemPosition()))
        );
    }

    public void showAlert(String user, String msg) {
        Toast toast = Toast.makeText(getContext(), user + ": " + msg, Toast.LENGTH_LONG);
        toast.show();
    }

    private void navigateToAddChildFragment() {
        loader.load(new AddChildrenFragment());
    }

    private void goToChildProfile(String username) {
        ParentChildView frag = new ParentChildView();
        frag.setChildUsername(username);
        loader.load(frag);
    }

    private void signOut() {
        UserBasicInfo.logOut();
        loader.load(UserBasicInfo.getHomeFragment());
    }

    private void editProviderSettings(String selected) {
        ProviderSettingsFragment frag = new ProviderSettingsFragment();
        frag.setName(selected);
        frag.setProvider(() -> loader.load(UserBasicInfo.getHomeFragment()));
        loader.load(frag);
    }

    private void goToLogs(String selected) {
        LogsView frag = new LogsView();
        frag.setUsername(selected);
        loader.load(frag);
    }
}
