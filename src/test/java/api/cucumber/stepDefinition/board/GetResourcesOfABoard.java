package api.cucumber.stepDefinition.board;

import api.base.BaseTest;
import api.base.TestData;
import io.cucumber.java.en.*;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

import static api.base.TestData.ListsTestData.toDoListId;

public class GetResourcesOfABoard extends BaseTest {

    @Given("I create a card on a list")
    public void i_create_a_card_on_a_list() {

        Map<String, String> queryParametersForRequestSpec = new HashMap<>();
        queryParametersForRequestSpec.put("idList", toDoListId);
        queryParametersForRequestSpec.put("name", "card created from Cucumber");

        getBoardService().createACard(queryParametersForRequestSpec);
    }

    @Then("I got back requested resource")
    public void i_got_back_requested_resource() {
        String requestedResourceId = TestData.commonResponseBetweenSteps.jsonPath().getString("id");
        System.out.println(requestedResourceId);
        Assert.assertTrue(!requestedResourceId.isEmpty());
    }

}
