package com.hapiflix.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentReportListener implements ITestListener {
    private ExtentReports extent;
    private ExtentTest test;
    // Se execută o singură dată, la începutul suitei de teste
    @Override
    public void onStart(ITestContext context) {
        ExtentSparkReporter spark = new ExtentSparkReporter("extent-report.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    // Se execută înainte de fiecare metodă @Test
    @Override
    public void onTestStart(ITestResult result) {
        // Creăm o intrare în raport pentru testul curent
        test = extent.createTest(result.getMethod().getDescription());
    }

    // Se execută când un test trece cu succes
    @Override
    public void onTestSuccess(ITestResult result) {
        test.pass("Testul a trecut cu succes!");
    }

    // Se execută când un test eșuează
    // În interiorul clasei ExtentReportListener.java
    @Override
    public void onTestFailure(ITestResult result) {
        test.fail("Testul a eșuat!");
        test.fail(result.getThrowable());

        // Logica pentru a prelua instanța driver-ului din clasa de test
        try {
            // Numele metodei de test devine numele fișierului
//            String screenshotPath = ((BaseTest) result.getInstance()).takeScreenshot(result.getMethod().getMethodName());
            String screenshotPath = DriverManager.takeScreenshot(result.getMethod().getMethodName());

            // Atașăm screenshot-ul la raport
            test.addScreenCaptureFromPath(screenshotPath);
        } catch (Exception e) {
            e.printStackTrace();
            test.fail("Nu s-a putut realiza captura de ecran: " + e.getMessage());
        }
    }


    // Se execută când un test este sărit (skipped)
    @Override
    public void onTestSkipped(ITestResult result) {
        test.skip("Testul a fost sărit.");
    }

    // Se execută o singură dată, la finalul suitei de teste
    @Override
    public void onFinish(ITestContext context) {
        // Scrie toate informațiile colectate în fișierul HTML
        extent.flush();
    }
}

