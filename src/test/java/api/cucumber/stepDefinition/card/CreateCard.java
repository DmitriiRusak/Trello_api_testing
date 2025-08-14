package api.cucumber.stepDefinition.card;

import api.resourcesForTests.ConfigurationDataForApiTests;
import api.services.ServiceWorkShop;
import api.resourcesForTests.PathParameters;
import io.cucumber.java.en.*;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

import static api.resourcesForTests.ConfigurationDataForApiTests.ListsTestData.toDoListId;

public class CreateCard extends ServiceWorkShop {

    @When("I create a card using {string} option with value {string}")
    public void iCreateACardUsingOptionWithValue(String optionName, String optionValue) {
        Map<String, String> queryParametersForRequestSpec = new HashMap<>();
        queryParametersForRequestSpec.put("idList", toDoListId);
        queryParametersForRequestSpec.put(optionName, optionValue);

        ConfigurationDataForApiTests.CardsTestData.cardId = getBoardService().createACard(queryParametersForRequestSpec).jsonPath().getString("id");
    }

    @When("I create a card using {string} option with value {string} on a list with name {string}")
    public void iCreateACardUsingOptionWithValueOnAListWithName(String optionName, String optionValue, String listName) {

        String idOfAList = getListsService().getIdOfAListByName(listName);

        Map<String, String> queryParametersForRequestSpec = new HashMap<>();
        queryParametersForRequestSpec.put("idList", idOfAList);
        queryParametersForRequestSpec.put(optionName, optionValue);
        getCardsService().createACard(queryParametersForRequestSpec);
    }

    @Then("A card is created")
    public void a_card_is_created() {

        String idOfTheCardThatWasRecivedAfterCardIsCreated = ConfigurationDataForApiTests.CardsTestData.cardId;

        String cardIdPresentedOnAToDoList = getListsService().getResourcesOfAList(ConfigurationDataForApiTests.ListsTestData.toDoListId,
                PathParameters.endPoints.get("card")).jsonPath().getString("id");

        cardIdPresentedOnAToDoList = cardIdPresentedOnAToDoList.substring(1, cardIdPresentedOnAToDoList.length()-1);

        Assert.assertEquals(idOfTheCardThatWasRecivedAfterCardIsCreated, cardIdPresentedOnAToDoList);

    }

    @And("a card has {string} option set wth value {string}")
    public void aCardHasOptionSetWthValue(String optionName, String expectedOptionValue) {
        ConfigurationDataForApiTests.commonResponseBetweenSteps = getCardsService().getCard(ConfigurationDataForApiTests.CardsTestData.cardId);
        String actualOptionValue = (ConfigurationDataForApiTests.commonResponseBetweenSteps.jsonPath().getString(optionName));

        Assert.assertEquals(actualOptionValue, expectedOptionValue);

    }
}
