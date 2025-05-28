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
        TestData.BoardTestData.commonResponseBetweenSteps = getBoardService().getResourceOfABoard(TestData.BoardTestData.boardId, "/"+string);
    }

    @Then("I got back requested resource")
    public void i_got_back_requested_resource() {
        TestData.BoardTestData.universalListForResource = TestData.BoardTestData.commonResponseBetweenSteps.jsonPath().getList("id");
        Assert.assertTrue(!TestData.BoardTestData.universalListForResource.isEmpty());
    }

}
