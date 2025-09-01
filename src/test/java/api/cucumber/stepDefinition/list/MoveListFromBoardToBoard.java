package api.cucumber.stepDefinition.list;

import api.cucumber.continer.ConfigTestDataHolder;
import api.resourcesForTests.CycymberConfigTestData;
import api.services.BoardService;
import api.services.ListsService;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class MoveListFromBoardToBoard{

    private CycymberConfigTestData cycymberConfigTestData;
    private ListsService listsService;
    private BoardService boardService;

    public MoveListFromBoardToBoard(CycymberConfigTestData cycymberConfigTestData, ListsService listsService, BoardService boardService) {
        this.cycymberConfigTestData = cycymberConfigTestData;
        this.listsService = listsService;
        this.boardService = boardService;
    }

    @And("I create one more board")
    public void iCreateOneMoreBoard() {
        cycymberConfigTestData.setSecondBoardId(
                boardService.createBoard("One more board").jsonPath().getString("id"));
    }

    @When("I move to do list, from one board, to another")
    public void iMoveToDoListFromOneBoardToAnother() {
        cycymberConfigTestData.setCommonResponse(
                listsService.moveListFromOneBoardToAnother(
                        cycymberConfigTestData.getToDoListId(),
                        cycymberConfigTestData.getSecondBoardId()));
    }

    @Then("I see to do list being moved from one board, to another")
    public void iSeeToDoListBeingMovedFromOneBoardToAnother() {
        String boardIdTheListisOn = listsService.getABoardAListIsOn(cycymberConfigTestData.getToDoListId()).jsonPath().getString("id");

        Assert.assertEquals(cycymberConfigTestData.getSecondBoardId(), boardIdTheListisOn);
    }

    @And("I delete the additional board")
    public void iDeleteTheAdditionalBoard() {
        boardService.deleteBoard(cycymberConfigTestData.getSecondBoardId());
    }
}
