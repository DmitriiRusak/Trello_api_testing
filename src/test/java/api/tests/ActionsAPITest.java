package api.tests;

import api.resourcesForTests.configurationData.ActionTestData;
import api.services.ServiceWorkShop;
import api.resourcesForTests.PathParameters.*;
import api.resourcesForTests.configurationData.CommonConfigData.*;
import api.utils.LogFactory;
import api.utils.TestListener;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Epic("API Tests")
@Feature("Actions")
@Tag("api")
@Listeners(TestListener.class)
public class ActionsAPITest extends ServiceWorkShop {

    private ActionTestData actionTestData = new ActionTestData();

    @BeforeClass
    public void setUp() {
        LogFactory.getLogger().info("+++++++++++++++ class \uD83D\uDFE1" + this.getClass().getName() + "\uD83D\uDFE1 started +++++++++++++++");
        actionTestData.setBoardId(getActionsService().createABord(actionTestData.BOARD_NAME));
        actionTestData.setToDoListId(getActionsService().getListOfIdOfAllListsOnABoard(actionTestData.getBoardId()).get(0).toString());
        actionTestData.setActiontId(getActionsService().getIdOfTheFirestActionOnABoard(actionTestData.getBoardId()));
        actionTestData.setIdMemberCreator(getActionsService().getAnAction(actionTestData.getActiontId()).jsonPath().getString("idMemberCreator"));
        actionTestData.setIdOrganizationThatBelongToAnAction(getActionsService().getAnAction(actionTestData.getActiontId()).jsonPath().getString("data.organization.id"));
    }

    @AfterClass
    public void tearDown() {

        getActionsService().deleteBoard(actionTestData.getBoardId());

    }

    @Test(priority = 0)
    @Description("Get the action from a board")
    @Severity(SeverityLevel.NORMAL)
    public void testGetAnAction() {
        Response response = getActionsService().getAnAction(actionTestData.getActiontId());

        Assert.assertEquals(response.jsonPath().getString("id"), actionTestData.getActiontId());
    }

    @Test(priority = 1)
    @Description("Update a comment of the action")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateAnAction() {
        String commentForAnAction = "Some comment, that was send from JavaRestAssured project";
        String updatedCommentForAnAction = "Comment has been successfully updated";
        Map<String, String> queryParametersForRequestSpec = new HashMap<>();
        queryParametersForRequestSpec.put("idList",  actionTestData.getToDoListId());
        queryParametersForRequestSpec.put("name", "card for actions");

        actionTestData.setCardId(getActionsService().createACard(queryParametersForRequestSpec).jsonPath().getString("id"));

        actionTestData.setActionIdAfterCreatingACard(getActionsService().addNewComentToACard(actionTestData.getCardId(), commentForAnAction, ActionsEndPoints.COMMENTS_ENDPOINT).jsonPath().getString("id"));

        System.out.println("----------------------------------------> " + actionTestData.getActionIdAfterCreatingACard());

        Response response = getActionsService().updateACommentOfTheAction(actionTestData.getActionIdAfterCreatingACard(), updatedCommentForAnAction);
        //Для ассерта надо достать обновлённый комент респонса и сверить с updatedCommentForAnAction
    }

    @Test(priority = 2)
    @Description("Get a field 'date' of an action")
    @Severity(SeverityLevel.NORMAL)
    public void testGetASpecificFieldOnAnAction() {

        LocalDate currentDateTime = LocalDate.now();
        Response response = getActionsService().getTheResourceOfAnAction(actionTestData.getActionIdAfterCreatingACard(), ActionsEndPoints.DATE_ENDPOINT);

        String recivedDateOfAnAction = response.jsonPath().getString("_value").substring(0, 10);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(recivedDateOfAnAction, currentDateTime.toString());
    }

    @Test(priority = 2)
    @Description("Get the board to which an action refers to")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTheBoardForAnAction() {

        Response response = getActionsService().getTheResourceOfAnAction(actionTestData.getActionIdAfterCreatingACard(), ActionsEndPoints.BOARD_ENDPOINT);
        String boardNameRecivedFromApiCall = response.jsonPath().getString("name");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(boardNameRecivedFromApiCall, actionTestData.BOARD_NAME);
    }

