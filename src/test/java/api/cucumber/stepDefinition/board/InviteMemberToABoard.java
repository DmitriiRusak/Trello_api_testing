package api.cucumber.stepDefinition.board;

import api.resourcesForTests.CycymberConfigTestData;
import api.services.BoardService;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.testng.Assert;

public class InviteMemberToABoard{

    private CycymberConfigTestData cycymberConfigTestData;
    private BoardService boardService;

    public InviteMemberToABoard(CycymberConfigTestData cycymberConfigTestData, BoardService boardService){
        this.cycymberConfigTestData = cycymberConfigTestData;
        this.boardService = boardService;
    }

    @When("I invite a new member on a board")
    public void i_invite_a_new_member_on_a_board() {
        boardService.inviteMemberToBoardViaEmail(cycymberConfigTestData.getBoardId());
    }

    @Then("A new member is added to a board")
    public void aNewMemberIsAddedToABoard() {

//        Response response = boardService.getMembershipsOfABoard(boardTestData.getBoardId()).jsonPath().getList("."));
        int numberOfMembersPresented = boardService.getMembershipsOfABoard(cycymberConfigTestData.getBoardId()).jsonPath().getList(".").size();

        Assert.assertEquals(numberOfMembersPresented, 2);
    }

}
