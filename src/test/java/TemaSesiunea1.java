import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;

public class TemaSesiunea1 {
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
}
