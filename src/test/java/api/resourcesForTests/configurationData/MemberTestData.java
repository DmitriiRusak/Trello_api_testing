package api.resourcesForTests.configurationData;

public class MemberTestData {

    private final String BOARD_NAME_FOR_MEMBERS = "Board_for_Members";
    private final String BOARD_STAR_NOT_FOUND_MESSAGE = "Board star not found";
    private final String POSITION = "top";
    private final String UPDATE_POSITION = "bottom";

    private String firstMemberId;
    private String backgroundId;
    private String starId;
    private String boardId;

    public String getBOARD_NAME_FOR_MEMBERS() {
        return BOARD_NAME_FOR_MEMBERS;
    }

    public String getBOARD_STAR_NOT_FOUND_MESSAGE() {
        return BOARD_STAR_NOT_FOUND_MESSAGE;
    }

    public String getPOSITION() {
        return POSITION;
    }

    public String getUPDATE_POSITION() {
        return UPDATE_POSITION;
    }

    public String getFirstMemberId() {
        return firstMemberId;
    }

    public void setFirstMemberId(String firstMemberId) {
        this.firstMemberId = firstMemberId;
    }

    public String getBackgroundId() {
        return backgroundId;
    }

    public void setBackgroundId(String backgroundId) {
        this.backgroundId = backgroundId;
    }

    public String getStarId() {
        return starId;
    }

    public void setStarId(String starId) {
        this.starId = starId;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }
}
