package com.example.smartAir.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.util.function.Consumer;

public class AuthProfileRepo {

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final DatabaseReference profilesRef =
            FirebaseDatabase.getInstance().getReference("userProfiles");

    @Nullable
    public FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }

    public void signOut() {
        auth.signOut();
    }

    /** 读取当前登录者的 UserProfile（userProfiles/{uid}） */
    public void getCurrentUserProfile(@NonNull Consumer<UserProfile> onOk,
                                      @NonNull Consumer<String> onErr) {
        FirebaseUser u = auth.getCurrentUser();
        if (u == null) { onErr.accept("Not signed in"); return; }

        profilesRef.child(u.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override public void onDataChange(@NonNull DataSnapshot snap) {
                        UserProfile p = snap.getValue(UserProfile.class);
                        if (p == null || p.roleEnum() == null) {
                            onErr.accept("Profile/role missing");
                        } else {
                            onOk.accept(p);
                        }
                    }
                    @Override public void onCancelled(@NonNull DatabaseError e) {
                        onErr.accept(e.getMessage());
                    }
                });
    }
}

