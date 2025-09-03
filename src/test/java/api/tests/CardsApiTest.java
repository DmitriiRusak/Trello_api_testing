package api.tests;

import api.resourcesForTests.CardFields;
import api.resourcesForTests.configurationData.ConfigTestData;
import api.services.CardsService;
import api.utils.LogFactory;
import api.utils.TestListener;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

import java.util.Map;

@Epic("API Tests")
@Feature("Cards")
@Listeners(TestListener.class)
public class CardsApiTest{

    private ConfigTestData configTestData = new ConfigTestData();
    private final CardsService cardsService = new CardsService();

    @BeforeClass
    public void setUp() {

        LogFactory.getLogger().info("+++++++++++++++ class \uD83D\uDFE1" + this.getClass().getName() + "\uD83D\uDFE1 started +++++++++++++++");
        configTestData.setBoardId(cardsService.createABord(configTestData.BOARD_NAME));
        configTestData.setToDoListId(cardsService.getListOfIdOfAllListsOnABoard(configTestData.getBoardId()).get(0).toString());
    }

    @AfterClass
    public void tearDown() {
        cardsService.deleteBoard(configTestData.getBoardId());
    }

    @Test(priority = 0)
    @Description("Create a new Card")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateANewCard() {
        Map<String, String> queryParametersForRequestSpec = new HashMap<>();
        queryParametersForRequestSpec.put("idList", configTestData.getToDoListId());

        Response response = cardsService.createACard(queryParametersForRequestSpec);

        configTestData.setCardId(response.jsonPath().get("id"));
        Assert.assertEquals(response.getStatusCode(), 200);

        configTestData.setListIdTheCardIsOn(cardsService.getTheListOfACard(configTestData.getCardId()).jsonPath().getString("id"));

        Assert.assertEquals(configTestData.getListIdTheCardIsOn(), configTestData.getToDoListId());
    }

    @Test(priority = 1)
    @Description("Get a card")
    @Severity(SeverityLevel.NORMAL)
    public void testGetACard() {

        Response response = cardsService.getCard(configTestData.getCardId());

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2)
    @Description("Update a card")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateACard() {

        String newCardName = "NewCardName";
        Response response = cardsService.updateCard(configTestData.getCardId(),"name", newCardName);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.path("name"), newCardName);
    }

    @Test(priority = 3)
    @Description("Get a field on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAFieldOnACard() {
        Response response = cardsService.getAFieldOfTheCard(configTestData.getCardId(), CardFields.name);
        String expectedName = "NewCardName";

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("_value"), expectedName);

    }

    @Test(priority = 4)
    @Description("Get an actions on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetActionsOnACard() {
        Response response = cardsService.getActionsOnACard(configTestData.getCardId());
        List arrayList = response.jsonPath().getList("id");

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 5)
    @Description("Get a attachments on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAttachmentsOnACard() {

        String emptyString = "[]";

        Response response = cardsService.getAttachmentsOnACard(configTestData.getCardId());
        String actualResponseBody = response.body().asString();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(actualResponseBody, emptyString);

    }

    @Test(priority = 6)
    @Description("Get the stickers on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetStickersOnACard() {
        Response response = cardsService.getStickersOnACard(configTestData.getCardId());

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 7)
    @Description("Create an attachment on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateAttachmentOnCard() {
        String nameOfCreatedAttachment = "ForCreateAttachmentOnCardTest.txt";

        Response response = cardsService.createAttachmentOnCard(configTestData.getCardId(), "src/test/resources/ForCreateAttachmentOnCardTest.txt");
        configTestData.setCreatedAttachmentId(response.jsonPath().getString("id"));
        String nameOfAttachmentReceivedBack = response.jsonPath().getString("name");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(nameOfAttachmentReceivedBack, nameOfCreatedAttachment);
    }

    @Test(priority = 8)
    @Description("Get an attachment on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAnAttachmentOnACard() {

        Response response = cardsService.getAnAttachmentOnACard(configTestData.getCardId(), configTestData.getCreatedAttachmentId());
        String attachmentIdReceivedBack = response.jsonPath().getString("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(attachmentIdReceivedBack, configTestData.getCreatedAttachmentId());
    }

    @Test(priority = 9)
    @Description("Delete an Attachment on a Card")
    @Severity(SeverityLevel.CRITICAL)
    public void testDeleteAnAttachmentOnACard() {

        Response response = cardsService.deleteAnAttachmentOnACard(configTestData.getCardId(), configTestData.getCreatedAttachmentId());

        String actualStringresponse = response.jsonPath().getString("limits");

        Assert.assertEquals(configTestData.EXPECTED_EMPTY_STRING_RESULT, actualStringresponse);
    }

    @Test(priority = 10)
    @Description("Get the Board the Card is on")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetTheBoardTheCardIsOn() {

        Response response = cardsService.getTheBoardTheCardIsOn(configTestData.getCardId());
        String actualNameOfTheBoardReceived = response.jsonPath().getString("name");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(actualNameOfTheBoardReceived, configTestData.BOARD_NAME);
    }

    @Test(priority = 11)
    @Description("Get the checkItems on a Card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetCheckItemsOnACard() {

        Response response = cardsService.getCheckItemsOnACard(configTestData.getCardId());

        String actualCheckItemsOnACard = response.body().asString();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(actualCheckItemsOnACard, configTestData.EXPECTED_RESULT);
    }

    @Test(priority = 12)
    @Description("Get all checklists on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetChecklistsOnACard() {

        Response response = cardsService.getChecklistsOnACard(configTestData.getCardId());

        String actualChecklistsOnACard = response.body().asString();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(actualChecklistsOnACard, configTestData.EXPECTED_RESULT);
    }



    @Test(priority = 13)
    @Description("Create checklist on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateChecklistOnACard() {

        Response response = cardsService.createAChecklistForACard(configTestData.getCardId(), configTestData.NAME_FOR_CHECKLIST_CREATED);
        String checklistNameReceivedBack = response.jsonPath().getString("name");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(checklistNameReceivedBack, configTestData.NAME_FOR_CHECKLIST_CREATED);

    }

    @Test(priority = 14)
    @Description("Get the List of a Card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetTheListOfACard() {
        Response response = cardsService.getTheListOfACard(configTestData.getCardId());

        String IdOfReceivedList = response.jsonPath().getString("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(IdOfReceivedList, configTestData.getListIdTheCardIsOn());
    }

    @Test(priority = 15)
    @Description("Get the Members of a Card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetTheMembersOfACard() {

        Response response = cardsService.getTheMembersOfACard(configTestData.getCardId());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), configTestData.EXPECTED_RESULT);
    }

    @Test(priority = 16)
    @Description("Delete a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testDeleteACard() {
        Response response = cardsService.deleteACard(configTestData.getCardId());

        Assert.assertEquals(response.getStatusCode(),200);
    }

    }


