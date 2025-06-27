package api.cucumber.stepDefinition.board;

import api.base.BaseTest;
import api.base.TestData;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.testng.Assert;

import static api.base.TestData.universalListForResource;

public class InviteMemberToABoard extends BaseTest {

    @When("I invite a new member on a board")
    public void i_invite_a_new_member_on_a_board() {
        getBoardService().inviteMemberToBoardViaEmail(TestData.BoardTestData.boardId);
    }

    @Then("A new member is added to a board")
    public void aNewMemberIsAddedToABoard() {

        universalListForResource = getBoardService().getMembershipsOfABoard(TestData.BoardTestData.boardId).jsonPath().getList(".");
        Assert.assertEquals(universalListForResource.size(), 2);
    }

}
