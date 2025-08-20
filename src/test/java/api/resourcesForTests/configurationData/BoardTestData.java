package api.resourcesForTests.configurationData;

public class BoardTestData {

    private final String BOARD_NAME = "Board_for_Board";
    private final String BOARD_NAME_CREATED_WITH_SPECIFIC_OPTIONS ="Board_specific_options";
    private final String NAME_FOR_LIST = "List test API";
    private final String NAME_FOR_A_LABEL = "Api_Label";
    private final String COLOR_OF_A_LABEL = "red";
    private final String EXPECTED_RESULT = "[]";
    private final String NAME_OF_A_FILTER = "closed";
    private final String NEW_NAME_FOR_A_BOARD = "New BoardApiTest";
    private final String PERMISSION_LEVEL_PUBLIC = "public";

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

    public String getBOARD_NAME() {
        return BOARD_NAME;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getBOARD_NAME_CREATED_WITH_SPECIFIC_OPTIONS() {
        return BOARD_NAME_CREATED_WITH_SPECIFIC_OPTIONS;
    }

    public String getNAME_FOR_LIST() {
        return NAME_FOR_LIST;
    }

    public String getNAME_FOR_A_LABEL() {
        return NAME_FOR_A_LABEL;
    }

    public String getCOLOR_OF_A_LABEL() {
        return COLOR_OF_A_LABEL;
    }

    public String getEXPECTED_RESULT() {
        return EXPECTED_RESULT;
    }

    public String getNAME_OF_A_FILTER() {
        return NAME_OF_A_FILTER;
    }

    public String getNEW_NAME_FOR_A_BOARD() {
        return NEW_NAME_FOR_A_BOARD;
    }

    public String getPERMISSION_LEVEL_PUBLIC() {
        return PERMISSION_LEVEL_PUBLIC;
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
