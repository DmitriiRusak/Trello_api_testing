package api.cucumber.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features/board",
                glue = "api.cucumber.stepDefinition.board",
                plugin = {"pretty", "html:target/cucumber.html"},
                tags = "@Invite_member")
//                tags = "@Invite_member or @Delete_a_board")
public class CucumberTestNGRunnerTest extends AbstractTestNGCucumberTests {



}
