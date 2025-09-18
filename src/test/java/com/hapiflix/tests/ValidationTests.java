package com.hapiflix.tests;

import com.hapiflix.utils.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class ValidationTests extends BaseTest {

    // Metodă ajutătoare privată pentru a nu repeta codul de navigare
    private void navigateToRegisterPage() {
        DriverManager.getDriver().get(Config.baseUrl + "/register.php");
    }

    @Test(groups = {"regression", "validation", "smoke"}, description = "Verifică eroarea la un username cu lungime invalidă")
    public void testUsernameLengthValidation() {
        // Inițializăm WebDriverWait local pentru a fi thread-safe
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));

        // STRATEGIA: Acest test validează regula de business: username-ul trebuie să aibă între 2 și 25 de caractere.
        navigateToRegisterPage();

        // --- Cazul 1: Prea scurt ---
        DriverManager.getDriver().findElement(By.name("firstName")).sendKeys("George");
        DriverManager.getDriver().findElement(By.name("lastName")).sendKeys("Datcu");
        DriverManager.getDriver().findElement(By.name("username")).sendKeys("a"); // username invalid
        DriverManager.getDriver().findElement(By.name("email")).sendKeys("abcde@example.com");
        DriverManager.getDriver().findElement(By.name("email2")).sendKeys("abcde@example.com");
        DriverManager.getDriver().findElement(By.name("password")).sendKeys("abcde123");
        DriverManager.getDriver().findElement(By.name("password2")).sendKeys("abcde123");
        DriverManager.getDriver().findElement(By.name("submitButton")).click();

        // Aserțiune - verificăm textul de eroare
        String expectedError = "Your username must be between 2 and 25 characters";
        WebElement errorMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class, 'errorMessage')]")));
        Assert.assertTrue(errorMessageElement.getText().contains(expectedError), "Eroarea pentru username prea scurt nu a apărut.");

        // --- Cazul 2: Prea lung ---
        navigateToRegisterPage();
        DriverManager.getDriver().findElement(By.name("firstName")).sendKeys("George");
        DriverManager.getDriver().findElement(By.name("lastName")).sendKeys("Datcu");
        DriverManager.getDriver().findElement(By.name("username")).sendKeys("unusernamefoartefoartelungcarearepeste25decaractere"); // username invalid
        DriverManager.getDriver().findElement(By.name("email")).sendKeys("abcde@example.com");
        DriverManager.getDriver().findElement(By.name("email2")).sendKeys("abcde@example.com");
        DriverManager.getDriver().findElement(By.name("password")).sendKeys("abcde123");
        DriverManager.getDriver().findElement(By.name("password2")).sendKeys("abcde123");
        DriverManager.getDriver().findElement(By.name("submitButton")).click();

        WebElement errorMessageElement2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class, 'errorMessage')]")));
        Assert.assertTrue(errorMessageElement2.getText().contains(expectedError), "Eroarea pentru username prea lung nu a apărut.");
    }

    @Test(description = "Demonstrează comutarea între tab-uri")
    public void testWindowSwitching() {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
        DriverManager.getDriver().get("https://the-internet.herokuapp.com/windows");

        String originalWindowHandle = DriverManager.getDriver().getWindowHandle();
        DriverManager.getDriver().findElement(By.linkText("Click Here")).click();

        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        for (String windowHandle : DriverManager.getDriver().getWindowHandles()) {
            if (!originalWindowHandle.equals(windowHandle)) {
                DriverManager.getDriver().switchTo().window(windowHandle);
                break;
            }
        }

        WebElement newWindowText = DriverManager.getDriver().findElement(By.tagName("h3"));
        Assert.assertEquals(newWindowText.getText(), "New Window");

        DriverManager.getDriver().close();
        DriverManager.getDriver().switchTo().window(originalWindowHandle);

        Assert.assertTrue(DriverManager.getDriver().getTitle().contains("The Internet"));
    }

    @Test(groups = {"regression", "validation"}, description = "Verifică eroarea la un email deja utilizat")
    public void testEmailAlreadyInUseValidation() {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
        navigateToRegisterPage();

        DriverManager.getDriver().findElement(By.name("firstName")).sendKeys("George");
        DriverManager.getDriver().findElement(By.name("lastName")).sendKeys("Datcu");
        DriverManager.getDriver().findElement(By.name("username")).sendKeys("uservalid123");
        DriverManager.getDriver().findElement(By.name("email")).sendKeys("testUser@example.com"); // Email care probabil există deja
        DriverManager.getDriver().findElement(By.name("email2")).sendKeys("testUser@example.com");
        DriverManager.getDriver().findElement(By.name("password")).sendKeys("abcde123");
        DriverManager.getDriver().findElement(By.name("password2")).sendKeys("abcde123");
        DriverManager.getDriver().findElement(By.name("submitButton")).click();

        String expectedError = "Email already in use";
        WebElement actualErrorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class, 'errorMessage')]")));
        String actualErrorText = actualErrorElement.getText();

        Assert.assertTrue(actualErrorText.contains(expectedError), "Eroarea pentru email deja utilizat nu este corectă. Așteptat: '" + expectedError + "', Găsit: '" + actualErrorText + "'");
    }
}
