package api.cucumber.runners;

import api.cucumber.continer.ConfigTestDataHolder;
import api.services.BoardService;
import api.utils.LogFactory;
import io.cucumber.java.*;
import io.cucumber.java.Scenario;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(features = "src/test/resources/cucumberFeatures",
                glue = {"api.cucumber.stepDefinition", "api.utils"},
                plugin = {"pretty", "api.utils.CucumberEventListener", "html:target/cucumber.html"},
//                tags = "@BoardComponentTesting")
                tags = "@DeleteACard")
//                tags = "@Smoke")
public class SmokeTest extends AbstractTestNGCucumberTests {

}
