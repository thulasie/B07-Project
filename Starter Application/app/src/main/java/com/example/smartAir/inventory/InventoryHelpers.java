package com.example.smartAir.inventory;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class InventoryHelpers {

    private static Date getTodaysMidnight() {
        return convertToMidnight(new Date());
    }

    static Date convertToMidnight(Date d) {
        Calendar c = new GregorianCalendar();
        c.setTime(new Date());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        return c.getTime();
    }
}
