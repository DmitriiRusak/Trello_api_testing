package api.services;

import api.resourcesForTests.PathParameters;
import api.resourcesForTests.PathParameters.BoardEndPoints;
import api.utils.ApiClient;
import api.utils.LogFactory;
import api.utils.Specification;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Map;

import static api.resourcesForTests.PathParameters.CardsEndPoints.CARDS_BASE_PATH;
import static api.resourcesForTests.PathParameters.CheckListsPath.CHECKLISTS_BASE_PATH;
import static api.resourcesForTests.PathParameters.LabelsPath.LABELS_BASE_PATH;
import static api.resourcesForTests.PathParameters.ListsPath.LISTS_BASE_PATH;
import static api.resourcesForTests.PathParameters.MembersPath.MEMBERS_BASE_PATH;
import static api.resourcesForTests.PathParameters.MembersPath.MY_WORK_SPACE_ENDPOINT;

public class BoardService{

    private Specification specification = new Specification();
    private RequestSpecification boardRequestSpecification = RestAssured.given().spec(specification.installRequest());
    private final ApiClient apiClient = new ApiClient();


    public RequestSpecification getBoardRequestSpecification() {
        return boardRequestSpecification;
    }

    public void reSetBoardRequestSpecification() {
        boardRequestSpecification = RestAssured.given().spec(specification.installRequest());
    }

    @Step("Create board with name: {nameOfTheBoard}")
    public Response createBoard(String nameOfTheBoard) {

        boardRequestSpecification.queryParam("name", nameOfTheBoard);
        Response response = apiClient.post(BoardEndPoints.BOARDS_BASE_PATH, boardRequestSpecification);
        reSetBoardRequestSpecification();
        return response;
    }

    public Response createABoardWithDefinedPermissionLevel(String boardNameCreatedWithSpecificOptions, String permissionLeve) {

        boardRequestSpecification.queryParam("name", boardNameCreatedWithSpecificOptions);
        boardRequestSpecification.queryParam("prefs_permissionLevel", permissionLeve);
        Response response = apiClient.post(BoardEndPoints.BOARDS_BASE_PATH, boardRequestSpecification);
        reSetBoardRequestSpecification();
        return response;
    }

    public Response createABoardWithSpecificResource(String boardName, String resourceName, String resourceValue){
        boardRequestSpecification.queryParam("name", boardName);
        boardRequestSpecification.queryParam(resourceName, resourceValue);
        Response response = apiClient.post(BoardEndPoints.BOARDS_BASE_PATH, boardRequestSpecification);
        reSetBoardRequestSpecification();
        return response;
    }

    @Step("Delete board {boardId}")
    public Response deleteABoardFromService(String boardId) {

        Response response = apiClient.delete(BoardEndPoints.BOARDS_BASE_PATH + boardId, boardRequestSpecification);
        reSetBoardRequestSpecification();
        return response;
    }

