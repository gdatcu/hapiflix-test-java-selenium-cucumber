package com.hapiflix.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AuthenticationTests extends BaseTest {

    @Test(description = "Verifică autentificarea cu credențiale valide")
    public void testSuccessfulLogin() {
        // Obținem driver-ul specific acestui thread de la manager
        DriverManager.getDriver().get("http://localhost/hapiflix/login.php");

        WebElement usernameInput = DriverManager.getDriver().findElement(By.name("username"));
        usernameInput.sendKeys("testUser");

        WebElement passwordInput = DriverManager.getDriver().findElement(By.name("password"));
        passwordInput.sendKeys("testPass");

        WebElement loginButton = DriverManager.getDriver().findElement(By.name("submitButton"));
        loginButton.click();

        Assert.assertTrue(DriverManager.getDriver().getCurrentUrl().contains("index.php"));
    }

    @Test(description = "Verifică eroarea la autentificare cu parolă greșită")
    public void testFailedLogin() {
        DriverManager.getDriver().get("http://localhost/hapiflix/login.php");

        DriverManager.getDriver().findElement(By.name("username")).sendKeys("testUser");
        DriverManager.getDriver().findElement(By.name("password")).sendKeys("wrongPass");
        DriverManager.getDriver().findElement(By.name("submitButton")).click();

        WebElement errorMessage = DriverManager.getDriver().findElement(By.xpath("//span[contains(@class, 'errorMessage')]"));
        Assert.assertTrue(errorMessage.getText().contains("incorrect"));
    }
}
