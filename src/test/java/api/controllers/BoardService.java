package api.controllers;

import api.base.PathParameters;
import api.base.PathParameters.BoardEndPoints;
import api.utils.ApiClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static api.base.PathParameters.CardsEndPoints.CARDS_BASE_PATH;
import static api.base.PathParameters.CheckListsPath.CHECKLISTS_BASE_PATH;
import static api.base.PathParameters.LabelsPath.LABELS_BASE_PATH;
import static api.base.PathParameters.ListsPath.LISTS_BASE_PATH;
import static api.base.PathParameters.MembersPath.MEMBERS_BASE_PATH;

public class BoardService extends BaseService {

    @Step("Create board with name: {nameOfTheBoard}")
    public Response createBoard(String nameOfTheBoard) {

        requestSpecification.queryParam("name", nameOfTheBoard);
        Response response = apiClient.post(BoardEndPoints.BOARDS_BASE_PATH, requestSpecification);
        initRequestSpecification();
        return response;
    }

    public Response createABoardWithDefinedPermissionLevel(String boardNameCreatedWithSpecificOptions, String permissionLeve) {

        requestSpecification.queryParam("name", boardNameCreatedWithSpecificOptions);
        requestSpecification.queryParam("prefs_permissionLevel", permissionLeve);
        Response response = apiClient.post(BoardEndPoints.BOARDS_BASE_PATH, requestSpecification);
        initRequestSpecification();
        return response;
    }

    public Response createABoardWithSpecificResource(String boardName, String resourceName, String resourceValue){
        requestSpecification.queryParam("name", boardName);
        requestSpecification.queryParam(resourceName, resourceValue);
        Response response = apiClient.post(BoardEndPoints.BOARDS_BASE_PATH, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Delete board {boardId}")
    public Response deleteABoardFromService(String boardId) {

        Response response = apiClient.delete(BoardEndPoints.BOARDS_BASE_PATH + boardId, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get Board: id board = {boardId}")
    public Response getBoard(String boardId) {

        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Update Board: id board = {boardId}, new name board = {bordName}")
    public Response updateBoard(String boardId, String optionName, String bordName) {
        requestSpecification.param(optionName, bordName);
        Response response = apiClient.put(BoardEndPoints.BOARDS_BASE_PATH + boardId, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Create a Label on a Board: id board = {boardId}, label name = {nameOfLabel}, label color = {color}")
    public Response createLabelOnBoard(String boardId, String nameOfLabel, String color) {
        requestSpecification.queryParam("name", nameOfLabel);
        requestSpecification.queryParam("color", color);
        Response response = apiClient.post(BoardEndPoints.BOARDS_BASE_PATH + boardId + LABELS_BASE_PATH, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get Labels on a Board: id board = {boardId}")
    public Response getLabelsOnBoard(String boardId) {
        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + LABELS_BASE_PATH, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Create a List on a Board: id board = {boardId}, list name = {nameForList}")
    public Response createListOnBoard(String boardId, String nameForList) {
        requestSpecification.queryParam("name", nameForList);
        Response response = apiClient.post(BoardEndPoints.BOARDS_BASE_PATH + boardId + LISTS_BASE_PATH, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("getting a field - {fieldName}, from a bord which id is - {boardId}")
    public Response getAField(String boardId, String fieldName) {

        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + fieldName, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get all actions existed on a board with id - {'boardId'}")
    public Response getActionsOfABoard(String boardId) {

        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + PathParameters.ActionsEndPoints.ACTIONS_BASE_PATH, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get checklists presented on a board with id - {'boardId'}")
    public Response getChecklists(String boardId) {
        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + CHECKLISTS_BASE_PATH, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get cards presented on a board")
    public Response getCardsOfABoard(String boardId) {

        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + CARDS_BASE_PATH, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get filtered cards presented on a board")
    public Response getFilteredCardsOfABoard(String boardId, String filterName) {

        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + CARDS_BASE_PATH + filterName, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get custom fields presented on a board")
    public Response getCustomFieldsOfAABoard(String boardId) {

        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + PathParameters.CUSTOM_FIELDS_BASE_PATH, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get lists presented on a board")
    public Response getListsOfABoard(String boardId) {

        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + LISTS_BASE_PATH, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get list on board filtered by - {'filter'}")
    public Response getFilteredListsOfABoard(String boardId, String filter) {

        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + LISTS_BASE_PATH + filter, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Invite member to a board with id - {'boardId'} via email")
    public Response inviteMemberToBoardViaEmail(String boardId) {

        requestSpecification.param("email", "ironman-968-privet-test@ya.ru");
        requestSpecification.param("allowBillableGuest", true);
        Response response = apiClient.put(BoardEndPoints.BOARDS_BASE_PATH + boardId + MEMBERS_BASE_PATH, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get boardStars on a Board: id board = {boardId}")
    public Response getBoardStarsOfABoard(String boardId) {

        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + BoardEndPoints.boardStarsEnPoint, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get memberships on a Board: id board = {boardId}")
    public Response getMembershipsOfABoard(String boardId) {

        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + BoardEndPoints.MEMBER_SHIPS_ENDPOINT, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get resource of a board")
    public Response getOptionOfABoard(String boardId, String resourceEndPoint){
        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + resourceEndPoint, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Remove member from Board: boardId = {boardId}, memberId = {memberId}")
    public Response removeMemberFromBoard(String boardId, String memberId) {

        Response response = ApiClient.getInstance().delete(BoardEndPoints.BOARDS_BASE_PATH + boardId +
                MEMBERS_BASE_PATH + memberId, requestSpecification);
        initRequestSpecification();
        return response;

    }

}