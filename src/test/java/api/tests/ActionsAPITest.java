package api.tests;

import api.services.ServiceWorkShop;
import api.resourcesForTests.PathParameters.*;
import api.resourcesForTests.ConfigurationDataForApiTests.*;
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

    @BeforeClass
    public void setUp() {
        LogFactory.getLogger().info("+++++++++++++++ class \uD83D\uDFE1" + this.getClass().getName() + "\uD83D\uDFE1 started +++++++++++++++");
        BoardTestData.boardId = getActionsService().createABord(ActionsTestData.BOARD_NAME);
        ListsTestData.toDoListId = getActionsService().getListOfIdOfAllListsOnABoard(BoardTestData.boardId).get(0).toString();
        ActionsTestData.actiontId = getActionsService().getIdOfTheFirestActionOnABoard(BoardTestData.boardId);
        ActionsTestData.idMemberCreator = getActionsService().getAnAction(ActionsTestData.actiontId).jsonPath().getString("idMemberCreator");
        ActionsTestData.idOrganizationThatBelongToAnAction = getActionsService().getAnAction(ActionsTestData.actiontId).jsonPath().getString("data.organization.id");
    }

    @AfterClass
    public void tearDown() {

        getActionsService().deleteBoard(BoardTestData.boardId);

    }

    @Test(priority = 0)
    @Description("Get the action from a board")
    @Severity(SeverityLevel.NORMAL)
    public void testGetAnAction() {
        Response response = getActionsService().getAnAction(ActionsTestData.actiontId);

        Assert.assertEquals(response.jsonPath().getString("id"), ActionsTestData.actiontId);
    }

    @Test(priority = 1)
    @Description("Update a comment of the action")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateAnAction() {
        String commentForAnAction = "Some comment, that was send from JavaRestAssured project";
        String updatedCommentForAnAction = "Comment has been successfully updated";
        Map<String, String> queryParametersForRequestSpec = new HashMap<>();
        queryParametersForRequestSpec.put("idList",  ListsTestData.toDoListId);
        queryParametersForRequestSpec.put("name", "card for actions");

        ActionsTestData.cardId = getActionsService().createACard(queryParametersForRequestSpec).jsonPath().getString("id");

        ActionsTestData.actionIdAfterCreatingACard = getActionsService().addNewComentToACard(ActionsTestData.cardId, commentForAnAction, ActionsEndPoints.COMMENTS_ENDPOINT).jsonPath().getString("id");

        Response response = getActionsService().updateACommentOfTheAction(ActionsTestData.actionIdAfterCreatingACard, updatedCommentForAnAction);
        //Для ассерта надо достать обновлённый комент респонса и сверить с updatedCommentForAnAction
    }

    @Test(priority = 2)
    @Description("Get a field 'date' of an action")
    @Severity(SeverityLevel.NORMAL)
    public void testGetASpecificFieldOnAnAction() {

        LocalDate currentDateTime = LocalDate.now();
        Response response = getActionsService().getTheResourceOfAnAction(ActionsTestData.actionIdAfterCreatingACard, ActionsEndPoints.DATE_ENDPOINT);

        String recivedDateOfAnAction = response.jsonPath().getString("_value").substring(0, 10);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(recivedDateOfAnAction, currentDateTime.toString());
    }

    @Test(priority = 2)
    @Description("Get the board to which an action refers to")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTheBoardForAnAction() {

        Response response = getActionsService().getTheResourceOfAnAction(ActionsTestData.actionIdAfterCreatingACard, ActionsEndPoints.BOARD_ENDPOINT);
        String boardNameRecivedFromApiCall = response.jsonPath().getString("name");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(boardNameRecivedFromApiCall, ActionsTestData.BOARD_NAME);
    }

    @Test(priority = 2)
    @Description("Get the card the action belong to")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTheCardForAnAction() {

        Response response = getActionsService().getTheResourceOfAnAction(ActionsTestData.actionIdAfterCreatingACard, ActionsEndPoints.CARD_ENDPOINT);
        String cardIdRecivedFromApiCall = response.jsonPath().getString("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(cardIdRecivedFromApiCall, ActionsTestData.cardId);
    }

    @Test(priority = 2)
    @Description("Get the list the action belong to")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTheListForAnAction() {

        Response response = getActionsService().getTheResourceOfAnAction(ActionsTestData.actionIdAfterCreatingACard, ActionsEndPoints.LIST_ENDPOINT);
        String listIdRecivedFromApiCall = response.jsonPath().getString("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(listIdRecivedFromApiCall,  ListsTestData.toDoListId);
    }

    @Test(priority = 2)
    @Description("Get the member creator the action belong to")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTheMemberCreatorOfAnAction() {

        Response response = getActionsService().getTheResourceOfAnAction(ActionsTestData.actiontId, ActionsEndPoints.MEMBER_CREATOR_ENDPOINT);
        String memberCreatorIdRecivedFromApiCall = response.jsonPath().getString("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(memberCreatorIdRecivedFromApiCall, ActionsTestData.idMemberCreator);
    }

    @Test(priority = 2)
    @Description("Get the organization that belong to action ")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTheOrganizationOfAnAction() {

        Response response = getActionsService().getTheResourceOfAnAction(ActionsTestData.actiontId, ActionsEndPoints.ORGANIZATION_ENDPOINT);

        String idOfOrganizationRecivedFromApiCall = response.jsonPath().getString("id");

        Assert.assertEquals(idOfOrganizationRecivedFromApiCall, ActionsTestData.idOrganizationThatBelongToAnAction);
    }

    @Test(priority = 2)
    @Description("Get the reactions related to the specific action ")
    @Severity(SeverityLevel.NORMAL)
    public void testGetActions_Reactions() {

        Response response = getActionsService().getActions_Reactions(ActionsTestData.actiontId);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), "[]");
    }

    @Test(priority = 3)
    @Description("Create reaction for specific action ")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateReactionForAction() {

        String expectedEmojiName = "GRINNING FACE";
        Response response = getActionsService().createReactionForAction(ActionsTestData.actionIdAfterCreatingACard);
        String actualEmojiName = response.jsonPath().getString("emoji.name");
        ActionsTestData.idOfReaction = response.jsonPath().getString("id");

        Assert.assertEquals(actualEmojiName, expectedEmojiName);
    }

    @Test(priority = 4)
    @Description("Get reaction of specific action")
    @Severity(SeverityLevel.NORMAL)
    public void testGetActionsReaction() {

        Response response = getActionsService().getActionsReaction(ActionsTestData.actionIdAfterCreatingACard, ActionsTestData.idOfReaction);
        String idOfReactionReceivedBack = response.jsonPath().getString("id");

        Assert.assertEquals(idOfReactionReceivedBack, ActionsTestData.idOfReaction);
    }

    @Test(priority = 5)
    @Description("Delete specific reaction of specific action")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteActionsReaction() {

        JSONObject jsonObject = new JSONObject();

        Response response = getActionsService().deleteActionsReaction(ActionsTestData.actionIdAfterCreatingACard, ActionsTestData.idOfReaction);
        Assert.assertEquals(response.body().asString(), jsonObject.toString());
    }

    @Test(priority = 6)
    @Description("Delete an action via id, and make sure it is deleted by trying to get the same action back")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteAnAction() {
        String responseMessageForDeletedAction = "The requested resource was not found.";
        Response response = getActionsService().deleteAnAction(ActionsTestData.actionIdAfterCreatingACard);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(getActionsService().getAnAction(ActionsTestData.actionIdAfterCreatingACard).asPrettyString(), responseMessageForDeletedAction);

    }
}
