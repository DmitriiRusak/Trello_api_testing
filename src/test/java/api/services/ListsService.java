package api.services;

import api.resourcesForTests.ConfigurationDataForApiTests;
import api.resourcesForTests.ListFields;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

import static api.resourcesForTests.PathParameters.ListsPath.*;

public class ListsService extends BaseService {


    @Step("Create a new List: name = {name}")
    public Response createList(String nameOfTheList, String boardId) {
        requestSpecification.queryParam("name", nameOfTheList);
        requestSpecification.queryParam("idBoard", boardId);
        Response response = apiClient.post(LISTS_BASE_PATH, requestSpecification);

        initRequestSpecification();

        return response;
    }

    //This method was created for cucumber in order to run scenario outline with multiple values
    @Step("Update a fields: {optionName} for the list with id {listId} with values: = {newValueForAnOption}")
    public Response updateAFieldsOfAList(String listId, String[] optionNames, String[] optionValues) {

        for (int i = 0; i < optionNames.length; i++) {
            requestSpecification.queryParam(optionNames[i], optionValues[i]);
        }

        Response response = apiClient.put(LISTS_BASE_PATH + listId, requestSpecification);
        initRequestSpecification();

        return response;
    }

    @Step("Update a field {fieldName} for the list with value = {newValueForAField}")
    public Response updateAFieldOfAList(String listId, ListFields fieldName, String newValueForAField) {
        requestSpecification.queryParam(fieldName.toString(), newValueForAField);
        Response response = apiClient.put(LISTS_BASE_PATH + listId, requestSpecification);

        initRequestSpecification();

        return response;
    }

    @Step("Get the list with id = {listIdTheCardIsOn}")
    public Response getAList(String listId) {
        Response response = apiClient.get(LISTS_BASE_PATH + listId, requestSpecification);

        initRequestSpecification();

        return response;
    }

    @Step("Get a field of a list")
    public Response getAFieldOfAList(String listId, String fieldName){
        requestSpecification.queryParam("fields", fieldName);
        Response response = apiClient.get(LISTS_BASE_PATH + listId, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Archive all existed cards on a list with id = {listIdTheCardIsOn}")
    public Response archiveAllCardOnTheList(String listId) {
        Response response = apiClient.post(LISTS_BASE_PATH + listId + archiveEndPoint, requestSpecification);

        initRequestSpecification();

        return response;
    }

    @Step("Move all cards from the list with id = {newCreatedListId} to the list with id = {IdOfTheListThatTheCardsShouldBeMovedTo}")
    public Response moveAllCardsFromOneListToAnother(String newCreatedListId, String boardId, String IdOfTheListThatTheCardsShouldBeMovedTo) {
        requestSpecification.queryParam("idBoard", boardId);
        requestSpecification.queryParam("idList", IdOfTheListThatTheCardsShouldBeMovedTo);
        Response response = apiClient.post(LISTS_BASE_PATH + newCreatedListId + moveAllCardsEndPoint, requestSpecification);

        initRequestSpecification();

        return response;
    }

    @Step("Archive a list with id = {idOfTheListToArchive}")
    public Response archiveAList(String idOfTheListToArchive) {
        requestSpecification.queryParam("value", "true");
        Response response = apiClient.put(LISTS_BASE_PATH + idOfTheListToArchive + archiveAListEndPoint, requestSpecification);

        initRequestSpecification();

        return response;
    }

    @Step("Unarchive a list with id = {idOfTheListToUnArchive}")
    public Response unArchiveAList(String idOfTheListToUnArchive) {
        requestSpecification.queryParam("value", "false");
        Response response = apiClient.put(LISTS_BASE_PATH + idOfTheListToUnArchive + archiveAListEndPoint, requestSpecification);

        initRequestSpecification();

        return response;
    }

    @Step("Get a cards from a list with id {idOfTheList}")
    public Response getResourcesOfAList(String idOfTheList, String requestedObject) {
        Response response = apiClient.get(LISTS_BASE_PATH + idOfTheList + requestedObject, requestSpecification);

        initRequestSpecification();

        return response;
    }

    @Step("Move list from one board to another board with id {idOfABoardToMoveListTo}")
    public Response moveListFromOneBoardToAnother(String idOfTheList, String idOfABoardToMoveListTo) {
        requestSpecification.queryParam("value", idOfABoardToMoveListTo);
        Response response = apiClient.put(LISTS_BASE_PATH + idOfTheList + idBoardEndPoint, requestSpecification);

        initRequestSpecification();

        return response;
    }

    @Step("Get the actions of a list")
    public Response getActionsofAList(String idOfTheList) {
        Response response = apiClient.get(LISTS_BASE_PATH + idOfTheList + actionsEndPoint, requestSpecification);

        initRequestSpecification();

        return response;
    }

    @Step("Get the boar id the list is on")
    public Response getABoardAListIsOn(String idOfTheList) {
        Response response = apiClient.get(LISTS_BASE_PATH + idOfTheList + boardEndPoint, requestSpecification);

        initRequestSpecification();

        return response;
    }

    private void matchTheNamesOfTheListsWithId(){

        List idOfAllLists = getListOfIdOfAllListsOnABoard(ConfigurationDataForApiTests.BoardTestData.boardId);

        for(int i = 0; i<idOfAllLists.size(); i++){
            String temp = getAList((String) idOfAllLists.get(i)).jsonPath().getString("name");
            ConfigurationDataForApiTests.ListsTestData.namesAndIdsOfLists.put(temp, (String) idOfAllLists.get(i));

        }
    }

    public String getIdOfAListByName(String listName) {
        matchTheNamesOfTheListsWithId();

        return ConfigurationDataForApiTests.ListsTestData.namesAndIdsOfLists.get(listName);
    }
}