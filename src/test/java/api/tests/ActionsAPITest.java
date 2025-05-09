package api.tests;

import api.base.BaseTest;
import api.base.PathParameters.*;
import api.base.TestData.*;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Epic("API Tests")
@Feature("Actions Validation")
@Owner("Group JavaForwardToOffer")
public class ActionsAPITest extends BaseTest {

    @BeforeClass
    public void setUp() {
        ActionsTestData.boardId = getActionsSteps().createABord(ActionsTestData.BOARD_NAME);
        ActionsTestData.toDoListId = getActionsSteps().getIdOfTheFirstListOnABoard(ActionsTestData.boardId);
        ActionsTestData.actiontId = getActionsSteps().getIdOfTheFirestActionOnABoard(ActionsTestData.boardId);
        ActionsTestData.idMemberCreator = getActionsSteps().getAnAction(ActionsTestData.actiontId).jsonPath().getString("idMemberCreator");
        ActionsTestData.idOrganizationThatBelongToAnAction = getActionsSteps().getAnAction(ActionsTestData.actiontId).jsonPath().getString("data.organization.id");
    }

    @AfterClass
    public void tearDown() {
        getActionsSteps().deleteBoard(ActionsTestData.boardId);
    }

    @Test(priority = 0)
    @Story("Actions")
    @Description("Get the action from a board")
    @Severity(SeverityLevel.NORMAL)
    public void testGetAnAction() {
        Response response = getActionsSteps().getAnAction(ActionsTestData.actiontId);

        Assert.assertEquals(response.jsonPath().getString("id"), ActionsTestData.actiontId);
    }

    @Test(priority = 1)
    @Story("Actions")
    @Description("Update a comment of the action")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateAnAction() {
        String commentForAnAction = "Some comment, that was send from JavaRestAssured project";
        String updatedCommentForAnAction = "Comment has been successfully updated";
        Map<String, String> queryParametersForRequestSpec = new HashMap<>();
        queryParametersForRequestSpec.put("idList",  ActionsTestData.toDoListId);
        queryParametersForRequestSpec.put("name", "card for actions");

        ActionsTestData.cardId = getActionsSteps().createACard(queryParametersForRequestSpec).jsonPath().getString("id");

        ActionsTestData.actionIdAfterCreatingACard = getActionsSteps().addNewComentToACard(ActionsTestData.cardId, commentForAnAction, ActionsEndPoints.COMMENTS_ENDPOINT).jsonPath().getString("id");

        Response response = getActionsSteps().updateACommentOfTheAction(ActionsTestData.actionIdAfterCreatingACard, updatedCommentForAnAction);
        //Для ассерта надо достать обновлённый комент респонса и сверить с updatedCommentForAnAction
    }

    @Test(priority = 2)
    @Story("Actions")
    @Description("Get a field 'date' of an action")
    @Severity(SeverityLevel.NORMAL)
    public void testGetASpecificFieldOnAnAction() {

        LocalDate currentDateTime = LocalDate.now();
        Response response = getActionsSteps().getTheResourceOfAnAction(ActionsTestData.actionIdAfterCreatingACard, ActionsEndPoints.DATE_ENDPOINT);

        String recivedDateOfAnAction = response.jsonPath().getString("_value").substring(0, 10);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(recivedDateOfAnAction, currentDateTime.toString());
    }

    @Test(priority = 2)
    @Story("Actions")
    @Description("Get the board to which an action refers to")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTheBoardForAnAction() {

        Response response = getActionsSteps().getTheResourceOfAnAction(ActionsTestData.actionIdAfterCreatingACard, ActionsEndPoints.BOARD_ENDPOINT);
        String boardNameRecivedFromApiCall = response.jsonPath().getString("name");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(boardNameRecivedFromApiCall, ActionsTestData.BOARD_NAME);
    }

