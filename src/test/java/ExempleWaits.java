import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class ExempleWaits {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setup() {
//        System.setProperty("webdriver.chrome.driver",
//                "C:\\WebDrivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        // Initializam WebDriverWait cu un timeout de 10 secunde 
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Aici vom adauga metodele @Test de mai jos 

    @Test
    public void testAsteptareElementVizibil() {
        // Navigam la pagina de test
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/2");
        // Gasim si dam click pe butonul Start
        WebElement startButton = driver.findElement(By.cssSelector("#start button"));
        startButton.click();
        // Asteptam in mod inteligent, PANA LA 10 secunde, ca elementul cu id - ul 'finish'
        // sa devina vizibil. Daca apare in 2 secunde, testul continua imediat.
        WebElement finishText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("finish")));

//                        driver.findElement(By.id("finish")); // bad practice in this case!
        // Daca am ajuns aici, elementul este garantat vizibil. Acum putem interactiona cu el.
        Assert.assertEquals(finishText.getText(), "Hello World!");
        System.out.println("Testul a trecut: Mesajul 'Hello World!' a fost gasit.");
    }

    @Test
    public void testAsteptareElementClickabil() {
        // Navigam la pagina de test
        driver.get("https://the-internet.herokuapp.com/dynamic_controls");

        // Gasim si dam click pe butonul "Enable"
        WebElement enableButton =
                driver.findElement(By.xpath("//button[text()='Enable']"));
        enableButton.click();

        // Gasim campul de text, care initial este inactiv
        WebElement inputField = driver.findElement(By.xpath("//form[@id='input-example']//input"));


        // Asteptam PANA LA 10 secunde ca acest camp sa devina activ (enabled).
        // WebDriverWait returneaza elementul odata ce conditia e indeplinita.
        wait.until(ExpectedConditions.elementToBeClickable(inputField));

        // Daca am ajuns aici, elementul este garantat activ. Acum putem scrie in el.
        inputField.sendKeys("Testul a reusit!");
        System.out.println("Am reusit sa scriem in campul activat.");

        // Verificam ca textul a fost introdus corect
        Assert.assertEquals(inputField.getAttribute("value"), "Testul a reusit!");
    }

    @Test
    public void testAsteptareInvizibilitate() {
        // Navigam la pagina de test
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");

        // Dam click pe butonul Start, care afiseaza un loading spinner
        WebElement startButton = driver.findElement(By.cssSelector("#start button"));
        startButton.click();

        // Definim locatorul pentru spinner
        By loadingSpinnerLocator = By.id("loading");

        // Asteptam PANA LA 10 secunde ca spinner-ul sa dispara.
        // Metoda returneaza 'true' daca elementul dispare in timpul alocat.
        boolean spinnerDisparut = wait.until(

                ExpectedConditions.invisibilityOfElementLocated(loadingSpinnerLocator)
        );

        // Validam ca asteptarea a functionat
        Assert.assertTrue(spinnerDisparut, "Spinner-ul de incarcare nu a disparut la timp!");
        System.out.println("Spinner-ul a disparut. Putem continua.");

        // Acum ca spinner-ul a disparut, putem verifica in siguranta mesajul de succes

        WebElement finishText = driver.findElement(By.id("finish"));
        Assert.assertTrue(finishText.isDisplayed());
        Assert.assertEquals(finishText.getText(), "Hello World!");
    }

    @Test
    public void testDropdownComplet() {
        driver.get("https://the-internet.herokuapp.com/dropdown");

// Pas 1: Gasim elementul <select>
        WebElement dropdownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dropdown")));
        // Pas 2: Cream obiectul Select
        Select dropdown = new Select(dropdownElement);


        // Verificam optiunea selectata initial
        String initialSelectedOption =
                dropdown.getFirstSelectedOption().getText();
        Assert.assertEquals(initialSelectedOption, "Please select an option");

        // --- Metode de Selectie ---

        // Metoda 1: Dupa textul vizibil (cea mai lizibila si recomandata)
        System.out.println("Selectam 'Option 2' dupa text...");
        dropdown.selectByVisibleText("Option 2");
        Assert.assertEquals(dropdown.getFirstSelectedOption().getText(),
                "Option 2");

        // Metoda 2: Dupa atributul 'value' (util cand textul se schimba, dar valoarea ramane constanta)
        // HTML: <option value="1">Option 1</option>
        System.out.println("Selectam 'Option 1' dupa valoare...");
        dropdown.selectByValue("1");
        Assert.assertEquals(dropdown.getFirstSelectedOption().getText(),
                "Option 1");

        // Metoda 3: Dupa index (0, 1, 2, ... - cea mai fragila, de evitat daca se poate)
        System.out.println("Selectam 'Option 2' dupa index...");
        dropdown.selectByIndex(2);
        Assert.assertEquals(dropdown.getFirstSelectedOption().getText(),
                "Option 2");

        // --- Alte metode utile ---
        java.util.List<WebElement> allOptions = dropdown.getOptions();
        System.out.println("Numarul total de optiuni: " + allOptions.size());
        Assert.assertEquals(allOptions.size(), 3);
    }

    @Test
    public void testRadioButtonsAndCheckboxes() {
        driver.get("https://demoqa.com/automation-practice-form");

        WebElement genderMaleRadio =
                wait.until(ExpectedConditions.elementToBeClickable(By.id("gender-radio-1")));
        WebElement genderFemaleRadio =
                driver.findElement(By.xpath("//label[text()='Female']/preceding sibling::input"));

        WebElement hobbiesSportsCheckbox =
                driver.findElement(By.xpath("//label[text()='Sports']/preceding sibling::input"));

        // Validare stare initiala
        Assert.assertFalse(genderMaleRadio.isSelected());
        Assert.assertFalse(genderFemaleRadio.isSelected());
        Assert.assertFalse(hobbiesSportsCheckbox.isSelected());

        // Selectam un radio button
        genderMaleRadio.click();

        // Validam ca s-a selectat corect si celalalt a ramas neselectat
        Assert.assertTrue(genderMaleRadio.isSelected());
        Assert.assertFalse(genderFemaleRadio.isSelected());

        // Selectam checkbox-ul
        hobbiesSportsCheckbox.click();
        Assert.assertTrue(hobbiesSportsCheckbox.isSelected());
    }

    @Test
    public void testRadioButtons() {
        driver.get("https://demoqa.com/radio-button");

        WebElement impressiveBtn =
                wait.until(ExpectedConditions.elementToBeClickable(By.id("impressiveRadio")));
        WebElement yesBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("yesRadio")));
// Validare stare initiala
        Assert.assertFalse(impressiveBtn.isSelected());
        Assert.assertFalse(yesBtn.isSelected());

        // Selectam un radio button
        impressiveBtn.click();

        // Validam ca s-a selectat corect si celalalt a ramas neselectat
        Assert.assertTrue(impressiveBtn.isSelected());
        Assert.assertFalse(yesBtn.isSelected());
    }

    @Test
    public void testJsAlerts() {
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");

        // Testam o alerta simpla (doar cu OK)
        driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
        Alert simpleAlert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = simpleAlert.getText();
        Assert.assertEquals(alertText, "I am a JS Alert");
        simpleAlert.accept(); // Da click pe OK

        // Testam o alerta de confirmare (OK si Cancel)
        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
        Alert confirmAlert = wait.until(ExpectedConditions.alertIsPresent());
        confirmAlert.dismiss(); // Da click pe Cancel
        WebElement result = driver.findElement(By.id("result"));
        Assert.assertEquals(result.getText(), "You clicked: Cancel");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}