    @Test(priority = 2)
    @Description("Get the card the action belong to")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTheCardForAnAction() {

        Response response = getActionsService().getTheResourceOfAnAction(actionTestData.getActionIdAfterCreatingACard(), ActionsEndPoints.CARD_ENDPOINT);
        String cardIdRecivedFromApiCall = response.jsonPath().getString("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(cardIdRecivedFromApiCall, actionTestData.getCardId());
    }

    @Test(priority = 2)
    @Description("Get the list the action belong to")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTheListForAnAction() {

        Response response = getActionsService().getTheResourceOfAnAction(actionTestData.getActionIdAfterCreatingACard(), ActionsEndPoints.LIST_ENDPOINT);
        String listIdRecivedFromApiCall = response.jsonPath().getString("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(listIdRecivedFromApiCall,  actionTestData.getToDoListId());
    }

    @Test(priority = 2)
    @Description("Get the member creator the action belong to")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTheMemberCreatorOfAnAction() {

        Response response = getActionsService().getTheResourceOfAnAction(actionTestData.getActiontId(), ActionsEndPoints.MEMBER_CREATOR_ENDPOINT);
        String memberCreatorIdRecivedFromApiCall = response.jsonPath().getString("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(memberCreatorIdRecivedFromApiCall, actionTestData.getIdMemberCreator());
    }

    @Test(priority = 2)
    @Description("Get the organization that belong to action ")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTheOrganizationOfAnAction() {

        Response response = getActionsService().getTheResourceOfAnAction(actionTestData.getActiontId(), ActionsEndPoints.ORGANIZATION_ENDPOINT);

        String idOfOrganizationRecivedFromApiCall = response.jsonPath().getString("id");

        Assert.assertEquals(idOfOrganizationRecivedFromApiCall, actionTestData.getIdOrganizationThatBelongToAnAction());
    }

    @Test(priority = 2)
    @Description("Get the reactions related to the specific action ")
    @Severity(SeverityLevel.NORMAL)
    public void testGetActions_Reactions() {

        Response response = getActionsService().getActions_Reactions(actionTestData.getActiontId());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), "[]");
    }

    @Test(priority = 3)
    @Description("Create reaction for specific action ")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateReactionForAction() {

        String expectedEmojiName = "GRINNING FACE";
        Response response = getActionsService().createReactionForAction(actionTestData.getActionIdAfterCreatingACard());
        String actualEmojiName = response.jsonPath().getString("emoji.name");
        actionTestData.setIdOfReaction(response.jsonPath().getString("id"));

        Assert.assertEquals(actualEmojiName, expectedEmojiName);
    }

    @Test(priority = 4)
    @Description("Get reaction of specific action")
    @Severity(SeverityLevel.NORMAL)
    public void testGetActionsReaction() {

        Response response = getActionsService().getActionsReaction(actionTestData.getActionIdAfterCreatingACard(), actionTestData.getIdOfReaction());
        String idOfReactionReceivedBack = response.jsonPath().getString("id");

        Assert.assertEquals(idOfReactionReceivedBack, actionTestData.getIdOfReaction());
    }

    @Test(priority = 5)
    @Description("Delete specific reaction of specific action")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteActionsReaction() {

        JSONObject jsonObject = new JSONObject();

        Response response = getActionsService().deleteActionsReaction(actionTestData.getActionIdAfterCreatingACard(), actionTestData.getIdOfReaction());
        Assert.assertEquals(response.body().asString(), jsonObject.toString());
    }

    @Test(priority = 6)
    @Description("Delete an action via id, and make sure it is deleted by trying to get the same action back")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteAnAction() {
        String responseMessageForDeletedAction = "The requested resource was not found.";
        System.out.println("----------------------------------------> " + actionTestData.getActionIdAfterCreatingACard());
        Response response = getActionsService().deleteAnAction(actionTestData.getActionIdAfterCreatingACard());

        Assert.assertEquals(response.getStatusCode(), 200);

        String actualMessage = getActionsService().getAnAction(actionTestData.getActionIdAfterCreatingACard()).asPrettyString();

        System.out.println(actualMessage + " " + responseMessageForDeletedAction);

        Assert.assertEquals(actualMessage,
                responseMessageForDeletedAction);

    }
}
