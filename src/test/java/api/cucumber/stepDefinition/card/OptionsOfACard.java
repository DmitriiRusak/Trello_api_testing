package api.cucumber.stepDefinition.card;

import api.resourcesForTests.configurationData.CycymberConfigTestData;
import api.services.CardsService;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.testng.Assert;

public class OptionsOfACard{

    private CycymberConfigTestData cycymberConfigTestData;
    private CardsService cardsService;

    public OptionsOfACard(CycymberConfigTestData cycymberConfigTestData, CardsService cardsService) {
        this.cycymberConfigTestData = cycymberConfigTestData;
        this.cardsService = cardsService;
    }

    @When("I request the cards information")
    public void i_request_the_cards_information() {

        cycymberConfigTestData.setCommonResponse(cardsService.
                getCard(cycymberConfigTestData.getCardId()));
    }

    @When("I change {string} of a card to {string}")
    public void iChangeOfACardTo(String optionName, String newOptionValue) {
        cardsService.updateCard(cycymberConfigTestData.getCardId(),
                optionName,
                newOptionValue);
    }

    @When("I move the card to the next list")
    public void iMoveTheCardToTheNextList() {

        String nextListId = "";

        for(int i = 0; i < cycymberConfigTestData.getIdsOfAllListsPresentedOnABoard().size()-1; i++){
            if(cycymberConfigTestData.getIdsOfAllListsPresentedOnABoard().get(i).equals(cycymberConfigTestData.getCurrentListId())
                && i+1 <= cycymberConfigTestData.getIdsOfAllListsPresentedOnABoard().size()-1){
                    nextListId = (String) cycymberConfigTestData.getIdsOfAllListsPresentedOnABoard().get((i+1));
                    cycymberConfigTestData.setCurrentListId(nextListId);
                    break;
            }
        }

        cycymberConfigTestData.setCommonResponse(cardsService.updateCard(cycymberConfigTestData.getCardId(), "idList", nextListId));

    }

    @Then("I got back requested option {string} for card")
    public void iGotBackRequestedOptionFor(String fieldName) {

        String expectedOptionValue = cycymberConfigTestData.getCommonResponse().jsonPath().getString(fieldName);

        String actualOptionValue = cardsService.getCard(cycymberConfigTestData.getCardId()).jsonPath().getString(fieldName);

        Assert.assertEquals(actualOptionValue, expectedOptionValue);

    }

    @When("I change color of a card to {string}")
    public void iChangeColorOfACardTo(String newColorForCard) {
        cycymberConfigTestData.setCommonResponse(cardsService.
                updateColorOfACard(cycymberConfigTestData.getCardId(), newColorForCard));
    }

    @Then("The color of a card changed to {string}")
    public void theColorOfACardChangedTo(String newcolorOfACard) {

        String currentColorOfACard = cardsService.getCard(cycymberConfigTestData.getCardId()).jsonPath().getString("cover.color");

        Assert.assertEquals(currentColorOfACard, newcolorOfACard);

    }

    @Then("I see the card is being moved to the next list")
    public void iSeeTheCardIsBeingMovedToTheNextList() {
        Response response = cardsService.getCard(cycymberConfigTestData.getCardId());

//        Assert.assertEquals(CommonConfigData.CardsTestData.currentListId, CommonConfigData.CardsTestData.listIdTheCardIsOn);
        Assert.assertNotEquals(cycymberConfigTestData.getListIdTheCardIsOn(), cycymberConfigTestData.getCurrentListId());
        cycymberConfigTestData.setListIdTheCardIsOn(cycymberConfigTestData.getCurrentListId());
    }
}
