package api.controllers;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static api.base.PathParameters.MembersPath.*;

public class MembersSteps extends BaseService {

    @Step("Get a member with id - {firstMemberId}")
    public Response getAMember(String firstMemberId) {
        Response response = apiClient.get(MEMBERS_BASE_PATH + firstMemberId, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Update a member: memberId = {memberId}")
    public Response updateMember(String memberId) {
        Response response = apiClient.put(MEMBERS_BASE_PATH + memberId, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get a Member's Actions: memberId = {memberId}")
    public Response getMemberActions(String memberId) {
        Response response = apiClient.get(MEMBERS_BASE_PATH + memberId + ACTIONS_ENDPOINT, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get Member's custom Board backgrounds: memberID = {memberId}")
    public Response getMemberCustomBackgrounds(String memberId) {
        Response response = apiClient.get(MEMBERS_BASE_PATH + memberId + BOARD_BACKGROUNDS_ENDPOINT, requestSpecification);
        initRequestSpecification();
        System.out.println(response.body().asString());
        return response;
    }

    @Step("Get a boardBackground of a Member: memberId = {memberId}, backgroundId = {backgroundId}")
    public Response getGetBoardBackgroundMember(String memberId, String backgroundId) {
        Response response = apiClient.get(MEMBERS_BASE_PATH + memberId + BOARD_BACKGROUNDS_ENDPOINT + backgroundId, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Create Star for Board: memberId = {memberId}, boardId = {boardId}, pos = {pos}")
    public Response getCreateStarBoard(String memberId, String boardId, String pos) {
        requestSpecification.queryParam("idBoard", boardId);
        requestSpecification.queryParam("pos", pos);
        Response response = apiClient.post(MEMBERS_BASE_PATH + memberId + BOARD_STARS_ENDPOINT, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Get a boardStar of Member: memberId = {memberId}, starId = {starId}")
    public Response getBoardStarMember(String memberId, String starId) {
        Response response = apiClient.get(MEMBERS_BASE_PATH + memberId + BOARD_STARS_ENDPOINT + starId, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Update the position of a boardStar of Member: memberId = {memberId}, starId = {starId}, pos = {pos}")
    public Response updatePositionBoardStarMember(String memberId, String starId, String pos) {
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

    @Step("List a member's board stars: memberId = {memberId}")
    public Response getMemberBoardStars(String memberId) {
        Response response = apiClient.get(MEMBERS_BASE_PATH + memberId + BOARD_STARS_ENDPOINT, requestSpecification);
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
    public Response getBoardsMemberInvited(String memberId) {
        Response response = apiClient.get(MEMBERS_BASE_PATH + memberId + BOARDS_INVITED_ENDPOINT, requestSpecification);
        initRequestSpecification();
        return response;
    }

    @Step("Gets the cards a member is on:  memberId = {memberId}")
    public Response getCardsMember(String memberId) {
        Response response = apiClient.get(MEMBERS_BASE_PATH + memberId + CARDS_ENDPOINT, requestSpecification);
        initRequestSpecification();
        return response;
    }
}
