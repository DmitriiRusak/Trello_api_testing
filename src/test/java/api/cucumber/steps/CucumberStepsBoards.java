package api.cucumber.steps;

import api.base.BaseTest;
import api.base.TestData;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;

import static api.base.PathParameters.ActionsEndPoints.ACTIONS_BASE_PATH;
import static api.base.PathParameters.BoardEndPoints.boardStarsEnPoint;
import static api.base.PathParameters.CUSTOM_FIELDS_BASE_PATH;
import static api.base.PathParameters.CardsEndPoints.CARDS_BASE_PATH;
import static api.base.PathParameters.CheckListsPath.CHECKLISTS_BASE_PATH;
import static api.base.PathParameters.ListsPath.LISTS_BASE_PATH;
import static api.base.PathParameters.MembersPath.MEMBERS_BASE_PATH;
import static api.base.TestData.BoardTestData.EXPECTED_RESULT;
import static api.base.TestData.BoardTestData.boardId;
import static api.base.TestData.response;

public class CucumberStepsBoards extends BaseTest {

    @When("I create a new board with name {string}")
    public void createBoard(String boardName) {
        TestData.BoardTestData.boardName = boardName;
        response = getBoardSteps().createBoard(boardName);
        boardId = response.jsonPath().getString("id");
    }

    @When("I get the board with board id")
    public void getBoard() {
        response = getBoardSteps().getBoard(boardId);
    }

    @When("I create a list on a board with name {string}")
    public void createListOnBoard(String listName) {
        response = getBoardSteps().createListOnBoard(boardId, listName);
    }

    @When("I create a label on a board with name {string} and color {string}")
    public void createLabelOnBoard(String labelName, String color) {
        response = getBoardSteps().createLabelOnBoard(boardId, labelName, color);
    }

    @When("I get label on a board")
    public void getLabel() {
        response = getBoardSteps().getLabelsOnBoard(boardId);
    }

    @When("I get specific field with {string} from a board")
    public void getFieldFromBoard(String fieldName) {
        response = getBoardSteps().getAField(boardId, "/" + fieldName);
    }

    @When("I get actions from a board")
    public void getActionsOnBoard() {
        response = getBoardSteps().getActions(boardId, ACTIONS_BASE_PATH);
    }

    @When("I get checklists from a board")
    public void getChecklistsFromBoard() {
        response = getBoardSteps().getChecklists(boardId, CHECKLISTS_BASE_PATH);
    }

    @When("I get all existed cards from a bord")
    public void getCardsFromBoard() {
        response = getBoardSteps().getCards(boardId, CARDS_BASE_PATH);
    }

    @When("I get all existed filtered {string} cards from a bord")
    public void getFilteredCardsFromBoard(String filter) {
        response = getBoardSteps().getFilteredCards(boardId, CARDS_BASE_PATH, filter);
    }

    @When("I get all existed custom fields from a bord")
    public void getCustomFieldsFomBoard() {
        response = getBoardSteps().getCustomFieldsForABoard(boardId, CUSTOM_FIELDS_BASE_PATH);
    }

    @When("I get {string} lists from a bord")
    public void getListsFromBoard(String filter) {
        response = getBoardSteps().getFilteredListsOnABoard(boardId, LISTS_BASE_PATH, filter);
    }

    @When("I get members of a bord")
    public void getMembersOfBoard() {
        response = getBoardSteps().getMembershipsOnBoard(boardId);
    }

    @When("I invite Member to Board via email")
    public void getInviteMembers() {
        response = getBoardSteps().inviteMemberToBoardViaEmail(boardId, MEMBERS_BASE_PATH);
    }

    @When("I update a board by giving a new name {string}")
    public void updateNameBoard(String newNameBoard) {
        response = getBoardSteps().updateBoard(boardId, newNameBoard);
    }

    @When("I get boardStars on a Board")
    public void getBoardStarsBoard() {
        response = getBoardSteps().getBoardStarsOnBoard(boardId, boardStarsEnPoint);
    }

    @When("I get memberships of a Board")
    public void getMembershipsBoard() {
        response = getBoardSteps().getMembershipsOnBoard(boardId);
    }

    @When("I remove member from board")
    public void removeMemberFromBoard() {
        Response membersResponse = getBoardSteps().getTheMembersOfABoard(TestData.BoardTestData.boardId);
        List<String> memberIds = membersResponse.jsonPath().getList("id"); // ИЛИ "members.id"
        String memberIdToRemove = memberIds.get(1);
        Response response = getBoardSteps().removeMemberFromBoard(TestData.BoardTestData.boardId, memberIdToRemove);
    }

    @When("I delete the created board")
    public void deleteBoard() {
        response = getBoardSteps().deleteABoardFromService(boardId);
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
