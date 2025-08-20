//package api.cucumber.stepDefinition.list;
//
//import api.cucumber.continer.ConfigTestDataHolder;
//import api.resourcesForTests.configurationData.CommonConfigData;
//import api.services.ServiceWorkShop;
//import io.cucumber.java.en.*;
//import org.testng.Assert;
//
//public class ArchiveOrUnArchiveAList extends ServiceWorkShop {
//
//    private ConfigTestDataHolder configTestDataHolder;
//
//    public ArchiveOrUnArchiveAList(ConfigTestDataHolder configTestDataHolder) {
//        this.configTestDataHolder = configTestDataHolder;
//    }
//
//    @When("I archive a list")
//    public void iArchiveAList() {
//        configTestDataHolder.getCommonConfigData().setCommonResponseBetweenSteps(
//                getListsService().archiveAList(configTestDataHolder.getListTestData().getToDoListId()));
//    }
//
//    @And("Un archive a list")
//    public void unArchiveAList() {
//        configTestDataHolder.getCommonConfigData().setCommonResponseBetweenSteps(
//                getListsService().unArchiveAList(configTestDataHolder.getListTestData().getToDoListId()));
//    }
//
//    @Then("A list is archived")
//    public void aListIsArchived() {
//        String actualListUrchiveStatus = getListsService().getAList(configTestDataHolder.getListTestData().getToDoListId()).
//                jsonPath().getString("closed");
//
//        Assert.assertEquals(actualListUrchiveStatus, "true");
//    }
//
//    @Then("A list is presented on a board")
//    public void aListIsPresentedOnABoard() {
//        String actualListUrchiveStatus = getListsService().getAList(configTestDataHolder.getListTestData().getToDoListId()).jsonPath().getString("closed");
//
//        Assert.assertEquals(actualListUrchiveStatus, "false");
//    }
//}
