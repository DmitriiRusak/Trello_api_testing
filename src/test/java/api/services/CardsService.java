package api.services;

import api.resourcesForTests.CardFields;
import api.resourcesForTests.PathParameters.*;
import api.utils.ApiClient;
import api.utils.Specification;
import api.utils.pojo.Cover;
import api.utils.pojo.CoverC;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.List;
import java.util.Map;

import static api.resourcesForTests.PathParameters.CheckListsPath.CHECKLISTS_BASE_PATH;
import static api.resourcesForTests.PathParameters.ListsPath.LISTS_BASE_PATH;

public class CardsService{

    private Specification specification = new Specification();
    private RequestSpecification cardRequestSpecification = RestAssured.given().spec(specification.installRequest());
    private final ApiClient apiClient = new ApiClient();


    public RequestSpecification getCardRequestSpecification() {
        return cardRequestSpecification;
    }

    public void reSetCardRequestSpecification() {
        cardRequestSpecification = RestAssured.given().spec(specification.installRequest());
    }

    @Step("Get a card: id card = {cardId}")
    public Response getCard(String cardId) {

        Response response = apiClient.get(CardsEndPoints.CARDS_BASE_PATH + cardId, cardRequestSpecification);
        reSetCardRequestSpecification();
        return response;
    }

    @Step("Update a card: id card = {cardID}, name = {name}")
    public Response updateCard(String cardId,String optionName, String newOptionValue) {

        cardRequestSpecification.queryParam(optionName, newOptionValue);

        Response response = apiClient.put(CardsEndPoints.CARDS_BASE_PATH + cardId, cardRequestSpecification);
        reSetCardRequestSpecification();
        return response;
    }

    @Step("Update a color of a card with id {cardID}, to color {newColorForACard}")
    public Response updateColorOfACard(String cardId, String newColorForACard){

        cardRequestSpecification.body(new CoverC(new Cover(newColorForACard)));
        Response response = apiClient.put(CardsEndPoints.CARDS_BASE_PATH + cardId, cardRequestSpecification);
        reSetCardRequestSpecification();
        return response;
    }

    @Step("Delete a card with id - {'cardID'}")
    public Response deleteACard(String cardID) {
        Response response = apiClient.delete(CardsEndPoints.CARDS_BASE_PATH + cardID, cardRequestSpecification);
        reSetCardRequestSpecification();
        return response;
    }

    @Step("Get custom fields on card: {cardId}, field = {field}")
    public Response getAFieldOfTheCard(String cardId, CardFields fieldName) {

        return apiClient.get(CardsEndPoints.CARDS_BASE_PATH + cardId +"/"+ fieldName, cardRequestSpecification);
    }

    @Step("Get actions on card: {cardId}")
    public Response getActionsOnACard(String cardId) {
        Response response = apiClient.get(CardsEndPoints.CARDS_BASE_PATH + cardId + ActionsEndPoints.ACTIONS_BASE_PATH, cardRequestSpecification);
        return response;
    }

    @Step("Get all attachments from a card with id - {'cardId'}}")
    public Response getAttachmentsOnACard(String cardId) {
        Response response = apiClient.get(CardsEndPoints.CARDS_BASE_PATH + cardId + CardsEndPoints.ATTACHMENTS_BASE_PATH, cardRequestSpecification);
        reSetCardRequestSpecification();
        return response;
    }

    @Step("Add attachment that is located - {'filePathOfAnAttachment'}, on a card with id - {'cardId'}")
    public Response createAttachmentOnCard(String cardId, String filePathOfAnAttachment) {

        cardRequestSpecification.multiPart(new File(filePathOfAnAttachment));
        cardRequestSpecification.contentType("multipart/form-data");

        Response response = apiClient.post(CardsEndPoints.CARDS_BASE_PATH + cardId + CardsEndPoints.ATTACHMENTS_ENDPOINT, cardRequestSpecification);
        reSetCardRequestSpecification();
        return response;
    }

    @Step("Get an attachment with id - {'createdAttachmentId'}, from a card with id - {'cardId'} ")
    public Response getAnAttachmentOnACard(String cardId, String createdAttachmentId) {
        Response response = apiClient.get(CardsEndPoints.CARDS_BASE_PATH + cardId + CardsEndPoints.ATTACHMENTS_ENDPOINT + createdAttachmentId, cardRequestSpecification);
        reSetCardRequestSpecification();
        return response;
    }

    @Step("Get checklists on a card with id - {'cardId'}")
    public Response getChecklistsOnACard(String cardId) {
        Response response = apiClient.get(CardsEndPoints.CARDS_BASE_PATH + cardId + CardsEndPoints.CHECKLISTS_ENDPOINT, cardRequestSpecification);
        reSetCardRequestSpecification();
        return response;
    }

    @Step("Delete an Attachment with id - {'attachmentId'}, from a card with id - {cardID}")
    public Response deleteAnAttachmentOnACard(String cardID, String attachmentId) {
        Response response = apiClient.delete(CardsEndPoints.CARDS_BASE_PATH + cardID + CardsEndPoints.ATTACHMENTS_BASE_PATH  + attachmentId, cardRequestSpecification);
        reSetCardRequestSpecification();
        return response;
    }
    @Step("Get the board the card with id - {'cardId'}, is on")
    public Response getTheBoardTheCardIsOn(String cardId) {
        Response response = apiClient.get(CardsEndPoints.CARDS_BASE_PATH + cardId + CardsEndPoints.BOARD_ENDPOINT, cardRequestSpecification);
        reSetCardRequestSpecification();
        return response;
    }

