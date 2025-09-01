package api.cucumber.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(features = "src/test/resources/cucumberFeatures",
        glue = {"api.cucumber.stepDefinition", "api.utils"},
        plugin = {"pretty", "api.utils.CucumberEventListener", "html:target/cucumber.html"},
        tags = "not (@EndToEndCustomerEnvironment or @Get_resources_of_a_board)")
public class ParallelTesting extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
