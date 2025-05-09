package api.cucumber.steps;

import api.base.TestData;
import api.controllers.BoardSteps;
import api.controllers.CardsSteps;
import api.controllers.ListsSteps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

public class SmokeStepDefinitions extends BoardSteps {
    private static final Logger logger = LogManager.getLogger(SmokeStepDefinitions.class);

    BoardSteps boardSteps = new BoardSteps();
    ListsSteps listsSteps = new ListsSteps();
    CardsSteps cardsSteps = new CardsSteps();
    private String boardId;
    private Response responseBord;
    private  Response responseList;
    private Response responseCard;
    private String  newCreatedListId;
    private String cardId;

//    @board
    @Given("I have valid Trello API credentials")
    public void i_have_valid_trello_api_credentials() {
        logger.info("I have valid Trello API credentials");
    }



    @When("i create a new board with name {string}")
    public void i_create_a_new_board_with_name(String string) {
        responseBord = boardSteps.createBoard(string);
        boardId = responseBord.jsonPath().getString("id");

    }

    @Then("the response status code should be {string}")
    public void the_response_status_code_should_be(String status) {
        System.out.println(status);
        Assert.assertEquals(responseBord.getStatusCode(), 200);
    }

    @Then("the response should contain a valid board id")
    public void the_response_should_contain_a_valid_board_id() {
        Assert.assertTrue(!responseBord.jsonPath().getString("id").isEmpty());
    }

    @Then("i delete the created board")
    public void i_delete_the_created_board() {
        responseBord = boardSteps.deleteABoardFromService(boardId);
    }
   // @list

    @When("I create a list with name {string} on the board")
    public void i_create_a_list_with_name_on_the_board(String string) {
        responseList = listsSteps.createList(string, boardId);

    }
    @Then("the response should contain a valid list id")
    public void the_response_should_contain_a_valid_list_id() {


    }
    @Then("the list name should be {string}")
    public void the_list_name_should_be(String string) {
        Assert.assertEquals(responseList.getStatusCode(), 200);
        Assert.assertEquals(responseList.path("name"), string );

    }

//    @card

    @When("I have created a list named {string} on the board")
    public void i_have_created_a_list_named_on_the_board(String string) {

        Response response = listsSteps.createList(string, boardId);
        newCreatedListId = listsSteps.getIdOfTheFirstListOnABoard(boardId);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.path("name"), string);

    }
    @When("I create a card with name {string} on the list")
    public void i_create_a_card_with_name_on_the_list(String string) {
        Map<String, String> queryParametersForRequestSpec = new HashMap<>();
        queryParametersForRequestSpec.put("idList", TestData.CardsTestData.listId);

        responseCard = cardsSteps.createACard(queryParametersForRequestSpec);


    }
    @Then("the response should contain a valid card id")
    public void the_response_should_contain_a_valid_card_id() {
//        cardId = responseCard.jsonPath().get("id");
//        Assert.assertEquals(responseCard.getStatusCode(), 200);
    }
    @Then("the card name should be {string}")
    public void the_card_name_should_be(String string) {

    }





}
