package com.openclassrooms.realestatemanager;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilsTest {

    @Test
    public void convertEuroToDollarTest() {
       assertEquals(1111,Utils.convertEuroToDollar(1000));
    }

    @Test
    public void convertDollarToEuroTest() {
        assertEquals(812,Utils.convertDollarToEuro(1000));
    }

    @Test
    public void getTodayDateTest() {
        String actualDate = Utils.getTodayDate();
        String newDate = "31/05/2020";
        assertEquals(newDate, actualDate);
    }
}
