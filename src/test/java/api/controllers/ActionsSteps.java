package api.controllers;

import api.base.PathParameters.*;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class ActionsSteps extends BaseService {

    @Step("Get an action with id = {actiontId} from a board")
    public Response getAnAction(String actiontId) {
        Response response = apiClient.get(ActionsEndPoints.ACTIONS_BASE_PATH + actiontId, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Update a comment of an action with value = {updatedCommentForAnAction}")
    public Response updateACommentOfTheAction(String actiontId, String updatedCommentForAnAction) {
        requestSpecification.queryParam("value", updatedCommentForAnAction);
        Response response = apiClient.put(ActionsEndPoints.ACTIONS_BASE_PATH + actiontId + ActionsEndPoints.TEXT_ENDPOINT, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Delete an action with id = {idOfAnActionToDelete}")
    public Response deleteAnAction(String idOfAnActionToDelete) {
        Response response = apiClient.delete(ActionsEndPoints.ACTIONS_BASE_PATH + idOfAnActionToDelete, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get the resource {resourceEndPoint}, from action with id {actionId}")
    public Response getTheResourceOfAnAction(String idOfAnAction, String resourceEndPoint) {
        Response response = apiClient.get(ActionsEndPoints.ACTIONS_BASE_PATH + idOfAnAction + resourceEndPoint, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get all existed reactions for action with id - {actionId}")
    public Response getActions_Reactions(String actionId) {
        Response response = apiClient.get(ActionsEndPoints.ACTIONS_BASE_PATH + actionId + ActionsEndPoints.REACTIONS_ENDPOINT, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Create reaction for action with id - {actiontId}")
    public Response createReactionForAction(String actiontId) {
        requestSpecification.queryParams("shortName", "grinning");
        Response response = apiClient.post(ActionsEndPoints.ACTIONS_BASE_PATH + actiontId + ActionsEndPoints.REACTIONS_ENDPOINT, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get reaction with id {idOfReaction}, from action with id - {actionIdAfterCreatingACard}")
    public Response getActionsReaction(String actionIdAfterCreatingACard, String idOfReaction) {
        Response response = apiClient.get(ActionsEndPoints.ACTIONS_BASE_PATH + actionIdAfterCreatingACard +
                ActionsEndPoints.REACTIONS_ENDPOINT + idOfReaction, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Delete reaction with id - {idOfReaction}, from action with id - {actionIdAfterCreatingACard}")
    public Response deleteActionsReaction(String actionIdAfterCreatingACard, String idOfReaction) {
        Response response = apiClient.delete(ActionsEndPoints.ACTIONS_BASE_PATH + actionIdAfterCreatingACard +
                ActionsEndPoints.REACTIONS_ENDPOINT + idOfReaction, requestSpecification);
        initRequestSpecification();
        return response;
    }
}
