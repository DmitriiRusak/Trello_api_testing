package api.tests;

import api.resourcesForTests.ConfigurationDataForApiTests;
import api.resourcesForTests.ConfigurationDataForApiTests.*;
import api.resourcesForTests.ListFields;
import api.resourcesForTests.PathParameters;
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
import java.util.List;
import java.util.Map;

import static api.resourcesForTests.PathParameters.ListsPath.cardsEndPoint;
import static api.resourcesForTests.ConfigurationDataForApiTests.ListsTestData.*;

@Epic("API Tests")
@Feature("Lists")
@Listeners(TestListener.class)
public class ListsApiTest extends ServiceWorkShop {

    @BeforeClass
    public void setUp() {

        LogFactory.getLogger().info("+++++++++++++++ class \uD83D\uDFE1" + this.getClass().getName() + "\uD83D\uDFE1 started +++++++++++++++");
        BoardTestData.boardId = getListsService().createABord(bordName);
        ListsTestData.toDoListId = getListsService().getListOfIdOfAllListsOnABoard(BoardTestData.boardId).get(0).toString();
    }

    @AfterClass
    public void tearDown() {
        getListsService().deleteBoard(BoardTestData.boardId);
    }

    @Test(priority = 0)
    @Description("Create a new List on a Board")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateNewList() {
        Response response = getListsService().createList(ListsTestData.nameOfTheList, BoardTestData.boardId);
        ListsTestData.newCreatedListId = getListsService().getListOfIdOfAllListsOnABoard(BoardTestData.boardId).get(0).toString();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.path("name"), ListsTestData.nameOfTheList);
    }

    @Test(priority = 1)
    @Description("Update a name of the list")
    @Severity(SeverityLevel.CRITICAL)
    public void tesUpdateANameForToDoList() {
        Response response = getListsService().updateAFieldOfAList(ListsTestData.toDoListId, ListFields.name, ListsTestData.newNameForTheList);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"), ListsTestData.newNameForTheList);
    }

    @Test(priority = 2)
    @Description("Get a list from a board")
    @Severity(SeverityLevel.NORMAL)
    public void testGetAList() {
        Response response = getListsService().getAList(ListsTestData.toDoListId);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"), newNameForTheList);
    }

    @Test(priority = 2)
    @Description("Archive all cards on a list")
    @Severity(SeverityLevel.CRITICAL)
    public void testArchiveAllCardsOnTheList() {
        //create a card on a list
        Map<String, String> queryParametersForRequestSpec = new HashMap<>();
        queryParametersForRequestSpec.put("idList", toDoListId);
        queryParametersForRequestSpec.put("name", "nameForCard");
        getListsService().createACard(queryParametersForRequestSpec);

        //make sure that list has 1 card
        Response response = getListsService().getResourcesOfAList(toDoListId, PathParameters.endPoints.get("card"));
        List <String> numberOfCardsPresentedOnAList = response.jsonPath().getList("name");
        Assert.assertEquals(numberOfCardsPresentedOnAList.size(), 1);

        //archive all cards on a list
        getListsService().archiveAllCardOnTheList(toDoListId);
        response = getListsService().getResourcesOfAList(toDoListId, PathParameters.endPoints.get("card"));
        numberOfCardsPresentedOnAList = response.jsonPath().getList("name");

        //make sure that list do not have any cards
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(numberOfCardsPresentedOnAList.size(), 0);
    }

    @Test(priority = 2)
    @Description("Move all cards from one list to another")
    @Severity(SeverityLevel.CRITICAL)
    public void testMoveAllCardsFromOneListToAnother() {
        //create 2 cards on a list
        Map<String, String> queryParametersForRequestSpec = new HashMap<>();
        queryParametersForRequestSpec.put("idList", newCreatedListId);
        queryParametersForRequestSpec.put("name", "nameForCard");
        getListsService().createACard(queryParametersForRequestSpec);
        getListsService().createACard(queryParametersForRequestSpec);

        //move all cards from the list that has 2 cards to another list that do not have cards at all
        Response response = getListsService().moveAllCardsFromOneListToAnother(newCreatedListId, BoardTestData.boardId, toDoListId);

        Assert.assertEquals(response.getStatusCode(), 200);
        response = getListsService().getResourcesOfAList(toDoListId, PathParameters.endPoints.get("card"));
        List <String> allCardsPresentedOnAList = response.jsonPath().getList("name");

        //make sure that all cards moved to toDo list, and that toDo list has 2 cards now.
        Assert.assertEquals(allCardsPresentedOnAList.size(), 2);
    }

    @Test(priority = 3)
    @Description("Archive a list on a board")
    @Severity(SeverityLevel.CRITICAL)
    public void testArchiveAList() {

        List numberOfListPresentedOnABoard = getListsService().getListOfIdOfAllListsOnABoard(BoardTestData.boardId);
        int numberOfListBeforeArchive = numberOfListPresentedOnABoard.size();

        Response response = getListsService().archiveAList(toDoListId);

        numberOfListPresentedOnABoard = getListsService().getListOfIdOfAllListsOnABoard(BoardTestData.boardId);
        int numberOfListAfterArchive = numberOfListPresentedOnABoard.size();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(numberOfListBeforeArchive, 4);
        Assert.assertEquals(numberOfListAfterArchive, 3);
        Assert.assertNotEquals(numberOfListAfterArchive, numberOfListBeforeArchive);
    }

    @Test(priority = 4)
    @Description("Unarchived a list on a board")
    @Severity(SeverityLevel.CRITICAL)
    public void testUnArchiveAList() {

        List numberOfListPresentedOnABoard = getListsService().getListOfIdOfAllListsOnABoard(BoardTestData.boardId);
        int numberOfListBeforeAnArchive = numberOfListPresentedOnABoard.size();

        Response response = getListsService().unArchiveAList(toDoListId);
        numberOfListPresentedOnABoard = getListsService().getListOfIdOfAllListsOnABoard(BoardTestData.boardId);
        int numberOfListAfterArchive = numberOfListPresentedOnABoard.size();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotEquals(numberOfListBeforeAnArchive, numberOfListAfterArchive);
        Assert.assertTrue(numberOfListAfterArchive > numberOfListBeforeAnArchive);
    }

    @Test(priority = 5)
    @Description("Get all cards available on a list")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetCardsInAList() {
        Response response = getListsService().getResourcesOfAList(toDoListId, cardsEndPoint);
        List arrayList = response.jsonPath().getList("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(arrayList.size(), 2);
    }

    @Test(priority = 5)
    @Description("Move list from one board to another")
    @Severity(SeverityLevel.CRITICAL)
    public void testMoveListFromOneBoardToAnother() {
        //create one more board in a workspace in order to move list on it
        String tempIdOfTheSecondBoard = getListsService().createABord(nameForSecondBoard);

        //check what is current id of a board the list is on
        String idOfABoardBeforeListBeingMoved = getListsService().getABoardAListIsOn(toDoListId).jsonPath().getString("id");

        //move list to another board
        Response response = getListsService().moveListFromOneBoardToAnother(toDoListId, tempIdOfTheSecondBoard);

        Assert.assertEquals(response.getStatusCode(), 200);
        String idOfABoardAfterListBeingBoved = getListsService().getABoardAListIsOn(toDoListId).jsonPath().getString("id");

        //make sure that id of a board, the list is on, changed ofter list being moved
        Assert.assertNotEquals(idOfABoardBeforeListBeingMoved, idOfABoardAfterListBeingBoved);
        Assert.assertEquals(idOfABoardAfterListBeingBoved, tempIdOfTheSecondBoard);

        getListsService().deleteBoard(tempIdOfTheSecondBoard);
    }

    @Test(priority = 5)
    @Description("Update subscribed field of a list")
    @Severity(SeverityLevel.CRITICAL)
    public void testUpdateASubscribedFieldOfAList() {

        String subscribedValueBeforeUpdate = getListsService().getAFieldOfAList(newCreatedListId, "subscribed").jsonPath().getString("subscribed");

        Response response = getListsService().updateAFieldOfAList(newCreatedListId, ListFields.subscribed, "true");

        String subscribedValueAfterUpdate = getListsService().getAFieldOfAList(newCreatedListId, "subscribed").jsonPath().getString("subscribed");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(subscribedValueAfterUpdate, "true");
        Assert.assertNotEquals(subscribedValueBeforeUpdate, subscribedValueAfterUpdate);
    }

    @Test(priority = 6)
    @Description("Update subscribed field of a list")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetActionsOfAList() {
        Response response = getListsService().getActionsofAList(newCreatedListId);
        List arrayList = response.jsonPath().getList("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(arrayList.size(), 3);
    }

    @Test(priority = 6)
    @Description("Get the Board a List is on")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetABoardAListIsOn() {
        Response response = getListsService().getABoardAListIsOn(newCreatedListId);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("id"), BoardTestData.boardId);
    }
}
