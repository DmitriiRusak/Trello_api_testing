package api.services;

import api.resourcesForTests.PathParameters.*;
import api.utils.ApiClient;
import api.utils.Specification;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static api.resourcesForTests.PathParameters.ListsPath.LISTS_BASE_PATH;

public class ActionsService{

    private final Specification specification = new Specification();
    private RequestSpecification actionRequestSpecification = specification.installRequest();

    @Step("Get an action with id = {actiontId} from a board")
    public Response getAnAction(String actiontId) {

        Response response = ApiClient.getInstance().get(ActionsEndPoints.ACTIONS_BASE_PATH + actiontId, actionRequestSpecification);
        actionRequestSpecification = specification.installRequest();
        return response;
    }

    @Step("Update a comment of an action with value = {updatedCommentForAnAction}")
    public Response updateACommentOfTheAction(String actiontId, String updatedCommentForAnAction) {
        actionRequestSpecification.queryParam("value", updatedCommentForAnAction);
        Response response = ApiClient.getInstance().put(ActionsEndPoints.ACTIONS_BASE_PATH + actiontId + ActionsEndPoints.TEXT_ENDPOINT, actionRequestSpecification);
        actionRequestSpecification = specification.installRequest();
        return response;
    }

    @Step("Delete an action with id = {idOfAnActionToDelete}")
    public Response deleteAnAction(String idOfAnActionToDelete) {
        Response response = ApiClient.getInstance().delete(ActionsEndPoints.ACTIONS_BASE_PATH + idOfAnActionToDelete, actionRequestSpecification);
        actionRequestSpecification = specification.installRequest();
        return response;
    }

    @Step("Get the resource {resourceEndPoint}, from action with id {actionId}")
    public Response getTheResourceOfAnAction(String idOfAnAction, String resourceEndPoint) {
        Response response = ApiClient.getInstance().get(ActionsEndPoints.ACTIONS_BASE_PATH + idOfAnAction + resourceEndPoint, actionRequestSpecification);
        actionRequestSpecification = specification.installRequest();
        return response;
    }

    @Step("Get all existed reactions for action with id - {actionId}")
    public Response getActions_Reactions(String actionId) {
        Response response = ApiClient.getInstance().get(ActionsEndPoints.ACTIONS_BASE_PATH + actionId + ActionsEndPoints.REACTIONS_ENDPOINT, actionRequestSpecification);
        actionRequestSpecification = specification.installRequest();
        return response;
    }

    @Step("Create reaction for action with id - {actiontId}")
    public Response createReactionForAction(String actiontId) {
        actionRequestSpecification.queryParams("shortName", "grinning");
        Response response = ApiClient.getInstance().post(ActionsEndPoints.ACTIONS_BASE_PATH + actiontId + ActionsEndPoints.REACTIONS_ENDPOINT, actionRequestSpecification);
        actionRequestSpecification = specification.installRequest();
        return response;
    }

    @Step("Get reaction with id {idOfReaction}, from action with id - {actionIdAfterCreatingACard}")
    public Response getActionsReaction(String actionIdAfterCreatingACard, String idOfReaction) {
        Response response = ApiClient.getInstance().get(ActionsEndPoints.ACTIONS_BASE_PATH + actionIdAfterCreatingACard +
                ActionsEndPoints.REACTIONS_ENDPOINT + idOfReaction, actionRequestSpecification);
        actionRequestSpecification = specification.installRequest();
        return response;
    }

    @Step("Delete reaction with id - {idOfReaction}, from action with id - {actionIdAfterCreatingACard}")
    public Response deleteActionsReaction(String actionIdAfterCreatingACard, String idOfReaction) {
        Response response = ApiClient.getInstance().delete(ActionsEndPoints.ACTIONS_BASE_PATH + actionIdAfterCreatingACard +
                ActionsEndPoints.REACTIONS_ENDPOINT + idOfReaction, actionRequestSpecification);
        actionRequestSpecification = specification.installRequest();
        return response;
    }

    @Step("Add a comment {'commentForAnAction'} to a card with id ={cardId}")
    public Response addNewComentToACard(String cardId, String commentForAnAction, String commentsEnpoint) {
        actionRequestSpecification.queryParams("text", commentForAnAction);
        Response response = ApiClient.getInstance().post(CardsEndPoints.CARDS_BASE_PATH + cardId + ActionsEndPoints.ACTIONS_BASE_PATH + commentsEnpoint, actionRequestSpecification);
        actionRequestSpecification = specification.installRequest();
        return response;
    }

    @Step("Create a card for list with id = {listIdTheCardIsOn}")
    public Response createACard(Map queryParamMap) {
        actionRequestSpecification.queryParams(queryParamMap);
        Response response = ApiClient.getInstance().post(CardsEndPoints.CARDS_BASE_PATH, actionRequestSpecification);
        actionRequestSpecification = specification.installRequest();
        return response;
    }

    @Step("Get id of the first action on a board with id = {boardId}")
    public String getIdOfTheFirestActionOnABoard(String boardId) {

        Response response = ApiClient.getInstance().get(BoardEndPoints.BOARDS_BASE_PATH + boardId + ActionsEndPoints.ACTIONS_BASE_PATH, actionRequestSpecification);

        List list = response.jsonPath().getList("id");
        actionRequestSpecification = specification.installRequest();
        return list.get(0).toString();
    }

    @Step("Get id of the first list on a board")
    public List getListOfIdOfAllListsOnABoard(String boardId) {

        Response resp = ApiClient.getInstance().get(BoardEndPoints.BOARDS_BASE_PATH + boardId + LISTS_BASE_PATH, actionRequestSpecification);
        List <String> list = resp.jsonPath().getList("id");
        actionRequestSpecification = specification.installRequest();
        return list;
    }

    @Step("Create a board with a name {boardName}")
    public String createABord(String boardName) {

        actionRequestSpecification.queryParam("name", boardName);

        Response response = ApiClient.getInstance().post(BoardEndPoints.BOARDS_BASE_PATH, actionRequestSpecification);
        actionRequestSpecification = specification.installRequest();

        return response.jsonPath().getString("id");
    }

    @Step("Delete a board with id = {boardId}")
    public void deleteBoard(String boardId) {

        ApiClient.getInstance().delete(BoardEndPoints.BOARDS_BASE_PATH + boardId, actionRequestSpecification);
        actionRequestSpecification = specification.installRequest();
    }
}
