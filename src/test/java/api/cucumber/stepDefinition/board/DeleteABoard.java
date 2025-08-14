package api.cucumber.stepDefinition.board;

import api.resourcesForTests.ConfigurationDataForApiTests;
import api.services.ServiceWorkShop;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class DeleteABoard extends ServiceWorkShop {

    @When("I delete the board")
    public void i_delete_the_board() {
        getBoardService().deleteBoard(ConfigurationDataForApiTests.BoardTestData.boardId);
    }

    @Then("Board is deleted")
    public void board_is_deleted() {
        ConfigurationDataForApiTests.commonResponseBetweenSteps = getBoardService().getBoard(ConfigurationDataForApiTests.BoardTestData.boardId);
        Assert.assertEquals(ConfigurationDataForApiTests.commonResponseBetweenSteps.asPrettyString(), "The requested resource was not found.");
    }
}
