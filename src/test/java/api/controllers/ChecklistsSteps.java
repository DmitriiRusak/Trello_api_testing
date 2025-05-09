package api.controllers;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static api.base.PathParameters.CheckListsPath.CHECKLISTS_BASE_PATH;
import static api.base.PathParameters.CheckListsPath.*;

public class ChecklistsSteps extends BaseService {

    @Step("Get all fields of a checklist with id - {'checklistId'}")
    public Response getCheckList(String checklistId) {
        Response response = apiClient.get(CHECKLISTS_BASE_PATH + checklistId, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Update a field - {'nameOfAField'}, on a checklist with id - {checklistId}, new value is 'newValueOfAField'")
    public Response updateAFieldOfCheckList(String checklistId, String nameOfAField, String newValueOfAField) {
        requestSpecification.queryParam(nameOfAField, newValueOfAField);
        Response response = apiClient.put(CHECKLISTS_BASE_PATH + checklistId, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get a field - {'fieldToGetBackFromTheChecklist'}, from a checklist with id - {checklistId}")
    public Response getFieldOnAChecklist(String checklistId, String fieldToGetBackFromTheChecklist) {
        Response response = apiClient.get(CHECKLISTS_BASE_PATH + checklistId + fieldToGetBackFromTheChecklist, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get the board the checklist with id - {'checklistId'} is on")
    public Response getTheBoardTheChecklistIsOn(String checklistId) {
        Response response = apiClient.get(CHECKLISTS_BASE_PATH + checklistId + BOARD_ENDPOINT, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get the card the checklist with id - {'checklistId'}, is on.")
    public Response getTheCardAChecklistIsOn(String checklistId) {
        Response response = apiClient.get(CHECKLISTS_BASE_PATH + checklistId + CARDS_ENDPOINT, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Create checkItem with the name - {'nameOfNewCheckItem'}, on a checklist with id - {'checklistId'}")
    public Response createCheckitemOnChecklist(String checklistId, String nameOfNewCheckItem) {
        requestSpecification.queryParam("name", nameOfNewCheckItem);
        Response response = apiClient.post(CHECKLISTS_BASE_PATH + checklistId + CHECKITEMS_ENDPOINT, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get all checkItems that are currently available on a checklist")
    public Response getCheckitemsOnAChecklist(String checklistId) {
        Response response = apiClient.get(CHECKLISTS_BASE_PATH + checklistId + CHECKITEMS_ENDPOINT, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get a checkItem with id - {'checkItemId'}, on a checklist with id - {'checklistId'}")
    public Response getACheckitemOnAChecklist(String checklistId, String checkItemId) {
        Response response = apiClient.get(CHECKLISTS_BASE_PATH + checklistId + CHECKITEMS_ENDPOINT + checkItemId, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Delete checkItem with id - {'checkItemId'}, from checklist with id - {'checklistId'}")
    public Response deleteCheckitemFromChecklist(String checklistId, String checkItemId) {
        Response response = apiClient.delete(CHECKLISTS_BASE_PATH + checklistId + CHECKITEMS_ENDPOINT + checkItemId, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Delete a checklist")
    public Response deleteAChecklist(String checklistId) {
        Response response = apiClient.delete(CHECKLISTS_BASE_PATH + checklistId, requestSpecification);
        initRequestSpecification();
        return response;
    }
}
