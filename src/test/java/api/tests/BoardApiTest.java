package api.tests;

import api.resourcesForTests.configurationData.BoardTestData;
import api.resourcesForTests.configurationData.CommonConfigData;
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

    private final BoardTestData boardTestData = new BoardTestData();
    private final BoardService boardService = new BoardService();

    @BeforeClass
    public void setUp(){

        LogFactory.getLogger().info("+++++++++++++++ class \uD83D\uDFE1" + this.getClass().getName() + "\uD83D\uDFE1 started +++++++++++++++");
    }

    @AfterClass
    public void tearDown(){
        boardService.deleteBoard(boardTestData.getBoardId());
    }

    @Test(priority = 0)
    @Description("Create a bord with default options")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateABoardWithDefaultOptions() {
        Response response = boardService.createBoard(boardTestData.getBOARD_NAME());
        boardTestData.setBoardId(response.jsonPath().getString("id"));

        Assert.assertFalse(response.jsonPath().getString("id").isEmpty());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 1)
    @Description("Create a board with public access")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateABoardWithPublicAccess() {

        Response response = boardService.createABoardWithDefinedPermissionLevel(boardTestData.getBOARD_NAME_CREATED_WITH_SPECIFIC_OPTIONS(), boardTestData.getPERMISSION_LEVEL_PUBLIC());

        boardTestData.setDefiendPermissionBoardId(response.jsonPath().getString("id"));

        Assert.assertFalse(response.jsonPath().getString("id").isEmpty());
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("prefs.permissionLevel"), boardTestData.getPERMISSION_LEVEL_PUBLIC());
        boardService.deleteBoard(response.jsonPath().getString("id"));
    }

    @Test(priority = 2)
    @Description("Get a board")
    @Severity(SeverityLevel.NORMAL)
    public void testGetBoard() {
        Response response = boardService.getBoard(boardTestData.getBoardId());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().jsonPath().get("id").toString(), boardTestData.getBoardId());
        Assert.assertEquals(response.body().jsonPath().get("name").toString(), boardTestData.getBOARD_NAME());
    }

    @Test(priority = 3)
    @Description("Create a List on a Board")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateAListOnABoard() {
        Response response = boardService.createListOnBoard(boardTestData.getBoardId(), boardTestData.getNAME_FOR_LIST());
        boardTestData.setListId(response.path("id"));
        String actualNameOfTheList = response.jsonPath().getString("name");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(actualNameOfTheList, boardTestData.getNAME_FOR_LIST());
    }

    @Test(priority = 4)
    @Description("Create a Label on a Board")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateALabelOnABoard() {
        Response response = boardService.createLabelOnBoard(boardTestData.getBoardId(), boardTestData.getNAME_FOR_A_LABEL(), boardTestData.getCOLOR_OF_A_LABEL());
        boardTestData.setLabelId(response.path("id").toString());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().path("name"), boardTestData.getNAME_FOR_A_LABEL());
        Assert.assertEquals(response.body().path("color"), boardTestData.getCOLOR_OF_A_LABEL());
    }

    @Test(priority = 5)
    @Description("Get Labels on a Board")
    @Severity(SeverityLevel.NORMAL)
    public void testGetLabelsOnABoard() {
        Response response = boardService.getLabelsOnBoard(boardTestData.getBoardId());

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 6)
    @Description("Get specific field from a board")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAFieldOnABord() {
        Response response = boardService.getAField(boardTestData.getBoardId(), boardTestData.getFIELD_NAME());

        Assert.assertEquals(response.jsonPath().getString("_value"), boardTestData.getBOARD_NAME());
    }

    @Test(priority = 7)
    @Description("Get actions from a board")
    @Severity(SeverityLevel.NORMAL)
    public void testGetActionsOfABoard() {

        Response response = boardService.getActionsOfABoard(boardTestData.getBoardId());
        List arrayList = response.jsonPath().getList("id");

        Assert.assertEquals(arrayList.size(), 3);
    }

    @Test(priority = 8)
    @Description("Get checklists from a board")
    @Severity(SeverityLevel.NORMAL)
    public void testGetChecklistsOnABoard() {
        Response response = boardService.getChecklists(boardTestData.getBoardId());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), boardTestData.getEXPECTED_RESULT());
    }

    @Test(priority = 9)
    @Description("Get all existed cards from a bord")
    @Severity(SeverityLevel.NORMAL)
    public void testGetCardsOnABoard() {
        Response response = boardService.getCardsOfABoard(boardTestData.getBoardId());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), boardTestData.getEXPECTED_RESULT());
    }

    @Test(priority = 10)
    @Description("Get all existed filtered cards from a bord")
    @Severity(SeverityLevel.NORMAL)
    public void testGetFilteredCardsOnABoard() {
        Response response = boardService.getFilteredCardsOfABoard(boardTestData.getBoardId(), "all");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), boardTestData.getEXPECTED_RESULT());
    }

    @Test(priority = 11)
    @Description("Get all existed custom fields from a bord")
    @Severity(SeverityLevel.NORMAL)
    public void testGetCustomFieldsForBoard() {
        Response response = boardService.getCustomFieldsOfAABoard(boardTestData.getBoardId());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), boardTestData.getEXPECTED_RESULT());
    }

    @Test(priority = 12)
    @Description("Get all existed lists from a bord")
    @Severity(SeverityLevel.NORMAL)
    public void testGetListsOnABoard() {
        Response response = boardService.getListsOfABoard(boardTestData.getBoardId());
        List arrayList = response.jsonPath().getList("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(arrayList.size(), 4);
    }

    @Test(priority = 13)
    @Description("Get closed lists from a bord")
    @Severity(SeverityLevel.NORMAL)
    public void testGetFilteredListsOnABoard() {
        Response response = boardService.getFilteredListsOfABoard(boardTestData.getBoardId(), boardTestData.getNAME_OF_A_FILTER());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), boardTestData.getEXPECTED_RESULT());
    }

    @Test(priority = 14)
    @Description("Get members of a bord")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTheMembersOfABoard() {
        Response response = boardService.getTheMembersOfABoard(boardTestData.getBoardId());
        List arrayList = response.jsonPath().getList("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(arrayList.size(), 1);
    }

    @Test(priority = 15)
    @Description("Invite Member to a Board via email")
    @Severity(SeverityLevel.NORMAL)
    public void testInviteMemberToBoardViaEmail() {
        Response response = boardService.inviteMemberToBoardViaEmail(boardTestData.getBoardId());
        List listOfMembers = response.jsonPath().getList("members.id");

        Assert.assertTrue(listOfMembers.size() == 2);
    }

    @Test(priority = 16)
    @Description("Update a board by giving a new name")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateABoard() {
        Response response = boardService.updateBoard(boardTestData.getBoardId(), boardTestData.getFIELD_NAME(), boardTestData.getNEW_NAME_FOR_A_BOARD());

        Assert.assertEquals(response.body().jsonPath().get("id").toString(), boardTestData.getBoardId());
        Assert.assertEquals(response.jsonPath().get("name").toString(), boardTestData.getNEW_NAME_FOR_A_BOARD());
    }

    @Test(priority = 17)
    @Description("Get boardStars on a Board")
    @Severity(SeverityLevel.NORMAL)
    public void testGetBoardStarsOnABoard() {
        Response response = boardService.getBoardStarsOfABoard(boardTestData.getBoardId());

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 18)
    @Description("Get memberships of a Board")
    @Severity(SeverityLevel.NORMAL)
    public void testGetMembershipsOfABoard() {
        Response response = boardService.getMembershipsOfABoard(boardTestData.getBoardId());

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 19)
    @Description("Remove member from a board")
    @Severity(SeverityLevel.NORMAL)
    public void testRemoveMemberFromBoard() {
        Response membersResponse = boardService.getTheMembersOfABoard(boardTestData.getBoardId());
        List<String> memberIds = membersResponse.jsonPath().getList("id"); // ИЛИ "members.id"
        String memberIdToRemove = memberIds.get(1);
        Response response = boardService.removeMemberFromBoard(boardTestData.getBoardId(), memberIdToRemove);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 20)
    @Description("Delete board")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteABoard() {
        Response response = boardService.deleteABoardFromService(boardTestData.getBoardId());
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
