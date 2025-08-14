package api.tests;

import api.services.ServiceWorkShop;
import api.utils.LogFactory;
import api.utils.TestListener;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;
import api.resourcesForTests.ConfigurationDataForApiTests.*;

import java.util.List;

@Epic("API Tests")
@Feature("Members")
@Listeners(TestListener.class)
public class MembersAPITest extends ServiceWorkShop {

    @BeforeClass
    public void setUp() {

        LogFactory.getLogger().info("+++++++++++++++ class \uD83D\uDFE1" + this.getClass().getName() + "\uD83D\uDFE1 started +++++++++++++++");
        BoardTestData.boardId = getMembersService().createABord(MembersTestData.BOARD_NAME_FOR_MEMBERS);
        MembersTestData.firstMemberId = getMembersService().getTheMembersOfABoard(BoardTestData.boardId).jsonPath().getString("id");
        MembersTestData.firstMemberId = MembersTestData.firstMemberId.substring(1, MembersTestData.firstMemberId.length() - 1);
    }

    @AfterClass
    public void tearDown() {
        getMembersService().deleteBoard(BoardTestData.boardId);
    }

    @Test(priority = 0)
    @Description("Get a member")
    @Severity(SeverityLevel.NORMAL)
    public void testGetAMember() {
        Response response = getMembersService().getAMember(MembersTestData.firstMemberId);
        String memberIdReceivedBack = response.jsonPath().getString("id");

        Assert.assertEquals(memberIdReceivedBack, MembersTestData.firstMemberId);
    }

    @Test(priority = 1)
    @Description("Get actions of a member")
    @Severity(SeverityLevel.NORMAL)
    public void testGetMembersActions() {
        Response response = getMembersService().getMembersActions(MembersTestData.firstMemberId);
        List actionsIds = response.jsonPath().getList("id");

        Assert.assertTrue(actionsIds.size()>1);
    }

    @Test(priority = 1)
    @Description("Get a member's custom board backgrounds")
    @Severity(SeverityLevel.NORMAL)
    public void testGetMemberCustomBackgrounds() {
        Response response = getMembersService().getMemberCustomBackgrounds(MembersTestData.firstMemberId);

        List membersBackgroundId = response.jsonPath().getList("id");

        MembersTestData.backgroundId = membersBackgroundId.get(0).toString();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(membersBackgroundId.size() > 5);
    }

    @Test(priority = 2)
    @Description("Create star for a board on behalf of a Member")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateStarForBoard() {

        Response response = getMembersService().createStarForABoard(MembersTestData.firstMemberId, BoardTestData.boardId, MembersTestData.POSITION);
        String idOfABoardTheStarIsOn = response.jsonPath().getString("idBoard");
        MembersTestData.starId = response.body().jsonPath().getString("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(idOfABoardTheStarIsOn, BoardTestData.boardId);
    }

    @Test(priority = 3)
    @Description("Get a specific boardStar")
    @Severity(SeverityLevel.NORMAL)
    public void testGetBoardStarOfAMember() {
        Response response = getMembersService().getBoardStarOfAMember(MembersTestData.firstMemberId, MembersTestData.starId);
        String idOfABoardStar = response.jsonPath().getString("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(idOfABoardStar, MembersTestData.starId);
    }

    @Test(priority = 4)
    @Description("Update the position of a board star ")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateThePositionOfABoardStarOfAMember() {
        String positionOfABoardStarBeforeUpdate = getMembersService().getBoardStarOfAMember(MembersTestData.firstMemberId, MembersTestData.starId).jsonPath().getString("pos");
        Response response = getMembersService().updateThePositionOfABoardStarOfAMember(MembersTestData.firstMemberId, MembersTestData.starId, MembersTestData.UPDATE_POSITION);
        String positionOfABoardStarAfterUpdate = response.jsonPath().getString("pos");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotEquals(positionOfABoardStarBeforeUpdate, positionOfABoardStarAfterUpdate);
    }

    @Test(priority = 5)
    @Description("Delete a star of a board")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteStarOfABoard() {
        Response response = getMembersService().deleteStarBoard(MembersTestData.firstMemberId, MembersTestData.starId);

        Response response1 =  getMembersService().getBoardStarOfAMember(MembersTestData.firstMemberId, MembersTestData.starId);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response1.asPrettyString(), MembersTestData.BOARD_STAR_NOT_FOUND_MESSAGE);
    }

    @Test(priority = 6)
    @Description("Get all the boards that the user is a member of")
    @Severity(SeverityLevel.NORMAL)
    public void testGetBoardsThatMemberBelongTo(){
        Response response = getMembersService().getBoardsMemberBelongs(MembersTestData.firstMemberId);

        List idOfBoardsThatBelongToMember = response.jsonPath().getList("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertFalse(idOfBoardsThatBelongToMember.isEmpty());

    }

    @Test(priority = 6)
    @Description("Get the boards the member has been invited to")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTheBoardsTheMemberInvitedTo() {
        Response response = getMembersService().getTheBoardsTheMemberInvitedTo(MembersTestData.firstMemberId);

        List idOfBoardsTheMemberInvitedTo = response.jsonPath().getList("id");


        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(idOfBoardsTheMemberInvitedTo.isEmpty());

    }

    @Test(priority = 6)
    @Description("Get the cards of a member")
    @Severity(SeverityLevel.NORMAL)
    public void testGetCardsOfAMember() {
        Response response = getMembersService().getCardsOfAMember(MembersTestData.firstMemberId);

        List idOfCardsTheMemberIsOn = response.jsonPath().getList("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(idOfCardsTheMemberIsOn.isEmpty());
    }
}
