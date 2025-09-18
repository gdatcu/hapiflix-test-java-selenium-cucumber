package com.hapiflix.steps;

import com.hapiflix.tests.DriverManager;
import com.hapiflix.utils.Config;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class LoginSteps {

    // NU mai avem variabila WebDriver aici!
    private WebDriverWait wait;

    @Given("Utilizatorul se află pe pagina de login")
    public void utilizatorul_se_află_pe_pagina_de_login() {
        // Obținem driver-ul din manager pentru thread-ul curent
        DriverManager.getDriver().get(Config.baseUrl + "/login.php");
        this.wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
    }

    @When("El introduce username-ul {string} și parola {string}")
    public void el_introduce_username_ul_și_parola(String username, String password) {
        DriverManager.getDriver().findElement(By.name("username")).sendKeys(username);
        DriverManager.getDriver().findElement(By.name("password")).sendKeys(password);
    }

    @When("Apasă pe butonul de login")
    public void apasă_pe_butonul_de_login() {
        DriverManager.getDriver().findElement(By.name("submitButton")).click();
    }

    @Then("El este redirecționat către pagina principală a aplicației")
    public void el_este_redirecționat_către_pagina_principală_a_aplicației() {
        wait.until(ExpectedConditions.urlToBe(Config.baseUrl + "/index.php"));
        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        Assert.assertEquals(currentUrl, Config.baseUrl + "/index.php");
    }

    @Then("I should see the error message {string}")
    public void i_should_see_the_error_message(String expectedMessage) {
        By errorMessageLocator = By.xpath("//span[contains(@class, 'errorMessage')]");
        String actualMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessageLocator)).getText();
        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }
}