package api.cucumber.stepDefinition.board;

import api.resourcesForTests.CycymberConfigTestData;
import api.services.BoardService;
import api.utils.LogFactory;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;

public class CreateBoardStepDefinition{

    private CycymberConfigTestData cycymberConfigTestData;
    private BoardService boardService;

    public CreateBoardStepDefinition(CycymberConfigTestData cycymberConfigTestData, BoardService boardService) {
        this.cycymberConfigTestData = cycymberConfigTestData;
        this.boardService = boardService;
    }

    @Given("I am registered user in the Trello app")
    public void i_am_registered_user_in_the_trello_app() {
    }

    @When("I create a board with default options")
    public void i_create_a_board_with_default_options() {

        Response response = boardService.createBoard(cycymberConfigTestData.getBOARD_NAME());
        cycymberConfigTestData.setBoardId(response.jsonPath().getString("id"));

        cycymberConfigTestData.setToDoListId(
                boardService.getListOfIdOfAllListsOnABoard(
                        cycymberConfigTestData.getBoardId()). get(0).toString());

        cycymberConfigTestData.setIdsOfAllListsPresentedOnABoard(boardService.getListOfIdOfAllListsOnABoard(cycymberConfigTestData.getBoardId()));
    }

    @When("I create a board using {string} option with value {string}")
    public void i_create_a_board_using_option_with_value(String optionName, String optionValue) {

        cycymberConfigTestData.setBoardId(boardService.createABoardWithSpecificResource(cycymberConfigTestData.getBOARD_NAME_CREATED_WITH_SPECIFIC_OPTIONS(),
                        optionName, optionValue).jsonPath().getString("id"));
    }

    @Then("A board is created")
    public void a_board_is_created() {

        String actualBoardId = boardService.getBoard(cycymberConfigTestData.getBoardId()).jsonPath().getString("id");

        Assert.assertEquals(actualBoardId, cycymberConfigTestData.getBoardId());
    }

    @Then("{int} number of lists presented on the board")
    public void number_of_lists_presented_on_the_board(Integer expectedNumberOfLists) {

        Response response = boardService.getListsOfABoard(cycymberConfigTestData.getBoardId());
        List<String> listsOnABoards = response.jsonPath().getList(".");

        int numberOfListsPresentedOnABoard = listsOnABoards.size();

        Assert.assertEquals(numberOfListsPresentedOnABoard, expectedNumberOfLists);
    }

    @And("a board has {string} option set wth value {string}")
    public void a_board_has_option_set_wth_value(String optionName, String newOptionValue) {

        if(optionName.contains("_") || optionName.contains("/")){
            optionName = optionName.replace('_', '.');
            optionName=optionName.replace('/', '.');
        }

        Response response = boardService.getBoard(cycymberConfigTestData.getBoardId());
        String currentOptionValue = response.jsonPath().getString(optionName);

        Assert.assertEquals(currentOptionValue, newOptionValue);
    }

}