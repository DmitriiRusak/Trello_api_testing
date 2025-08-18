package api.resourcesForTests.configurationData;

import java.util.HashMap;
import java.util.List;

public class ListTestData {

    public static final String BOARD_NAME = "Board_for_lists";
    public static final String NAME_FOR_SECOND_BOARD = "Board_for_moving_lists";
    public static final String NAME_OF_THE_LIST = "List from API";
    public static final String NEW_NAME_FOR_THE_LIST = "List with Updated name";

    private String toDoListId;
    private List idsOfAllListsPresentedOnABoard;
    private String newCreatedListId;
    private String boardId;
    private HashMap<String, String> namesAndIdsOfLists = new HashMap<>();

    public String getToDoListId() {
        return toDoListId;
    }

    public void setToDoListId(String toDoListId) {
        this.toDoListId = toDoListId;
    }

    public List getIdsOfAllListsPresentedOnABoard() {
        return idsOfAllListsPresentedOnABoard;
    }

    public void setIdsOfAllListsPresentedOnABoard(List idsOfAllListsPresentedOnABoard) {
        this.idsOfAllListsPresentedOnABoard = idsOfAllListsPresentedOnABoard;
    }

    public String getNewCreatedListId() {
        return newCreatedListId;
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
