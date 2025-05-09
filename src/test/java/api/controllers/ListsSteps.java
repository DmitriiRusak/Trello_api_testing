package api.controllers;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static api.base.PathParameters.ListsPath.*;

public class ListsSteps extends BaseService {


    @Step("Create a new List: name = {name}")
    public Response createList(String nameOfTheList, String boardId) {
        requestSpecification.queryParam("name", nameOfTheList);
        requestSpecification.queryParam("idBoard", boardId);
        Response response = apiClient.post(LISTS_BASE_PATH, requestSpecification);

        initRequestSpecification();

        return response;
    }

    @Step("Update a neme for the list with value = {newNameForTheList}")
    public Response updateANameForList(String listId, String newNameForTheList) {
        requestSpecification.queryParam("name", newNameForTheList);
        Response response = apiClient.put(LISTS_BASE_PATH + listId, requestSpecification);

        initRequestSpecification();

        return response;
    }

    @Step("Get the list with id = {listId}")
    public Response getAList(String listId) {
        Response response = apiClient.get(LISTS_BASE_PATH + listId, requestSpecification);

        initRequestSpecification();

        return response;
    }

    @Step("Archive all existed cards on a list with id = {listId}")
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
    public Response getCardsOnAList(String idOfTheList) {
        Response response = apiClient.get(LISTS_BASE_PATH + idOfTheList + cardsEndPoint, requestSpecification);

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

    @Step("Update the subscribed field of a list wit id = {idOfAList} ")
    public Response updateSubscribedFieldOfAList(String idOfAList, boolean valueForSubscribedField) {
        requestSpecification.queryParam("value", valueForSubscribedField);
        Response response = apiClient.put(LISTS_BASE_PATH + idOfAList + subscribedFieldEndPoint, requestSpecification);

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
}