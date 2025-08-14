package api.cucumber.stepDefinition.card;

import api.resourcesForTests.ConfigurationDataForApiTests;
import api.services.ServiceWorkShop;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class DeleteACard extends ServiceWorkShop {

    @When("I do delete a card")
    public void i_do_delete_a_card() {
       getCardsService().deleteACard(ConfigurationDataForApiTests.CardsTestData.cardId);
    }

    @Then("Card is deleted")
    public void card_is_deleted() {

        String actualMessage = getCardsService().getCard(ConfigurationDataForApiTests.CardsTestData.cardId).body().asString();

        String expectedResponseMessage = "The requested resource was not found.";

        Assert.assertEquals(actualMessage, expectedResponseMessage);
    }

}
