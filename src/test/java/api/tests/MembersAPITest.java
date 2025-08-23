package api.tests;

import api.resourcesForTests.configurationData.MemberTestData;
import api.services.MembersService;
import api.utils.LogFactory;
import api.utils.TestListener;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

@Epic("API Tests")
@Feature("Members")
@Listeners(TestListener.class)
public class MembersAPITest{

    private final MemberTestData memberTestData = new MemberTestData();
    private final MembersService membersService = new MembersService();

    @BeforeClass
    public void setUp() {

        LogFactory.getLogger().info("+++++++++++++++ class \uD83D\uDFE1" + this.getClass().getName() + "\uD83D\uDFE1 started +++++++++++++++");
        memberTestData.setBoardId(membersService.createABord(memberTestData.getBOARD_NAME_FOR_MEMBERS()));
        memberTestData.setFirstMemberId(membersService.getTheMembersOfABoard(memberTestData.getBoardId()).jsonPath().getString("id"));
        memberTestData.setFirstMemberId(memberTestData.getFirstMemberId().substring(1, memberTestData.getFirstMemberId().length() - 1));
    }

    @AfterClass
    public void tearDown() {
        membersService.deleteBoard(memberTestData.getBoardId());
    }

    @Test(priority = 0)
    @Description("Get a member")
    @Severity(SeverityLevel.NORMAL)
    public void testGetAMember() {
        Response response = membersService.getAMember(memberTestData.getFirstMemberId());
        String memberIdReceivedBack = response.jsonPath().getString("id");

        Assert.assertEquals(memberIdReceivedBack, memberTestData.getFirstMemberId());
    }

    @Test(priority = 1)
    @Description("Get actions of a member")
    @Severity(SeverityLevel.NORMAL)
    public void testGetMembersActions() {
        Response response = membersService.getMembersActions(memberTestData.getFirstMemberId());
        List actionsIds = response.jsonPath().getList("id");

        Assert.assertTrue(actionsIds.size()>1);
    }

    @Test(priority = 1)
    @Description("Get a member's custom board backgrounds")
    @Severity(SeverityLevel.NORMAL)
    public void testGetMemberCustomBackgrounds() {
        Response response = membersService.getMemberCustomBackgrounds(memberTestData.getFirstMemberId());

        List membersBackgroundId = response.jsonPath().getList("id");

        memberTestData.setBackgroundId(membersBackgroundId.get(0).toString());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(membersBackgroundId.size() > 5);
    }

    @Test(priority = 2)
    @Description("Create star for a board on behalf of a Member")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateStarForBoard() {

        Response response = membersService.createStarForABoard(memberTestData.getFirstMemberId(), memberTestData.getBoardId(), memberTestData.getPOSITION());
        String idOfABoardTheStarIsOn = response.jsonPath().getString("idBoard");
        memberTestData.setStarId(response.body().jsonPath().getString("id"));

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(idOfABoardTheStarIsOn, memberTestData.getBoardId());
    }

    @Test(priority = 3)
    @Description("Get a specific boardStar")
    @Severity(SeverityLevel.NORMAL)
    public void testGetBoardStarOfAMember() {
        Response response = membersService.getBoardStarOfAMember(memberTestData.getFirstMemberId(), memberTestData.getStarId());
        String idOfABoardStar = response.jsonPath().getString("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(idOfABoardStar, memberTestData.getStarId());
    }

    @Test(priority = 4)
    @Description("Update the position of a board star ")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateThePositionOfABoardStarOfAMember() {
        String positionOfABoardStarBeforeUpdate = membersService.getBoardStarOfAMember(memberTestData.getFirstMemberId(), memberTestData.getStarId()).jsonPath().getString("pos");
        Response response = membersService.updateThePositionOfABoardStarOfAMember(memberTestData.getFirstMemberId(), memberTestData.getStarId(), memberTestData.getUPDATE_POSITION());
        String positionOfABoardStarAfterUpdate = response.jsonPath().getString("pos");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotEquals(positionOfABoardStarBeforeUpdate, positionOfABoardStarAfterUpdate);
    }

    @Test(priority = 5)
    @Description("Delete a star of a board")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteStarOfABoard() {
        Response response = membersService.deleteStarBoard(memberTestData.getFirstMemberId(), memberTestData.getStarId());

        Response response1 =  membersService.getBoardStarOfAMember(memberTestData.getFirstMemberId(), memberTestData.getStarId());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response1.asPrettyString(), memberTestData.getBOARD_STAR_NOT_FOUND_MESSAGE());
    }

    @Test(priority = 6)
    @Description("Get all the boards that the user is a member of")
    @Severity(SeverityLevel.NORMAL)
    public void testGetBoardsThatMemberBelongTo(){
        Response response = membersService.getBoardsMemberBelongs(memberTestData.getFirstMemberId());

        List idOfBoardsThatBelongToMember = response.jsonPath().getList("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertFalse(idOfBoardsThatBelongToMember.isEmpty());

    }

    @Test(priority = 6)
    @Description("Get the boards the member has been invited to")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTheBoardsTheMemberInvitedTo() {
        Response response = membersService.getTheBoardsTheMemberInvitedTo(memberTestData.getFirstMemberId());

        List idOfBoardsTheMemberInvitedTo = response.jsonPath().getList("id");


        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(idOfBoardsTheMemberInvitedTo.isEmpty());

    }

    @Test(priority = 6)
    @Description("Get the cards of a member")
    @Severity(SeverityLevel.NORMAL)
    public void testGetCardsOfAMember() {
        Response response = membersService.getCardsOfAMember(memberTestData.getFirstMemberId());

        List idOfCardsTheMemberIsOn = response.jsonPath().getList("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(idOfCardsTheMemberIsOn.isEmpty());
    }
}
