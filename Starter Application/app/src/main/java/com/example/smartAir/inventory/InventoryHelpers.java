package com.example.smartAir.inventory;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class InventoryHelpers {

    static Date convertToMidnight(Date d) {
        Calendar c = new GregorianCalendar();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTime();
    }
}
