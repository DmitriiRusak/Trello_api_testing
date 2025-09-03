package api.cucumber.stepDefinition.card;


import api.resourcesForTests.configurationData.CycymberConfigTestData;
import api.services.CardsService;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class DeleteACard {

    private CycymberConfigTestData cycymberConfigTestData;
    private CardsService cardsService;

    public DeleteACard(CycymberConfigTestData cycymberConfigTestData, CardsService cardsService) {
        this.cycymberConfigTestData = cycymberConfigTestData;
        this.cardsService = cardsService;
    }

    @When("I do delete a card")
    public void i_do_delete_a_card() {
        cardsService.deleteACard(cycymberConfigTestData.getCardId());
    }

    @Then("Card is deleted")
    public void card_is_deleted() {

        String actualMessage = cardsService.getCard(cycymberConfigTestData.getCardId()).body().asString();

        String expectedResponseMessage = "The requested resource was not found.";

        Assert.assertEquals(actualMessage, expectedResponseMessage);
    }

}
