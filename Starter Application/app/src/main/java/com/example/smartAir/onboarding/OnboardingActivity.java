package com.example.smartAir.onboarding;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.smartAir.R;
import com.example.smartAir.domain.UserRole;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class OnboardingActivity extends AppCompatActivity {
    public OnboardingActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.onboarding_container);
        ViewPager2 onboarding = (ViewPager2) findViewById(R.id.onboarding_pager);
        Object a = null;
        if (savedInstance != null) {
            a = savedInstance.get("role"); // Replace with however the roles are
        }
        OnboardingContent c = new TestOnboarding();
        if (a instanceof UserRole) {

            if (a == UserRole.CHILD) { // child
                c = new ChildOnboarding();
            } else if (a == UserRole.PARENT) { // parent
                c = new ParentOnboarding();
            } else if (a == UserRole.PROVIDER) { // provider
                c = new ProviderOnboarding();
            }
        }


        // Some code to switch onboarding style based on instance...
        OnboardingCollectionAdapter adapter = new OnboardingCollectionAdapter(this, c);
        onboarding.setAdapter(adapter);

        // Connect to pagination dots
        TabLayout pager_dots = findViewById(R.id.onboarding_pager_dots);
        new TabLayoutMediator(pager_dots, onboarding,
                (tab, position) -> {
                }).attach();
    }

}



