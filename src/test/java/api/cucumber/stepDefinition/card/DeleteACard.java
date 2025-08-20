//package api.cucumber.stepDefinition.card;
//
//import api.cucumber.continer.ConfigTestDataHolder;
//import api.resourcesForTests.configurationData.CommonConfigData;
//import api.services.ServiceWorkShop;
//import io.cucumber.java.en.*;
//import org.testng.Assert;
//
//public class DeleteACard extends ServiceWorkShop {
//
//    private ConfigTestDataHolder configTestDataHolder;
//
//    public DeleteACard(ConfigTestDataHolder configTestDataHolder) {
//        this.configTestDataHolder = configTestDataHolder;
//    }
//
//    @When("I do delete a card")
//    public void i_do_delete_a_card() {
//       getCardsService().deleteACard(configTestDataHolder.getCardTestData().getCardId());
//    }
//
//    @Then("Card is deleted")
//    public void card_is_deleted() {
//
//        String actualMessage = getCardsService().getCard(configTestDataHolder.getCardTestData().getCardId()).body().asString();
//
//        String expectedResponseMessage = "The requested resource was not found.";
//
//        Assert.assertEquals(actualMessage, expectedResponseMessage);
//    }
//
//}
