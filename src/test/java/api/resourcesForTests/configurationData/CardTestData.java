package api.resourcesForTests.configurationData;

public class CardTestData {

    private final String BOARD_NAME_FOR_CARDS = "Board_for_Cards";
    private final String NAME_FOR_CHECKLIST_CREATED = "Checklist for CardsAPITest";
    private final String EMPTY_STRING = "[]";
    private final String EXPECTED_EMPTY_STRING_RESULT = "[:]";

    private String boardId;
    private String cardId;
    private String listIdTheCardIsOn;
    private String currentListId;
    private String createdAttachmentId;
    private String toDoListId;

    public String getEMPTY_STRING() {
        return EMPTY_STRING;
    }

    public String getBoardNameForCards() {
        return BOARD_NAME_FOR_CARDS;
    }

    public String getNAME_FOR_CHECKLIST_CREATED() {
        return NAME_FOR_CHECKLIST_CREATED;
    }

    public String getEXPECTED_EMPTY_STRING_RESULT() {
        return EXPECTED_EMPTY_STRING_RESULT;
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

    public String getListIdTheCardIsOn() {
        return listIdTheCardIsOn;
    }

    public void setListIdTheCardIsOn(String listIdTheCardIsOn) {
        this.listIdTheCardIsOn = listIdTheCardIsOn;
    }

    public String getCurrentListId() {
        return currentListId;
    }

    public void setCurrentListId(String currentListId) {
        this.currentListId = currentListId;
    }

    public String getCreatedAttachmentId() {
        return createdAttachmentId;
    }

    public void setCreatedAttachmentId(String createdAttachmentId) {
        this.createdAttachmentId = createdAttachmentId;
    }
}
