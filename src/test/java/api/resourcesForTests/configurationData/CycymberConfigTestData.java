package api.resourcesForTests.configurationData;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;

public class CycymberConfigTestData {

    public final String BOARD_NAME = "Cucumber_board_for_Board";
    public final String BOARD_NAME_CREATED_WITH_SPECIFIC_OPTIONS ="Board_specific_options";
    public final String NAME_OF_CHECKLIST_CREATED = "First Checklist";
    public final String LABEL_NAME = "Label from API";
    public static final String EXPECTED_EMPTY_STRING_RESULT = "[:]";
    public static final String EMPTY_STRING = "[]";

    private String boardId;
    private List idsOfAllListsPresentedOnABoard;
    private String toDoListId;
    private Response commonResponse;
    private String cardId;
    private String listIdTheCardIsOn;
    private String currentListId;
    private String createdAttachmentId;
    private HashMap<String, String> namesAndIdsOfLists = new HashMap<>();
    private String checklistId;
    private String labelId;
    private String newCreatedListId;
    private String secondBoardId;

    public String getSecondBoardId() {
        return secondBoardId;
    }

    public void setSecondBoardId(String secondBoardId) {
        this.secondBoardId = secondBoardId;
    }

    public String getNewCreatedListId() {
        return newCreatedListId;
    }

    public void setNewCreatedListId(String newCreatedListId) {
        this.newCreatedListId = newCreatedListId;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(String checklistId) {
        this.checklistId = checklistId;
    }

    public HashMap<String, String> getNamesAndIdsOfLists() {
        return namesAndIdsOfLists;
    }

    public String getCreatedAttachmentId() {
        return createdAttachmentId;
    }

    public void setCreatedAttachmentId(String createdAttachmentId) {
        this.createdAttachmentId = createdAttachmentId;
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
