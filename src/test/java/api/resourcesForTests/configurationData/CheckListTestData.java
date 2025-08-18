package api.resourcesForTests.configurationData;

public class CheckListTestData {

    public static final String BOARD_NAME_FOR_CHECKLIST = "Board_for_Checklists";
    public static final String NAME_OF_CHECKLIST_CREATED = "First Checklist";
    public static final String NEW_NAME_FOR_CHECKLIST = "New name for checklist";
    public static final String NAME_FOR_NEW_CHECKITEM = "Mark";

    private String boardId;
    private String cardId;
    private String checklistId;
    private String checkItemId;
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
