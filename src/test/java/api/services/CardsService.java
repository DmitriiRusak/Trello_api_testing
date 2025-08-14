package api.services;

import api.resourcesForTests.CardFields;
import api.resourcesForTests.PathParameters.*;
import api.utils.pojo.Cover;
import api.utils.pojo.CoverC;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.io.File;

public class CardsService extends BaseService {



    @Step("Get a card: id card = {cardId}")
    public Response getCard(String cardId) {

        Response response = apiClient.get(CardsEndPoints.CARDS_BASE_PATH + cardId, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Update a card: id card = {cardID}, name = {name}")
    public Response updateCard(String cardId,String optionName, String newOptionValue) {

        requestSpecification.queryParam(optionName, newOptionValue);

        Response response = apiClient.put(CardsEndPoints.CARDS_BASE_PATH + cardId, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Update a color of a card with id {cardID}, to color {newColorForACard}")
    public Response updateColorOfACard(String cardId, String newColorForACard){

        requestSpecification.body(new CoverC(new Cover(newColorForACard)));
        Response response = apiClient.put(CardsEndPoints.CARDS_BASE_PATH + cardId, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Delete a card with id - {'cardID'}")
    public Response deleteACard(String cardID) {
        Response response = apiClient.delete(CardsEndPoints.CARDS_BASE_PATH + cardID, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get custom fields on card: {cardId}, field = {field}")
    public Response getAFieldOfTheCard(String cardId, CardFields fieldName) {

        return apiClient.get(CardsEndPoints.CARDS_BASE_PATH + cardId +"/"+ fieldName, requestSpecification);
    }

    @Step("Get actions on card: {cardId}")
    public Response getActionsOnACard(String cardId) {
        Response response = apiClient.get(CardsEndPoints.CARDS_BASE_PATH + cardId + ActionsEndPoints.ACTIONS_BASE_PATH, requestSpecification);
        return response;
    }

    @Step("Get all attachments from a card with id - {'cardId'}}")
    public Response getAttachmentsOnACard(String cardId) {
        Response response = apiClient.get(CardsEndPoints.CARDS_BASE_PATH + cardId + CardsEndPoints.ATTACHMENTS_BASE_PATH, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Add attachment that is located - {'filePathOfAnAttachment'}, on a card with id - {'cardId'}")
    public Response createAttachmentOnCard(String cardId, String filePathOfAnAttachment) {

        requestSpecification.multiPart(new File(filePathOfAnAttachment));
        requestSpecification.contentType("multipart/form-data");

        Response response = apiClient.post(CardsEndPoints.CARDS_BASE_PATH + cardId + CardsEndPoints.ATTACHMENTS_ENDPOINT, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get an attachment with id - {'createdAttachmentId'}, from a card with id - {'cardId'} ")
    public Response getAnAttachmentOnACard(String cardId, String createdAttachmentId) {
        Response response = apiClient.get(CardsEndPoints.CARDS_BASE_PATH + cardId + CardsEndPoints.ATTACHMENTS_ENDPOINT + createdAttachmentId, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get checklists on a card with id - {'cardId'}")
    public Response getChecklistsOnACard(String cardId) {
        Response response = apiClient.get(CardsEndPoints.CARDS_BASE_PATH + cardId + CardsEndPoints.CHECKLISTS_ENDPOINT, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Delete an Attachment with id - {'attachmentId'}, from a card with id - {cardID}")
    public Response deleteAnAttachmentOnACard(String cardID, String attachmentId) {
        Response response = apiClient.delete(CardsEndPoints.CARDS_BASE_PATH + cardID + CardsEndPoints.ATTACHMENTS_BASE_PATH  + attachmentId, requestSpecification);
        initRequestSpecification();
        return response;
    }
    @Step("Get the board the card with id - {'cardId'}, is on")
    public Response getTheBoardTheCardIsOn(String cardId) {
        Response response = apiClient.get(CardsEndPoints.CARDS_BASE_PATH + cardId + CardsEndPoints.BOARD_ENDPOINT, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get checkItems on card: {cardId}, field = {field}")
    public Response getCheckItemsOnACard(String cardId) {
        Response response = apiClient.get(CardsEndPoints.CARDS_BASE_PATH + cardId + CardsEndPoints.CHECKITEMSTATES_ENDPOINT, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get Checklists on card: {cardId}")
    public Response getChecklistsCard(String cardId) {
        requestSpecification.queryParam("checkItems", "all");
        requestSpecification.queryParam("checkItem_fields", "id,name,state");
        Response response = apiClient.get(CardsEndPoints.CARDS_BASE_PATH + cardId + CardsEndPoints.CHECKLISTS_ENDPOINT, requestSpecification);
        initRequestSpecification();
        return response;
    }


    @Step("Get list on a card with id - {'cardId'}")
    public Response getTheListOfACard(String cardId) {
        Response response = apiClient.get(CardsEndPoints.CARDS_BASE_PATH + cardId + CardsEndPoints.LISTS_ENDPOINT, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get the members of a card with id - {'cardId'}")
    public Response getTheMembersOfACard(String cardId) {
        Response response = apiClient.get(CardsEndPoints.CARDS_BASE_PATH + cardId + CardsEndPoints.MEMBERS_ENDPOINT, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get stickers on a card with id - {'cardId'}")
    public Response getStickersOnACard(String cardId) {
        Response response = apiClient.get(CardsEndPoints.CARDS_BASE_PATH + cardId + CardsEndPoints.STICKERS_ENDPOINT, requestSpecification);
        initRequestSpecification();
        return response;
    }

}



