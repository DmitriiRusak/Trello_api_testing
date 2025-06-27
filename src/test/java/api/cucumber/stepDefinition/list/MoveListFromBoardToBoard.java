package api.cucumber.stepDefinition.list;

import api.base.BaseTest;
import api.base.TestData;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class MoveListFromBoardToBoard extends BaseTest {

    @And("I create one more board")
    public void iCreateOneMoreBoard() {
        TestData.BoardTestData.secondBoardId = getBoardService().createBoard("One more board").jsonPath().getString("id");
    }

    @When("I move to do list, from one board, to another")
    public void iMoveToDoListFromOneBoardToAnother() {
        TestData.commonResponseBetweenSteps = getListsService().
                moveListFromOneBoardToAnother(TestData.ListsTestData.toDoListId, TestData.BoardTestData.secondBoardId);
    }

    @Then("I see to do list being moved from one board, to another")
    public void iSeeToDoListBeingMovedFromOneBoardToAnother() {
        String boardIdTheListisOn = getListsService().getABoardAListIsOn(TestData.ListsTestData.toDoListId).jsonPath().getString("id");

        Assert.assertEquals(TestData.BoardTestData.secondBoardId, boardIdTheListisOn);
    }
}
