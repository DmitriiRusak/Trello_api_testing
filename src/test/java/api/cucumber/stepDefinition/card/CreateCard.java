package api.cucumber.stepDefinition.card;

import api.cucumber.continer.ConfigTestDataHolder;
import api.resourcesForTests.configurationData.CommonConfigData;
import api.services.ServiceWorkShop;
import api.resourcesForTests.PathParameters;
import io.cucumber.java.en.*;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

public class CreateCard extends ServiceWorkShop {

    private ConfigTestDataHolder configTestDataHolder;

    public CreateCard(ConfigTestDataHolder configTestDataHolder) {
        this.configTestDataHolder = configTestDataHolder;
    }

    @When("I create a card using {string} option with value {string}")
    public void iCreateACardUsingOptionWithValue(String optionName, String optionValue) {
        Map<String, String> queryParametersForRequestSpec = new HashMap<>();
        queryParametersForRequestSpec.put("idList", configTestDataHolder.getBoardTestData().getToDoListId());
        queryParametersForRequestSpec.put(optionName, optionValue);

        configTestDataHolder.getCardTestData().setCardId(getBoardService().createACard(queryParametersForRequestSpec).jsonPath().getString("id"));
    }

    @When("I create a card using {string} option with value {string} on a list with name {string}")
    public void iCreateACardUsingOptionWithValueOnAListWithName(String optionName, String optionValue, String listName) {

        String idOfAList = getListsService().getIdOfAListByName(
                listName,
                configTestDataHolder.getBoardTestData().getBoardId(),
                configTestDataHolder.getListTestData());

        Map<String, String> queryParametersForRequestSpec = new HashMap<>();
        queryParametersForRequestSpec.put("idList", idOfAList);
        queryParametersForRequestSpec.put(optionName, optionValue);
        getCardsService().createACard(queryParametersForRequestSpec);
    }

    @Then("A card is created")
    public void a_card_is_created() {

        String idOfTheCardThatWasRecivedAfterCardIsCreated = configTestDataHolder.getCardTestData().getCardId();

        String cardIdPresentedOnAToDoList = getListsService().getResourcesOfAList(configTestDataHolder.getListTestData().getToDoListId(),
                PathParameters.endPoints.get("card")).jsonPath().getString("id");

        cardIdPresentedOnAToDoList = cardIdPresentedOnAToDoList.substring(1, cardIdPresentedOnAToDoList.length()-1);

        Assert.assertEquals(idOfTheCardThatWasRecivedAfterCardIsCreated, cardIdPresentedOnAToDoList);

    }

    @And("a card has {string} option set wth value {string}")
    public void aCardHasOptionSetWthValue(String optionName, String expectedOptionValue) {
        configTestDataHolder.getCommonConfigData().setCommonResponseBetweenSteps(getCardsService().getCard(configTestDataHolder.getCardTestData().getCardId()));
        String actualOptionValue = (configTestDataHolder.getCommonConfigData().getCommonResponseBetweenSteps().jsonPath().getString(optionName));

        Assert.assertEquals(actualOptionValue, expectedOptionValue);

    }
}
