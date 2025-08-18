package api.cucumber.stepDefinition.card;

import api.cucumber.continer.ConfigTestDataHolder;
import api.resourcesForTests.configurationData.CommonConfigData;
import io.cucumber.java.en.*;
import api.services.ServiceWorkShop;
import io.restassured.response.Response;
import org.testng.Assert;

public class OptionsOfACard extends ServiceWorkShop {

    private ConfigTestDataHolder configTestDataHolder;

    public OptionsOfACard(ConfigTestDataHolder configTestDataHolder) {
        this.configTestDataHolder = configTestDataHolder;
    }

    @When("I request the cards information")
    public void i_request_the_cards_information() {

        configTestDataHolder.getCommonConfigData().setCommonResponseBetweenSteps(getCardsService().
                getCard(configTestDataHolder.getCardTestData().getCardId()));
    }

    @When("I change {string} of a card to {string}")
    public void iChangeOfACardTo(String optionName, String newOptionValue) {
        getCardsService().updateCard(configTestDataHolder.getCardTestData().getCardId(),
                optionName,
                newOptionValue);
    }

    @When("I move the card to the next list")
    public void iMoveTheCardToTheNextList() {

        String nextListId = "";

        for(int i = 0; i < configTestDataHolder.getListTestData().getIdsOfAllListsPresentedOnABoard().size()-1; i++){
            if(configTestDataHolder.getListTestData().getIdsOfAllListsPresentedOnABoard().get(i).equals(configTestDataHolder.getCardTestData().getCurrentListId())
                && i+1 <= configTestDataHolder.getListTestData().getIdsOfAllListsPresentedOnABoard().size()-1){
                nextListId = (String) configTestDataHolder.getListTestData().getIdsOfAllListsPresentedOnABoard().get((i+1));
                configTestDataHolder.getCardTestData().setCurrentListId(nextListId);
                break;
            }
        }

        configTestDataHolder.getCommonConfigData().setCommonResponseBetweenSteps(getCardsService().
                updateCard(configTestDataHolder.getCardTestData().getCardId(), "idList", nextListId));

    }

    @Then("I got back requested option {string} for card")
    public void iGotBackRequestedOptionFor(String fieldName) {

        String expectedOptionValue = configTestDataHolder.getCommonConfigData().getCommonResponseBetweenSteps().jsonPath().getString(fieldName);

        String actualOptionValue = getCardsService().getCard(configTestDataHolder.getCardTestData().getCardId()).jsonPath().getString(fieldName);

        Assert.assertEquals(actualOptionValue, expectedOptionValue);

    }

    @When("I change color of a card to {string}")
    public void iChangeColorOfACardTo(String newColorForCard) {
        configTestDataHolder.getCommonConfigData().setCommonResponseBetweenSteps(getCardsService().
                updateColorOfACard(configTestDataHolder.getCardTestData().getCardId(), newColorForCard));
    }

    @Then("The color of a card changed to {string}")
    public void theColorOfACardChangedTo(String newcolorOfACard) {

        String currentColorOfACard = getCardsService().getCard(configTestDataHolder.getCardTestData().getCardId())
                                                        .jsonPath().getString("cover.color");

        Assert.assertEquals(currentColorOfACard, newcolorOfACard);

    }

    @Then("I see the card is being moved to the next list")
    public void iSeeTheCardIsBeingMovedToTheNextList() {
        Response response = getCardsService().getCard(configTestDataHolder.getCardTestData().getCardId());

//        Assert.assertEquals(CommonConfigData.CardsTestData.currentListId, CommonConfigData.CardsTestData.listIdTheCardIsOn);
        Assert.assertNotEquals(configTestDataHolder.getCardTestData().getListIdTheCardIsOn(), configTestDataHolder.getCardTestData().getCurrentListId());
        configTestDataHolder.getCardTestData().setListIdTheCardIsOn(configTestDataHolder.getCardTestData().getCurrentListId());
    }
}
