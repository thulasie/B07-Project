package com.example.smartAir.inventory;

public class InventoryFacade {

    private static String username;

    public static void changeUserName (String username, Callback c) {
        InventoryFacade.username = username;
        InventoryDatabaseAccess.switchUser(username, c);
    }

    public static CanisterFragmentContainer generateCanisterMainPage() {
        CanisterFragmentContainer frag =  new CanisterFragmentContainer();

        CanisterEntryFragment.switcher = frag;
        CanisterMainPage.switcher = frag;

        return frag;
    }
}
