package api.cucumber.stepDefinition.board;

import api.cucumber.continer.ConfigTestDataHolder;
import api.resourcesForTests.configurationData.CommonConfigData;
import api.services.ServiceWorkShop;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;


public class GetResourcesOfABoard extends ServiceWorkShop {

    private ConfigTestDataHolder configTestDataHolder;

    @Given("I create a card on a list")
    public void i_create_a_card_on_a_list() {

        Map<String, String> queryParametersForRequestSpec = new HashMap<>();
        queryParametersForRequestSpec.put("idList", configTestDataHolder.getListTestData().getToDoListId());
        queryParametersForRequestSpec.put("name", "card created from Cucumber");

        Response response = getBoardService().createACard(queryParametersForRequestSpec);

        configTestDataHolder.getCardTestData().setCardId(response.jsonPath().getString("id"));
        configTestDataHolder.getCardTestData().setListIdTheCardIsOn(response.jsonPath().getString("idList"));
        configTestDataHolder.getCardTestData().setCurrentListId(response.jsonPath().getString("idList"));

    }

//    Method gatAvailableBoard() will retern all the id`s of boards on a workspace.
//    But the logic is to Initialize only one variable. So if there are more than one board on a workspace the variable
//    boardId will consist all id`s returned back
//    So method will work correctly only if one board is presented. If more than 1 board than variable board id
//    might look like 34242342523, 23435235235

    @When("I request to get available board")
    public void iRequestToGetAvailableBoard() {
        configTestDataHolder.getBoardTestData().setBoardId(getBoardService().getAllAvailableBoards());
    }

    @Then("I got back requested resource")
    public void i_got_back_requested_resource() {
        String requestedResourceId = configTestDataHolder.getCommonConfigData().getCommonResponseBetweenSteps().jsonPath().getString("id");

        Assert.assertTrue(!requestedResourceId.isEmpty());
        Assert.assertTrue(requestedResourceId.length()>3);

    }

    @Then("I got back available board")
    public void iGotBackAvailableBoard() {

        Assert.assertFalse(configTestDataHolder.getBoardTestData().getBoardId().isEmpty());
    }
}
