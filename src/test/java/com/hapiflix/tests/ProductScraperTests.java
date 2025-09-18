package com.hapiflix.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import java.util.List;

public class ProductScraperTests extends BaseTest {

    @Test(description = "Extrage date despre laptopuri de pe un site de e-commerce")
    public void scrapeLaptopData() {
        // ARRANGE - Navigăm la pagina țintă
        DriverManager.getDriver().get("https://webscraper.io/test-sites/e-commerce/allinone/computers/laptops");

        // ACT - Găsim TOATE containerele de produse
        // Folosim findElements (plural) pentru a obține o listă, apelând driver-ul prin DriverManager
        List<WebElement> productCards = DriverManager.getDriver().findElements(By.cssSelector("div.thumbnail"));

        System.out.println("Am găsit " + productCards.size() + " produse pe pagină.");

        // ITERATE & EXTRACT - Parcurgem fiecare card de produs și extragem datele
        for (WebElement card : productCards) {
            // Folosim card.findElement() pentru a căuta DOAR în interiorul acestui element
            String title = card.findElement(By.cssSelector("a.title")).getText();
            String price = card.findElement(By.cssSelector("h4.price")).getText();
            String reviews = card.findElement(By.cssSelector("p.review-count")).getText();

            // CLEAN & DISPLAY - Curățăm și afișăm datele
            System.out.println("------------------------------------");
            System.out.println("Nume: " + title);
            System.out.println("Preț: " + price);
            System.out.println("Review-uri: " + reviews);
        }
    }
}