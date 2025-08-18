package api.resourcesForTests.configurationData;

public class MemberTestData {

    public static final String BOARD_NAME_FOR_MEMBERS = "Board_for_Members";
    public static final String BOARD_STAR_NOT_FOUND_MESSAGE = "Board star not found";
    public static final String POSITION = "top";
    public static final String UPDATE_POSITION = "bottom";

    private String firstMemberId;
    private String backgroundId;
    private String starId;
    private String boardId;

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
