package api.resourcesForTests;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;

public class CycymberConfigTestData {

    private final String BOARD_NAME = "Cucumber_board_for_Board";
    private final String BOARD_NAME_CREATED_WITH_SPECIFIC_OPTIONS ="Board_specific_options";

    private String boardId;
    private List idsOfAllListsPresentedOnABoard;
    private String toDoListId;
    private Response commonResponse;
    private String cardId;
    private String listIdTheCardIsOn;
    private String currentListId;
    private HashMap<String, String> namesAndIdsOfLists = new HashMap<>();

    public HashMap<String, String> getNamesAndIdsOfLists() {
        return namesAndIdsOfLists;
    }

    public void setNamesAndIdsOfLists(HashMap<String, String> namesAndIdsOfLists) {
        this.namesAndIdsOfLists = namesAndIdsOfLists;
    }

    public String getCurrentListId() {
        return currentListId;
    }

    public void setCurrentListId(String currentListId) {
        this.currentListId = currentListId;
    }

    public String getListIdTheCardIsOn() {
        return listIdTheCardIsOn;
    }

    public void setListIdTheCardIsOn(String listIdTheCardIsOn) {
        this.listIdTheCardIsOn = listIdTheCardIsOn;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Response getCommonResponse() {
        return commonResponse;
    }

    public void setCommonResponse(Response commonResponse) {
        this.commonResponse = commonResponse;
    }

    public String getToDoListId() {
        return toDoListId;
    }

    public String getBOARD_NAME_CREATED_WITH_SPECIFIC_OPTIONS() {
        return BOARD_NAME_CREATED_WITH_SPECIFIC_OPTIONS;
    }

    public List getIdsOfAllListsPresentedOnABoard() {
        return idsOfAllListsPresentedOnABoard;
    }

    public void setIdsOfAllListsPresentedOnABoard(List idsOfAllListsPresentedOnABoard) {
        this.idsOfAllListsPresentedOnABoard = idsOfAllListsPresentedOnABoard;
    }

    public void setToDoListId(String toDoListId) {
        this.toDoListId = toDoListId;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getBOARD_NAME() {
        return BOARD_NAME;
    }
}
