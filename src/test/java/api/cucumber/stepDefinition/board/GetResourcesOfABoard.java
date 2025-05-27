package api.cucumber.stepDefinition.board;

import api.base.BaseTest;
import api.base.TestData;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

import static api.base.TestData.ListsTestData.toDoListId;

public class GetResourcesOfABoard extends BaseTest {

    @Given("I create a card on a list")
    public void i_create_a_card_on_a_list() {
        TestData.ListsTestData.toDoListId = getCardsService().getIdOfTheFirstListOnABoard(TestData.BoardTestData.boardId);

        Map<String, String> queryParametersForRequestSpec = new HashMap<>();
        queryParametersForRequestSpec.put("idList", toDoListId);
        queryParametersForRequestSpec.put("name", "card created from Cucumber");

        getBoardService().createACard(queryParametersForRequestSpec);
    }

    @When("I do request to get {string} Of a board")
    public void i_do_request_to_get_of_a_board(String string) {
        Response response = getBoardService().getResourceOfABoard(TestData.BoardTestData.boardId, "/"+string);
        TestData.BoardTestData.universalListForResource = response.jsonPath().getList("id");
    }

    @Then("I got back requested resource")
    public void i_got_back_requested_resource() {
        Assert.assertTrue(!TestData.BoardTestData.universalListForResource.isEmpty());
    }

//    @Then("I got back lists that currently presented on a board")
//    public void i_got_back_lists_that_currently_presented_on_a_board() {
//        Assert.assertEquals(TestData.BoardTestData.universalListForResource.size(), 3);
//    }
//
//    @Then("I got back members that currently presented on a board")
//    public void i_got_back_members_that_currently_presented_on_a_board() {
//        Assert.assertEquals(TestData.BoardTestData.universalListForResource.size(), 1 );
//    }
//
//    @Then("I got back cards that currently presented on a board")
//    public void i_got_back_cards_that_currently_presented_on_a_board() {
//        Assert.assertEquals(TestData.BoardTestData.universalListForResource.size(),1);
//    }
//
//    @Then("I got back labels that currently presented on a board")
//    public void i_got_back_labels_that_currently_presented_on_a_board() {
//        Assert.assertEquals(TestData.BoardTestData.universalListForResource.size(), 6);
//    }

}
