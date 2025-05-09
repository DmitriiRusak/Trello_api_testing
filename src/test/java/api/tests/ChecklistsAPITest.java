package api.tests;

import api.base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;

import static api.base.TestData.CheckListsTestData.*;

public class ChecklistsAPITest extends BaseTest {

    @BeforeClass
    public void setUp() {
        boardId = getChecklistsSteps().createABord(bordName);
        toDoListId = getChecklistsSteps().getIdOfTheFirstListOnABoard(boardId);
        cardId = getChecklistsSteps().
                createACard(new HashMap<>() {{
                    put("idList", toDoListId);
                    put("name", "card");
                }})
                .jsonPath().getString("id");
    }

    @AfterClass
    public void tearDown() {
        getChecklistsSteps().deleteBoard(boardId);
    }

    @Test(priority = 0)
    @Story("Checklists")
    @Description("Create a checklist on a card")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateAChecklist() {
        Response response = getChecklistsSteps().createAChecklist(cardId, nameOfAChecklistCreated);

        String actualNameOfChecklistReceived = response.jsonPath().getString("name");
        checklistId = response.jsonPath().getString("id");

        Assert.assertEquals(actualNameOfChecklistReceived, nameOfAChecklistCreated);
    }

    @Test(priority = 1)
    @Story("Checklists")
    @Description("Get a checklist on a card")
    @Severity(SeverityLevel.NORMAL)
    public void testGetAChecklist() {
        Response response = getChecklistsSteps().getCheckList(checklistId);
        String actualIdOfChecklistReceived = response.jsonPath().getString("id");

        Assert.assertEquals(actualIdOfChecklistReceived, checklistId);
    }

    @Test(priority = 2)
    @Story("Checklists")
    @Description("Update a name of a checklist")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateAChecklist() {
        Response response = getChecklistsSteps().updateAFieldOfCheckList(checklistId, nameOfAFieldToBeUpdated, valueForAFieldToBeUpdated);

        String actualNameOfChecklistReceived = response.jsonPath().getString("name");

        Assert.assertEquals(actualNameOfChecklistReceived, valueForAFieldToBeUpdated);
    }

    @Test(priority = 3)
    @Story("Checklists")
    @Description("Get a 'pos' field on a checklist")
    @Severity(SeverityLevel.NORMAL)
    public void testGetFieldOnAChecklist() {
        Response response = getChecklistsSteps().getFieldOnAChecklist(checklistId, fieldToGetBackFromTheChecklist);
        String actualPosOfAChecklistReceivedBack = response.jsonPath().getString("_value");

        Assert.assertEquals(actualPosOfAChecklistReceivedBack, expectedPosOfAChecklist);

    }

    @Test(priority = 3)
    @Story("Checklists")
    @Description("Get a board checklist is on")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTheBoardTheChecklistIsOn() {
        Response response = getChecklistsSteps().getTheBoardTheChecklistIsOn(checklistId);
        String actualIdOfABoardReceived = response.jsonPath().getString("id");

        Assert.assertEquals(actualIdOfABoardReceived, boardId);
    }

    @Test(priority = 3)
    @Story("Checklists")
    @Description("Get the card checklist is on")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTheCardAChecklistIsOn() {
        Response response = getChecklistsSteps().getTheCardAChecklistIsOn(checklistId);
        String actualIdOfACardReceived = response.jsonPath().getString("id");
        actualIdOfACardReceived = actualIdOfACardReceived.substring(1, actualIdOfACardReceived.length() - 1);  //have to remove square brackets

        Assert.assertEquals(actualIdOfACardReceived, cardId);
    }

    @Test(priority = 3)
    @Story("Checklists")
    @Description("Get all checkItems presented on a checklist")
    @Severity(SeverityLevel.NORMAL)
    public void testGetCheckitemsOnAChecklist() {
        Response response = getChecklistsSteps().getCheckitemsOnAChecklist(checklistId);
        String adtualCheckItemsOnAChecklist = response.body().asString();

        Assert.assertEquals(adtualCheckItemsOnAChecklist, emptyString);
    }

    @Test(priority = 4)
    @Story("Checklists")
    @Description("Create new checkItem on a checklist")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateCheckitemOnChecklist() {
        Response response = getChecklistsSteps().createCheckitemOnChecklist(checklistId, nameForNewCheckItem);
        String actualNameOfNewCheckItem = response.jsonPath().getString("name");
        checkItemId = response.jsonPath().getString("id");

        Assert.assertEquals(actualNameOfNewCheckItem, nameForNewCheckItem);
    }

    @Test(priority = 5)
    @Story("Checklists")
    @Description("Get specific checkItem on a checklist")
    @Severity(SeverityLevel.NORMAL)
    public void testGetACheckitemOnAChecklist() {
        Response response = getChecklistsSteps().getACheckitemOnAChecklist(checklistId, checkItemId);
        String actualCheckItemIdReceived = response.jsonPath().getString("id");

        Assert.assertEquals(actualCheckItemIdReceived, checkItemId);
    }

    @Test(priority = 6)
    @Story("Checklists")
    @Description("Delete specific checkItem from checklist")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteCheckitemFromChecklist() {
        Response response = getChecklistsSteps().deleteCheckitemFromChecklist(checklistId, checkItemId);
        String emptyBody = response.jsonPath().getString("limits");

        Assert.assertEquals(emptyBody, expectedStringResult);
    }

    @Test(priority = 7)
    @Story("Checklists")
    @Description("Delete specific checkItem from checklist")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteAChecklist() {
        Response response = getChecklistsSteps().deleteAChecklist(checklistId);
        String emptyBody = response.jsonPath().getString("limits");

        Assert.assertEquals(emptyBody, expectedStringResult);
    }
}
