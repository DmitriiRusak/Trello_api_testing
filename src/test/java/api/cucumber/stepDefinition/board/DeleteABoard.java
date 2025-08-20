//package api.cucumber.stepDefinition.board;
//
//import api.cucumber.continer.ConfigTestDataHolder;
//import api.resourcesForTests.configurationData.CommonConfigData;
//import api.services.ServiceWorkShop;
//import io.cucumber.java.en.*;
//import org.testng.Assert;
//
//public class DeleteABoard extends ServiceWorkShop {
//
//    private ConfigTestDataHolder configTestDataHolder;
//
//    public DeleteABoard(ConfigTestDataHolder configTestDataHolder) {
//        this.configTestDataHolder = configTestDataHolder;
//    }
//
//    @When("I delete the board")
//    public void i_delete_the_board() {
//        getBoardService().deleteBoard(configTestDataHolder.getBoardTestData().getBoardId(), getBoardService().getBoardRequestSpecification());
//    }
//
//    @Then("Board is deleted")
//    public void board_is_deleted() {
//        configTestDataHolder.getCommonConfigData().setCommonResponseBetweenSteps(getBoardService().getBoard(configTestDataHolder.getBoardTestData().getBoardId()));
//        Assert.assertEquals(configTestDataHolder.getCommonConfigData().getCommonResponseBetweenSteps().asPrettyString(), "The requested resource was not found.");
//    }
//}
