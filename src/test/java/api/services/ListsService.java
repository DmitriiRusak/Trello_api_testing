package api.services;

import api.resourcesForTests.PathParameters;
import api.resourcesForTests.configurationData.CommonConfigData;
import api.resourcesForTests.ListFields;
import api.resourcesForTests.configurationData.ListTestData;
import api.utils.ApiClient;
import api.utils.Specification;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Map;

import static api.resourcesForTests.PathParameters.ListsPath.*;

public class ListsService{

    private Specification specification = new Specification();
    private RequestSpecification listRequestSpecification = RestAssured.given().spec(specification.installRequest());
    private final ApiClient apiClient = new ApiClient();


    public RequestSpecification getListRequestSpecification() {
        return listRequestSpecification;
    }

    public void reSetListRequestSpecification( ) {
        listRequestSpecification = RestAssured.given().spec(specification.installRequest());
    }

    @Step("Create a new List: name = {name}")
    public Response createList(String nameOfTheList, String boardId) {
        listRequestSpecification.queryParam("name", nameOfTheList);
        listRequestSpecification.queryParam("idBoard", boardId);
        Response response = apiClient.post(LISTS_BASE_PATH, listRequestSpecification);

        reSetListRequestSpecification();

        return response;
    }

    //This method was created for cucumber in order to run scenario outline with multiple values
    @Step("Update a fields: {optionName} for the list with id {listId} with values: = {newValueForAnOption}")
    public Response updateAFieldsOfAList(String listId, String[] optionNames, String[] optionValues) {

        for (int i = 0; i < optionNames.length; i++) {
            listRequestSpecification.queryParam(optionNames[i], optionValues[i]);
        }

        Response response = apiClient.put(LISTS_BASE_PATH + listId, listRequestSpecification);
        reSetListRequestSpecification();

        return response;
    }

    @Step("Update a field {fieldName} for the list with value = {newValueForAField}")
    public Response updateAFieldOfAList(String listId, ListFields fieldName, String newValueForAField) {
        listRequestSpecification.queryParam(fieldName.toString(), newValueForAField);
        Response response = apiClient.put(LISTS_BASE_PATH + listId, listRequestSpecification);

        reSetListRequestSpecification();
        return response;
    }

    @Step("Get the list with id = {listIdTheCardIsOn}")
    public Response getAList(String listId) {
        Response response = apiClient.get(LISTS_BASE_PATH + listId, listRequestSpecification);

        reSetListRequestSpecification();
        return response;
    }

    @Step("Get a field of a list")
    public Response getAFieldOfAList(String listId, String fieldName){
        listRequestSpecification.queryParam("fields", fieldName);
        Response response = apiClient.get(LISTS_BASE_PATH + listId, listRequestSpecification);

        reSetListRequestSpecification();
        return response;
    }

    @Step("Archive all existed cards on a list with id = {listIdTheCardIsOn}")
    public Response archiveAllCardOnTheList(String listId) {
        Response response = apiClient.post(LISTS_BASE_PATH + listId + archiveEndPoint, listRequestSpecification);

        reSetListRequestSpecification();
        return response;
    }

    @Step("Move all cards from the list with id = {newCreatedListId} to the list with id = {IdOfTheListThatTheCardsShouldBeMovedTo}")
    public Response moveAllCardsFromOneListToAnother(String newCreatedListId, String boardId, String IdOfTheListThatTheCardsShouldBeMovedTo) {
        listRequestSpecification.queryParam("idBoard", boardId);
        listRequestSpecification.queryParam("idList", IdOfTheListThatTheCardsShouldBeMovedTo);
        Response response = apiClient.post(LISTS_BASE_PATH + newCreatedListId + moveAllCardsEndPoint, listRequestSpecification);

        reSetListRequestSpecification();
        return response;
    }

    @Step("Archive a list with id = {idOfTheListToArchive}")
    public Response archiveAList(String idOfTheListToArchive) {
        listRequestSpecification.queryParam("value", "true");
        Response response = apiClient.put(LISTS_BASE_PATH + idOfTheListToArchive + archiveAListEndPoint, listRequestSpecification);

        reSetListRequestSpecification();
        return response;
    }

