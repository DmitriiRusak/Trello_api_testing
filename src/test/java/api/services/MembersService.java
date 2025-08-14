package api.services;

import api.resourcesForTests.MemberFields;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static api.resourcesForTests.PathParameters.MembersPath.*;

public class MembersService extends BaseService {

    @Step("Get a member with id - {firstMemberId}")
    public Response getAMember(String firstMemberId) {
        Response response = apiClient.get(MEMBERS_BASE_PATH + firstMemberId, requestSpecification);
        initRequestSpecification();
        return response;
    }

//    @Step("Get a field of a member")
//    public Response getAMember(String memberId){
////        requestSpecification.queryParam("field", fieldName);
//        Response response = apiClient.get(MEMBERS_BASE_PATH + memberId, requestSpecification);
//        initRequestSpecification();
//        return response;
//    }

    @Step("Update a field {fieldName} for the member with value = {newValueForAField}")
    public Response updateAFieldOfAMember(String memberId, MemberFields fieldName, String newValueForAField) {
        requestSpecification.queryParam(fieldName.toString(), newValueForAField);
        Response response = apiClient.put(MEMBERS_BASE_PATH + memberId, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get a Member's Actions: memberId = {memberId}")
    public Response getMembersActions(String memberId) {
        Response response = apiClient.get(MEMBERS_BASE_PATH + memberId + ACTIONS_ENDPOINT, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get Member's custom Board backgrounds: memberID = {memberId}")
    public Response getMemberCustomBackgrounds(String memberId) {
        Response response = apiClient.get(MEMBERS_BASE_PATH + memberId + BOARD_BACKGROUNDS_ENDPOINT, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Create Star for Board: memberId = {memberId}, boardId = {boardId}, pos = {pos}")
    public Response createStarForABoard(String memberId, String boardId, String pos) {
        requestSpecification.queryParam("idBoard", boardId);
        requestSpecification.queryParam("pos", pos);
        Response response = apiClient.post(MEMBERS_BASE_PATH + memberId + BOARD_STARS_ENDPOINT, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get a boardStar of Member: memberId = {memberId}, starId = {starId}")
    public Response getBoardStarOfAMember(String memberId, String starId) {
        Response response = apiClient.get(MEMBERS_BASE_PATH + memberId + BOARD_STARS_ENDPOINT + starId, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Update the position of a boardStar of a Member: memberId = {memberId}, starId = {starId}, pos = {pos}")
    public Response updateThePositionOfABoardStarOfAMember(String memberId, String starId, String pos) {
        requestSpecification.queryParam("pos", pos);
        Response response = apiClient.put(MEMBERS_BASE_PATH + memberId + BOARD_STARS_ENDPOINT + starId, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Unstar a board: memberId = {memberId}, starId = {starId}")
    public Response deleteStarBoard(String memberId, String starId) {
        Response response = apiClient.delete(MEMBERS_BASE_PATH + memberId + BOARD_STARS_ENDPOINT + starId, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Lists the boards that the user is a member of:  memberId = {memberId}")
    public Response getBoardsMemberBelongs(String memberId) {
        Response response = apiClient.get(MEMBERS_BASE_PATH + memberId + BOARDS_ENDPOINT, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get the boards the member has been invited to:  memberId = {memberId}")
    public Response getTheBoardsTheMemberInvitedTo(String memberId) {
        Response response = apiClient.get(MEMBERS_BASE_PATH + memberId + BOARDS_INVITED_ENDPOINT, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Gets the cards a member is on:  memberId = {memberId}")
    public Response getCardsOfAMember(String memberId) {
        Response response = apiClient.get(MEMBERS_BASE_PATH + memberId + CARDS_ENDPOINT, requestSpecification);
        initRequestSpecification();
        return response;
    }
}
