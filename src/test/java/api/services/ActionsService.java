package api.services;

import api.resourcesForTests.PathParameters.*;
import api.utils.ApiClient;
import api.utils.Specification;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;

import java.util.List;
import java.util.Map;

import static api.resourcesForTests.PathParameters.ListsPath.LISTS_BASE_PATH;

public class ActionsService{

    private Specification specification = new Specification();
    private RequestSpecification actionRequestSpecification = RestAssured.given().spec(specification.installRequest());
    private final ApiClient apiClient = new ApiClient();

    public RequestSpecification getActionRequestSpecification() {
        return actionRequestSpecification;
    }

    public void reSetActionRequestSpecification() {
        actionRequestSpecification = RestAssured.given().spec(specification.installRequest());
    }

    @Step("Get an action with id = {actiontId} from a board")
    public Response getAnAction(String actiontId) {

        Response response = apiClient.get(ActionsEndPoints.ACTIONS_BASE_PATH + actiontId, actionRequestSpecification);
        reSetActionRequestSpecification();
        return response;
    }

    @Step("Update a comment of an action with value = {updatedCommentForAnAction}")
    public Response updateACommentOfTheAction(String actiontId, String updatedCommentForAnAction) {
        actionRequestSpecification.queryParam("value", updatedCommentForAnAction);
        Response response = apiClient.put(ActionsEndPoints.ACTIONS_BASE_PATH + actiontId + ActionsEndPoints.TEXT_ENDPOINT, actionRequestSpecification);
        reSetActionRequestSpecification();
        return response;
    }

    @Step("Delete an action with id = {idOfAnActionToDelete}")
    public Response deleteAnAction(String idOfAnActionToDelete) {
        Response response = apiClient.delete(ActionsEndPoints.ACTIONS_BASE_PATH + idOfAnActionToDelete, actionRequestSpecification);
        reSetActionRequestSpecification();
        return response;
    }

    @Step("Get the resource {resourceEndPoint}, from action with id {actionId}")
    public Response getTheResourceOfAnAction(String idOfAnAction, String resourceEndPoint) {
        Response response = apiClient.get(ActionsEndPoints.ACTIONS_BASE_PATH + idOfAnAction + resourceEndPoint, actionRequestSpecification);
        reSetActionRequestSpecification();
        return response;
    }

    @Step("Get all existed reactions for action with id - {actionId}")
    public Response getActions_Reactions(String actionId) {
        Response response = apiClient.get(ActionsEndPoints.ACTIONS_BASE_PATH + actionId + ActionsEndPoints.REACTIONS_ENDPOINT, actionRequestSpecification);
        reSetActionRequestSpecification();
        return response;
    }

    @Step("Create reaction for action with id - {actiontId}")
    public Response createReactionForAction(String actiontId) {
        actionRequestSpecification.queryParams("shortName", "grinning");
        Response response = apiClient.post(ActionsEndPoints.ACTIONS_BASE_PATH + actiontId + ActionsEndPoints.REACTIONS_ENDPOINT, actionRequestSpecification);
        reSetActionRequestSpecification();
        return response;
    }

    @Step("Get reaction with id {idOfReaction}, from action with id - {actionIdAfterCreatingACard}")
    public Response getActionsReaction(String actionIdAfterCreatingACard, String idOfReaction) {
        Response response = apiClient.get(ActionsEndPoints.ACTIONS_BASE_PATH + actionIdAfterCreatingACard +
                ActionsEndPoints.REACTIONS_ENDPOINT + idOfReaction, actionRequestSpecification);
        reSetActionRequestSpecification();
        return response;
    }

    @Step("Delete reaction with id - {idOfReaction}, from action with id - {actionIdAfterCreatingACard}")
    public Response deleteActionsReaction(String actionIdAfterCreatingACard, String idOfReaction) {
        Response response = apiClient.delete(ActionsEndPoints.ACTIONS_BASE_PATH + actionIdAfterCreatingACard +
                ActionsEndPoints.REACTIONS_ENDPOINT + idOfReaction, actionRequestSpecification);
        reSetActionRequestSpecification();
        return response;
    }

    @Step("Add a comment {'commentForAnAction'} to a card with id ={cardId}")
    public Response addNewComentToACard(String cardId, String commentForAnAction, String commentsEnpoint, RequestSpecification specificToTestClassRequestSpecification) {
        specificToTestClassRequestSpecification.queryParams("text", commentForAnAction);
        Response response = apiClient.post(CardsEndPoints.CARDS_BASE_PATH + cardId + ActionsEndPoints.ACTIONS_BASE_PATH + commentsEnpoint, specificToTestClassRequestSpecification);
        reSetActionRequestSpecification();
        return response;
    }

    @Step("Create a card for list with id = {listIdTheCardIsOn}")
    public Response createACard(Map queryParamMap, RequestSpecification specificToTestClassRequestSpecification) {
        specificToTestClassRequestSpecification.queryParams(queryParamMap);
        Response response = apiClient.post(CardsEndPoints.CARDS_BASE_PATH, specificToTestClassRequestSpecification);
        reSetActionRequestSpecification();
        return response;
    }

    @Step("Get id of the first action on a board with id = {boardId}")
    public String getIdOfTheFirestActionOnABoard(String boardId, RequestSpecification specificToTestClassRequestSpecification) {

        Response response = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + ActionsEndPoints.ACTIONS_BASE_PATH, specificToTestClassRequestSpecification);

        List list = response.jsonPath().getList("id");
        reSetActionRequestSpecification();
        return list.get(0).toString();
    }

    @Step("Get id of the first list on a board")
    public List getListOfIdOfAllListsOnABoard(String boardId, RequestSpecification specificToTestClassRequestSpecification) {

        Response resp = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + LISTS_BASE_PATH, specificToTestClassRequestSpecification);
        List <String> list = resp.jsonPath().getList("id");
        reSetActionRequestSpecification();
        return list;
    }

    @Step("Create a board with a name {boardName}")
    public String createABord(String boardName, RequestSpecification specificToTestClassRequestSpecification) {

        specificToTestClassRequestSpecification.queryParam("name", boardName);

        Response response = apiClient.post(BoardEndPoints.BOARDS_BASE_PATH, specificToTestClassRequestSpecification);
        reSetActionRequestSpecification();

        return response.jsonPath().getString("id");
    }

    @Step("Delete a board with id = {boardId}")
    public void deleteBoard(String boardId, RequestSpecification specificToTestClassRequestSpecification) {

        apiClient.delete(BoardEndPoints.BOARDS_BASE_PATH + boardId, specificToTestClassRequestSpecification);
    }
}
