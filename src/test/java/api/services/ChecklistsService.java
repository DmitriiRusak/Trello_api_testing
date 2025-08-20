package api.services;

import api.resourcesForTests.CheckListFields;
import api.resourcesForTests.PathParameters;
import api.utils.ApiClient;
import api.utils.Specification;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Map;

import static api.resourcesForTests.PathParameters.CheckListsPath.CHECKLISTS_BASE_PATH;
import static api.resourcesForTests.PathParameters.CheckListsPath.*;
import static api.resourcesForTests.PathParameters.ListsPath.LISTS_BASE_PATH;

public class ChecklistsService{

    private Specification specification = new Specification();
    private RequestSpecification checklistRequestSpecification = RestAssured.given().spec(specification.installRequest());
    private final ApiClient apiClient = new ApiClient();

    public RequestSpecification getChecklistRequestSpecification() {
        return checklistRequestSpecification;
    }

    public void reSetChecklistRequestSpecification() {
        checklistRequestSpecification = RestAssured.given().spec(specification.installRequest());
    }

    @Step("Get all fields of a checklist with id - {'checklistId'}")
    public Response getCheckList(String checklistId) {
        Response response = apiClient.get(CHECKLISTS_BASE_PATH + checklistId, checklistRequestSpecification);
        reSetChecklistRequestSpecification();
        return response;
    }

    @Step("Update a field - {'nameOfAField'}, on a checklist with id - {checklistId}, new value is 'newValueOfAField'")
    public Response updateAFieldOfCheckList(String checklistId, CheckListFields nameOfAField, String newValueOfAField) {
        checklistRequestSpecification.queryParam(nameOfAField.toString(), newValueOfAField);
        Response response = apiClient.put(CHECKLISTS_BASE_PATH + checklistId, checklistRequestSpecification);
        reSetChecklistRequestSpecification();
        return response;
    }

    @Step("Get a field - {'fieldToGetBackFromTheChecklist'}, from a checklist with id - {checklistId}")
    public Response getFieldOnAChecklist(String checklistId, CheckListFields fieldToGetBackFromTheChecklist) {
        Response response = apiClient.get(CHECKLISTS_BASE_PATH + checklistId + "/" + fieldToGetBackFromTheChecklist, checklistRequestSpecification);
        reSetChecklistRequestSpecification();
        return response;
    }

    @Step("Get the board the checklist with id - {'checklistId'} is on")
    public Response getTheBoardTheChecklistIsOn(String checklistId) {
        Response response = apiClient.get(CHECKLISTS_BASE_PATH + checklistId + BOARD_ENDPOINT, checklistRequestSpecification);
        reSetChecklistRequestSpecification();
        return response;
    }

    @Step("Get the card the checklist with id - {'checklistId'}, is on.")
    public Response getTheCardAChecklistIsOn(String checklistId) {
        Response response = apiClient.get(CHECKLISTS_BASE_PATH + checklistId + CARDS_ENDPOINT, checklistRequestSpecification);
        reSetChecklistRequestSpecification();
        return response;
    }

    @Step("Create checkItem with the name - {'nameOfNewCheckItem'}, on a checklist with id - {'checklistId'}")
    public Response createCheckitemOnChecklist(String checklistId, String nameOfNewCheckItem) {
        checklistRequestSpecification.queryParam("name", nameOfNewCheckItem);
        Response response = apiClient.post(CHECKLISTS_BASE_PATH + checklistId + CHECKITEMS_ENDPOINT, checklistRequestSpecification);
        reSetChecklistRequestSpecification();
        return response;
    }

    @Step("Get all checkItems that are currently available on a checklist")
    public Response getCheckitemsOnAChecklist(String checklistId) {
        Response response = apiClient.get(CHECKLISTS_BASE_PATH + checklistId + CHECKITEMS_ENDPOINT, checklistRequestSpecification);
        reSetChecklistRequestSpecification();
        return response;
    }

    @Step("Get a checkItem with id - {'checkItemId'}, on a checklist with id - {'checklistId'}")
    public Response getACheckitemOnAChecklist(String checklistId, String checkItemId) {
        Response response = apiClient.get(CHECKLISTS_BASE_PATH + checklistId + CHECKITEMS_ENDPOINT + checkItemId, checklistRequestSpecification);
        reSetChecklistRequestSpecification();
        return response;
    }

    @Step("Delete checkItem with id - {'checkItemId'}, from checklist with id - {'checklistId'}")
    public Response deleteCheckitemFromChecklist(String checklistId, String checkItemId) {
        Response response = apiClient.delete(CHECKLISTS_BASE_PATH + checklistId + CHECKITEMS_ENDPOINT + checkItemId, checklistRequestSpecification);
        reSetChecklistRequestSpecification();
        return response;
    }

    @Step("Delete a checklist")
    public Response deleteAChecklist(String checklistId) {
        Response response = apiClient.delete(CHECKLISTS_BASE_PATH + checklistId, checklistRequestSpecification);
        reSetChecklistRequestSpecification();
        return response;
    }

    @Step("Create a checklist on a card with id - {'idCard'}, with a name - {'nameOfAChecklistBeingCreated'}")
    public Response createChecklistFromCheckListService(String idCard, String nameOfAChecklistBeingCreated, RequestSpecification specificToTestClassRequestSpecification) {
        specificToTestClassRequestSpecification.queryParam("idCard", idCard);
        specificToTestClassRequestSpecification.queryParam("name", nameOfAChecklistBeingCreated);
        Response response = apiClient.post(CHECKLISTS_BASE_PATH, specificToTestClassRequestSpecification);
        reSetChecklistRequestSpecification();
        return response;
    }

    @Step("Create a card for list with id = {listIdTheCardIsOn}")
    public Response createACard(Map queryParamMap, RequestSpecification specificToTestClassRequestSpecification) {
        specificToTestClassRequestSpecification.queryParams(queryParamMap);
        Response response = apiClient.post(PathParameters.CardsEndPoints.CARDS_BASE_PATH, specificToTestClassRequestSpecification);
        reSetChecklistRequestSpecification();
        return response;
    }

    @Step("Create a board with a name {boardName}")
    public String createABord(String boardName, RequestSpecification specificToTestClassRequestSpecification) {

        specificToTestClassRequestSpecification.queryParam("name", boardName);

        Response response = apiClient.post(PathParameters.BoardEndPoints.BOARDS_BASE_PATH, specificToTestClassRequestSpecification);
        reSetChecklistRequestSpecification();

        return response.jsonPath().getString("id");
    }

    @Step("Delete a board with id = {boardId}")
    public void deleteBoard(String boardId, RequestSpecification specificToTestClassRequestSpecification) {

        apiClient.delete(PathParameters.BoardEndPoints.BOARDS_BASE_PATH + boardId, specificToTestClassRequestSpecification);
        reSetChecklistRequestSpecification();
    }

    @Step("Get id of the first list on a board")
    public List getListOfIdOfAllListsOnABoard(String boardId, RequestSpecification specificToTestClassRequestSpecification) {

        Response resp = apiClient.get(PathParameters.BoardEndPoints.BOARDS_BASE_PATH + boardId + LISTS_BASE_PATH, specificToTestClassRequestSpecification);
        List <String> list = resp.jsonPath().getList("id");
        reSetChecklistRequestSpecification();
        return list;
    }
}
