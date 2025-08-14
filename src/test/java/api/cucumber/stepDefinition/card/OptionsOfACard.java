package api.cucumber.stepDefinition.card;

import api.resourcesForTests.ConfigurationDataForApiTests;
import io.cucumber.java.en.*;
import api.services.ServiceWorkShop;
import io.restassured.response.Response;
import org.testng.Assert;

public class OptionsOfACard extends ServiceWorkShop {

    @When("I request the cards information")
    public void i_request_the_cards_information() {

        ConfigurationDataForApiTests.commonResponseBetweenSteps = getCardsService().getCard(ConfigurationDataForApiTests.CardsTestData.cardId);
    }

    @When("I change {string} of a card to {string}")
    public void iChangeOfACardTo(String optionName, String newOptionValue) {
        getCardsService().updateCard(ConfigurationDataForApiTests.CardsTestData.cardId,
                optionName,
                newOptionValue);
    }

    @When("I move the card to the next list")
    public void iMoveTheCardToTheNextList() {

        String nextListId = "";

        for(int i = 0; i < ConfigurationDataForApiTests.ListsTestData.idsOfAllListsPresentedOnABoard.size()-1; i++){
            if(ConfigurationDataForApiTests.ListsTestData.idsOfAllListsPresentedOnABoard.get(i).equals(ConfigurationDataForApiTests.CardsTestData.currentListId)
                && i+1 <= ConfigurationDataForApiTests.ListsTestData.idsOfAllListsPresentedOnABoard.size()-1){
                nextListId = (String) ConfigurationDataForApiTests.ListsTestData.idsOfAllListsPresentedOnABoard.get((i+1));
                ConfigurationDataForApiTests.CardsTestData.currentListId = nextListId;
                break;
            }
        }

        ConfigurationDataForApiTests.commonResponseBetweenSteps = getCardsService().
                updateCard(ConfigurationDataForApiTests.CardsTestData.cardId, "idList", nextListId);

    }

    @Then("I got back requested option {string} for card")
    public void iGotBackRequestedOptionFor(String fieldName) {

        String expectedOptionValue = ConfigurationDataForApiTests.commonResponseBetweenSteps.jsonPath().getString(fieldName);

        String actualOptionValue = getCardsService().getCard(ConfigurationDataForApiTests.CardsTestData.cardId).jsonPath().getString(fieldName);

        Assert.assertEquals(actualOptionValue, expectedOptionValue);

    }

    @When("I change color of a card to {string}")
    public void iChangeColorOfACardTo(String newColorForCard) {
        ConfigurationDataForApiTests.commonResponseBetweenSteps = getCardsService().
                updateColorOfACard(ConfigurationDataForApiTests.CardsTestData.cardId, newColorForCard);
    }

    @Then("The color of a card changed to {string}")
    public void theColorOfACardChangedTo(String newcolorOfACard) {

        String currentColorOfACard = getCardsService().getCard(ConfigurationDataForApiTests.CardsTestData.cardId)
                                                        .jsonPath().getString("cover.color");

        Assert.assertEquals(currentColorOfACard, newcolorOfACard);

    }

    @Then("I see the card is being moved to the next list")
    public void iSeeTheCardIsBeingMovedToTheNextList() {
        Response response = getCardsService().getCard(ConfigurationDataForApiTests.CardsTestData.cardId);

//        Assert.assertEquals(ConfigurationDataForApiTests.CardsTestData.currentListId, ConfigurationDataForApiTests.CardsTestData.listIdTheCardIsOn);
        Assert.assertNotEquals(ConfigurationDataForApiTests.CardsTestData.listIdTheCardIsOn, ConfigurationDataForApiTests.CardsTestData.currentListId);
        ConfigurationDataForApiTests.CardsTestData.listIdTheCardIsOn = ConfigurationDataForApiTests.CardsTestData.currentListId;
    }
}
