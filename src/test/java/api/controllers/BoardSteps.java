package api.controllers;

import api.base.PathParameters.BoardEndPoints;
import api.utils.ApiClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static api.base.PathParameters.LabelsPath.LABELS_BASE_PATH;
import static api.base.PathParameters.ListsPath.LISTS_BASE_PATH;
import static api.base.PathParameters.MembersPath.MEMBERS_BASE_PATH;

public class BoardSteps extends BaseService {

    @Step("Create board with name: {nameOfTheBoard}")
    public Response createBoard(String nameOfTheBoard) {

        requestSpecification.queryParam("name", nameOfTheBoard);
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
    public Response updateBoard(String boardId, String bordName) {
        requestSpecification.param("name", bordName);
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
    public Response getActions(String boardId, String actions) {

        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + actions, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get checklists presented on a board with id - {'boardId'}")
    public Response getChecklists(String boardId, String checklistsEndPoint) {
        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + checklistsEndPoint, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get cards presented on a board")
    public Response getCards(String boardId, String cardsEndPoint) {

        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + cardsEndPoint, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get filtered cards presented on a board")
    public Response getFilteredCards(String boardId, String filtereCardsEndPoint, String filterName) {

        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + filtereCardsEndPoint + filterName, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get custom fields presented on a board")
    public Response getCustomFieldsForABoard(String boardId, String customFieldsEndPoint) {

        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + customFieldsEndPoint, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get lists presented on a board")
    public Response getListsOfABoard(String boardId, String listsEndPoint) {

        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + listsEndPoint, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get list on board filtered by - {'filter'}")
    public Response getFilteredListsOnABoard(String boardId, String listsEndPoint, String filter) {

        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + listsEndPoint + filter, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Invite member to a board with id - {'boardId'} via email")
    public Response inviteMemberToBoardViaEmail(String boardId, String membersEndPoint) {
        requestSpecification.param("email", "ironman-968-privet-test@ya.ru");
        requestSpecification.param("allowBillableGuest", true);
        Response response = apiClient.put(BoardEndPoints.BOARDS_BASE_PATH + boardId + membersEndPoint, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get boardStars on a Board: id board = {boardId}")
    public Response getBoardStarsOnBoard(String boardId, String boardStarsEnPoint) {
        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + boardStarsEnPoint, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get memberships on a Board: id board = {boardId}")
    public Response getMembershipsOnBoard(String boardId) {

        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + BoardEndPoints.MEMBER_SHIPS_ENDPOINT, requestSpecification);
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