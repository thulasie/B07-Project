package com.example.smartAir.data;

import androidx.annotation.Keep;

import com.example.smartAir.domain.UserRole;

@Keep
public abstract class UserProfile {
    private static UserProfile profile;
    public static UserProfile getProfileSingleton () {
        return profile;
    }

    public static void initializeEmailProfile (EmailProfileCallback a) {
        profile = new EmailProfile(a);
    }

    public static void initializeUsernameProfile (String username) { // Is instant
        profile = new ChildProfile(username);
    }

    public abstract UserRole getRole();

    public abstract String getUserSlug();

    public abstract String getUsername();
}

class ChildProfile extends UserProfile {
    private String username;

    public ChildProfile(String username) {
        this.username = username;
    }

    public UserRole getRole() {
        return UserRole.CHILD;
    }

    public String getUserSlug () {
        return "user:" + username;
    }

    @Override
    public String getUsername() {
        return username;
    }
}

