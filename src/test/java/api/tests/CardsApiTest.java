package api.tests;

import api.base.BaseTest;
import api.base.PathParameters.*;
import api.base.TestData.*;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

import java.util.Map;


@Epic("API Tests")
@Feature("Cards Validation")
@Owner("Group JavaForwardToOffer")
public class CardsApiTest extends BaseTest {

    @BeforeClass
    public void setUp() {
        CardsTestData.boardId = getCardsSteps().createABord(CardsTestData.BOARD_NAME);
        CardsTestData.listId = getCardsSteps().getIdOfTheFirstListOnABoard(CardsTestData.boardId);
    }

    @AfterClass
    public void tearDown() {
        getCardsSteps().deleteBoard(CardsTestData.boardId);
    }

    @Test(priority = 0)
    @Story("Verify cards")
    @Description("Create a new Card")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateANewCard() {
        Map<String, String> queryParametersForRequestSpec = new HashMap<>();
        queryParametersForRequestSpec.put("idList", CardsTestData.listId);

        Response response = getCardsSteps().createACard(queryParametersForRequestSpec);
        CardsTestData.cardId = response.jsonPath().get("id");
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 1)
    @Story("Verify cards")
    @Description("Get a card")
    @Severity(SeverityLevel.NORMAL)
    public void testGetACard() {

        Response response = getCardsSteps().getCard(CardsTestData.cardId);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 1)
    @Story("Verify cards")
    @Description("Update a card")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateACard() {

        String newCardName = "NewCardName";
        Response response = getCardsSteps().updateCard(CardsTestData.cardId, newCardName);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.path("name"), newCardName);
    }

    @Test(priority = 3)
    @Story("Verify field on a cards")
    @Description("Get a field on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAFieldOnACard() {
        Response response = getCardsSteps().getFieldCard(CardsTestData.cardId);

        Assert.assertEquals(response.getStatusCode(), 200);

    }

    @Test(priority = 3)
    @Story("Verify actions on a cards")
    @Description("Get an actions on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetActionsOnACard() {
        Response response = getCardsSteps().getActionsOnACard(CardsTestData.cardId, ActionsEndPoints.ACTIONS_BASE_PATH);
        List arrayList = response.jsonPath().getList("id");

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3)
    @Story("Verify attachments on a cards")
    @Description("Get a attachments on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAttachmentsOnACard() {

        String emptyString = "[]";

        Response response = getCardsSteps().getAttachmentsOnACard(CardsTestData.cardId);
        String actualResponseBody = response.body().asString();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(actualResponseBody, emptyString);

    }

    @Test(priority = 3)
    @Story("Verify stickers of a cards")
    @Description("Get the stickers on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetStickersOnACard() {
        Response response = getCardsSteps().getStickersOnACard(CardsTestData.cardId);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 4)
    @Story("Verify attachments on a cards")
    @Description("Create an attachment on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateAttachmentOnCard() {
        String nameOfCreatedAttachment = "ForCreateAttachmentOnCardTest.txt";

        Response response = getCardsSteps().createAttachmentOnCard(CardsTestData.cardId, "src/test/resources/ForCreateAttachmentOnCardTest.txt");
        CardsTestData.createdAttachmentId = response.jsonPath().getString("id");
        String nameOfAttachmentReceivedBack = response.jsonPath().getString("name");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(nameOfAttachmentReceivedBack, nameOfCreatedAttachment);
    }

    @Test(priority = 5)
    @Story("Cards")
    @Description("Get an attachment on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAnAttachmentOnACard() {

        Response response = getCardsSteps().getAnAttachmentOnACard(CardsTestData.cardId, CardsTestData.createdAttachmentId);
        String attachmentIdReceivedBack = response.jsonPath().getString("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(attachmentIdReceivedBack, CardsTestData.createdAttachmentId);
    }

    @Test(priority = 6)
    @Story("Verify delete an Attachment on a Card")
    @Description("Delete an Attachment on a Card")
    @Severity(SeverityLevel.CRITICAL)
    public void testDeleteAnAttachmentOnACard() {
        Response response = getCardsSteps().deleteAnAttachmentOnACard(CardsTestData.cardId, CardsTestData.createdAttachmentId);
        System.out.println(response.asPrettyString());
    }

    @Test(priority = 7)
    @Story("Verify board on a cards")
    @Description("Get the Board the Card is on")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetTheBoardTheCardIsOn() {

        Response response = getCardsSteps().getTheBoardTheCardIsOn(CardsTestData.cardId);
        System.out.println(CardsTestData.cardId);
        System.out.println(response.asPrettyString());
        String actualNameOfTheBoardReceived = response.jsonPath().getString("name");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(actualNameOfTheBoardReceived, CardsTestData.BOARD_NAME);
    }

    @Test(priority = 8)
    @Story("Verify checkItems on a card")
    @Description("Get the checkItems on a Card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetCheckItemsOnACard() {

        Response response = getCardsSteps().getCheckItemsOnACard(CardsTestData.cardId);

        String actualCheckItemsOnACard = response.body().asString();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(actualCheckItemsOnACard, CardsTestData.emptyString);
    }

    @Test(priority = 8)
    @Story("Cards")
    @Description("Get all checklists on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetChecklistsOnACard() {

        Response response = getCardsSteps().getChecklistsOnACard(CardsTestData.cardId);

        String actualChecklistsOnACard = response.body().asString();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(actualChecklistsOnACard, CardsTestData.emptyString);
    }



    @Test(priority = 9)
    @Story("Cards")
    @Description("Create checklist on a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateChecklistOnACard() {

        Response response = getCardsSteps().createAChecklist(CardsTestData.cardId, CardsTestData.NAME_FOR_CHECKLIST_CREATED);
        String checklistNameReceivedBack = response.jsonPath().getString("name");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(checklistNameReceivedBack, CardsTestData.NAME_FOR_CHECKLIST_CREATED);

    }

    @Test(priority = 10)
    @Story("Verify list of a cards")
    @Description("Get the List of a Card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetTheListOfACard() {
        Response response = getCardsSteps().getTheListOfACard(CardsTestData.cardId);

        String IdOfReceivedList = response.jsonPath().getString("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(IdOfReceivedList, CardsTestData.listId);
    }

    @Test(priority = 10)
    @Story("Verify Members of a cards")
    @Description("Get the Members of a Card")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetTheMembersOfACard() {

        Response response = getCardsSteps().getTheMembersOfACard(CardsTestData.cardId);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().asString(), CardsTestData.emptyString);
    }

    @Test(priority = 11)
    @Story("Verify cards")
    @Description("Delete a card")
    @Severity(SeverityLevel.CRITICAL)
    public void testDeleteACard() {
        Response response = getCardsSteps().deleteACard(CardsTestData.cardId);

        Assert.assertEquals(response.getStatusCode(),200);
    }

    }


