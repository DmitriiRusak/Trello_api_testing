package api.cucumber;

import api.base.BaseTest;
import api.base.TestData;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;

import static api.base.TestData.BoardTestData.EXPECTED_RESULT;
import static api.base.TestData.BoardTestData.boardId;
import static api.base.TestData.response;

public class CucumberStepsBoards extends BaseTest {

    @When("I create a new board with name {string}")
    public void createBoard(String boardName) {
        TestData.BoardTestData.boardName = boardName;
        response = getBoardService().createBoard(boardName);
        boardId = response.jsonPath().getString("id");
    }

    @When("I get the board with board id")
    public void getBoard() {
        response = getBoardService().getBoard(boardId);
    }

    @When("I create a list on a board with name {string}")
    public void createListOnBoard(String listName) {
        response = getBoardService().createListOnBoard(boardId, listName);
    }

    @When("I create a label on a board with name {string} and color {string}")
    public void createLabelOnBoard(String labelName, String color) {
        response = getBoardService().createLabelOnBoard(boardId, labelName, color);
    }

    @When("I get label on a board")
    public void getLabel() {
        response = getBoardService().getLabelsOnBoard(boardId);
    }

    @When("I get specific field with {string} from a board")
    public void getFieldFromBoard(String fieldName) {
        response = getBoardService().getAField(boardId, "/" + fieldName);
    }

    @When("I get actions from a board")
    public void getActionsOnBoard() {
//        response = getBoardService().getActions(boardId, ACTIONS_BASE_PATH);
    }

    @When("I get checklists from a board")
    public void getChecklistsFromBoard() {
//        response = getBoardService().getChecklists(boardId, CHECKLISTS_BASE_PATH);
    }

    @When("I get all existed cards from a bord")
    public void getCardsFromBoard() {
//        response = getBoardService().getCards(boardId, CARDS_BASE_PATH);
    }

    @When("I get all existed filtered {string} cards from a bord")
    public void getFilteredCardsFromBoard(String filter) {
//        response = getBoardService().getFilteredCards(boardId, CARDS_BASE_PATH, filter);
    }

    @When("I get all existed custom fields from a bord")
    public void getCustomFieldsFomBoard() {
//        response = getBoardService().getCustomFieldsForABoard(boardId, CUSTOM_FIELDS_BASE_PATH);
    }

    @When("I get {string} lists from a bord")
    public void getListsFromBoard(String filter) {
//        response = getBoardService().getFilteredListsOnABoard(boardId, LISTS_BASE_PATH, filter);
    }

    @When("I get members of a bord")
    public void getMembersOfBoard() {
        response = getBoardService().getMembershipsOnBoard(boardId);
    }

    @When("I invite Member to Board via email")
    public void getInviteMembers() {
//        response = getBoardService().inviteMemberToBoardViaEmail(boardId, MEMBERS_BASE_PATH);
    }

    @When("I update a board by giving a new name {string}")
    public void updateNameBoard(String newNameBoard) {
        response = getBoardService().updateBoard(boardId, newNameBoard);
    }

    @When("I get boardStars on a Board")
    public void getBoardStarsBoard() {
//        response = getBoardService().getBoardStarsOnBoard(boardId, boardStarsEnPoint);
    }

    @When("I get memberships of a Board")
    public void getMembershipsBoard() {
        response = getBoardService().getMembershipsOnBoard(boardId);
    }

    @When("I remove member from board")
    public void removeMemberFromBoard() {
        Response membersResponse = getBoardService().getTheMembersOfABoard(TestData.BoardTestData.boardId);
        List<String> memberIds = membersResponse.jsonPath().getList("id"); // ИЛИ "members.id"
        String memberIdToRemove = memberIds.get(1);
        Response response = getBoardService().removeMemberFromBoard(TestData.BoardTestData.boardId, memberIdToRemove);
    }

    @When("I delete the created board")
    public void deleteBoard() {
        response = getBoardService().deleteABoardFromService(boardId);
    }

    @Then("The response actions in count {string}")
    public void checkActions(String count) {
        Assert.assertEquals(response.jsonPath().getList("id").size(), Integer.parseInt(count));
    }

    @And("The response should contain a valid board id")
    public void checkBoardId() {
        Assert.assertEquals(response.body().jsonPath().get("id"), boardId);
    }

    @And("The response should contain a valid board name {string}")
    public void checkBoardName(String boardName) {
        Assert.assertEquals(response.body().jsonPath().get("name"), boardName);
    }

    @And("The response should contain a valid label name {string}")
    public void checkLabelName(String labelName) {
        Assert.assertEquals(response.body().path("name"), labelName);
    }

    @And("The response should contain a valid color {string}")
    public void checkLabelColor(String color) {
        Assert.assertEquals(response.body().path("color"), color);
    }

    @And("The response should contain empty array")
    public void checkIsEmpty() {
        Assert.assertEquals(response.body().asString(), EXPECTED_RESULT);
    }
}
