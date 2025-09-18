package com.hapiflix.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage { // Moștenim BasePage

    // 1. Definim elementele cu adnotarea @FindBy. E mai curat și mai lizibil.
    @FindBy(name = "username")
    private WebElement usernameInput;

    @FindBy(name = "password")
    private WebElement passwordInput;

    @FindBy(name = "submitButton")
    private WebElement loginButton;

    @FindBy(xpath = "//span[contains(@class, 'errorMessage')]")
    private WebElement errorMessageSpan;

    // 2. Constructorul primește driver-ul și inițializează elementele
    public LoginPage(WebDriver driver) {
        super(driver); // Apelăm constructorul din clasa părinte (BasePage)
        // Această linie magică inițializează toate elementele definite cu @FindBy de mai sus.
        PageFactory.initElements(driver, this);
    }

    // 3. Metodele de acțiune folosesc direct variabilele WebElement
    public void enterUsername(String username) {
        usernameInput.sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordInput.sendKeys(password);
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    public String getErrorMessageText() {
        return errorMessageSpan.getText();
    }

    /**
     * Aceasta este o metodă de business care încapsulează un flux complet.
     * @return O nouă instanță a paginii HomePage, deoarece un login reușit ne duce acolo.
     * Aceasta este o tehnică numită "Fluent Interface".
     */

    public HomePage performSuccessfulLogin(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        return new HomePage(driver); // Returnăm obiectul paginii următoare
    }

    /**
     * Metodă de business pentru un login eșuat.
     * Nu returnează nimic (void) deoarece acțiunea ne menține pe aceeași pagină.
     */
    public void performFailedLogin(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }


}