package com.hapiflix.tests;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.io.File;
import java.io.IOException;

public class DriverManager {
    // 1. Creeam o variabila ThreadLocal care ne va stoca instantele de WebDriver

    private static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    // 2. Metoda care ne initializeaza driver-ul pentru thread-ul curent

    public static void setupDriver() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        // Setam instanta de driver specifica pentru acest thread!
        driverThread.set(driver);
    }

    // 3. Metoda care inchide si curata driver

    public static void tearDownDriver() {
        if (driverThread.get() != null) {
            driverThread.get().quit(); // Inchide browser-ul
            driverThread.remove(); // Elibereaza memoria pentru acest thread
        }
    }

    // 4. Metoda prin care testele vor putea accesa instanta de driver corect
    public static WebDriver getDriver() {
        return driverThread.get();
    }

    public static String takeScreenshot(String testName) {
        // Obține instanța de driver corectă pentru acest thread
        WebDriver driver = getDriver();

        // Convertim driver-ul la TakesScreenshot
        TakesScreenshot ts = (TakesScreenshot) driver;

        // Facem screenshot-ul ca fișier temporar
        File source = ts.getScreenshotAs(OutputType.FILE);

        // Definim calea de destinație
        String destinationPath = System.getProperty("user.dir") + "/screenshots/" + testName + "_" + System.currentTimeMillis() + ".png";
        File destination = new File(destinationPath);

        try {
            // Copiem fișierul din locația temporară în destinația finală
            FileUtils.copyFile(source, destination);
        } catch (IOException e) {
            System.err.println("A apărut o eroare la salvarea screenshot-ului: " + e.getMessage());
        }

        // Returnăm calea pentru a o putea adăuga în raport
        return destinationPath;
    }
}
