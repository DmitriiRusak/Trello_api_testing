package api.resourcesForTests.configurationData;

public class LabelTestData {

    private final String BOARD_NAME = "Board_for_Labels";
    private final String LABEL_NAME = "Label from API";
    private final String COLOR = "red";
    private final String NEW_NAME = "New Label from API";
    private final String NEW_COLOR = "blue";

    private String labelId;
    private String boardId;

    public String getLabelId() {
        return labelId;
    }

    public String getNEW_COLOR() {
        return NEW_COLOR;
    }

    public String getBOARD_NAME() {
        return BOARD_NAME;
    }

    public String getNEW_NAME() {
        return NEW_NAME;
    }

    public String getCOLOR() {
        return COLOR;
    }

    public String getLABEL_NAME() {
        return LABEL_NAME;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }
}
