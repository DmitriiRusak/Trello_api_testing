package api.resourcesForTests.configurationData;

public class LabelTestData {

    public static final String BOARD_NAME = "Board_for_Labels";
    public static final String LABEL_NAME = "Label from API";
    public static final String COLOR = "red";
    public static final String NEW_NAME = "New Label from API";
    public static final String NEW_COLOR = "blue";

    private String labelId;
    private String boardId;

    public String getLabelId() {
        return labelId;
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
