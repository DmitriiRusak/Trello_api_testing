package api.tests;

import api.resourcesForTests.CardFields;
import api.resourcesForTests.ConfigurationDataForApiTests;
import api.services.ServiceWorkShop;
import api.resourcesForTests.ConfigurationDataForApiTests.*;
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

import static api.resourcesForTests.ConfigurationDataForApiTests.EXPECTED_EMPTY_STRING_RESULT;


@Epic("API Tests")
@Feature("Cards")
@Listeners(TestListener.class)
public class CardsApiTest extends ServiceWorkShop {

    @BeforeClass
    public void setUp() {

        LogFactory.getLogger().info("+++++++++++++++ class \uD83D\uDFE1" + this.getClass().getName() + "\uD83D\uDFE1 started +++++++++++++++");
        BoardTestData.boardId = getCardsService().createABord(CardsTestData.BOARD_NAME_FOR_CARDS);
        ListsTestData.toDoListId = getActionsService().getListOfIdOfAllListsOnABoard(BoardTestData.boardId).get(0).toString();
    }

    @AfterClass
    public void tearDown() {
        getCardsService().deleteBoard(BoardTestData.boardId);
    }

    @Test(priority = 0)
    @Description("Create a new Card")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateANewCard() {
        Map<String, String> queryParametersForRequestSpec = new HashMap<>();
        queryParametersForRequestSpec.put("idList", ListsTestData.toDoListId);

        Response response = getCardsService().createACard(queryParametersForRequestSpec);

        CardsTestData.cardId = response.jsonPath().get("id");
        Assert.assertEquals(response.getStatusCode(), 200);

        CardsTestData.listIdTheCardIsOn = getCardsService().getTheListOfACard(CardsTestData.cardId).jsonPath().getString("id");

        Assert.assertEquals(CardsTestData.listIdTheCardIsOn, ListsTestData.toDoListId);
    }

    @Test(priority = 1)
    @Description("Get a card")
    @Severity(SeverityLevel.NORMAL)
    public void testGetACard() {

        Response response = getCardsService().getCard(CardsTestData.cardId);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 1)
    @Description("Update a card")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateACard() {

        String newCardName = "NewCardName";
        Response response = getCardsService().updateCard(CardsTestData.cardId,"name", newCardName);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.path("name"), newCardName);
    }

    @Test(priority = 3)
    @Description("Get a field on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAFieldOnACard() {
        Response response = getCardsService().getAFieldOfTheCard(CardsTestData.cardId, CardFields.name);
        String expectedName = "NewCardName";

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("_value"), expectedName);

    }

    @Test(priority = 3)
    @Description("Get an actions on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetActionsOnACard() {
        Response response = getCardsService().getActionsOnACard(CardsTestData.cardId);
        List arrayList = response.jsonPath().getList("id");

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3)
    @Description("Get a attachments on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAttachmentsOnACard() {

        String emptyString = "[]";

        Response response = getCardsService().getAttachmentsOnACard(CardsTestData.cardId);
        String actualResponseBody = response.body().asString();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(actualResponseBody, emptyString);

    }

    @Test(priority = 3)
    @Description("Get the stickers on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetStickersOnACard() {
        Response response = getCardsService().getStickersOnACard(CardsTestData.cardId);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 4)
    @Description("Create an attachment on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateAttachmentOnCard() {
        String nameOfCreatedAttachment = "ForCreateAttachmentOnCardTest.txt";

        Response response = getCardsService().createAttachmentOnCard(CardsTestData.cardId, "src/test/resources/ForCreateAttachmentOnCardTest.txt");
        CardsTestData.createdAttachmentId = response.jsonPath().getString("id");
        String nameOfAttachmentReceivedBack = response.jsonPath().getString("name");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(nameOfAttachmentReceivedBack, nameOfCreatedAttachment);
    }

    @Test(priority = 5)
    @Description("Get an attachment on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAnAttachmentOnACard() {

        Response response = getCardsService().getAnAttachmentOnACard(CardsTestData.cardId, CardsTestData.createdAttachmentId);
        String attachmentIdReceivedBack = response.jsonPath().getString("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(attachmentIdReceivedBack, CardsTestData.createdAttachmentId);
    }

    @Test(priority = 6)
    @Description("Delete an Attachment on a Card")
    @Severity(SeverityLevel.CRITICAL)
    public void testDeleteAnAttachmentOnACard() {

        Response response = getCardsService().deleteAnAttachmentOnACard(CardsTestData.cardId, CardsTestData.createdAttachmentId);

        String actualStringresponse = response.jsonPath().getString("limits");

        Assert.assertEquals(EXPECTED_EMPTY_STRING_RESULT, actualStringresponse);
    }

    @Test(priority = 7)
    @Description("Get the Board the Card is on")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetTheBoardTheCardIsOn() {

        Response response = getCardsService().getTheBoardTheCardIsOn(CardsTestData.cardId);
        String actualNameOfTheBoardReceived = response.jsonPath().getString("name");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(actualNameOfTheBoardReceived, CardsTestData.BOARD_NAME_FOR_CARDS);
    }

    @Test(priority = 8)
    @Description("Get the checkItems on a Card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetCheckItemsOnACard() {

        Response response = getCardsService().getCheckItemsOnACard(CardsTestData.cardId);

        String actualCheckItemsOnACard = response.body().asString();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(actualCheckItemsOnACard, ConfigurationDataForApiTests.EMPTY_STRING);
    }

    @Test(priority = 8)
    @Description("Get all checklists on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetChecklistsOnACard() {

        Response response = getCardsService().getChecklistsOnACard(CardsTestData.cardId);

        String actualChecklistsOnACard = response.body().asString();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(actualChecklistsOnACard, ConfigurationDataForApiTests.EMPTY_STRING);
    }



    @Test(priority = 9)
    @Description("Create checklist on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateChecklistOnACard() {

        Response response = getCardsService().createAChecklist(CardsTestData.cardId, CardsTestData.NAME_FOR_CHECKLIST_CREATED);
        String checklistNameReceivedBack = response.jsonPath().getString("name");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(checklistNameReceivedBack, CardsTestData.NAME_FOR_CHECKLIST_CREATED);

    }

    @Test(priority = 10)
    @Description("Get the List of a Card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetTheListOfACard() {
        Response response = getCardsService().getTheListOfACard(CardsTestData.cardId);

        String IdOfReceivedList = response.jsonPath().getString("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(IdOfReceivedList, CardsTestData.listIdTheCardIsOn);
    }

    @Test(priority = 10)
    @Description("Get the Members of a Card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetTheMembersOfACard() {

        Response response = getCardsService().getTheMembersOfACard(CardsTestData.cardId);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), ConfigurationDataForApiTests.EMPTY_STRING);
    }

    @Test(priority = 11)
    @Description("Delete a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testDeleteACard() {
        Response response = getCardsService().deleteACard(CardsTestData.cardId);

        Assert.assertEquals(response.getStatusCode(),200);
    }

    }


