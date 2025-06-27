package api.cucumber.stepDefinition.board;

import api.base.BaseTest;
import api.base.TestData;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.testng.Assert;

public class DeleteABoard extends BaseTest {

    @When("I delete the board")
    public void i_delete_the_board() {
        getBoardService().deleteBoard(TestData.BoardTestData.boardId);
    }
    @Then("Board is deleted")
    public void board_is_deleted() {
        TestData.commonResponseBetweenSteps = getBoardService().getBoard(TestData.BoardTestData.boardId);
        Assert.assertEquals(TestData.commonResponseBetweenSteps.asPrettyString(), "The requested resource was not found.");
    }
}
