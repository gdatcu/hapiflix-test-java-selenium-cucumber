package com.hapiflix.steps;

import com.hapiflix.tests.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

    /**
     * Această metodă adnotată cu @Before se va executa
     * înaintea FIECĂRUI scenariu BDD.
     */
    @Before
    public void setupScenario() {
        System.out.println("HOOK: Pornire browser pentru un nou scenariu...");
        DriverManager.setupDriver();
    }

    /**
     * Această metodă adnotată cu @After se va executa
     * după FIECĂRUI scenariu BDD, indiferent dacă a trecut sau a eșuat.
     */
    @After
    public void tearDownScenario(Scenario scenario) {
        // Verificăm dacă scenariul a eșuat
        if (scenario.isFailed()) {
            System.out.println("HOOK: Scenariul a eșuat. Se face screenshot...");
            // Facem un screenshot și îl atașăm la raportul Cucumber
            DriverManager.takeScreenshot(scenario.getName().replaceAll("\\s", "_"));
        }
        System.out.println("HOOK: Închidere browser pentru scenariul finalizat.");
        DriverManager.tearDownDriver();
    }
}
