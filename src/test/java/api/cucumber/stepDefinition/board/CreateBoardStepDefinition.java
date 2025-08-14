package api.cucumber.stepDefinition.board;

import api.resourcesForTests.ConfigurationDataForApiTests;
import api.services.ServiceWorkShop;
import api.utils.LogFactory;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;

public class CreateBoardStepDefinition extends ServiceWorkShop {

    @Given("I am registered user in the Trello app")
    public void i_am_registered_user_in_the_trello_app() {
        LogFactory.getLogger().info("User registration in the system");
    }

    @When("I create a board with default options")
    public void i_create_a_board_with_default_options() {

        Response response = getBoardService().createBoard(ConfigurationDataForApiTests.BoardTestData.BOARD_NAME);
        ConfigurationDataForApiTests.BoardTestData.boardId = response.jsonPath().getString("id");

        ConfigurationDataForApiTests.ListsTestData.toDoListId = getBoardService().getListOfIdOfAllListsOnABoard(ConfigurationDataForApiTests.BoardTestData.boardId).get(0).toString();
        ConfigurationDataForApiTests.ListsTestData.idsOfAllListsPresentedOnABoard = getBoardService().getListOfIdOfAllListsOnABoard(ConfigurationDataForApiTests.BoardTestData.boardId);
    }

    @When("I create a board using {string} option with value {string}")
    public void i_create_a_board_using_option_with_value(String optionName, String optionValue) {

        ConfigurationDataForApiTests.BoardTestData.boardId = getBoardService().
                createABoardWithSpecificResource(ConfigurationDataForApiTests.BoardTestData.BOARD_NAME_CREATED_WITH_SPECIFIC_OPTIONS,
                        optionName, optionValue).jsonPath().getString("id");
    }

    @Then("A board is created")
    public void a_board_is_created() {

        String actualBoardId = getBoardService().getBoard(ConfigurationDataForApiTests.BoardTestData.boardId).jsonPath().getString("id");

        Assert.assertEquals(actualBoardId, ConfigurationDataForApiTests.BoardTestData.boardId);
    }

    @Then("{int} number of lists presented on the board")
    public void number_of_lists_presented_on_the_board(Integer expectedNumberOfLists) {
        Response response = getBoardService().getListsOfABoard(ConfigurationDataForApiTests.BoardTestData.boardId);
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

        Response response = getBoardService().getBoard(ConfigurationDataForApiTests.BoardTestData.boardId);
        String currentOptionValue = response.jsonPath().getString(optionName);

        Assert.assertEquals(currentOptionValue, newOptionValue);
    }

}