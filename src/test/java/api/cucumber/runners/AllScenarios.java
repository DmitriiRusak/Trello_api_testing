package api.cucumber.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/cucumberFeatures",
        glue = {"api.cucumber.stepDefinition", "api.utils"},
        plugin = {"pretty", "api.utils.CucumberEventListener", "html:target/cucumber.html"},
        tags = "@All")
public class AllScenarios extends AbstractTestNGCucumberTests {
}
