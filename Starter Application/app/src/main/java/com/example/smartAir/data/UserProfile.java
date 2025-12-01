package com.example.smartAir.data;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.example.smartAir.domain.UserRole;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


@Keep
public abstract class UserProfile implements UserProfileData {
    protected static UserProfile profile;
    protected String username;
    protected UserRole userRole;

    public static UserProfile getProfileSingleton () {
        return profile;
    }

    public static void initializeEmailProfile (EmailProfileCallback a) {
        profile = new EmailProfile(a);
    }

    public static void initializeUsernameProfile (String username) { // Is instant
        profile = new ChildProfile(username);
    }

    public abstract String getUserSlug();

    public String getUsername() {
        return username;
    }

    public UserRole getRole() {
        return userRole;
    }

    public abstract void signOut();
}

class ChildProfile extends UserProfile {
    public ChildProfile(String username) {
        this.username = username;
    }

    public String getUserSlug () {
        return "user:" + username;
    }

    @Override
    public void signOut() {

    }

}

class EmailProfile extends UserProfile {
    private final FirebaseAuth auth = FirebaseAuth.getInstance();


    public EmailProfile(EmailProfileCallback a) {
        super();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        assert auth.getCurrentUser() != null;
        this.username = auth.getCurrentUser().getDisplayName();

        db.getReference("users/" + auth.getUid()).child("role").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    userRole = UserRole.extract((String) Objects.requireNonNull(task.getResult().getValue()));
                    a.onComplete();
                } else {
                    System.out.println("unsuccessful");
                }
            }
        });
    }

    @Override
    public String getUserSlug() {
        return auth.getUid();
    }

    @Override
    public void signOut() {
        auth.signOut();
    }
}

