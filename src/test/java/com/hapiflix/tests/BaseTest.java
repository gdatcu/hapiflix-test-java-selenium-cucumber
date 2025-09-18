package com.hapiflix.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Clasa de bază pentru toate testele.
 * Responsabilitatea ei este să asigure că un browser este pornit
 * înainte de fiecare test și închis după, folosind DriverManager.
 */
public class BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        // Cere manager-ului să creeze o instanță de driver izolată pentru acest thread
        DriverManager.setupDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        // Cere manager-ului să închidă instanța de driver pentru acest thread
        DriverManager.tearDownDriver();
    }
}