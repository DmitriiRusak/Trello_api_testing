package api.cucumber.stepDefinition.board;

import api.cucumber.continer.ConfigTestDataHolder;
import api.resourcesForTests.configurationData.CommonConfigData;
import api.services.ServiceWorkShop;
import api.utils.LogFactory;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;

public class CreateBoardStepDefinition extends ServiceWorkShop {

    private ConfigTestDataHolder configTestDataHolder;

    public CreateBoardStepDefinition(ConfigTestDataHolder configTestDataHolder) {
        this.configTestDataHolder = configTestDataHolder;
    }

    @Given("I am registered user in the Trello app")
    public void i_am_registered_user_in_the_trello_app() {
        LogFactory.getLogger().info("User registration in the system");
    }

    @When("I create a board with default options")
    public void i_create_a_board_with_default_options() {

        Response response = getBoardService().createBoard(configTestDataHolder.getBoardTestData().BOARD_NAME);
        configTestDataHolder.getBoardTestData().setBoardId(response.jsonPath().getString("id"));

        configTestDataHolder.getBoardTestData().setToDoListId(getBoardService().getListOfIdOfAllListsOnABoard(configTestDataHolder.getBoardTestData().getBoardId()).get(0).toString());
        configTestDataHolder.getListTestData().setIdsOfAllListsPresentedOnABoard(getBoardService().getListOfIdOfAllListsOnABoard(configTestDataHolder.getBoardTestData().getBoardId()));
    }

    @When("I create a board using {string} option with value {string}")
    public void i_create_a_board_using_option_with_value(String optionName, String optionValue) {

        configTestDataHolder.getBoardTestData().setBoardId(getBoardService().
                createABoardWithSpecificResource(configTestDataHolder.getBoardTestData().BOARD_NAME_CREATED_WITH_SPECIFIC_OPTIONS,
                        optionName, optionValue).jsonPath().getString("id"));
    }

    @Then("A board is created")
    public void a_board_is_created() {

        String actualBoardId = getBoardService().getBoard(configTestDataHolder.getBoardTestData().getBoardId()).jsonPath().getString("id");

        Assert.assertEquals(actualBoardId, configTestDataHolder.getBoardTestData().getBoardId());
    }

    @Then("{int} number of lists presented on the board")
    public void number_of_lists_presented_on_the_board(Integer expectedNumberOfLists) {

        Response response = getBoardService().getListsOfABoard(configTestDataHolder.getBoardTestData().getBoardId());
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

        Response response = getBoardService().getBoard(configTestDataHolder.getBoardTestData().getBoardId());
        String currentOptionValue = response.jsonPath().getString(optionName);

        Assert.assertEquals(currentOptionValue, newOptionValue);
    }

}