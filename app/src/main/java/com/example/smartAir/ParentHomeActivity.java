package com.example.smartAir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ParentHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_parent_home);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null){      // if the user is not logged in, we are redirected to the login page
            startActivity(new Intent(this, LoginPage.class));   // this is a PLACEHOLDER for the Login page, replace with real LoginPage
            // we should open the fragment?

            finish();   // user is not logged in so we cannot go back (destroys)
            return;
        }

        final Button logoutButton = findViewById(R.id.logout_id);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v){
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ParentHomeActivity.this, LoginPage.class));
                finish();   // can't go back to the original page


            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}




