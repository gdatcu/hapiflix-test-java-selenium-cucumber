package com.hapiflix.tests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
//        src/test/java/com/hapiflix
        features = "src/test/java/com/hapiflix/features",
        glue = "com.hapiflix.steps", // Pachetul unde se află Steps și Hooks
        plugin = {"pretty", "html:target/cucumber-report/report.html"}
)
public class TestRunner extends AbstractTestNGCucumberTests {

    /**
     * Această metodă suprascrisă, adnotată cu @DataProvider(parallel = true),
     * îi spune lui TestNG să trateze fiecare scenariu ca pe o unitate de lucru
     * separată și să le distribuie pe thread-uri diferite.
     */
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}

