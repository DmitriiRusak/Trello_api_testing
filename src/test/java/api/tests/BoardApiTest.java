package api.tests;

import api.base.BaseTest;
import api.base.PathParameters;
import api.base.PathParameters.ActionsEndPoints;
import api.base.PathParameters.BoardEndPoints;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.List;

import static api.base.PathParameters.CardsEndPoints.CARDS_BASE_PATH;
import static api.base.PathParameters.CheckListsPath.CHECKLISTS_BASE_PATH;
import static api.base.PathParameters.ListsPath.LISTS_BASE_PATH;
import static api.base.PathParameters.MembersPath.MEMBERS_BASE_PATH;
import static api.base.TestData.BoardTestData;
import static api.base.TestData.BoardTestData.*;

@Epic("API Tests")
@Feature("Board Validation")
@Owner("Group JavaForwardToOffer")
@Tag("api")
public class BoardApiTest extends BaseTest {

    @AfterClass
    public void tearDown(){
        getBoardSteps().deleteBoard(DEFIEND_PERMISSION_BOARD_ID);
    }

    @Test(priority = 1)
    @Story("Bord")
    @Description("Get list of user")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateABoardWithDefaultOptions() {
        Response response = getBoardSteps().createBoard(BoardTestData.BOARD_NAME);
        BoardTestData.boardId = response.jsonPath().getString("id");

        Assert.assertTrue(!response.jsonPath().getString("id").isEmpty());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 1)
    @Story("Bord")
    @Description("Get list of user")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateABoardWithPublicAccess() {

        Response response = getBoardSteps().createABoardWithDefinedPermissionLevel(BoardTestData.BOARD_NAME_CREATED_WITH_SPECIFIC_OPTIONS, PERMISSION_LEVEL_PUBLIC);
        BoardTestData.DEFIEND_PERMISSION_BOARD_ID = response.jsonPath().getString("id");

        Assert.assertTrue(!response.jsonPath().getString("id").isEmpty());
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("prefs.permissionLevel"), PERMISSION_LEVEL_PUBLIC);
    }

    @Test(priority = 2)
    @Story("Bord")
    @Description("Get board")
    @Severity(SeverityLevel.NORMAL)
    public void testGetBoard() {
        Response response = getBoardSteps().getBoard(BoardTestData.boardId);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().jsonPath().get("id").toString(), BoardTestData.boardId);
        Assert.assertEquals(response.body().jsonPath().get("name").toString(), BoardTestData.BOARD_NAME);
    }

    @Test(priority = 3)
    @Story("Bord")
    @Description("Create a List on a Board")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateAListOnABoard() {
        Response response = getBoardSteps().createListOnBoard(BoardTestData.boardId, NAME_FOR_LIST);
        BoardTestData.listId = response.path("id");

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 4)
    @Story("Bord")
    @Description("Create a Label on a Board")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateALabelOnABoard() {
        Response response = getBoardSteps().createLabelOnBoard(BoardTestData.boardId, NAME_FOR_A_LABEL, COLOR_OF_A_LABEL);
        BoardTestData.labelId = response.path("id").toString();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().path("name"), NAME_FOR_A_LABEL);
        Assert.assertEquals(response.body().path("color"), COLOR_OF_A_LABEL);
    }

    @Test(priority = 5)
    @Story("Bord")
    @Description("Get Labels on a Board")
    @Severity(SeverityLevel.NORMAL)
    public void testGetLabelsOnABoard() {
        Response response = getBoardSteps().getLabelsOnBoard(BoardTestData.boardId);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 5)
    @Story("Bord")
    @Description("get specific field from a board")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAFieldOnABord() {
        Response response = getBoardSteps().getAField(BoardTestData.boardId, FIELD_NAME);
        System.out.println(response.body().asString());
        Assert.assertEquals(response.jsonPath().getString("_value"), BoardTestData.BOARD_NAME);

    }

    @Test(priority = 5)
    @Story("Bord")
    @Description("Get actions from a board")
    @Severity(SeverityLevel.NORMAL)
    public void testGetActionsOfABoard() {

        Response response = getBoardSteps().getActions(BoardTestData.boardId, ActionsEndPoints.ACTIONS_BASE_PATH);
        List arrayList = response.jsonPath().getList("id");

        Assert.assertEquals(arrayList.size(), 3);
    }

    @Test(priority = 5)
    @Story("Bord")
    @Description("Get checklists from a board")
    @Severity(SeverityLevel.NORMAL)
    public void testGetChecklistsOnABoard() {
        Response response = getBoardSteps().getChecklists(BoardTestData.boardId, CHECKLISTS_BASE_PATH);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), EXPECTED_RESULT);
    }

    @Test(priority = 5)
    @Story("Bord")
    @Description("Get all existed cards from a bord")
    @Severity(SeverityLevel.NORMAL)
    public void testGetCardsOnABoard() {
        Response response = getBoardSteps().getCards(BoardTestData.boardId, CARDS_BASE_PATH);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), EXPECTED_RESULT);
    }

    @Test(priority = 5)
    @Story("Bord")
    @Description("Get all existed filtered cards from a bord")
    @Severity(SeverityLevel.NORMAL)
    public void testGetFilteredCardsOnABoard() {
        Response response = getBoardSteps().getFilteredCards(BoardTestData.boardId, CARDS_BASE_PATH, "all");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), EXPECTED_RESULT);
    }

    @Test(priority = 5)
    @Story("Bord")
    @Description("Get all existed custom fields from a bord")
    @Severity(SeverityLevel.NORMAL)
    public void testGetCustomFieldsForBoard() {
        Response response = getBoardSteps().getCustomFieldsForABoard(BoardTestData.boardId, PathParameters.CUSTOM_FIELDS_BASE_PATH);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), EXPECTED_RESULT);
    }

    @Test(priority = 5)
    @Story("Bord")
    @Description("Get all existed lists from a bord")
    @Severity(SeverityLevel.NORMAL)
    public void testGetListsOnABoard() {
        Response response = getBoardSteps().getListsOfABoard(BoardTestData.boardId, LISTS_BASE_PATH);
        List arrayList = response.jsonPath().getList("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(arrayList.size(), 4);
    }

    @Test(priority = 5)
    @Story("Bord")
    @Description("Get closed lists from a bord")
    @Severity(SeverityLevel.NORMAL)
    public void testGetFilteredListsOnABoard() {
        Response response = getBoardSteps().getFilteredListsOnABoard(BoardTestData.boardId, LISTS_BASE_PATH, NAME_OF_A_FILTER);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), EXPECTED_RESULT);
    }

    @Test(priority = 5)
    @Story("Bord")
    @Description("Get members of a bord")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTheMembersOfABoard() {
        Response response = getBoardSteps().getTheMembersOfABoard(BoardTestData.boardId);
        List arrayList = response.jsonPath().getList("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(arrayList.size(), 1);
    }

    @Test(priority = 5)
    @Story("Bord")
    @Description("Invite Member to Board via email")
    @Severity(SeverityLevel.NORMAL)
    public void testInviteMemberToBoardViaEmail() {
        Response response = getBoardSteps().inviteMemberToBoardViaEmail(BoardTestData.boardId, MEMBERS_BASE_PATH);
        List listOfMembers = response.jsonPath().getList("members.id");

        Assert.assertTrue(listOfMembers.size() == 2);
    }

    @Test(priority = 6)
    @Story("Bord")
    @Description("Update a board by giving a new name")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateABoard() {
        Response response = getBoardSteps().updateBoard(BoardTestData.boardId, NEW_NAME_FOR_A_BOARD);

        Assert.assertEquals(response.body().jsonPath().get("id").toString(), BoardTestData.boardId);
        Assert.assertEquals(response.body().jsonPath().get("name").toString(), NEW_NAME_FOR_A_BOARD);
    }

    @Test(priority = 6)
    @Story("Bord")
    @Description("Get boardStars on a Board")
    @Severity(SeverityLevel.NORMAL)
    public void testGetBoardStarsOnABoard() {
        Response response = getBoardSteps().getBoardStarsOnBoard(BoardTestData.boardId, BoardEndPoints.boardStarsEnPoint);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 6)
    @Story("Bord")
    @Description("Get memberships of a Board")
    @Severity(SeverityLevel.NORMAL)
    public void testGetMembershipsOfABoard() {
        Response response = getBoardSteps().getMembershipsOnBoard(BoardTestData.boardId);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 7)
    @Story("Bord")
    @Description("Remove member from board")
    @Severity(SeverityLevel.NORMAL)
    public void testRemoveMemberFromBoard() {
        Response membersResponse = getBoardSteps().getTheMembersOfABoard(BoardTestData.boardId);
        List<String> memberIds = membersResponse.jsonPath().getList("id"); // ИЛИ "members.id"
        String memberIdToRemove = memberIds.get(1);
        Response response = getBoardSteps().removeMemberFromBoard(BoardTestData.boardId, memberIdToRemove);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 8)
    @Story("Bord")
    @Description("Delete board")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteABoard() {
        Response response = getBoardSteps().deleteABoardFromService(BoardTestData.boardId);
        System.out.println(response.asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
