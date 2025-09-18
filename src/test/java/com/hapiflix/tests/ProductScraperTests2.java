package com.hapiflix.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

// Presupunem că această clasă extinde o clasă BaseTest care se ocupă de setup-ul driver-ului
public class ProductScraperTests2 extends BaseTest {

    @Test(description = "Extrage date despre laptopuri și le salvează într-un fișier CSV")
    public void scrapeAndSaveLaptopData() {
        // ARRANGE - Navigăm la pagina țintă
        DriverManager.getDriver().get("https://webscraper.io/test-sites/e-commerce/allinone/computers/laptops");

        // PREPARE FILE - Pregătim fișierul CSV pentru scriere
        // Folosim un try-with-resources pentru a ne asigura că fișierul este închis automat la final
        try (PrintWriter writer = new PrintWriter(new FileWriter("laptops.csv"))) {

            // Scriem antetul (header-ul) fișierului CSV
            writer.println("Nume Produs,Pret,Numar Review-uri");

            // ACT - Găsim TOATE containerele de produse
            List<WebElement> productCards = DriverManager.getDriver().findElements(By.cssSelector("div.thumbnail"));
            System.out.println("Am găsit " + productCards.size() + " produse. Se salvează în laptops.csv...");

            // ITERATE, EXTRACT & SAVE - Parcurgem fiecare card, extragem datele și le scriem în fișier
            for (WebElement card : productCards) {
                String title = card.findElement(By.cssSelector("a.title")).getText();
                String price = card.findElement(By.cssSelector("h4.price")).getText();
                String reviews = card.findElement(By.cssSelector("p.review-count")).getText();

                // Curățăm datele de posibile virgule care ar strica formatul CSV
                String cleanTitle = title.replace(",", "");
                String cleanPrice = price.replace(",", "");
                String cleanReviews = reviews.replace(",", "");

                // Construim un rând în format CSV
                String csvRow = String.format("\"%s\",%s,\"%s\"", cleanTitle, cleanPrice, cleanReviews);
                writer.println(csvRow);
            }

            System.out.println("Salvarea în fișierul CSV a fost finalizată cu succes!");

        } catch (IOException e) {
            // Prindem și afișăm orice eroare care ar putea apărea la scrierea fișierului
            System.err.println("A apărut o eroare la scrierea fișierului CSV: " + e.getMessage());
        }
    }
}

