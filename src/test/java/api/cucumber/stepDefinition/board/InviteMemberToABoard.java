//package api.cucumber.stepDefinition.board;
//
//import api.cucumber.continer.ConfigTestDataHolder;
//import io.cucumber.java.en.*;
//import org.testng.Assert;
//
//public class InviteMemberToABoard{
//
//    private ConfigTestDataHolder configTestDataHolder;
//
//    @When("I invite a new member on a board")
//    public void i_invite_a_new_member_on_a_board() {
//        getBoardService().inviteMemberToBoardViaEmail(configTestDataHolder.getBoardTestData().getBoardId());
//    }
//
//    @Then("A new member is added to a board")
//    public void aNewMemberIsAddedToABoard() {
//
//        configTestDataHolder.getCommonConfigData().setUniversalListForResource(getBoardService().getMembershipsOfABoard(configTestDataHolder.getBoardTestData().getBoardId()).jsonPath().getList("."));
//        Assert.assertEquals(configTestDataHolder.getCommonConfigData().getUniversalListForResource().size(), 2);
//    }
//
//}
