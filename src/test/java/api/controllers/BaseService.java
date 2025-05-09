package api.controllers;

import api.base.PathParameters;
import api.base.PathParameters.*;
import api.utils.ApiClient;
import api.utils.Specification;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Map;

import static api.base.PathParameters.CheckListsPath.CHECKLISTS_BASE_PATH;
import static api.base.PathParameters.ListsPath.LISTS_BASE_PATH;
import static api.base.PathParameters.MembersPath.MEMBERS_BASE_PATH;

public abstract class BaseService {

    protected static final Specification specification = new Specification();
    protected RequestSpecification requestSpecification;
    protected ApiClient apiClient = ApiClient.getInstance();

    {
        initRequestSpecification();
    }

    @Step("Create a board with a name {'boardName'}")
    public String createABord(String boardName) {

        requestSpecification.queryParam("name", boardName);

        Response response = apiClient.post(BoardEndPoints.BOARDS_BASE_PATH, requestSpecification);
        initRequestSpecification();
        return response.jsonPath().getString("id");
    }

    @Step("Delete a board with id = {boardId}")
    public void deleteBoard(String boardId) {

        apiClient.delete(BoardEndPoints.BOARDS_BASE_PATH + boardId, requestSpecification);
    }

    @Step("Get id of the first list on a board")
    public String getIdOfTheFirstListOnABoard(String boardId) {

        Response resp = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + LISTS_BASE_PATH, requestSpecification);
        List arrayList = resp.jsonPath().getList("id");
        initRequestSpecification();
        return (String) arrayList.get(0);
    }

    @Step("Get id of the first action on a board with id = {boardId}")
    public String getIdOfTheFirestActionOnABoard(String boardId) {
        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + ActionsEndPoints.ACTIONS_BASE_PATH, requestSpecification);

        List list = response.jsonPath().getList("id");
        return list.get(0).toString();
    }

    protected void initRequestSpecification() {
        requestSpecification = RestAssured.given().spec(specification.installRequest());

    }

    @Step("Create a card for list with id = {listId}")
    public Response createACard(Map queryParamMap) {
        requestSpecification.queryParams(queryParamMap);
        Response response = apiClient.post(CardsEndPoints.CARDS_BASE_PATH, requestSpecification);

        initRequestSpecification();
        return response;
    }

    @Step("Add a comment {'commentForAnAction'} to a card with id ={cardId}")
    public Response addNewComentToACard(String cardId, String commentForAnAction, String commentsEnpoint) {
        requestSpecification.queryParams("text", commentForAnAction);
        Response response = apiClient.post(CardsEndPoints.CARDS_BASE_PATH + cardId + ActionsEndPoints.ACTIONS_BASE_PATH + commentsEnpoint, requestSpecification);
        initRequestSpecification();
        return response;
    }


    public Response getTheMembersOfABoard(String boardId) {

        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + MEMBERS_BASE_PATH, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Create a checklist on a card with id - {'idCard'}, with a name - {'nameOfAChecklistBeingCreated'}")
    public Response createAChecklist(String idCard, String nameOfAChecklistBeingCreated) {
        requestSpecification.queryParam("idCard", idCard);
        requestSpecification.queryParam("name", nameOfAChecklistBeingCreated);
        Response response = apiClient.post(CHECKLISTS_BASE_PATH, requestSpecification);
        initRequestSpecification();
        return response;
    }
}