    @Step("Unarchive a list with id = {idOfTheListToUnArchive}")
    public Response unArchiveAList(String idOfTheListToUnArchive) {
        listRequestSpecification.queryParam("value", "false");
        Response response = apiClient.put(LISTS_BASE_PATH + idOfTheListToUnArchive + archiveAListEndPoint, listRequestSpecification);

        reSetListRequestSpecification();
        return response;
    }

    @Step("Get a cards from a list with id {idOfTheList}")
    public Response getResourcesOfAList(String idOfTheList, String requestedObject) {
        Response response = apiClient.get(LISTS_BASE_PATH + idOfTheList + requestedObject, listRequestSpecification);

        reSetListRequestSpecification();
        return response;
    }

    @Step("Move list from one board to another board with id {idOfABoardToMoveListTo}")
    public Response moveListFromOneBoardToAnother(String idOfTheList, String idOfABoardToMoveListTo) {
        listRequestSpecification.queryParam("value", idOfABoardToMoveListTo);
        Response response = apiClient.put(LISTS_BASE_PATH + idOfTheList + idBoardEndPoint, listRequestSpecification);

        reSetListRequestSpecification();
        return response;
    }

    @Step("Get the actions of a list")
    public Response getActionsofAList(String idOfTheList) {
        Response response = apiClient.get(LISTS_BASE_PATH + idOfTheList + actionsEndPoint, listRequestSpecification);

        reSetListRequestSpecification();
        return response;
    }

    @Step("Get the boar id the list is on")
    public Response getABoardAListIsOn(String idOfTheList) {
        Response response = apiClient.get(LISTS_BASE_PATH + idOfTheList + boardEndPoint, listRequestSpecification);

        reSetListRequestSpecification();
        return response;
    }

    private void matchTheNamesOfTheListsWithId(String boardId, ListTestData listTestData){

        List idOfAllLists = getListOfIdOfAllListsOnABoard(boardId,listRequestSpecification);

        for(int i = 0; i<idOfAllLists.size(); i++){
            String temp = getAList((String) idOfAllLists.get(i)).jsonPath().getString("name");
            listTestData.getNamesAndIdsOfLists().put(temp, (String) idOfAllLists.get(i));
        }
    }

    public String getIdOfAListByName(String listName, String boardId, ListTestData listTestData) {
        matchTheNamesOfTheListsWithId(boardId, listTestData);

        return listTestData.getNamesAndIdsOfLists().get(listName);
    }

    @Step("Get id of the first list on a board")
    public List getListOfIdOfAllListsOnABoard(String boardId, RequestSpecification specificToTestClassRequestSpecification) {

        Response resp = apiClient.get(PathParameters.BoardEndPoints.BOARDS_BASE_PATH + boardId + LISTS_BASE_PATH, specificToTestClassRequestSpecification);
        List <String> list = resp.jsonPath().getList("id");
        reSetListRequestSpecification();
        return list;
    }

    @Step("Create a board with a name {boardName}")
    public String createABord(String boardName, RequestSpecification specificToTestClassRequestSpecification) {

        specificToTestClassRequestSpecification.queryParam("name", boardName);

        Response response = apiClient.post(PathParameters.BoardEndPoints.BOARDS_BASE_PATH, specificToTestClassRequestSpecification);
        reSetListRequestSpecification();

        return response.jsonPath().getString("id");
    }

    @Step("Delete a board with id = {boardId}")
    public void deleteBoard(String boardId, RequestSpecification specificToTestClassRequestSpecification) {

        apiClient.delete(PathParameters.BoardEndPoints.BOARDS_BASE_PATH + boardId, specificToTestClassRequestSpecification);
        reSetListRequestSpecification();
    }

    @Step("Create a card for list with id = {listIdTheCardIsOn}")
    public Response createACard(Map queryParamMap, RequestSpecification specificToTestClassRequestSpecification) {
        specificToTestClassRequestSpecification.queryParams(queryParamMap);
        Response response = apiClient.post(PathParameters.CardsEndPoints.CARDS_BASE_PATH, specificToTestClassRequestSpecification);
        reSetListRequestSpecification();
        return response;
    }

    @Step("Get resource of a board")
    public Response getOptionOfAnObject(String resourceEndPoint, String resourceId, String optionName, RequestSpecification specificToTestClassRequestSpecification){

        specificToTestClassRequestSpecification.queryParam("fields", optionName);
        Response response = apiClient.get(resourceEndPoint + resourceId, specificToTestClassRequestSpecification);
        reSetListRequestSpecification();
        return response;
    }

}