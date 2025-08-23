package api.resourcesForTests.configurationData;

public class CheckListTestData {

    private final String BOARD_NAME_FOR_CHECKLIST = "Board_for_Checklists";
    private final String NAME_OF_CHECKLIST_CREATED = "First Checklist";
    private final String NEW_NAME_FOR_CHECKLIST = "New name for checklist";
    private final String NAME_FOR_NEW_CHECKITEM = "Mark";
    private final String EMPTY_STRING = "[]";
    private final String EXPECTED_EMPTY_STRING_RESULT = "[:]";

    private String boardId;
    private String cardId;
    private String checklistId;
    private String checkItemId;
    private String toDoListId;

    public String getEMPTY_STRING() {
        return EMPTY_STRING;
    }

    public String getEXPECTED_EMPTY_STRING_RESULT() {
        return EXPECTED_EMPTY_STRING_RESULT;
    }

    public String getNEW_NAME_FOR_CHECKLIST() {
        return NEW_NAME_FOR_CHECKLIST;
    }

    public String getNAME_FOR_NEW_CHECKITEM() {
        return NAME_FOR_NEW_CHECKITEM;
    }

    public String getNAME_OF_CHECKLIST_CREATED() {
        return NAME_OF_CHECKLIST_CREATED;
    }

    public String getBOARD_NAME_FOR_CHECKLIST() {
        return BOARD_NAME_FOR_CHECKLIST;
    }

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

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(String checklistId) {
        this.checklistId = checklistId;
    }

    public String getCheckItemId() {
        return checkItemId;
    }

    public void setCheckItemId(String checkItemId) {
        this.checkItemId = checkItemId;
    }
}
