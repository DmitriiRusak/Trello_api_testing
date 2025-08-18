package api.tests;

import api.resourcesForTests.configurationData.MemberTestData;
import api.services.ServiceWorkShop;
import api.utils.LogFactory;
import api.utils.TestListener;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;
import api.resourcesForTests.configurationData.CommonConfigData.*;

import java.util.List;

@Epic("API Tests")
@Feature("Members")
@Listeners(TestListener.class)
public class MembersAPITest extends ServiceWorkShop {

    private MemberTestData memberTestData = new MemberTestData();

    @BeforeClass
    public void setUp() {

        LogFactory.getLogger().info("+++++++++++++++ class \uD83D\uDFE1" + this.getClass().getName() + "\uD83D\uDFE1 started +++++++++++++++");
        memberTestData.setBoardId(getMembersService().createABord(memberTestData.BOARD_NAME_FOR_MEMBERS));
        memberTestData.setFirstMemberId(getMembersService().getTheMembersOfABoard(memberTestData.getBoardId()).jsonPath().getString("id"));
        memberTestData.setFirstMemberId(memberTestData.getFirstMemberId().substring(1, memberTestData.getFirstMemberId().length() - 1));
    }

    @AfterClass
    public void tearDown() {
        getMembersService().deleteBoard(memberTestData.getBoardId());
    }

    @Test(priority = 0)
    @Description("Get a member")
    @Severity(SeverityLevel.NORMAL)
    public void testGetAMember() {
        Response response = getMembersService().getAMember(memberTestData.getFirstMemberId());
        String memberIdReceivedBack = response.jsonPath().getString("id");

        Assert.assertEquals(memberIdReceivedBack, memberTestData.getFirstMemberId());
    }

    @Test(priority = 1)
    @Description("Get actions of a member")
    @Severity(SeverityLevel.NORMAL)
    public void testGetMembersActions() {
        Response response = getMembersService().getMembersActions(memberTestData.getFirstMemberId());
        List actionsIds = response.jsonPath().getList("id");

        Assert.assertTrue(actionsIds.size()>1);
    }

    @Test(priority = 1)
    @Description("Get a member's custom board backgrounds")
    @Severity(SeverityLevel.NORMAL)
    public void testGetMemberCustomBackgrounds() {
        Response response = getMembersService().getMemberCustomBackgrounds(memberTestData.getFirstMemberId());

        List membersBackgroundId = response.jsonPath().getList("id");

        memberTestData.setBackgroundId(membersBackgroundId.get(0).toString());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(membersBackgroundId.size() > 5);
    }

    @Test(priority = 2)
    @Description("Create star for a board on behalf of a Member")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateStarForBoard() {

        Response response = getMembersService().createStarForABoard(memberTestData.getFirstMemberId(), memberTestData.getBoardId(), memberTestData.POSITION);
        String idOfABoardTheStarIsOn = response.jsonPath().getString("idBoard");
        memberTestData.setStarId(response.body().jsonPath().getString("id"));

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(idOfABoardTheStarIsOn, memberTestData.getBoardId());
    }

    @Test(priority = 3)
    @Description("Get a specific boardStar")
    @Severity(SeverityLevel.NORMAL)
    public void testGetBoardStarOfAMember() {
        Response response = getMembersService().getBoardStarOfAMember(memberTestData.getFirstMemberId(), memberTestData.getStarId());
        String idOfABoardStar = response.jsonPath().getString("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(idOfABoardStar, memberTestData.getStarId());
    }

    @Test(priority = 4)
    @Description("Update the position of a board star ")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateThePositionOfABoardStarOfAMember() {
        String positionOfABoardStarBeforeUpdate = getMembersService().getBoardStarOfAMember(memberTestData.getFirstMemberId(), memberTestData.getStarId()).jsonPath().getString("pos");
        Response response = getMembersService().updateThePositionOfABoardStarOfAMember(memberTestData.getFirstMemberId(), memberTestData.getStarId(), memberTestData.UPDATE_POSITION);
        String positionOfABoardStarAfterUpdate = response.jsonPath().getString("pos");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotEquals(positionOfABoardStarBeforeUpdate, positionOfABoardStarAfterUpdate);
    }

    @Test(priority = 5)
    @Description("Delete a star of a board")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteStarOfABoard() {
        Response response = getMembersService().deleteStarBoard(memberTestData.getFirstMemberId(), memberTestData.getStarId());

        Response response1 =  getMembersService().getBoardStarOfAMember(memberTestData.getFirstMemberId(), memberTestData.getStarId());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response1.asPrettyString(), memberTestData.BOARD_STAR_NOT_FOUND_MESSAGE);
    }

    @Test(priority = 6)
    @Description("Get all the boards that the user is a member of")
    @Severity(SeverityLevel.NORMAL)
    public void testGetBoardsThatMemberBelongTo(){
        Response response = getMembersService().getBoardsMemberBelongs(memberTestData.getFirstMemberId());

        List idOfBoardsThatBelongToMember = response.jsonPath().getList("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertFalse(idOfBoardsThatBelongToMember.isEmpty());

    }

    @Test(priority = 6)
    @Description("Get the boards the member has been invited to")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTheBoardsTheMemberInvitedTo() {
        Response response = getMembersService().getTheBoardsTheMemberInvitedTo(memberTestData.getFirstMemberId());

        List idOfBoardsTheMemberInvitedTo = response.jsonPath().getList("id");


        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(idOfBoardsTheMemberInvitedTo.isEmpty());

    }

    @Test(priority = 6)
    @Description("Get the cards of a member")
    @Severity(SeverityLevel.NORMAL)
    public void testGetCardsOfAMember() {
        Response response = getMembersService().getCardsOfAMember(memberTestData.getFirstMemberId());

        List idOfCardsTheMemberIsOn = response.jsonPath().getList("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(idOfCardsTheMemberIsOn.isEmpty());
    }
}
