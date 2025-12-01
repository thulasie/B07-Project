package com.example.smartAir.data;

import com.example.smartAir.domain.UserRole;

///  It's okay if this gets deleted just refactor it with
/// a class equivalent in functionality
/// 

public interface UserProfileData {
    String getUserSlug();

    String getUsername();

    UserRole getRole();

    void signOut();
}
