package api.cucumber.stepDefinition.list;

import api.resourcesForTests.ConfigurationDataForApiTests;
import api.services.ServiceWorkShop;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class MoveListFromBoardToBoard extends ServiceWorkShop {

    @And("I create one more board")
    public void iCreateOneMoreBoard() {
        ConfigurationDataForApiTests.BoardTestData.secondBoardId = getBoardService().createBoard("One more board").jsonPath().getString("id");
    }

    @When("I move to do list, from one board, to another")
    public void iMoveToDoListFromOneBoardToAnother() {
        ConfigurationDataForApiTests.commonResponseBetweenSteps = getListsService().
                moveListFromOneBoardToAnother(ConfigurationDataForApiTests.ListsTestData.toDoListId, ConfigurationDataForApiTests.BoardTestData.secondBoardId);
    }

    @Then("I see to do list being moved from one board, to another")
    public void iSeeToDoListBeingMovedFromOneBoardToAnother() {
        String boardIdTheListisOn = getListsService().getABoardAListIsOn(ConfigurationDataForApiTests.ListsTestData.toDoListId).jsonPath().getString("id");

        Assert.assertEquals(ConfigurationDataForApiTests.BoardTestData.secondBoardId, boardIdTheListisOn);
    }
}
