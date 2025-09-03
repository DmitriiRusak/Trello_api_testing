package api.cucumber.stepDefinition.card;

import api.resourcesForTests.configurationData.CycymberConfigTestData;
import api.resourcesForTests.PathParameters;
import api.services.CardsService;
import api.services.ListsService;
import io.cucumber.java.en.*;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

public class CreateCard{

    private CycymberConfigTestData cycymberConfigTestData;
    private ListsService listsService;
    private CardsService cardsService;

    public CreateCard(CardsService cardsService, CycymberConfigTestData cycymberConfigTestData, ListsService listsService) {
        this.cardsService = cardsService;
        this.cycymberConfigTestData = cycymberConfigTestData;
        this.listsService = listsService;
    }

    @When("I create a card using {string} option with value {string}")
    public void iCreateACardUsingOptionWithValue(String optionName, String optionValue) {

        Map<String, String> queryParametersForRequestSpec = new HashMap<>();
        queryParametersForRequestSpec.put("idList", cycymberConfigTestData.getToDoListId());
        queryParametersForRequestSpec.put(optionName, optionValue);

        cycymberConfigTestData.setCardId(cardsService.createACard(queryParametersForRequestSpec).jsonPath().getString("id"));
    }

    @When("I create a card using {string} option with value {string} on a list with name {string}")
    public void iCreateACardUsingOptionWithValueOnAListWithName(String optionName, String optionValue, String listName) {

        String idOfAList = listsService.getIdOfAListByName(
                listName,
                cycymberConfigTestData.getBoardId(),
                cycymberConfigTestData);

        Map<String, String> queryParametersForRequestSpec = new HashMap<>();
        queryParametersForRequestSpec.put("idList", idOfAList);
        queryParametersForRequestSpec.put(optionName, optionValue);
        cardsService.createACard(queryParametersForRequestSpec);
    }

    @Then("A card is created")
    public void a_card_is_created() {

        String idOfTheCardThatWasRecivedAfterCardIsCreated = cycymberConfigTestData.getCardId();

        String cardIdPresentedOnAToDoList = listsService.getResourcesOfAList(cycymberConfigTestData.getToDoListId(),
                PathParameters.endPoints.get("card")).
                jsonPath().getString("id");

        cardIdPresentedOnAToDoList = cardIdPresentedOnAToDoList.substring(1, cardIdPresentedOnAToDoList.length()-1);

        Assert.assertEquals(idOfTheCardThatWasRecivedAfterCardIsCreated, cardIdPresentedOnAToDoList);

    }

    @And("a card has {string} option set wth value {string}")
    public void aCardHasOptionSetWthValue(String optionName, String expectedOptionValue) {
        cycymberConfigTestData.setCommonResponse(cardsService.getCard(cycymberConfigTestData.getCardId()));
        String actualOptionValue = (cycymberConfigTestData.getCommonResponse().jsonPath().getString(optionName));

        Assert.assertEquals(actualOptionValue, expectedOptionValue);

    }
}
