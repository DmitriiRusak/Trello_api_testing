package api.cucumber.runners;

import api.services.ServiceWorkShop;
import api.resourcesForTests.ConfigurationDataForApiTests;
import api.utils.LogFactory;
import io.cucumber.java.*;
import io.cucumber.java.Scenario;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(features = "src/test/resources/cucumberFeatures",
                glue = {"api.cucumber.stepDefinition","api.cucumber.runners"},
                plugin = {"pretty", "api.utils.CucumberEventListener", "html:target/cucumber.html"},
                tags = "@Smoke")
public class SmokeTest extends AbstractTestNGCucumberTests {

    private final ServiceWorkShop serviceWorkShop = new ServiceWorkShop();

    @BeforeAll
    public static void beforeAll(){

        LogFactory.getLogger().info(" ");
        LogFactory.getLogger().info("****************************** \uD83E\uDD52 New run for cucumber framework has been initialized \uD83E\uDD52 *************************");
    }

    //Delete the board after every Scenario, but do it accordingly to test logic. If you do not need to keep the board existed
    //after scenario is done, add scenario teg to this hook.

    @After("@Smoke or @EndToEndBasicSetUp")
    public void deleteTheBoardAfterEveryScenario(Scenario scenario) {

        serviceWorkShop.getBoardService().deleteBoard(ConfigurationDataForApiTests.BoardTestData.boardId);
    }


}
