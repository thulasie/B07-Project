package com.example.smartAir;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

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

        // Some code to switch onboarding style based on instance...
        OnboardingCollectionAdapter adapter = new OnboardingCollectionAdapter(this, new TestOnboarding());
        onboarding.setAdapter(adapter);

        // Connect to pagination dots
        TabLayout pager_dots = (TabLayout) findViewById(R.id.onboarding_pager_dots);
        new TabLayoutMediator(pager_dots, onboarding,
                (tab, position) -> {}).attach();

    }

}



