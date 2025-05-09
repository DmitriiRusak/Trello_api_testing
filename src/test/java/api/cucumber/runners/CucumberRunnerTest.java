package api.cucumber.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
//        tags = "@RU",
        glue = {"api.cucumber.steps", "api.cucumber.runners"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports.html",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"}
)

public class CucumberRunnerTest extends AbstractTestNGCucumberTests {
}