    @Step("Get Board: id board = {boardId}")
    public Response getBoard(String boardId) {

        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId, boardRequestSpecification);
        reSetBoardRequestSpecification();
        return response;
    }

    @Step("Update Board: id board = {boardId}, new name board = {BOARD_NAME_FOR_MEMBERS}")
    public Response updateBoard(String boardId, String optionName, String optionValue) {
        boardRequestSpecification.param(optionName, optionValue);
        Response response = apiClient.put(BoardEndPoints.BOARDS_BASE_PATH + boardId, boardRequestSpecification);
        reSetBoardRequestSpecification();
        return response;
    }

    @Step("Create a Label on a Board: id board = {boardId}, label name = {nameOfLabel}, label color = {color}")
    public Response createLabelOnBoard(String boardId, String nameOfLabel, String color) {
        boardRequestSpecification.queryParam("name", nameOfLabel);
        boardRequestSpecification.queryParam("color", color);
        Response response = apiClient.post(BoardEndPoints.BOARDS_BASE_PATH + boardId + LABELS_BASE_PATH, boardRequestSpecification);
        reSetBoardRequestSpecification();
        return response;
    }

    @Step("Get Labels on a Board: id board = {boardId}")
    public Response getLabelsOnBoard(String boardId) {
        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + LABELS_BASE_PATH, boardRequestSpecification);
        reSetBoardRequestSpecification();
        return response;
    }

    @Step("Create a List on a Board: id board = {boardId}, list name = {nameForList}")
    public Response createListOnBoard(String boardId, String nameForList) {
        boardRequestSpecification.queryParam("name", nameForList);
        Response response = apiClient.post(BoardEndPoints.BOARDS_BASE_PATH + boardId + LISTS_BASE_PATH, boardRequestSpecification);
        reSetBoardRequestSpecification();
        return response;
    }

    @Step("getting a field - {fieldName}, from a bord which id is - {boardId}")
    public Response getAField(String boardId, String fieldName) {
        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId +"/"+ fieldName, boardRequestSpecification);
        reSetBoardRequestSpecification();
        return response;
    }

    @Step("Get all actions existed on a board with id - {'boardId'}")
    public Response getActionsOfABoard(String boardId) {

        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + PathParameters.ActionsEndPoints.ACTIONS_BASE_PATH, boardRequestSpecification);
        reSetBoardRequestSpecification();
        return response;
    }

    @Step("Get checklists presented on a board with id - {'boardId'}")
    public Response getChecklists(String boardId) {
        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + CHECKLISTS_BASE_PATH, boardRequestSpecification);
        reSetBoardRequestSpecification();
        return response;
    }

    @Step("Get cards presented on a board")
    public Response getCardsOfABoard(String boardId) {

        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + CARDS_BASE_PATH, boardRequestSpecification);
        reSetBoardRequestSpecification();
        return response;
    }

    @Step("Get filtered cards presented on a board")
    public Response getFilteredCardsOfABoard(String boardId, String filterName) {

        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + CARDS_BASE_PATH + filterName, boardRequestSpecification);
        reSetBoardRequestSpecification();
        return response;
    }

    @Step("Get custom fields presented on a board")
    public Response getCustomFieldsOfAABoard(String boardId) {

        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + PathParameters.CUSTOM_FIELDS_BASE_PATH, boardRequestSpecification);
        reSetBoardRequestSpecification();
        return response;
    }

    @Step("Get lists presented on a board")
    public Response getListsOfABoard(String boardId) {

        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + LISTS_BASE_PATH, boardRequestSpecification);
        reSetBoardRequestSpecification();
        return response;
    }

    @Step("Get list on board filtered by - {'filter'}")
    public Response getFilteredListsOfABoard(String boardId, String filter) {

        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + LISTS_BASE_PATH + filter, boardRequestSpecification);
        reSetBoardRequestSpecification();
        return response;
    }

    @Step("Invite member to a board with id - {'boardId'} via email")
    public Response inviteMemberToBoardViaEmail(String boardId) {

        boardRequestSpecification.param("email", "ironman-968-privet-test@ya.ru");
        boardRequestSpecification.param("allowBillableGuest", true);
        Response response = apiClient.put(BoardEndPoints.BOARDS_BASE_PATH + boardId + MEMBERS_BASE_PATH, boardRequestSpecification);
        reSetBoardRequestSpecification();
        return response;
    }

    @Step("Get boardStars on a Board: id board = {boardId}")
    public Response getBoardStarsOfABoard(String boardId) {

        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + BoardEndPoints.boardStarsEnPoint, boardRequestSpecification);
        reSetBoardRequestSpecification();
        return response;
    }

    @Step("Get memberships on a Board: id board = {boardId}")
    public Response getMembershipsOfABoard(String boardId) {

        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + BoardEndPoints.MEMBER_SHIPS_ENDPOINT, boardRequestSpecification);
        reSetBoardRequestSpecification();
        return response;
    }

    @Step("Get resource of a board")
    public Response getOptionOfABoard(String boardId, String resourceEndPoint){
        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + resourceEndPoint, boardRequestSpecification);
        reSetBoardRequestSpecification();
        return response;
    }

    @Step("Remove member from Board: boardId = {boardId}, memberId = {memberId}")
    public Response removeMemberFromBoard(String boardId, String memberId) {

        Response response = ApiClient.getInstance().delete(BoardEndPoints.BOARDS_BASE_PATH + boardId +
                MEMBERS_BASE_PATH + memberId, boardRequestSpecification);
        reSetBoardRequestSpecification();
        return response;

    }

    //for cucumber
    @Step("Get all available boards")
    public String getAllAvailableBoards() {

        Response response = apiClient.get(MEMBERS_BASE_PATH + MY_WORK_SPACE_ENDPOINT + BoardEndPoints.BOARDS_BASE_PATH, boardRequestSpecification);

        String tempIdThatWasRecivedBack = response.jsonPath().getString("id");
        tempIdThatWasRecivedBack = tempIdThatWasRecivedBack.substring(1, tempIdThatWasRecivedBack.length()-1);
        reSetBoardRequestSpecification();
        return tempIdThatWasRecivedBack;
    }

    public Response getTheMembersOfABoard(String boardId, RequestSpecification specificToTestClassRequestSpecification) {

        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + MEMBERS_BASE_PATH, specificToTestClassRequestSpecification);
        reSetBoardRequestSpecification();
        return response;
    }

    @Step("Create a card for list with id = {listIdTheCardIsOn}")
    public Response createACard(Map queryParamMap, RequestSpecification specificToTestClassRequestSpecification) {
        specificToTestClassRequestSpecification.queryParams(queryParamMap);
        Response response = apiClient.post(PathParameters.CardsEndPoints.CARDS_BASE_PATH, specificToTestClassRequestSpecification);
        reSetBoardRequestSpecification();
        return response;
    }

    @Step("Get id of the first list on a board")
    public List getListOfIdOfAllListsOnABoard(String boardId, RequestSpecification specificToTestClassRequestSpecification) {

        Response resp = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + LISTS_BASE_PATH, specificToTestClassRequestSpecification);
        List <String> list = resp.jsonPath().getList("id");
        reSetBoardRequestSpecification();
        return list;
    }

    @Step("Delete a board with id = {boardId}")
    public void deleteBoard(String boardId, RequestSpecification specificToTestClassRequestSpecification) {

        apiClient.delete(BoardEndPoints.BOARDS_BASE_PATH + boardId, specificToTestClassRequestSpecification);
        reSetBoardRequestSpecification();
    }

    @Step("Get resource of a board")
    public Response getOptionOfAnObject(String resourceEndPoint, String resourceId, String optionName, RequestSpecification specificToTestClassRequestSpecification){

        specificToTestClassRequestSpecification.queryParam("fields", optionName);
        Response response = apiClient.get(resourceEndPoint + resourceId, specificToTestClassRequestSpecification);
        reSetBoardRequestSpecification();
        return response;
    }

    @Step("Get the board of a list")
    public Response getResourceOfAnObject(String objectEndPoint, String resourceId, String resourceEndPoint, RequestSpecification specificToTestClassRequestSpecification){

        Response response = apiClient.get(objectEndPoint + resourceId + resourceEndPoint, specificToTestClassRequestSpecification);
        reSetBoardRequestSpecification();
        return response;
    }


}