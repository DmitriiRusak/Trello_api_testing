package api.services;

import api.resourcesForTests.CycymberConfigTestData;
import api.resourcesForTests.PathParameters;
import api.resourcesForTests.ListFields;
import api.utils.ApiClient;
import api.utils.Specification;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Map;

import static api.resourcesForTests.PathParameters.ListsPath.*;

public class ListsService{

    private final Specification specification = new Specification();
    private RequestSpecification listRequestSpecification = specification.installRequest();

    @Step("Create a new List: name = {name}")
    public Response createList(String nameOfTheList, String boardId) {
        listRequestSpecification.queryParam("name", nameOfTheList);
        listRequestSpecification.queryParam("idBoard", boardId);
        Response response = ApiClient.getInstance().post(LISTS_BASE_PATH, listRequestSpecification);

        listRequestSpecification = specification.installRequest();

        return response;
    }

    //This method was created for cucumber in order to run scenario outline with multiple values
    @Step("Update a fields: {optionName} for the list with id {listId} with values: = {newValueForAnOption}")
    public Response updateAFieldsOfAList(String listId, String[] optionNames, String[] optionValues) {

        for (int i = 0; i < optionNames.length; i++) {
            listRequestSpecification.queryParam(optionNames[i], optionValues[i]);
        }

        Response response = ApiClient.getInstance().put(LISTS_BASE_PATH + listId, listRequestSpecification);
        listRequestSpecification = specification.installRequest();

        return response;
    }

    @Step("Update a field {fieldName} for the list with value = {newValueForAField}")
    public Response updateAFieldOfAList(String listId, ListFields fieldName, String newValueForAField) {
        listRequestSpecification.queryParam(fieldName.toString(), newValueForAField);
        Response response = ApiClient.getInstance().put(LISTS_BASE_PATH + listId, listRequestSpecification);

        listRequestSpecification = specification.installRequest();
        return response;
    }

    @Step("Get the list with id = {listIdTheCardIsOn}")
    public Response getAList(String listId) {
        Response response = ApiClient.getInstance().get(LISTS_BASE_PATH + listId, listRequestSpecification);

        listRequestSpecification = specification.installRequest();
        return response;
    }

    @Step("Get a field of a list")
    public Response getAFieldOfAList(String listId, String fieldName){
        listRequestSpecification.queryParam("fields", fieldName);
        Response response = ApiClient.getInstance().get(LISTS_BASE_PATH + listId, listRequestSpecification);

        listRequestSpecification = specification.installRequest();
        return response;
    }

    @Step("Archive all existed cards on a list with id = {listIdTheCardIsOn}")
    public Response archiveAllCardOnTheList(String listId) {
        Response response = ApiClient.getInstance().post(LISTS_BASE_PATH + listId + archiveEndPoint, listRequestSpecification);

        listRequestSpecification = specification.installRequest();
        return response;
    }

    @Step("Move all cards from the list with id = {newCreatedListId} to the list with id = {IdOfTheListThatTheCardsShouldBeMovedTo}")
    public Response moveAllCardsFromOneListToAnother(String newCreatedListId, String boardId, String IdOfTheListThatTheCardsShouldBeMovedTo) {
        listRequestSpecification.queryParam("idBoard", boardId);
        listRequestSpecification.queryParam("idList", IdOfTheListThatTheCardsShouldBeMovedTo);
        Response response = ApiClient.getInstance().post(LISTS_BASE_PATH + newCreatedListId + moveAllCardsEndPoint, listRequestSpecification);

        listRequestSpecification = specification.installRequest();
        return response;
    }

    @Step("Archive a list with id = {idOfTheListToArchive}")
    public Response archiveAList(String idOfTheListToArchive) {
        listRequestSpecification.queryParam("value", "true");
        Response response = ApiClient.getInstance().put(LISTS_BASE_PATH + idOfTheListToArchive + archiveAListEndPoint, listRequestSpecification);

        listRequestSpecification = specification.installRequest();
        return response;
    }

    @Step("Unarchive a list with id = {idOfTheListToUnArchive}")
    public Response unArchiveAList(String idOfTheListToUnArchive) {
        listRequestSpecification.queryParam("value", "false");
        Response response = ApiClient.getInstance().put(LISTS_BASE_PATH + idOfTheListToUnArchive + archiveAListEndPoint, listRequestSpecification);

        listRequestSpecification = specification.installRequest();
        return response;
    }

