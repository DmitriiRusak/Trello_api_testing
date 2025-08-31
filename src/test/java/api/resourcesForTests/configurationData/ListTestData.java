package api.resourcesForTests.configurationData;

import java.util.HashMap;
import java.util.List;

public class ListTestData {

    private final String BOARD_NAME = "Board_for_lists";
    private final String NAME_FOR_SECOND_BOARD = "Board_for_moving_lists";
    private final String NAME_OF_THE_LIST = "List from API";
    private final String NEW_NAME_FOR_THE_LIST = "List with Updated name";

    private String toDoListId;
    private String newCreatedListId;
    private String boardId;
    private HashMap<String, String> namesAndIdsOfLists = new HashMap<>();

    public String getToDoListId() {
        return toDoListId;
    }

    public void setToDoListId(String toDoListId) {
        this.toDoListId = toDoListId;
    }

    public String getNAME_OF_THE_LIST() {
        return NAME_OF_THE_LIST;
    }

    public String getNEW_NAME_FOR_THE_LIST() {
        return NEW_NAME_FOR_THE_LIST;
    }

    public String getNewCreatedListId() {
        return newCreatedListId;
    }

    public String getBOARD_NAME() {
        return BOARD_NAME;
    }

    public String getNAME_FOR_SECOND_BOARD() {
        return NAME_FOR_SECOND_BOARD;
    }

    public void setNewCreatedListId(String newCreatedListId) {
        this.newCreatedListId = newCreatedListId;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public HashMap<String, String> getNamesAndIdsOfLists() {
        return namesAndIdsOfLists;
    }

    public void setNamesAndIdsOfLists(HashMap<String, String> namesAndIdsOfLists) {
        this.namesAndIdsOfLists = namesAndIdsOfLists;
    }
}
