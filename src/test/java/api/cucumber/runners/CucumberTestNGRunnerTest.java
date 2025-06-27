package api.cucumber.runners;

import api.base.BaseTest;
import api.base.TestData;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.restassured.response.Response;

@CucumberOptions(features = "src/test/resources/features",
                glue = {"api.cucumber.stepDefinition","api.cucumber.runners"},
//                glue = "api.cucumber.stepDefinition.board",
                plugin = {"pretty", "html:target/cucumber.html"},
                tags = "@Move_list_from_board_to_board")

public class CucumberTestNGRunnerTest extends AbstractTestNGCucumberTests {

    private BaseTest baseTest = new BaseTest();

//    @After
//    public void deleteTheBoardAfterEveryScenario() {
//        baseTest.getBoardService().deleteBoard(TestData.BoardTestData.boardId);
//    }

}
