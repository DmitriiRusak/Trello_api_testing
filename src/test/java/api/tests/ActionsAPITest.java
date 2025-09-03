package api.tests;

import api.resourcesForTests.configurationData.ConfigTestData;
import api.services.ActionsService;
import api.resourcesForTests.PathParameters.*;
import api.utils.LogFactory;
import api.utils.TestListener;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;

import java.security.PublicKey;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Epic("API Tests")
@Feature("Actions")
@Tag("api")
@Listeners(TestListener.class)
public class ActionsAPITest {

    private ConfigTestData configTestData = new ConfigTestData();
    private final ActionsService actionsService = new ActionsService();

    @BeforeClass
    public void setUp() {
        LogFactory.getLogger().info("+++++++++++++++ class \uD83D\uDFE1" + this.getClass().getName() + "\uD83D\uDFE1 started +++++++++++++++");
        configTestData.setBoardId(actionsService.createABord(configTestData.BOARD_NAME));

        configTestData.setToDoListId(actionsService.getListOfIdOfAllListsOnABoard(configTestData.getBoardId()).get(0).toString());

        configTestData.setActiontId(actionsService.getIdOfTheFirestActionOnABoard(configTestData.getBoardId()));

        configTestData.setIdMemberCreator(actionsService.getAnAction(configTestData.getActiontId()).jsonPath().getString("idMemberCreator"));

        configTestData.setIdOrganizationThatBelongToAnAction(actionsService.getAnAction(configTestData.getActiontId()).jsonPath().getString("data.organization.id"));

    }

    @AfterClass
    public void tearDown() {

        actionsService.deleteBoard(configTestData.getBoardId());

    }

    @BeforeMethod
    public void setUpSpecification(){

    }

    @Test(priority = 0)
    @Description("Get the action from a board")
    @Severity(SeverityLevel.NORMAL)
    public void testGetAnAction() {
        Response response = actionsService.getAnAction(configTestData.getActiontId());

        Assert.assertEquals(response.jsonPath().getString("id"), configTestData.getActiontId());
    }

    @Test(priority = 1)
    @Description("Update a comment of the action")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateAnAction() {
        String commentForAnAction = "Some comment, that was send from JavaRestAssured project";
        String updatedCommentForAnAction = "Comment has been successfully updated";
        Map<String, String> queryParametersForRequestSpec = new HashMap<>();
        queryParametersForRequestSpec.put("idList",  configTestData.getToDoListId());
        queryParametersForRequestSpec.put("name", "card for actions");

        configTestData.setCardId(actionsService.createACard(queryParametersForRequestSpec).jsonPath().getString("id"));

        configTestData.setActionIdAfterCreatingACard(actionsService.addNewComentToACard(configTestData.getCardId(), commentForAnAction, ActionsEndPoints.COMMENTS_ENDPOINT).jsonPath().getString("id"));

        Response response = actionsService.updateACommentOfTheAction(configTestData.getActionIdAfterCreatingACard(), updatedCommentForAnAction);
        //Для ассерта надо достать обновлённый комент респонса и сверить с updatedCommentForAnAction
    }

    @Test(priority = 2)
    @Description("Get a field 'date' of an action")
    @Severity(SeverityLevel.NORMAL)
    public void testGetASpecificFieldOnAnAction() {

        LocalDate currentDateTime = LocalDate.now();
        Response response = actionsService.getTheResourceOfAnAction(configTestData.getActionIdAfterCreatingACard(), ActionsEndPoints.DATE_ENDPOINT);

        String recivedDateOfAnAction = response.jsonPath().getString("_value").substring(0, 10);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(recivedDateOfAnAction, currentDateTime.toString());
    }

    @Test(priority = 3)
    @Description("Get the board to which an action refers to")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTheBoardForAnAction() {

        Response response = actionsService.getTheResourceOfAnAction(configTestData.getActionIdAfterCreatingACard(), ActionsEndPoints.BOARD_ENDPOINT);
        String boardNameRecivedFromApiCall = response.jsonPath().getString("name");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(boardNameRecivedFromApiCall, configTestData.BOARD_NAME);
    }

