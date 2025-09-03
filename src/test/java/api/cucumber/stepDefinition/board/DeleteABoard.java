package api.cucumber.stepDefinition.board;

import api.resourcesForTests.configurationData.CycymberConfigTestData;
import api.services.BoardService;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class DeleteABoard{

    private CycymberConfigTestData cycymberConfigTestData;
    private BoardService boardService;

    public DeleteABoard(CycymberConfigTestData cycymberConfigTestData, BoardService boardService) {
        this.cycymberConfigTestData = cycymberConfigTestData;
        this.boardService = boardService;
    }

    @When("I delete the board")
    public void i_delete_the_board() {
        boardService.deleteBoard(cycymberConfigTestData.getBoardId());
    }

    @Then("Board is deleted")
    public void board_is_deleted() {
        cycymberConfigTestData.setCommonResponse(boardService.getBoard(cycymberConfigTestData.getBoardId()));
        Assert.assertEquals(cycymberConfigTestData.getCommonResponse().asPrettyString(), "The requested resource was not found.");
    }
}
