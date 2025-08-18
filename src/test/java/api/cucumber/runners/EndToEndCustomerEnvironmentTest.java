package api.cucumber.runners;

import api.cucumber.continer.ConfigTestDataHolder;
import api.resourcesForTests.configurationData.CommonConfigData;
import api.services.ServiceWorkShop;
import api.utils.LogFactory;
import io.cucumber.java.*;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/cucumberFeatures",
        glue = {"api.cucumber.stepDefinition","api.cucumber.runners"},
        plugin = {"pretty", "api.utils.CucumberEventListener", "html:target/cucumber.html"},
        tags = "@EndToEndCustomerEnvironment")
public class EndToEndCustomerEnvironmentTest extends AbstractTestNGCucumberTests {

    private static final ServiceWorkShop serviceWorkShop = new ServiceWorkShop();
    private static ConfigTestDataHolder configTestDataHolder;
    public static boolean stepStatusPassed = true;
    private static String boardIdToDelete = configTestDataHolder.getBoardTestData().getBoardId();


    @Before
    public void logsConfigurationBeforeScenario(Scenario scenario){
        LogFactory.getLogger().info("Scenario name: " + scenario.getName());
        LogFactory.getLogger().info("scenario location in a project structure: " + scenario.getUri());
    }

    @AfterStep
    public void logsConfigurationAfterStep() {

        LogFactory.getLogger().info("");
        stepStatusPassed = true;
    }

    @After
    public void logsConfigurationAfterEveryScenario(Scenario scenario) {
        if(scenario.getStatus() == Status.FAILED){
            LogFactory.getLogger().error("Scenario failed ❌ --------------------------------------------------------");
        }else {
            LogFactory.getLogger().info("Scenario passed ✔\uFE0F ++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }
    }

    //clean workSpace after all scenarios passed (delete the board that was used for tests)
    @AfterAll
    public static void afterAllExecution(){
        serviceWorkShop.getBoardService().deleteBoard(boardIdToDelete);
    }

}
