package api.tests;

import api.resourcesForTests.configurationData.ConfigTestData;
import api.services.BoardService;
import api.utils.LogFactory;
import api.utils.TestListener;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

@Epic("API Tests")
@Feature("Board")
@Tag("api")
@Listeners(TestListener.class)
public class BoardApiTest{

    private ConfigTestData configTestData = new ConfigTestData();
    private final BoardService boardService = new BoardService();

    @BeforeSuite
    public void setUpLogs(){
        LogFactory.getLogger().info(" ");
        LogFactory.getLogger().info("******************************  New run for RestAssured + TestNG framework has been initialized  *************************");
    }

    @BeforeClass
    public void setUp(){

        LogFactory.getLogger().info("+++++++++++++++ class \uD83D\uDFE1" + this.getClass().getName() + "\uD83D\uDFE1 started +++++++++++++++");
    }

    @AfterClass
    public void tearDown(){
        boardService.deleteBoard(configTestData.getBoardId());
    }

    @Test(priority = 0)
    @Description("Create a bord with default options")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateABoardWithDefaultOptions() {
        Response response = boardService.createBoard(configTestData.BOARD_NAME);
        configTestData.setBoardId(response.jsonPath().getString("id"));

        Assert.assertFalse(response.jsonPath().getString("id").isEmpty());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 1)
    @Description("Create a board with public access")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateABoardWithPublicAccess() {

        Response response = boardService.createABoardWithDefinedPermissionLevel(configTestData.BOARD_NAME_CREATED_WITH_SPECIFIC_OPTIONS, configTestData.PERMISSION_LEVEL_PUBLIC);

        configTestData.setDefiendPermissionBoardId(response.jsonPath().getString("id"));

        Assert.assertFalse(response.jsonPath().getString("id").isEmpty());
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("prefs.permissionLevel"), configTestData.PERMISSION_LEVEL_PUBLIC);
        boardService.deleteBoard(response.jsonPath().getString("id"));
    }

    @Test(priority = 2)
    @Description("Get a board")
    @Severity(SeverityLevel.NORMAL)
    public void testGetBoard() {
        Response response = boardService.getBoard(configTestData.getBoardId());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().jsonPath().get("id").toString(), configTestData.getBoardId());
        Assert.assertEquals(response.body().jsonPath().get("name").toString(), configTestData.BOARD_NAME);
    }

    @Test(priority = 3)
    @Description("Create a List on a Board")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateAListOnABoard() {
        Response response = boardService.createListOnBoard(configTestData.getBoardId(), configTestData.NAME_FOR_LIST);
        configTestData.setListId(response.path("id"));
        String actualNameOfTheList = response.jsonPath().getString("name");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(actualNameOfTheList, configTestData.NAME_FOR_LIST);
    }

    @Test(priority = 4)
    @Description("Create a Label on a Board")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateALabelOnABoard() {
        Response response = boardService.createLabelOnBoard(configTestData.getBoardId(), configTestData.NAME_FOR_A_LABEL, configTestData.COLOR_OF_A_LABEL);
        configTestData.setLabelId(response.path("id").toString());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().path("name"), configTestData.NAME_FOR_A_LABEL);
        Assert.assertEquals(response.body().path("color"), configTestData.COLOR_OF_A_LABEL);
    }

    @Test(priority = 5)
    @Description("Get Labels on a Board")
    @Severity(SeverityLevel.NORMAL)
    public void testGetLabelsOnABoard() {
        Response response = boardService.getLabelsOnBoard(configTestData.getBoardId());

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 6)
    @Description("Get specific field from a board")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAFieldOnABord() {
        Response response = boardService.getAField(configTestData.getBoardId(), configTestData.FIELD_NAME);

        Assert.assertEquals(response.jsonPath().getString("_value"), configTestData.BOARD_NAME);
    }

    @Test(priority = 7)
    @Description("Get actions from a board")
    @Severity(SeverityLevel.NORMAL)
    public void testGetActionsOfABoard() {

        Response response = boardService.getActionsOfABoard(configTestData.getBoardId());
        List arrayList = response.jsonPath().getList("id");

        Assert.assertEquals(arrayList.size(), 3);
    }

    @Test(priority = 8)
    @Description("Get checklists from a board")
    @Severity(SeverityLevel.NORMAL)
    public void testGetChecklistsOnABoard() {
        Response response = boardService.getChecklists(configTestData.getBoardId());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), configTestData.EXPECTED_RESULT);
    }

    @Test(priority = 9)
    @Description("Get all existed cards from a bord")
    @Severity(SeverityLevel.NORMAL)
    public void testGetCardsOnABoard() {
        Response response = boardService.getCardsOfABoard(configTestData.getBoardId());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), configTestData.EXPECTED_RESULT);
    }

    @Test(priority = 10)
    @Description("Get all existed filtered cards from a bord")
    @Severity(SeverityLevel.NORMAL)
    public void testGetFilteredCardsOnABoard() {
        Response response = boardService.getFilteredCardsOfABoard(configTestData.getBoardId(), "all");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), configTestData.EXPECTED_RESULT);
    }

    @Test(priority = 11)
    @Description("Get all existed custom fields from a bord")
    @Severity(SeverityLevel.NORMAL)
    public void testGetCustomFieldsForBoard() {
        Response response = boardService.getCustomFieldsOfAABoard(configTestData.getBoardId());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), configTestData.EXPECTED_RESULT);
    }

    @Test(priority = 12)
    @Description("Get all existed lists from a bord")
    @Severity(SeverityLevel.NORMAL)
    public void testGetListsOnABoard() {
        Response response = boardService.getListsOfABoard(configTestData.getBoardId());
        List arrayList = response.jsonPath().getList("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(arrayList.size(), 4);
    }

    @Test(priority = 13)
    @Description("Get closed lists from a bord")
    @Severity(SeverityLevel.NORMAL)
    public void testGetFilteredListsOnABoard() {
        Response response = boardService.getFilteredListsOfABoard(configTestData.getBoardId(), configTestData.NAME_OF_A_FILTER);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), configTestData.EXPECTED_RESULT);
    }

    @Test(priority = 14)
    @Description("Get members of a bord")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTheMembersOfABoard() {
        Response response = boardService.getTheMembersOfABoard(configTestData.getBoardId());
        List arrayList = response.jsonPath().getList("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(arrayList.size(), 1);
    }

    @Test(priority = 15)
    @Description("Invite Member to a Board via email")
    @Severity(SeverityLevel.NORMAL)
    public void testInviteMemberToBoardViaEmail() {
        Response response = boardService.inviteMemberToBoardViaEmail(configTestData.getBoardId());
        List listOfMembers = response.jsonPath().getList("members.id");

        Assert.assertTrue(listOfMembers.size() == 2);
    }

    @Test(priority = 16)
    @Description("Update a board by giving a new name")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateABoard() {
        Response response = boardService.updateBoard(configTestData.getBoardId(), configTestData.FIELD_NAME, configTestData.NEW_NAME_FOR_A_BOARD);

        Assert.assertEquals(response.body().jsonPath().get("id").toString(), configTestData.getBoardId());
        Assert.assertEquals(response.jsonPath().get("name").toString(), configTestData.NEW_NAME_FOR_A_BOARD);
    }

    @Test(priority = 17)
    @Description("Get boardStars on a Board")
    @Severity(SeverityLevel.NORMAL)
    public void testGetBoardStarsOnABoard() {
        Response response = boardService.getBoardStarsOfABoard(configTestData.getBoardId());

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 18)
    @Description("Get memberships of a Board")
    @Severity(SeverityLevel.NORMAL)
    public void testGetMembershipsOfABoard() {
        Response response = boardService.getMembershipsOfABoard(configTestData.getBoardId());

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 19)
    @Description("Remove member from a board")
    @Severity(SeverityLevel.NORMAL)
    public void testRemoveMemberFromBoard() {
        Response membersResponse = boardService.getTheMembersOfABoard(configTestData.getBoardId());
        List<String> memberIds = membersResponse.jsonPath().getList("id"); // ИЛИ "members.id"
        String memberIdToRemove = memberIds.get(1);
        Response response = boardService.removeMemberFromBoard(configTestData.getBoardId(), memberIdToRemove);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 20)
    @Description("Delete board")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteABoard() {
        Response response = boardService.deleteABoardFromService(configTestData.getBoardId());
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
