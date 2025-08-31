package api.services;

import api.resourcesForTests.PathParameters;
import api.utils.ApiClient;
import api.utils.Specification;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static api.resourcesForTests.PathParameters.LabelsPath.LABELS_BASE_PATH;

public class LabelsService{

    private final Specification specification = new Specification();
    private RequestSpecification labelRequestSpecification = specification.installRequest();

    @Step("Create a new Label: name = {name}, color = {color}, board id = {idBoard}")
    public Response createLabel(String labelName, String color, String boardId) {
        labelRequestSpecification.queryParam("name", labelName);
        labelRequestSpecification.queryParam("color", color);
        labelRequestSpecification.queryParam("idBoard", boardId);

        Response response = ApiClient.getInstance().post(LABELS_BASE_PATH, labelRequestSpecification);
        labelRequestSpecification = specification.installRequest();
        return response;
    }

    public Response addALabelToACard(String cardId, String idOfALabel) {

        labelRequestSpecification.queryParam("value", idOfALabel);
        Response response = ApiClient.getInstance().post(PathParameters.CardsEndPoints.CARDS_BASE_PATH +
                        cardId +
                        PathParameters.CardsEndPoints.ID_LABELS_ENDPOINT,
                labelRequestSpecification);

        labelRequestSpecification = specification.installRequest();
        return response;
    }

    @Step("Get a Label: id label = {labelId}")
    public Response getLabel(String labelId) {
        Response response = ApiClient.getInstance().get(LABELS_BASE_PATH + labelId, labelRequestSpecification);
        labelRequestSpecification = specification.installRequest();
        return response;
    }

    @Step("Update Label: label id = {labelId}, new name = {newName}, new color = {newColor}")
    public Response updateLabel(String labelId, String newName, String newColor) {
        labelRequestSpecification.queryParam("name", newName);
        labelRequestSpecification.queryParam("color", newColor);

        Response response = ApiClient.getInstance().put(LABELS_BASE_PATH + labelId, labelRequestSpecification);
        labelRequestSpecification = specification.installRequest();
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

        Response response = ApiClient.getInstance().delete(LABELS_BASE_PATH + labelId, labelRequestSpecification);
        labelRequestSpecification = specification.installRequest();
        return response;
    }

    @Step("Create a board with a name {boardName}")
    public String createABord(String boardName) {

        labelRequestSpecification.queryParam("name", boardName);

        Response response = ApiClient.getInstance().post(PathParameters.BoardEndPoints.BOARDS_BASE_PATH, labelRequestSpecification);
        labelRequestSpecification = specification.installRequest();

        return response.jsonPath().getString("id");
    }

    @Step("Delete a board with id = {boardId}")
    public void deleteBoard(String boardId) {

        ApiClient.getInstance().delete(PathParameters.BoardEndPoints.BOARDS_BASE_PATH + boardId, labelRequestSpecification);
        labelRequestSpecification = specification.installRequest();
    }
}