    @Step("Get a cards from a list with id {idOfTheList}")
    public Response getResourcesOfAList(String idOfTheList, String requestedObject) {
        Response response = ApiClient.getInstance().get(LISTS_BASE_PATH + idOfTheList + requestedObject, listRequestSpecification);

        listRequestSpecification = specification.installRequest();
        return response;
    }

    @Step("Move list from one board to another board with id {idOfABoardToMoveListTo}")
    public Response moveListFromOneBoardToAnother(String idOfTheList, String idOfABoardToMoveListTo) {
        listRequestSpecification.queryParam("value", idOfABoardToMoveListTo);
        Response response = ApiClient.getInstance().put(LISTS_BASE_PATH + idOfTheList + idBoardEndPoint, listRequestSpecification);

        listRequestSpecification = specification.installRequest();
        return response;
    }

    @Step("Get the actions of a list")
    public Response getActionsofAList(String idOfTheList) {
        Response response = ApiClient.getInstance().get(LISTS_BASE_PATH + idOfTheList + actionsEndPoint, listRequestSpecification);

        listRequestSpecification = specification.installRequest();
        return response;
    }

    @Step("Get the boar id the list is on")
    public Response getABoardAListIsOn(String idOfTheList) {
        Response response = ApiClient.getInstance().get(LISTS_BASE_PATH + idOfTheList + boardEndPoint, listRequestSpecification);

        listRequestSpecification = specification.installRequest();
        return response;
    }

    private void matchTheNamesOfTheListsWithId(String boardId, CycymberConfigTestData cycymberConfigTestData){

        List idOfAllLists = getListOfIdOfAllListsOnABoard(boardId);

        for(int i = 0; i<idOfAllLists.size(); i++){
            String temp = getAList((String) idOfAllLists.get(i)).jsonPath().getString("name");
            cycymberConfigTestData.getNamesAndIdsOfLists().put(temp, (String) idOfAllLists.get(i));
        }
    }

    public String getIdOfAListByName(String listName, String boardId, CycymberConfigTestData cycymberConfigTestData) {
        matchTheNamesOfTheListsWithId(boardId, cycymberConfigTestData);

        return cycymberConfigTestData.getNamesAndIdsOfLists().get(listName);
    }

    @Step("Get id of the first list on a board")
    public List getListOfIdOfAllListsOnABoard(String boardId) {

        Response resp = ApiClient.getInstance().get(PathParameters.BoardEndPoints.BOARDS_BASE_PATH + boardId + LISTS_BASE_PATH, listRequestSpecification);
        List <String> list = resp.jsonPath().getList("id");
        listRequestSpecification = specification.installRequest();
        return list;
    }

    @Step("Create a board with a name {boardName}")
    public String createABord(String boardName) {

        listRequestSpecification.queryParam("name", boardName);

        Response response = ApiClient.getInstance().post(PathParameters.BoardEndPoints.BOARDS_BASE_PATH, listRequestSpecification);
        listRequestSpecification = specification.installRequest();

        return response.jsonPath().getString("id");
    }

    @Step("Delete a board with id = {boardId}")
    public void deleteBoard(String boardId) {

        ApiClient.getInstance().delete(PathParameters.BoardEndPoints.BOARDS_BASE_PATH + boardId, listRequestSpecification);
        listRequestSpecification = specification.installRequest();
    }

    @Step("Create a card for list with id = {listIdTheCardIsOn}")
    public Response createACard(Map queryParamMap) {
        listRequestSpecification.queryParams(queryParamMap);
        Response response = ApiClient.getInstance().post(PathParameters.CardsEndPoints.CARDS_BASE_PATH, listRequestSpecification);
        listRequestSpecification = specification.installRequest();
        return response;
    }

    @Step("Get resource of a board")
    public Response getOptionOfAnObject(String resourceEndPoint, String resourceId, String optionName){

        listRequestSpecification.queryParam("fields", optionName);
        Response response = ApiClient.getInstance().get(resourceEndPoint + resourceId, listRequestSpecification);
        listRequestSpecification = specification.installRequest();
        return response;
    }

}