package api.tests;

import api.resourcesForTests.CardFields;
import api.resourcesForTests.configurationData.CardTestData;
import api.resourcesForTests.configurationData.CommonConfigData;
import api.services.ServiceWorkShop;
import api.resourcesForTests.configurationData.CommonConfigData.*;
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

import static api.resourcesForTests.configurationData.CommonConfigData.EXPECTED_EMPTY_STRING_RESULT;


@Epic("API Tests")
@Feature("Cards")
@Listeners(TestListener.class)
public class CardsApiTest extends ServiceWorkShop {

    private CardTestData cardTestData = new CardTestData();

    @BeforeClass
    public void setUp() {

        LogFactory.getLogger().info("+++++++++++++++ class \uD83D\uDFE1" + this.getClass().getName() + "\uD83D\uDFE1 started +++++++++++++++");
        cardTestData.setBoardId(getCardsService().createABord(cardTestData.BOARD_NAME_FOR_CARDS));
        cardTestData.setToDoListId(getActionsService().getListOfIdOfAllListsOnABoard(cardTestData.getBoardId()).get(0).toString());
    }

    @AfterClass
    public void tearDown() {
        getCardsService().deleteBoard(cardTestData.getBoardId());
    }

    @Test(priority = 0)
    @Description("Create a new Card")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateANewCard() {
        Map<String, String> queryParametersForRequestSpec = new HashMap<>();
        queryParametersForRequestSpec.put("idList", cardTestData.getToDoListId());

        Response response = getCardsService().createACard(queryParametersForRequestSpec);

        cardTestData.setCardId(response.jsonPath().get("id"));
        Assert.assertEquals(response.getStatusCode(), 200);

        cardTestData.setListIdTheCardIsOn(getCardsService().getTheListOfACard(cardTestData.getCardId()).jsonPath().getString("id"));

        Assert.assertEquals(cardTestData.getListIdTheCardIsOn(), cardTestData.getToDoListId());
    }

    @Test(priority = 1)
    @Description("Get a card")
    @Severity(SeverityLevel.NORMAL)
    public void testGetACard() {

        Response response = getCardsService().getCard(cardTestData.getCardId());

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 1)
    @Description("Update a card")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateACard() {

        String newCardName = "NewCardName";
        Response response = getCardsService().updateCard(cardTestData.getCardId(),"name", newCardName);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.path("name"), newCardName);
    }

    @Test(priority = 3)
    @Description("Get a field on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAFieldOnACard() {
        Response response = getCardsService().getAFieldOfTheCard(cardTestData.getCardId(), CardFields.name);
        String expectedName = "NewCardName";

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("_value"), expectedName);

    }

    @Test(priority = 3)
    @Description("Get an actions on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetActionsOnACard() {
        Response response = getCardsService().getActionsOnACard(cardTestData.getCardId());
        List arrayList = response.jsonPath().getList("id");

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3)
    @Description("Get a attachments on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAttachmentsOnACard() {

        String emptyString = "[]";

        Response response = getCardsService().getAttachmentsOnACard(cardTestData.getCardId());
        String actualResponseBody = response.body().asString();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(actualResponseBody, emptyString);

    }

    @Test(priority = 3)
    @Description("Get the stickers on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetStickersOnACard() {
        Response response = getCardsService().getStickersOnACard(cardTestData.getCardId());

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 4)
    @Description("Create an attachment on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateAttachmentOnCard() {
        String nameOfCreatedAttachment = "ForCreateAttachmentOnCardTest.txt";

        Response response = getCardsService().createAttachmentOnCard(cardTestData.getCardId(), "src/test/resources/ForCreateAttachmentOnCardTest.txt");
        cardTestData.setCreatedAttachmentId(response.jsonPath().getString("id"));
        String nameOfAttachmentReceivedBack = response.jsonPath().getString("name");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(nameOfAttachmentReceivedBack, nameOfCreatedAttachment);
    }

    @Test(priority = 5)
    @Description("Get an attachment on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAnAttachmentOnACard() {

        Response response = getCardsService().getAnAttachmentOnACard(cardTestData.getCardId(), cardTestData.getCreatedAttachmentId());
        String attachmentIdReceivedBack = response.jsonPath().getString("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(attachmentIdReceivedBack, cardTestData.getCreatedAttachmentId());
    }

    @Test(priority = 6)
    @Description("Delete an Attachment on a Card")
    @Severity(SeverityLevel.CRITICAL)
    public void testDeleteAnAttachmentOnACard() {

        Response response = getCardsService().deleteAnAttachmentOnACard(cardTestData.getCardId(), cardTestData.getCreatedAttachmentId());

        String actualStringresponse = response.jsonPath().getString("limits");

        Assert.assertEquals(EXPECTED_EMPTY_STRING_RESULT, actualStringresponse);
    }

    @Test(priority = 7)
    @Description("Get the Board the Card is on")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetTheBoardTheCardIsOn() {

        Response response = getCardsService().getTheBoardTheCardIsOn(cardTestData.getCardId());
        String actualNameOfTheBoardReceived = response.jsonPath().getString("name");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(actualNameOfTheBoardReceived, cardTestData.BOARD_NAME_FOR_CARDS);
    }

    @Test(priority = 8)
    @Description("Get the checkItems on a Card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetCheckItemsOnACard() {

        Response response = getCardsService().getCheckItemsOnACard(cardTestData.getCardId());

        String actualCheckItemsOnACard = response.body().asString();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(actualCheckItemsOnACard, CommonConfigData.EMPTY_STRING);
    }

    @Test(priority = 8)
    @Description("Get all checklists on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetChecklistsOnACard() {

        Response response = getCardsService().getChecklistsOnACard(cardTestData.getCardId());

        String actualChecklistsOnACard = response.body().asString();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(actualChecklistsOnACard, CommonConfigData.EMPTY_STRING);
    }



    @Test(priority = 9)
    @Description("Create checklist on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateChecklistOnACard() {

        Response response = getCardsService().createAChecklist(cardTestData.getCardId(), cardTestData.NAME_FOR_CHECKLIST_CREATED);
        String checklistNameReceivedBack = response.jsonPath().getString("name");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(checklistNameReceivedBack, cardTestData.NAME_FOR_CHECKLIST_CREATED);

    }

    @Test(priority = 10)
    @Description("Get the List of a Card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetTheListOfACard() {
        Response response = getCardsService().getTheListOfACard(cardTestData.getCardId());

        String IdOfReceivedList = response.jsonPath().getString("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(IdOfReceivedList, cardTestData.getListIdTheCardIsOn());
    }

    @Test(priority = 10)
    @Description("Get the Members of a Card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetTheMembersOfACard() {

        Response response = getCardsService().getTheMembersOfACard(cardTestData.getCardId());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), CommonConfigData.EMPTY_STRING);
    }

    @Test(priority = 11)
    @Description("Delete a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testDeleteACard() {
        Response response = getCardsService().deleteACard(cardTestData.getCardId());

        Assert.assertEquals(response.getStatusCode(),200);
    }

    }


