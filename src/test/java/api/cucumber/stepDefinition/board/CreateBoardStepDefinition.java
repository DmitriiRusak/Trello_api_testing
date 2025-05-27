package api.cucumber.stepDefinition.board;
import api.base.BaseTest;
import api.base.TestData;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;

public class CreateBoardStepDefinition extends BaseTest {

    @Given("I am registered user in the Trello app")
    public void i_am_registered_user_in_the_trello_app() {

    }

    @When("I create a board with default options")
    public void i_create_a_board_with_default_options() {

        Response response = getBoardService().createBoard(TestData.BoardTestData.BOARD_NAME);
        TestData.BoardTestData.boardId = response.jsonPath().getString("id");

    }

    @When("I create a board using {string} option with value {string}")
    public void i_create_a_board_using_option_with_value(String option, String optionValue) {
                Response response = getBoardService().createABoardWithSpecificResource
                (TestData.BoardTestData.BOARD_NAME_CREATED_WITH_SPECIFIC_OPTIONS, option, optionValue);
        TestData.BoardTestData.boardId = response.jsonPath().getString("id");
    }

    @Then("A board is created")
    public void a_board_is_created() {

        String actualBoardId = getBoardService().getBoard(TestData.BoardTestData.boardId).jsonPath().getString("id");

        Assert.assertEquals(actualBoardId, TestData.BoardTestData.boardId);

    }

    @Then("{int} number of lists presented on the board")
    public void number_of_lists_presented_on_the_board(Integer expectedNumberOfLists) {
        Response response = getBoardService().getListsOfABoard(TestData.BoardTestData.boardId);
        List<String> listsOnABoards = response.jsonPath().getList(".");

        int numberOfListsPresentedOnABoard = listsOnABoards.size();

        Assert.assertEquals(numberOfListsPresentedOnABoard, expectedNumberOfLists);
    }

    @Then("a board has {string} option set wth value {string}")
    public void a_board_has_option_set_wth_value(String string, String string2) {
                if(string.contains("_")){
            string=string.replace('_', '.');
        }
        Response response = getBoardService().getBoard(TestData.BoardTestData.boardId);
        String resourceValue = response.jsonPath().getString(string);

        Assert.assertEquals(resourceValue, string2);
    }

    @Then("Since the scenario is for testing purpose only I delete the board to keep workspace clean")
    public void since_the_scenario_is_for_testing_purpose_only_i_delete_the_board_to_keep_workspace_clean() {
        getBoardService().deleteBoard(TestData.BoardTestData.boardId);
    }

}