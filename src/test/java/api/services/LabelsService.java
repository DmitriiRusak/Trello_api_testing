package api.services;

import api.resourcesForTests.PathParameters;
import api.utils.ApiClient;
import api.utils.Specification;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static api.resourcesForTests.PathParameters.LabelsPath.LABELS_BASE_PATH;

public class LabelsService{

    private Specification specification = new Specification();
    private RequestSpecification labelRequestSpecification = RestAssured.given().spec(specification.installRequest());
    private final ApiClient apiClient = new ApiClient();

    public RequestSpecification getLabelRequestSpecification() {
        return labelRequestSpecification;
    }

    public void reSetLabelRequestSpecification() {
        labelRequestSpecification = RestAssured.given().spec(specification.installRequest());
    }

    @Step("Create a new Label: name = {name}, color = {color}, board id = {idBoard}")
    public Response createLabel(String labelName, String color, String boardId) {
        labelRequestSpecification.queryParam("name", labelName);
        labelRequestSpecification.queryParam("color", color);
        labelRequestSpecification.queryParam("idBoard", boardId);

        Response response = apiClient.post(LABELS_BASE_PATH, labelRequestSpecification);
        reSetLabelRequestSpecification();
        return response;
    }

    public Response addALabelToACard(String cardId, String idOfALabel) {

        labelRequestSpecification.queryParam("value", idOfALabel);
        Response response = apiClient.post(PathParameters.CardsEndPoints.CARDS_BASE_PATH +
                        cardId +
                        PathParameters.CardsEndPoints.ID_LABELS_ENDPOINT,
                labelRequestSpecification);

        reSetLabelRequestSpecification();
        return response;
    }

    @Step("Get a Label: id label = {labelId}")
    public Response getLabel(String labelId) {
        Response response = apiClient.get(LABELS_BASE_PATH + labelId, labelRequestSpecification);
        reSetLabelRequestSpecification();
        return response;
    }

    @Step("Update Label: label id = {labelId}, new name = {newName}, new color = {newColor}")
    public Response updateLabel(String labelId, String newName, String newColor) {
        labelRequestSpecification.queryParam("name", newName);
        labelRequestSpecification.queryParam("color", newColor);

        Response response = apiClient.put(LABELS_BASE_PATH + labelId, labelRequestSpecification);
        reSetLabelRequestSpecification();
        return response;
    }

//    @Step("Update field Label: label id = {labelId}, field = {field}, value = {value]")
//    public Response updateFieldLabel(String labelId, String field, String value) {
//        requestSpecification.queryParam("value", value);
//
//        Response response = apiClient.put(LABELS_BASE_PATH + labelId + "/" + field, requestSpecification);
//        initRequestSpecification();
//        return response;
//    }

    @Step("Delete Label: label id = {labelId}")
    public Response deleteLabel(String labelId) {

        Response response = apiClient.delete(LABELS_BASE_PATH + labelId, labelRequestSpecification);
        reSetLabelRequestSpecification();
        return response;
    }

    @Step("Create a board with a name {boardName}")
    public String createABord(String boardName, RequestSpecification specificToTestClassRequestSpecification) {

        specificToTestClassRequestSpecification.queryParam("name", boardName);

        Response response = apiClient.post(PathParameters.BoardEndPoints.BOARDS_BASE_PATH, specificToTestClassRequestSpecification);
        reSetLabelRequestSpecification();

        return response.jsonPath().getString("id");
    }

    @Step("Delete a board with id = {boardId}")
    public void deleteBoard(String boardId, RequestSpecification specificToTestClassRequestSpecification) {

        apiClient.delete(PathParameters.BoardEndPoints.BOARDS_BASE_PATH + boardId, specificToTestClassRequestSpecification);
        reSetLabelRequestSpecification();
    }
}
