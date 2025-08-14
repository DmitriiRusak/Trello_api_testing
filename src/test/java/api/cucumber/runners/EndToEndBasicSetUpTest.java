package api.cucumber.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/cucumberFeatures",
        glue = {"api.cucumber.stepDefinition","api.cucumber.runners"},
        plugin = {"pretty", "api.utils.CucumberEventListener", "html:target/cucumber.html"},
        tags = "@EndToEndBasicSetUp")

public class EndToEndBasicSetUpTest extends AbstractTestNGCucumberTests {


}
