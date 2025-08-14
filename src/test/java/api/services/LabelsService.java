package api.services;

import api.resourcesForTests.PathParameters;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static api.resourcesForTests.PathParameters.LabelsPath.LABELS_BASE_PATH;

public class LabelsService extends BaseService {

    @Step("Create a new Label: name = {name}, color = {color}, board id = {idBoard}")
    public Response createLabel(String labelName, String color, String boardId) {
        requestSpecification.queryParam("name", labelName);
        requestSpecification.queryParam("color", color);
        requestSpecification.queryParam("idBoard", boardId);

        Response response = apiClient.post(LABELS_BASE_PATH, requestSpecification);
        initRequestSpecification();
        return response;
    }

    public Response addALabelToACard(String cardId, String idOfALabel) {

        requestSpecification.queryParam("value", idOfALabel);
        Response response = apiClient.post(PathParameters.CardsEndPoints.CARDS_BASE_PATH +
                        cardId +
                        PathParameters.CardsEndPoints.ID_LABELS_ENDPOINT,
                        requestSpecification);

        initRequestSpecification();
        return response;
    }

    @Step("Get a Label: id label = {labelId}")
    public Response getLabel(String labelId) {
        Response response = apiClient.get(LABELS_BASE_PATH + labelId, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Update Label: label id = {labelId}, new name = {newName}, new color = {newColor}")
    public Response updateLabel(String labelId, String newName, String newColor) {
        requestSpecification.queryParam("name", newName);
        requestSpecification.queryParam("color", newColor);

        Response response = apiClient.put(LABELS_BASE_PATH + labelId, requestSpecification);
        initRequestSpecification();
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

        Response response = apiClient.delete(LABELS_BASE_PATH + labelId, requestSpecification);
        initRequestSpecification();
        return response;
    }
}