    @Step("Get checkItems on card: {cardId}, field = {field}")
    public Response getCheckItemsOnACard(String cardId) {
        Response response = apiClient.get(CardsEndPoints.CARDS_BASE_PATH + cardId + CardsEndPoints.CHECKITEMSTATES_ENDPOINT, cardRequestSpecification);
        reSetCardRequestSpecification();
        return response;
    }

    @Step("Get Checklists on card: {cardId}")
    public Response getChecklistsCard(String cardId) {
        cardRequestSpecification.queryParam("checkItems", "all");
        cardRequestSpecification.queryParam("checkItem_fields", "id,name,state");
        Response response = apiClient.get(CardsEndPoints.CARDS_BASE_PATH + cardId + CardsEndPoints.CHECKLISTS_ENDPOINT, cardRequestSpecification);
        reSetCardRequestSpecification();
        return response;
    }


    @Step("Get list on a card with id - {'cardId'}")
    public Response getTheListOfACard(String cardId) {
        Response response = apiClient.get(CardsEndPoints.CARDS_BASE_PATH + cardId + CardsEndPoints.LISTS_ENDPOINT, cardRequestSpecification);
        reSetCardRequestSpecification();
        return response;
    }

    @Step("Get the members of a card with id - {'cardId'}")
    public Response getTheMembersOfACard(String cardId) {
        Response response = apiClient.get(CardsEndPoints.CARDS_BASE_PATH + cardId + CardsEndPoints.MEMBERS_ENDPOINT, cardRequestSpecification);
        reSetCardRequestSpecification();
        return response;
    }

    @Step("Get stickers on a card with id - {'cardId'}")
    public Response getStickersOnACard(String cardId) {
        Response response = apiClient.get(CardsEndPoints.CARDS_BASE_PATH + cardId + CardsEndPoints.STICKERS_ENDPOINT, cardRequestSpecification);
        reSetCardRequestSpecification();
        return response;
    }

    @Step("Create a checklist on a card with id - {'idCard'}, with a name - {'nameOfAChecklistBeingCreated'}")
    public Response createAChecklistForACard(String idCard, String nameOfAChecklistBeingCreated, RequestSpecification specificToTestClassRequestSpecification) {
        specificToTestClassRequestSpecification.queryParam("idCard", idCard);
        specificToTestClassRequestSpecification.queryParam("name", nameOfAChecklistBeingCreated);
        Response response = apiClient.post(CHECKLISTS_BASE_PATH, specificToTestClassRequestSpecification);
        reSetCardRequestSpecification();
        return response;
    }

    @Step("Add a comment {'commentForAnAction'} to a card with id ={cardId}")
    public Response addNewComentToACard(String cardId, String commentForAnAction, String commentsEnpoint, RequestSpecification specificToTestClassRequestSpecification) {
        specificToTestClassRequestSpecification.queryParams("text", commentForAnAction);
        Response response = apiClient.post(CardsEndPoints.CARDS_BASE_PATH + cardId + ActionsEndPoints.ACTIONS_BASE_PATH + commentsEnpoint, specificToTestClassRequestSpecification);
        reSetCardRequestSpecification();
        return response;
    }

    @Step("Create a card for list with id = {listIdTheCardIsOn}")
    public Response createACard(Map queryParamMap, RequestSpecification specificToTestClassRequestSpecification) {
        specificToTestClassRequestSpecification.queryParams(queryParamMap);
        Response response = apiClient.post(CardsEndPoints.CARDS_BASE_PATH, specificToTestClassRequestSpecification);
        reSetCardRequestSpecification();
        return response;
    }

    @Step("Create a board with a name {boardName}")
    public String createABord(String boardName, RequestSpecification specificToTestClassRequestSpecification) {

        specificToTestClassRequestSpecification.queryParam("name", boardName);

        Response response = apiClient.post(BoardEndPoints.BOARDS_BASE_PATH, specificToTestClassRequestSpecification);
        reSetCardRequestSpecification();

        return response.jsonPath().getString("id");
    }

    @Step("Delete a board with id = {boardId}")
    public void deleteBoard(String boardId, RequestSpecification specificToTestClassRequestSpecification) {

        apiClient.delete(BoardEndPoints.BOARDS_BASE_PATH + boardId, specificToTestClassRequestSpecification);
        reSetCardRequestSpecification();
    }

    @Step("Get id of the first list on a board")
    public List getListOfIdOfAllListsOnABoard(String boardId, RequestSpecification specificToTestClassRequestSpecification) {

        Response resp = apiClient.get(BoardEndPoints.BOARDS_BASE_PATH + boardId + LISTS_BASE_PATH, specificToTestClassRequestSpecification);
        List <String> list = resp.jsonPath().getList("id");
        reSetCardRequestSpecification();
        return list;
    }
}



