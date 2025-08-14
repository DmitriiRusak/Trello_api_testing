package api.tests;

import api.resourcesForTests.CheckListFields;
import api.services.ServiceWorkShop;
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

import static api.resourcesForTests.ConfigurationDataForApiTests.CheckListsTestData.*;
import static api.resourcesForTests.ConfigurationDataForApiTests.EMPTY_STRING;
import static api.resourcesForTests.ConfigurationDataForApiTests.EXPECTED_EMPTY_STRING_RESULT;

import api.resourcesForTests.ConfigurationDataForApiTests.*;

@Epic("API Tests")
@Feature("Checklist")
@Listeners(TestListener.class)
public class ChecklistsAPITest extends ServiceWorkShop {

    @BeforeClass
    public void setUp() {

        LogFactory.getLogger().info("+++++++++++++++ class \uD83D\uDFE1" + this.getClass().getName() + "\uD83D\uDFE1 started +++++++++++++++");
        BoardTestData.boardId = getChecklistsService().createABord(CheckListsTestData.BOARD_NAME_FOR_CHECKLIST);
        ListsTestData.toDoListId = getChecklistsService().getListOfIdOfAllListsOnABoard(BoardTestData.boardId).get(0).toString();
        CardsTestData.cardId = getChecklistsService().
                createACard(new HashMap<>() {{
                    put("idList", ListsTestData.toDoListId);
                    put("name", "card");
                }})
                .jsonPath().getString("id");
    }

    @AfterClass
    public void tearDown() {
        getChecklistsService().deleteBoard(BoardTestData.boardId);
    }

    @Test(priority = 0)
    @Description("Create a checklist on a card")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateAChecklist() {
        Response response = getChecklistsService().createAChecklist(CardsTestData.cardId, nameOfAChecklistCreated);

        String actualNameOfChecklistReceived = response.jsonPath().getString("name");
        checklistId = response.jsonPath().getString("id");

        Assert.assertEquals(actualNameOfChecklistReceived, nameOfAChecklistCreated);
    }

    @Test(priority = 1)
    @Description("Get a checklist on a card")
    @Severity(SeverityLevel.NORMAL)
    public void testGetAChecklist() {
        Response response = getChecklistsService().getCheckList(checklistId);
        String actualIdOfChecklistReceived = response.jsonPath().getString("id");

        Assert.assertEquals(actualIdOfChecklistReceived, checklistId);
    }

    @Test(priority = 2)
    @Description("Update a name of a checklist")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateAChecklist() {
        Response response = getChecklistsService().updateAFieldOfCheckList(checklistId,
                CheckListFields.name, CheckListsTestData.NEW_NAME_FOR_CHECKLIST);

        String actualNameOfChecklistReceived = response.jsonPath().getString("name");

        Assert.assertEquals(actualNameOfChecklistReceived, NEW_NAME_FOR_CHECKLIST);
    }

    @Test(priority = 3)
    @Description("Get a 'name' field on a checklist")
    @Severity(SeverityLevel.NORMAL)
    public void testGetANameOfAChecklist() {
        Response response = getChecklistsService().getFieldOnAChecklist(CheckListsTestData.checklistId, CheckListFields.name);
        String actualNameOfAChecklistReceivedBack = response.jsonPath().getString("_value");

        Assert.assertEquals(actualNameOfAChecklistReceivedBack, NEW_NAME_FOR_CHECKLIST);

    }

    @Test(priority = 3)
    @Description("Get a board checklist is on")
    @Severity(SeverityLevel.NORMAL)
    public void testGetABoardTheChecklistIsOn() {
        Response response = getChecklistsService().getTheBoardTheChecklistIsOn(checklistId);
        String actualIdOfABoardReceived = response.jsonPath().getString("id");

        Assert.assertEquals(actualIdOfABoardReceived, BoardTestData.boardId);
    }

    @Test(priority = 3)
    @Description("Get the card checklist is on")
    @Severity(SeverityLevel.NORMAL)
    public void testGetACardTheChecklistIsOn() {
        Response response = getChecklistsService().getTheCardAChecklistIsOn(checklistId);
        String actualIdOfACardReceived = response.jsonPath().getString("id");
        actualIdOfACardReceived = actualIdOfACardReceived.substring(1, actualIdOfACardReceived.length() - 1);  //have to remove square brackets

        Assert.assertEquals(actualIdOfACardReceived, CardsTestData.cardId);
    }

    @Test(priority = 3)
    @Description("Get all checkItems presented on a checklist")
    @Severity(SeverityLevel.NORMAL)
    public void testGetCheckitemsOnAChecklist() {
        Response response = getChecklistsService().getCheckitemsOnAChecklist(checklistId);
        String adtualCheckItemsOnAChecklist = response.body().asString();

        Assert.assertEquals(adtualCheckItemsOnAChecklist, EMPTY_STRING);
    }

    @Test(priority = 4)
    @Description("Create new checkItem on a checklist")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateCheckitemOnChecklist() {
        Response response = getChecklistsService().createCheckitemOnChecklist(checklistId, nameForNewCheckItem);
        String actualNameOfNewCheckItem = response.jsonPath().getString("name");
        checkItemId = response.jsonPath().getString("id");

        Assert.assertEquals(actualNameOfNewCheckItem, nameForNewCheckItem);
    }

    @Test(priority = 5)
    @Description("Get specific checkItem on a checklist")
    @Severity(SeverityLevel.NORMAL)
    public void testGetACheckitemOnAChecklist() {
        Response response = getChecklistsService().getACheckitemOnAChecklist(checklistId, checkItemId);
        String actualCheckItemIdReceived = response.jsonPath().getString("id");

        Assert.assertEquals(actualCheckItemIdReceived, checkItemId);
    }

    @Test(priority = 6)
    @Description("Delete specific checkItem from checklist")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteCheckitemFromChecklist() {
        Response response = getChecklistsService().deleteCheckitemFromChecklist(checklistId, checkItemId);
        String emptyBody = response.jsonPath().getString("limits");

        Assert.assertEquals(emptyBody, EXPECTED_EMPTY_STRING_RESULT);
    }

    @Test(priority = 7)
    @Description("Delete checklist")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteAChecklist() {
        Response response = getChecklistsService().deleteAChecklist(checklistId);
        String emptyBody = response.jsonPath().getString("limits");

        Assert.assertEquals(emptyBody, EXPECTED_EMPTY_STRING_RESULT);
    }
}
