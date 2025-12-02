package com.example.smartAir.userinfo;

import androidx.fragment.app.Fragment;

public abstract class UserDashboardFactory {
    FragmentLoader loader;

    protected abstract Fragment makeUserDashboard();
    public Fragment getUserDashboard () {
        return makeUserDashboard();
    }
}

class ChildDashboardFactory extends UserDashboardFactory {
    protected Fragment makeUserDashboard() {
        HomeFragmentChild frag = new HomeFragmentChild();
        frag.provider = () -> {
            loader.load(makeUserDashboard());
        };

        return frag;
    }
}



