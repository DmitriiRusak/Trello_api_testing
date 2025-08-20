package api.tests;

import api.resourcesForTests.ListFields;
import api.resourcesForTests.PathParameters;
import api.resourcesForTests.configurationData.ListTestData;
import api.services.ListsService;
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

@Epic("API Tests")
@Feature("Lists")
@Listeners(TestListener.class)
public class ListsApiTest{

    private final ListTestData listTestData = new ListTestData();
    private final ListsService listsService = new ListsService();

    @BeforeClass
    public void setUp() {

        LogFactory.getLogger().info("+++++++++++++++ class \uD83D\uDFE1" + this.getClass().getName() + "\uD83D\uDFE1 started +++++++++++++++");
        listTestData.setBoardId(listsService.createABord(listTestData.BOARD_NAME, listsService.getListRequestSpecification()));
        listsService.reSetListRequestSpecification();
        listTestData.setToDoListId(listsService.getListOfIdOfAllListsOnABoard(listTestData.getBoardId(), listsService.getListRequestSpecification()).get(0).toString());
        listsService.reSetListRequestSpecification();
    }

    @AfterClass
    public void tearDown() {
        listsService.deleteBoard(listTestData.getBoardId(), listsService.getListRequestSpecification());
        listsService.reSetListRequestSpecification();
    }

