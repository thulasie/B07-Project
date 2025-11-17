package com.example.b07demosummer2024.data;

import androidx.annotation.Keep;
import androidx.annotation.Nullable;

import com.example.b07demosummer2024.domain.UserRole;

@Keep
public class UserProfile {
    public String uid;
    public String displayName;
    public String role;

    public UserProfile() {} 

    public UserProfile(String uid, String displayName, UserRole roleEnum) {
        this.uid = uid;
        this.displayName = displayName;
        this.role = roleEnum.name();
    }

    @Nullable
    public UserRole roleEnum() {
        try { return UserRole.valueOf(role); }
        catch (Exception e) { return null; }
    }
}