    @Test(priority = 2)
    @Story("Actions")
    @Description("Get the card the action belong to")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTheCardForAnAction() {

        Response response = getActionsSteps().getTheResourceOfAnAction(ActionsTestData.actionIdAfterCreatingACard, ActionsEndPoints.CARD_ENDPOINT);
        String cardIdRecivedFromApiCall = response.jsonPath().getString("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(cardIdRecivedFromApiCall, ActionsTestData.cardId);
    }

    @Test(priority = 2)
    @Story("Actions")
    @Description("Get the list the action belong to")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTheListForAnAction() {

        Response response = getActionsSteps().getTheResourceOfAnAction(ActionsTestData.actionIdAfterCreatingACard, ActionsEndPoints.LIST_ENDPOINT);
        String listIdRecivedFromApiCall = response.jsonPath().getString("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(listIdRecivedFromApiCall,  ActionsTestData.toDoListId);
    }

    @Test(priority = 2)
    @Story("Actions")
    @Description("Get the member creator the action belong to")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTheMemberCreatorOfAnAction() {

        Response response = getActionsSteps().getTheResourceOfAnAction(ActionsTestData.actiontId, ActionsEndPoints.MEMBER_CREATOR_ENDPOINT);
        String memberCreatorIdRecivedFromApiCall = response.jsonPath().getString("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(memberCreatorIdRecivedFromApiCall, ActionsTestData.idMemberCreator);
    }

    @Test(priority = 2)
    @Story("Actions")
    @Description("Get the organization that belong to action ")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTheOrganizationOfAnAction() {

        Response response = getActionsSteps().getTheResourceOfAnAction(ActionsTestData.actiontId, ActionsEndPoints.ORGANIZATION_ENDPOINT);

        String idOfOrganizationRecivedFromApiCall = response.jsonPath().getString("id");

        Assert.assertEquals(idOfOrganizationRecivedFromApiCall, ActionsTestData.idOrganizationThatBelongToAnAction);
    }

    @Test(priority = 2)
    @Story("Actions")
    @Description("Get the reactions related to the specific action ")
    @Severity(SeverityLevel.NORMAL)
    public void testGetActions_Reactions() {

        Response response = getActionsSteps().getActions_Reactions(ActionsTestData.actiontId);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), "[]");
    }

    @Test(priority = 3)
    @Story("Actions")
    @Description("Create reaction for specific action ")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateReactionForAction() {

        String expectedEmojiName = "GRINNING FACE";
        Response response = getActionsSteps().createReactionForAction(ActionsTestData.actionIdAfterCreatingACard);
        String actualEmojiName = response.jsonPath().getString("emoji.name");
        ActionsTestData.idOfReaction = response.jsonPath().getString("id");

        Assert.assertEquals(actualEmojiName, expectedEmojiName);
    }

    @Test(priority = 4)
    @Story("Actions")
    @Description("Get reaction of specific action")
    @Severity(SeverityLevel.NORMAL)
    public void testGetActionsReaction() {

        Response response = getActionsSteps().getActionsReaction(ActionsTestData.actionIdAfterCreatingACard, ActionsTestData.idOfReaction);
        String idOfReactionReceivedBack = response.jsonPath().getString("id");

        Assert.assertEquals(idOfReactionReceivedBack, ActionsTestData.idOfReaction);
    }

    @Test(priority = 5)
    @Story("Actions")
    @Description("Delete specific reaction of specific action")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteActionsReaction() {

        JSONObject jsonObject = new JSONObject();

        Response response = getActionsSteps().deleteActionsReaction(ActionsTestData.actionIdAfterCreatingACard, ActionsTestData.idOfReaction);
        Assert.assertEquals(response.body().asString(), jsonObject.toString());
    }

    @Test(priority = 6)
    @Story("Actions")
    @Description("Delete an action via id, and make sure it is deleted by trying to get the same action back")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteAnAction() {
        String responseMessageForDeletedAction = "The requested resource was not found.";
        Response response = getActionsSteps().deleteAnAction(ActionsTestData.actionIdAfterCreatingACard);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(getActionsSteps().getAnAction(ActionsTestData.actionIdAfterCreatingACard).asPrettyString(), responseMessageForDeletedAction);

    }
}