    @Test(priority = 0)
    @Description("Create a new List on a Board")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateNewList() {
        Response response = listsService.createList(ListTestData.NAME_OF_THE_LIST, listTestData.getBoardId());
        listTestData.setNewCreatedListId(listsService.getListOfIdOfAllListsOnABoard(listTestData.getBoardId(), listsService.getListRequestSpecification()).get(0).toString());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.path("name"), listTestData.NAME_OF_THE_LIST);
    }

    @Test(priority = 1)
    @Description("Update a name of the list")
    @Severity(SeverityLevel.CRITICAL)
    public void tesUpdateANameForToDoList() {
        Response response = listsService.updateAFieldOfAList(listTestData.getToDoListId(), ListFields.name, listTestData.NEW_NAME_FOR_THE_LIST);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"), listTestData.NEW_NAME_FOR_THE_LIST);
    }

    @Test(priority = 2)
    @Description("Get a list from a board")
    @Severity(SeverityLevel.NORMAL)
    public void testGetAList() {
        Response response = listsService.getAList(listTestData.getToDoListId());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"), listTestData.NEW_NAME_FOR_THE_LIST);
    }

    @Test(priority = 2)
    @Description("Archive all cards on a list")
    @Severity(SeverityLevel.CRITICAL)
    public void testArchiveAllCardsOnTheList() {
        //create a card on a list
        Map<String, String> queryParametersForRequestSpec = new HashMap<>();
        queryParametersForRequestSpec.put("idList", listTestData.getToDoListId());
        queryParametersForRequestSpec.put("name", "nameForCard");
        listsService.createACard(queryParametersForRequestSpec, listsService.getListRequestSpecification());

        //make sure that list has 1 card
        Response response = listsService.getResourcesOfAList(listTestData.getToDoListId(), PathParameters.endPoints.get("card"));
        List <String> numberOfCardsPresentedOnAList = response.jsonPath().getList("name");
        Assert.assertEquals(numberOfCardsPresentedOnAList.size(), 1);

        //archive all cards on a list
        listsService.archiveAllCardOnTheList(listTestData.getToDoListId());
        response = listsService.getResourcesOfAList(listTestData.getToDoListId(), PathParameters.endPoints.get("card"));
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
        queryParametersForRequestSpec.put("idList", listTestData.getNewCreatedListId());
        queryParametersForRequestSpec.put("name", "nameForCard");
        listsService.createACard(queryParametersForRequestSpec, listsService.getListRequestSpecification());
        listsService.reSetListRequestSpecification();
        listsService.createACard(queryParametersForRequestSpec, listsService.getListRequestSpecification());
        listsService.reSetListRequestSpecification();

        //move all cards from the list that has 2 cards to another list that do not have cards at all
        Response response = listsService.moveAllCardsFromOneListToAnother(listTestData.getNewCreatedListId(), listTestData.getBoardId(), listTestData.getToDoListId());

        Assert.assertEquals(response.getStatusCode(), 200);
        response = listsService.getResourcesOfAList(listTestData.getToDoListId(), PathParameters.endPoints.get("card"));
        List <String> allCardsPresentedOnAList = response.jsonPath().getList("name");

        //make sure that all cards moved to toDo list, and that toDo list has 2 cards now.
        Assert.assertEquals(allCardsPresentedOnAList.size(), 2);
    }

    @Test(priority = 3)
    @Description("Archive a list on a board")
    @Severity(SeverityLevel.CRITICAL)
    public void testArchiveAList() {

        List numberOfListPresentedOnABoard = listsService.getListOfIdOfAllListsOnABoard(listTestData.getBoardId(), listsService.getListRequestSpecification());
        int numberOfListBeforeArchive = numberOfListPresentedOnABoard.size();

        Response response = listsService.archiveAList(listTestData.getToDoListId());

        numberOfListPresentedOnABoard = listsService.getListOfIdOfAllListsOnABoard(listTestData.getBoardId(), listsService.getListRequestSpecification());
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

        List numberOfListPresentedOnABoard = listsService.getListOfIdOfAllListsOnABoard(listTestData.getBoardId(), listsService.getListRequestSpecification());
        listsService.reSetListRequestSpecification();
        int numberOfListBeforeAnArchive = numberOfListPresentedOnABoard.size();

        Response response = listsService.unArchiveAList(listTestData.getToDoListId());
        numberOfListPresentedOnABoard = listsService.getListOfIdOfAllListsOnABoard(listTestData.getBoardId(), listsService.getListRequestSpecification());
        int numberOfListAfterArchive = numberOfListPresentedOnABoard.size();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotEquals(numberOfListBeforeAnArchive, numberOfListAfterArchive);
        Assert.assertTrue(numberOfListAfterArchive > numberOfListBeforeAnArchive);
    }

    @Test(priority = 5)
    @Description("Get all cards available on a list")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetCardsInAList() {
        Response response = listsService.getResourcesOfAList(listTestData.getToDoListId(), cardsEndPoint);
        List arrayList = response.jsonPath().getList("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(arrayList.size(), 2);
    }

    @Test(priority = 5)
    @Description("Move list from one board to another")
    @Severity(SeverityLevel.CRITICAL)
    public void testMoveListFromOneBoardToAnother() {
        //create one more board in a workspace in order to move list on it
        String tempIdOfTheSecondBoard = listsService.createABord(listTestData.NAME_FOR_SECOND_BOARD, listsService.getListRequestSpecification());

        //check what is current id of a board the list is on
        String idOfABoardBeforeListBeingMoved = listsService.getABoardAListIsOn(listTestData.getToDoListId()).jsonPath().getString("id");

        //move list to another board
        Response response = listsService.moveListFromOneBoardToAnother(listTestData.getToDoListId(), tempIdOfTheSecondBoard);

        Assert.assertEquals(response.getStatusCode(), 200);
        String idOfABoardAfterListBeingBoved = listsService.getABoardAListIsOn(listTestData.getToDoListId()).jsonPath().getString("id");

        //make sure that id of a board, the list is on, changed ofter list being moved
        Assert.assertNotEquals(idOfABoardBeforeListBeingMoved, idOfABoardAfterListBeingBoved);
        Assert.assertEquals(idOfABoardAfterListBeingBoved, tempIdOfTheSecondBoard);

        listsService.deleteBoard(tempIdOfTheSecondBoard, listsService.getListRequestSpecification());
    }

    @Test(priority = 5)
    @Description("Update subscribed field of a list")
    @Severity(SeverityLevel.CRITICAL)
    public void testUpdateASubscribedFieldOfAList() {

        String subscribedValueBeforeUpdate = listsService.getAFieldOfAList(listTestData.getNewCreatedListId(), "subscribed").jsonPath().getString("subscribed");

        Response response = listsService.updateAFieldOfAList(listTestData.getNewCreatedListId(), ListFields.subscribed, "true");

        String subscribedValueAfterUpdate = listsService.getAFieldOfAList(listTestData.getNewCreatedListId(), "subscribed").jsonPath().getString("subscribed");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(subscribedValueAfterUpdate, "true");
        Assert.assertNotEquals(subscribedValueBeforeUpdate, subscribedValueAfterUpdate);
    }

    @Test(priority = 6)
    @Description("Update subscribed field of a list")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetActionsOfAList() {
        Response response = listsService.getActionsofAList(listTestData.getNewCreatedListId());
        List arrayList = response.jsonPath().getList("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(arrayList.size(), 3);
    }

    @Test(priority = 6)
    @Description("Get the Board a List is on")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetABoardAListIsOn() {
        Response response = listsService.getABoardAListIsOn(listTestData.getNewCreatedListId());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("id"), listTestData.getBoardId());
    }
}
