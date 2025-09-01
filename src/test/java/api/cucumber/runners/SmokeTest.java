package api.cucumber.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(features = "src/test/resources/cucumberFeatures",
                glue = {"api.cucumber.stepDefinition", "api.utils"},
                plugin = {"pretty", "api.utils.CucumberEventListener", "html:target/cucumber.html"},
//                tags = "@BoardComponentTesting")
                tags = "@Get_resources_of_a_board")
//                tags = "@Smoke")
public class SmokeTest extends AbstractTestNGCucumberTests {

}
