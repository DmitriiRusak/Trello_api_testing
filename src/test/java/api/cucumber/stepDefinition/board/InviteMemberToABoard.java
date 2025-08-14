package api.cucumber.stepDefinition.board;

import api.resourcesForTests.ConfigurationDataForApiTests;
import api.services.ServiceWorkShop;
import io.cucumber.java.en.*;
import org.testng.Assert;

import static api.resourcesForTests.ConfigurationDataForApiTests.universalListForResource;

public class InviteMemberToABoard extends ServiceWorkShop {

    @When("I invite a new member on a board")
    public void i_invite_a_new_member_on_a_board() {
        getBoardService().inviteMemberToBoardViaEmail(ConfigurationDataForApiTests.BoardTestData.boardId);
    }

    @Then("A new member is added to a board")
    public void aNewMemberIsAddedToABoard() {

        universalListForResource = getBoardService().getMembershipsOfABoard(ConfigurationDataForApiTests.BoardTestData.boardId).jsonPath().getList(".");
        Assert.assertEquals(universalListForResource.size(), 2);
    }

}
