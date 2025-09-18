package com.hapiflix.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        // Inițializăm wait-ul aici, pentru a fi disponibil în toate paginile
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
}