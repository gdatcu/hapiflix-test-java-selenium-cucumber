package com.hapiflix.tests;

import com.hapiflix.utils.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

public class DataDrivenLoginTests extends BaseTest {

    // Acest @DataProvider va furniza datele de test
    @DataProvider(name = "loginCredentials", parallel = true) // Adăugăm parallel=true pentru a rula seturile de date simultan
    public Object[][] provideLoginCredentials() {
        return new Object[][]{
                // { username,       password,       shouldBeSuccessful, errorMessage (if any) }
                {"tautester", "qazXSW", true, null}, // Cazul 1: Login valid
                {"testUser", "parolaGresita", false, "Your username or password was incorrect"}, // Cazul 2: Parolă greșită
                {"userInexistent", "parola", false, "Your username or password was incorrect"}, // Cazul 3: User greșit
                {"a", "a", false, "Your username or password was incorrect"}, // Cazul 4: Date invalide scurte
        };
    }

    // Acest @Test este legat de DataProvider prin `dataProvider = "..."`
    @Test(dataProvider = "loginCredentials", groups = {"regression", "smoke"}, description = "Verifică diverse scenarii de login folosind un DataProvider")
    public void testLoginScenarios(String username, String password, boolean shouldBeSuccessful, String expectedErrorMessage) {

        // ARRANGE
        // Inițializăm WebDriverWait local pentru a fi thread-safe
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
        DriverManager.getDriver().get(Config.baseUrl + "/login.php");

        // ACT
        DriverManager.getDriver().findElement(By.name("username")).sendKeys(username);
        DriverManager.getDriver().findElement(By.name("password")).sendKeys(password);
        DriverManager.getDriver().findElement(By.name("submitButton")).click();

        // ASSERT
        if (shouldBeSuccessful) {
            // Validăm un login reușit
            // Așteptăm până când URL-ul se schimbă la cel așteptat
            wait.until(ExpectedConditions.urlToBe(Config.baseUrl + "/index.php"));
            Assert.assertEquals(DriverManager.getDriver().getCurrentUrl(), Config.baseUrl + "/index.php", "Login-ul ar fi trebuit să reușească, dar nu a redirectat corect.");
        } else {
            // Validăm un login eșuat
            // Așteptăm vizibilitatea mesajului de eroare
            WebElement errorMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class, 'errorMessage')]")));
            Assert.assertTrue(errorMessageElement.getText().contains(expectedErrorMessage),
                    "Mesajul de eroare pentru login eșuat nu este corect. Așteptat: '" + expectedErrorMessage + "', Găsit: '" + errorMessageElement.getText() + "'");
        }
    }
}