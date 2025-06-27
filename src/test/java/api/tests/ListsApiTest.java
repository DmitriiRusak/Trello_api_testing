package api.tests;

import api.base.BaseTest;
import api.base.TestData;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static api.base.PathParameters.ListsPath.cardsEndPoint;
import static api.base.TestData.ListsTestData.*;

@Epic("API Tests")
@Feature("Lists Validation")
@Owner("Group JavaForwardToOffer")
public class ListsApiTest extends BaseTest {

    @BeforeClass
    public void setUp() {
        TestData.ListsTestData.boardId = getListsService().createABord(bordName);
        TestData.ListsTestData.toDoListId = getListsService().getIdOfTheFirstListOnABoard(boardId);
    }

    @AfterClass
    public void tearDown() {
        getListsService().deleteBoard(boardId);
    }

    @Test(priority = 0)
    @Story("lists")
    @Description("Create a new List on a Board")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateNewList() {
        Response response = getListsService().createList(nameOfTheList, boardId);
        newCreatedListId = getListsService().getIdOfTheFirstListOnABoard(boardId);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.path("name"), nameOfTheList);
    }

    @Test(priority = 1)
    @Story("lists")
    @Description("Update a name of the list")
    @Severity(SeverityLevel.CRITICAL)
    public void tesUpdateANameForToDoList() {
        Response response = getListsService().updateANameForList(toDoListId, newNameForTheList);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"), newNameForTheList);
    }

    @Test(priority = 2)
    @Story("lists")
    @Description("Get a list from a board with updated name (before 'ToDO' now it is 'Updated name for the list')")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAList() {
        Response response = getListsService().getAList(toDoListId);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"), newNameForTheList);
    }

    @Test(priority = 2)
    @Story("lists")
    @Description("Create a new List on a Board")
    @Severity(SeverityLevel.CRITICAL)
    public void testArchiveAllCardsOnTheList() {
        Map<String, String> queryParametersForRequestSpec = new HashMap<>();
        queryParametersForRequestSpec.put("idList", toDoListId);
        queryParametersForRequestSpec.put("name", "nameForCard");

        getListsService().createACard(queryParametersForRequestSpec);

        Response response = getListsService().archiveAllCardOnTheList(toDoListId);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2)
    @Story("lists")
    @Description("Move all cards from one list to another")
    @Severity(SeverityLevel.CRITICAL)
    public void testMoveAllCardsFromOneListToAnother() {
        Map<String, String> queryParametersForRequestSpec = new HashMap<>();
        queryParametersForRequestSpec.put("idList", newCreatedListId);
        queryParametersForRequestSpec.put("name", "nameForCard");

        getListsService().createACard(queryParametersForRequestSpec);
        getListsService().createACard(queryParametersForRequestSpec);

        Response response = getListsService().moveAllCardsFromOneListToAnother(newCreatedListId, boardId, toDoListId);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3)
    @Story("lists")
    @Description("Archive a list on a board")
    @Severity(SeverityLevel.CRITICAL)
    public void testArchiveAList() {
        Response response = getListsService().archiveAList(toDoListId);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 4)
    @Story("lists")
    @Description("Unarchived a list on a board")
    @Severity(SeverityLevel.CRITICAL)
    public void testUnArchiveAList() {
        Response response = getListsService().unArchiveAList(toDoListId);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 5)
    @Story("lists")
    @Description("Get all cards available on a list")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetCardsInAList() {
        Response response = getListsService().getResourcesOfAList(toDoListId, cardsEndPoint);
        List arrayList = response.jsonPath().getList("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(arrayList.size(), 2);
    }

    @Test(priority = 5)
    @Story("lists")
    @Description("Move list from one board to another")
    @Severity(SeverityLevel.CRITICAL)
    public void testMoveListFromOneBoardToAnother() {
        String idOfTheSecondBoard = getListsService().createABord(nameForSecondBoard);

        Response response = getListsService().moveListFromOneBoardToAnother(toDoListId, idOfTheSecondBoard);

        Assert.assertEquals(response.getStatusCode(), 200);
        getListsService().deleteBoard(idOfTheSecondBoard);
    }

    @Test(priority = 5)
    @Story("lists")
    @Description("Update subscribed field of a list")
    @Severity(SeverityLevel.CRITICAL)
    public void testUpdateASubscribedFieldOfAList() {
        Response response = getListsService().updateSubscribedFieldOfAList(newCreatedListId, subscribeValue);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 6)
    @Story("lists")
    @Description("Update subscribed field of a list")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetActionsOfAList() {
        Response response = getListsService().getActionsofAList(newCreatedListId);
        List arrayList = response.jsonPath().getList("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(arrayList.size(), 3);
    }

    @Test(priority = 6)
    @Story("lists")
    @Description("Get the Board a List is on")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetABoardAListIsOn() {
        Response response = getListsService().getABoardAListIsOn(newCreatedListId);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("id"), boardId);
    }
}
