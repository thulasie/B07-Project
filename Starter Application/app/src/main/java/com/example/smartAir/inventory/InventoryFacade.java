package com.example.smartAir.inventory;

import com.example.smartAir.domain.EntryAuthor;

public class InventoryFacade {

    private static String username;
    private static InventoryMarking author = InventoryMarking.NA;

    public static void setInventoryMarking(InventoryMarking author) {
        InventoryFacade.author = author;
    }

    public static void changeUserName (String username, Callback c) {
        InventoryFacade.username = username;
        InventoryDatabaseAccess.switchUser(username, c);
    }

    public static CanisterFragmentContainer generateCanisterMainPage(Callback c) {
        CanisterFragmentContainer frag =  new CanisterFragmentContainer();

        CanisterEntryFragment.switcher = frag;
        CanisterHomePage.switcher = frag;
        CanisterFragmentContainer.goBackToDashboard = c;
        CanisterFragmentContainer.marks = author;

        return frag;
    }
}
