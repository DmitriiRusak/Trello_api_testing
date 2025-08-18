package api.resourcesForTests.configurationData;

public class BoardTestData {

    public final static String BOARD_NAME = "Board_for_Board";
    public final static String BOARD_NAME_CREATED_WITH_SPECIFIC_OPTIONS ="Board_specific_options";
    public final static String NAME_FOR_LIST = "List test API";
    public final static String NAME_FOR_A_LABEL = "Api_Label";
    public final static String COLOR_OF_A_LABEL = "red";
    public final static String EXPECTED_RESULT = "[]";
    public final static String NAME_OF_A_FILTER = "closed";
    public final static String NEW_NAME_FOR_A_BOARD = "New BoardApiTest";
    public final static String PERMISSION_LEVEL_PUBLIC = "public";

    private String boardId;
    private String secondBoardId;
    private String DefiendPermissionBoardId;
    private String labelId;
    private String listId;
    private String toDoListId;

    public String getToDoListId() {
        return toDoListId;
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

    public String getSecondBoardId() {
        return secondBoardId;
    }

    public void setSecondBoardId(String secondBoardId) {
        this.secondBoardId = secondBoardId;
    }

    public String getDefiendPermissionBoardId() {
        return DefiendPermissionBoardId;
    }

    public void setDefiendPermissionBoardId(String defiendPermissionBoardId) {
        DefiendPermissionBoardId = defiendPermissionBoardId;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }
}
