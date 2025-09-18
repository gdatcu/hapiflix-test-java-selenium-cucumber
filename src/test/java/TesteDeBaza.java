import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TesteDeBaza {

    // Declaram driver-ul la nivel de clasa pentru a fi accesibil in toate metodele
    private WebDriver driver;

    @BeforeMethod
    public void setup() {
        System.out.println("Rulam metoda de setup...");
        // Acest cod se va executa INAINTEA FIECAREI metode @Test
//        System.setProperty("webdriver.chrome.driver", "C:\\WebDrivers\\chromedriver.exe");
        driver = new ChromeDriver();
//        driver.navigate().back();
//        driver.navigate().forward();
//        driver.navigate().to("www.google.com");
//        driver.navigate().refresh();
        driver.manage().window().maximize();
    }

//    @Test
//    public void testTitluGoogle() {
//        System.out.println("Rulam testul pentru titlul Google...");
//        driver.get("https://www.google.com");
//        String titluActual = driver.getTitle();
//        String titluAsteptat = "Googlee";
//
//        // ASERTIUNE: Daca aceasta conditie este falsa, testul pica si se opreste aici.
//        Assert.assertEquals(titluActual, titluAsteptat, "Titlul paginii Google nu este corect!");
//    }
//
//    @Test
//    public void testNavigareInapoiInainte() {
//        System.out.println("Rulam testul de navigare...");
//        driver.navigate().to("https://www.emag.ro/");
//        driver.navigate().to("https://www.wikipedia.org/");
//
//        // Ne intoarcem la pagina anterioara (eMAG)
//        driver.navigate().back();
//        Assert.assertTrue(driver.getCurrentUrl().contains("altex.ro"), "Intoarcerea la eMAG a esuat!");
//
//        // Mergem la pagina urmatoare (Wikipedia)
//        driver.navigate().forward();
//        Assert.assertTrue(driver.getCurrentUrl().contains("wikipedia.org"), "Navigarea inainte la Wikipedia a esuat!");
//    }

    @Test
    public void testInteractionsAndValidations() {
        driver.get("https://the-internet.herokuapp.com/checkboxes");

        // Gasim cele doua checkbox-uri
        WebElement checkbox1 =
                driver.findElement(By.xpath("//form[@id='checkboxes']/input[1]"));
        WebElement checkbox2 =
                driver.findElement(By.xpath("//form[@id='checkboxes']/input[2]"));

        // Validam starea initiala
        Assert.assertFalse(checkbox1.isSelected(), "Checkbox 1 nu ar trebui sa fie selectat initial!");
        Assert.assertTrue(checkbox2.isSelected(), "Checkbox 2 ar trebui sa fie selectat initial!");

        // Interacționăm
        System.out.println("Bifam primul checkbox...");
        checkbox1.click();

        // Validam noua stare
        Assert.assertTrue(checkbox1.isSelected(), "Checkbox 1 ar trebui sa fie acum selectat!");
    }


    @AfterMethod
    public void tearDown() {
        System.out.println("Rulam metoda de teardown...");
        // Acest cod se va executa DUPA FIECARE metoda @Test
        if (driver != null) {
            driver.quit();
        }
    }
}