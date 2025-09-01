package api.utils;

import api.cucumber.continer.ConfigTestDataHolder;
import api.resourcesForTests.CycymberConfigTestData;
import api.resourcesForTests.configurationData.BoardTestData;
import api.services.BoardService;
import io.cucumber.java.*;

import java.io.FileNotFoundException;

public class CucumberHooks {

    private CycymberConfigTestData cycymberConfigTestData;
    private BoardService boardService;


    public CucumberHooks (CycymberConfigTestData cycymberConfigTestData, BoardService boardService){
        this.cycymberConfigTestData = cycymberConfigTestData;
        this.boardService = boardService;
    }

    @BeforeAll
    public static void beforeAll(){

        LogFactory.getLogger().info(" ");
        LogFactory.getLogger().info("****************************** \uD83E\uDD52 New run for cucumber framework has been initialized \uD83E\uDD52 *************************");
        LogFactory.getLogger().info("");
    }


    @Before
    public void logsConfigurationBeforeScenario(Scenario scenario){
        LogFactory.getLogger().info("");
        LogFactory.getLogger().info("Scenario name: ➤ " + scenario.getName());
        LogFactory.getLogger().info("");
    }

    //Delete the board after every Scenario, but do it accordingly to test logic. If you do not need to keep the board existed
    //after scenario is done, add scenario teg to this hook.
    @After("not (@EndToEndCustomerEnvironment or @Get_resources_of_a_board)")
    public void deleteTheBoardAfterEveryScenario() {
        LogFactory.getLogger().info("Deliting the board after Scenario is over");
        boardService.deleteBoard(cycymberConfigTestData.getBoardId());
    }

    @After
    public void logsConfigurationAfterEveryScenario(Scenario scenario) {
        if(scenario.getStatus() == Status.FAILED){
            LogFactory.getLogger().error("Scenario failed ❌ --------------------------------------------------------");
        }else {
            LogFactory.getLogger().info("Scenario passed ✔\uFE0F ++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }
    }

}
