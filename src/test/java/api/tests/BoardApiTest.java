package api.tests;

import api.services.ServiceWorkShop;
import api.resourcesForTests.ConfigurationDataForApiTests;
import api.utils.LogFactory;
import api.utils.TestListener;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

import static api.resourcesForTests.ConfigurationDataForApiTests.BoardTestData;
import static api.resourcesForTests.ConfigurationDataForApiTests.BoardTestData.*;

@Epic("API Tests")
@Feature("Board")
@Tag("api")
@Listeners(TestListener.class)
public class BoardApiTest extends ServiceWorkShop {

    @BeforeClass
    public void setUp(){

        LogFactory.getLogger().info("+++++++++++++++ class \uD83D\uDFE1" + this.getClass().getName() + "\uD83D\uDFE1 started +++++++++++++++");
    }

    @AfterClass
    public void tearDown(){
        getBoardService().deleteBoard(DefiendPermissionBoardId);
    }

    @Test(priority = 0)
    @Description("Create a bord with default options")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateABoardWithDefaultOptions() {
        Response response = getBoardService().createBoard(BoardTestData.BOARD_NAME);
        BoardTestData.boardId = response.jsonPath().getString("id");

        Assert.assertTrue(!response.jsonPath().getString("id").isEmpty());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 1)
    @Description("Create a board with public access")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateABoardWithPublicAccess() {

        Response response = getBoardService().createABoardWithDefinedPermissionLevel(BoardTestData.BOARD_NAME_CREATED_WITH_SPECIFIC_OPTIONS, PERMISSION_LEVEL_PUBLIC);
        BoardTestData.DefiendPermissionBoardId = response.jsonPath().getString("id");

        Assert.assertFalse(response.jsonPath().getString("id").isEmpty());
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("prefs.permissionLevel"), PERMISSION_LEVEL_PUBLIC);
    }

    @Test(priority = 2)
    @Description("Get a board")
    @Severity(SeverityLevel.NORMAL)
    public void testGetBoard() {
        Response response = getBoardService().getBoard(BoardTestData.boardId);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().jsonPath().get("id").toString(), BoardTestData.boardId);
        Assert.assertEquals(response.body().jsonPath().get("name").toString(), BoardTestData.BOARD_NAME);
    }

    @Test(priority = 3)
    @Description("Create a List on a Board")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateAListOnABoard() {
        Response response = getBoardService().createListOnBoard(BoardTestData.boardId, NAME_FOR_LIST);
        BoardTestData.listId = response.path("id");
        String actualNameOfTheList = response.jsonPath().getString("name");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(actualNameOfTheList, NAME_FOR_LIST);
    }

    @Test(priority = 4)
    @Description("Create a Label on a Board")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateALabelOnABoard() {
        Response response = getBoardService().createLabelOnBoard(BoardTestData.boardId, NAME_FOR_A_LABEL, COLOR_OF_A_LABEL);
        BoardTestData.labelId = response.path("id").toString();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().path("name"), NAME_FOR_A_LABEL);
        Assert.assertEquals(response.body().path("color"), COLOR_OF_A_LABEL);
    }

    @Test(priority = 5)
    @Description("Get Labels on a Board")
    @Severity(SeverityLevel.NORMAL)
    public void testGetLabelsOnABoard() {
        Response response = getBoardService().getLabelsOnBoard(BoardTestData.boardId);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 5)
    @Description("Get specific field from a board")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAFieldOnABord() {
        Response response = getBoardService().getAField(BoardTestData.boardId, ConfigurationDataForApiTests.FIELD_NAME);

        Assert.assertEquals(response.jsonPath().getString("_value"), BoardTestData.BOARD_NAME);
    }

    @Test(priority = 5)
    @Description("Get actions from a board")
    @Severity(SeverityLevel.NORMAL)
    public void testGetActionsOfABoard() {

        Response response = getBoardService().getActionsOfABoard(BoardTestData.boardId);
        List arrayList = response.jsonPath().getList("id");

        Assert.assertEquals(arrayList.size(), 3);
    }

    @Test(priority = 5)
    @Description("Get checklists from a board")
    @Severity(SeverityLevel.NORMAL)
    public void testGetChecklistsOnABoard() {
        Response response = getBoardService().getChecklists(BoardTestData.boardId);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), EXPECTED_RESULT);
    }

    @Test(priority = 5)
    @Description("Get all existed cards from a bord")
    @Severity(SeverityLevel.NORMAL)
    public void testGetCardsOnABoard() {
        Response response = getBoardService().getCardsOfABoard(BoardTestData.boardId);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), EXPECTED_RESULT);
    }

    @Test(priority = 5)
    @Description("Get all existed filtered cards from a bord")
    @Severity(SeverityLevel.NORMAL)
    public void testGetFilteredCardsOnABoard() {
        Response response = getBoardService().getFilteredCardsOfABoard(BoardTestData.boardId, "all");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), EXPECTED_RESULT);
    }

    @Test(priority = 5)
    @Description("Get all existed custom fields from a bord")
    @Severity(SeverityLevel.NORMAL)
    public void testGetCustomFieldsForBoard() {
        Response response = getBoardService().getCustomFieldsOfAABoard(BoardTestData.boardId);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), EXPECTED_RESULT);
    }

    @Test(priority = 5)
    @Description("Get all existed lists from a bord")
    @Severity(SeverityLevel.NORMAL)
    public void testGetListsOnABoard() {
        Response response = getBoardService().getListsOfABoard(BoardTestData.boardId);
        List arrayList = response.jsonPath().getList("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(arrayList.size(), 4);
    }

    @Test(priority = 5)
    @Description("Get closed lists from a bord")
    @Severity(SeverityLevel.NORMAL)
    public void testGetFilteredListsOnABoard() {
        Response response = getBoardService().getFilteredListsOfABoard(BoardTestData.boardId, NAME_OF_A_FILTER);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), EXPECTED_RESULT);
    }

    @Test(priority = 5)
    @Description("Get members of a bord")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTheMembersOfABoard() {
        Response response = getBoardService().getTheMembersOfABoard(BoardTestData.boardId);
        List arrayList = response.jsonPath().getList("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(arrayList.size(), 1);
    }

    @Test(priority = 5)
    @Description("Invite Member to a Board via email")
    @Severity(SeverityLevel.NORMAL)
    public void testInviteMemberToBoardViaEmail() {
        Response response = getBoardService().inviteMemberToBoardViaEmail(BoardTestData.boardId);
        List listOfMembers = response.jsonPath().getList("members.id");

        Assert.assertTrue(listOfMembers.size() == 2);
    }

    @Test(priority = 6)
    @Description("Update a board by giving a new name")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateABoard() {
        Response response = getBoardService().updateBoard(BoardTestData.boardId, ConfigurationDataForApiTests.FIELD_NAME, NEW_NAME_FOR_A_BOARD);

        Assert.assertEquals(response.body().jsonPath().get("id").toString(), BoardTestData.boardId);
        Assert.assertEquals(response.jsonPath().get("name").toString(), NEW_NAME_FOR_A_BOARD);
    }

    @Test(priority = 6)
    @Description("Get boardStars on a Board")
    @Severity(SeverityLevel.NORMAL)
    public void testGetBoardStarsOnABoard() {
        Response response = getBoardService().getBoardStarsOfABoard(BoardTestData.boardId);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 6)
    @Description("Get memberships of a Board")
    @Severity(SeverityLevel.NORMAL)
    public void testGetMembershipsOfABoard() {
        Response response = getBoardService().getMembershipsOfABoard(BoardTestData.boardId);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 7)
    @Description("Remove member from a board")
    @Severity(SeverityLevel.NORMAL)
    public void testRemoveMemberFromBoard() {
        Response membersResponse = getBoardService().getTheMembersOfABoard(BoardTestData.boardId);
        List<String> memberIds = membersResponse.jsonPath().getList("id"); // ИЛИ "members.id"
        String memberIdToRemove = memberIds.get(1);
        Response response = getBoardService().removeMemberFromBoard(BoardTestData.boardId, memberIdToRemove);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 8)
    @Description("Delete board")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteABoard() {
        Response response = getBoardService().deleteABoardFromService(BoardTestData.boardId);
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
