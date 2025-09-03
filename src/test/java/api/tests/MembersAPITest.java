package api.tests;

import api.resourcesForTests.configurationData.ConfigTestData;
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

    private ConfigTestData configTestData = new ConfigTestData();
    private final MembersService membersService = new MembersService();

    @BeforeClass
    public void setUp() {

        LogFactory.getLogger().info("+++++++++++++++ class \uD83D\uDFE1" + this.getClass().getName() + "\uD83D\uDFE1 started +++++++++++++++");
        configTestData.setBoardId(membersService.createABord(configTestData.BOARD_NAME));
        configTestData.setFirstMemberId(membersService.getTheMembersOfABoard(configTestData.getBoardId()).jsonPath().getString("id"));
        configTestData.setFirstMemberId(configTestData.getFirstMemberId().substring(1, configTestData.getFirstMemberId().length() - 1));
    }

    @AfterClass
    public void tearDown() {
        membersService.deleteBoard(configTestData.getBoardId());
    }

    @Test(priority = 0)
    @Description("Get a member")
    @Severity(SeverityLevel.NORMAL)
    public void testGetAMember() {
        Response response = membersService.getAMember(configTestData.getFirstMemberId());
        String memberIdReceivedBack = response.jsonPath().getString("id");

        Assert.assertEquals(memberIdReceivedBack, configTestData.getFirstMemberId());
    }

    @Test(priority = 1)
    @Description("Get actions of a member")
    @Severity(SeverityLevel.NORMAL)
    public void testGetMembersActions() {
        Response response = membersService.getMembersActions(configTestData.getFirstMemberId());
        List actionsIds = response.jsonPath().getList("id");

        Assert.assertTrue(actionsIds.size()>1);
    }

    @Test(priority = 1)
    @Description("Get a member's custom board backgrounds")
    @Severity(SeverityLevel.NORMAL)
    public void testGetMemberCustomBackgrounds() {
        Response response = membersService.getMemberCustomBackgrounds(configTestData.getFirstMemberId());

        List membersBackgroundId = response.jsonPath().getList("id");

        configTestData.setBackgroundId(membersBackgroundId.get(0).toString());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(membersBackgroundId.size() > 5);
    }

    @Test(priority = 2)
    @Description("Create star for a board on behalf of a Member")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateStarForBoard() {

        Response response = membersService.createStarForABoard(configTestData.getFirstMemberId(), configTestData.getBoardId(), configTestData.POSITION);
        String idOfABoardTheStarIsOn = response.jsonPath().getString("idBoard");
        configTestData.setStarId(response.body().jsonPath().getString("id"));

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(idOfABoardTheStarIsOn, configTestData.getBoardId());
    }

    @Test(priority = 3)
    @Description("Get a specific boardStar")
    @Severity(SeverityLevel.NORMAL)
    public void testGetBoardStarOfAMember() {
        Response response = membersService.getBoardStarOfAMember(configTestData.getFirstMemberId(), configTestData.getStarId());
        String idOfABoardStar = response.jsonPath().getString("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(idOfABoardStar, configTestData.getStarId());
    }

    @Test(priority = 4)
    @Description("Update the position of a board star ")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateThePositionOfABoardStarOfAMember() {
        String positionOfABoardStarBeforeUpdate = membersService.getBoardStarOfAMember(configTestData.getFirstMemberId(), configTestData.getStarId()).jsonPath().getString("pos");
        Response response = membersService.updateThePositionOfABoardStarOfAMember(configTestData.getFirstMemberId(), configTestData.getStarId(), configTestData.UPDATE_POSITION);
        String positionOfABoardStarAfterUpdate = response.jsonPath().getString("pos");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotEquals(positionOfABoardStarBeforeUpdate, positionOfABoardStarAfterUpdate);
    }

    @Test(priority = 5)
    @Description("Delete a star of a board")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteStarOfABoard() {
        Response response = membersService.deleteStarBoard(configTestData.getFirstMemberId(), configTestData.getStarId());

        Response response1 =  membersService.getBoardStarOfAMember(configTestData.getFirstMemberId(), configTestData.getStarId());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response1.asPrettyString(), configTestData.BOARD_STAR_NOT_FOUND_MESSAGE);
    }

    @Test(priority = 6)
    @Description("Get all the boards that the user is a member of")
    @Severity(SeverityLevel.NORMAL)
    public void testGetBoardsThatMemberBelongTo(){
        Response response = membersService.getBoardsMemberBelongs(configTestData.getFirstMemberId());

        List idOfBoardsThatBelongToMember = response.jsonPath().getList("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertFalse(idOfBoardsThatBelongToMember.isEmpty());

    }

    @Test(priority = 6)
    @Description("Get the boards the member has been invited to")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTheBoardsTheMemberInvitedTo() {
        Response response = membersService.getTheBoardsTheMemberInvitedTo(configTestData.getFirstMemberId());

        List idOfBoardsTheMemberInvitedTo = response.jsonPath().getList("id");


        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(idOfBoardsTheMemberInvitedTo.isEmpty());

    }

    @Test(priority = 6)
    @Description("Get the cards of a member")
    @Severity(SeverityLevel.NORMAL)
    public void testGetCardsOfAMember() {
        Response response = membersService.getCardsOfAMember(configTestData.getFirstMemberId());

        List idOfCardsTheMemberIsOn = response.jsonPath().getList("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(idOfCardsTheMemberIsOn.isEmpty());
    }
}