    @Test(priority = 4)
    @Description("Get the card the action belong to")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTheCardForAnAction() {

        Response response = actionsService.getTheResourceOfAnAction(configTestData.getActionIdAfterCreatingACard(), ActionsEndPoints.CARD_ENDPOINT);
        String cardIdRecivedFromApiCall = response.jsonPath().getString("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(cardIdRecivedFromApiCall, configTestData.getCardId());
    }

    @Test(priority = 5)
    @Description("Get the list the action belong to")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTheListForAnAction() {

        Response response = actionsService.getTheResourceOfAnAction(configTestData.getActionIdAfterCreatingACard(), ActionsEndPoints.LIST_ENDPOINT);
        String listIdRecivedFromApiCall = response.jsonPath().getString("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(listIdRecivedFromApiCall,  configTestData.getToDoListId());
    }

    @Test(priority = 6)
    @Description("Get the member creator the action belong to")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTheMemberCreatorOfAnAction() {

        Response response = actionsService.getTheResourceOfAnAction(configTestData.getActiontId(), ActionsEndPoints.MEMBER_CREATOR_ENDPOINT);
        String memberCreatorIdRecivedFromApiCall = response.jsonPath().getString("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(memberCreatorIdRecivedFromApiCall, configTestData.getIdMemberCreator());
    }

    @Test(priority = 7)
    @Description("Get the organization that belong to action ")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTheOrganizationOfAnAction() {

        Response response = actionsService.getTheResourceOfAnAction(configTestData.getActiontId(), ActionsEndPoints.ORGANIZATION_ENDPOINT);

        String idOfOrganizationRecivedFromApiCall = response.jsonPath().getString("id");

        Assert.assertEquals(idOfOrganizationRecivedFromApiCall, configTestData.getIdOrganizationThatBelongToAnAction());
    }

    @Test(priority = 8)
    @Description("Get the reactions related to the specific action ")
    @Severity(SeverityLevel.NORMAL)
    public void testGetActions_Reactions() {

        Response response = actionsService.getActions_Reactions(configTestData.getActiontId());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), "[]");
    }

    @Test(priority = 9)
    @Description("Create reaction for specific action ")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateReactionForAction() {

        String expectedEmojiName = "GRINNING FACE";
        Response response = actionsService.createReactionForAction(configTestData.getActionIdAfterCreatingACard());

        String actualEmojiName = response.jsonPath().getString("emoji.name");
        configTestData.setIdOfReaction(response.jsonPath().getString("id"));

        Assert.assertEquals(actualEmojiName, expectedEmojiName);
    }

    @Test(priority = 10)
    @Description("Get reaction of specific action")
    @Severity(SeverityLevel.NORMAL)
    public void testGetActionsReaction() {

        Response response = actionsService.getActionsReaction(configTestData.getActionIdAfterCreatingACard(), configTestData.getIdOfReaction());
        String idOfReactionReceivedBack = response.jsonPath().getString("id");

        Assert.assertEquals(idOfReactionReceivedBack, configTestData.getIdOfReaction());
    }

    @Test(priority = 11)
    @Description("Delete specific reaction of specific action")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteActionsReaction() {

        JSONObject jsonObject = new JSONObject();

        Response response = actionsService.deleteActionsReaction(configTestData.getActionIdAfterCreatingACard(), configTestData.getIdOfReaction());
        Assert.assertEquals(response.body().asString(), jsonObject.toString());
    }

    @Test(priority = 12)
    @Description("Delete an action via id, and make sure it is deleted by trying to get the same action back")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteAnAction() {
        String responseMessageForDeletedAction = "The requested resource was not found.";
        Response response = actionsService.deleteAnAction(configTestData.getActionIdAfterCreatingACard());

        Assert.assertEquals(response.getStatusCode(), 200);

        String actualMessage = actionsService.getAnAction(configTestData.getActionIdAfterCreatingACard()).asPrettyString();

        Assert.assertEquals(actualMessage,
                responseMessageForDeletedAction);

    }
}
