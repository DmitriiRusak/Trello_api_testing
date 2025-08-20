//package api.cucumber.stepDefinition.list;
//
//import api.cucumber.continer.ConfigTestDataHolder;
//import api.resourcesForTests.configurationData.CommonConfigData;
//import api.services.ServiceWorkShop;
//import io.cucumber.java.en.*;
//import org.testng.Assert;
//
//public class MoveListFromBoardToBoard extends ServiceWorkShop {
//
//    private ConfigTestDataHolder configTestDataHolder;
//
//    public MoveListFromBoardToBoard(ConfigTestDataHolder configTestDataHolder) {
//        this.configTestDataHolder = configTestDataHolder;
//    }
//
//    @And("I create one more board")
//    public void iCreateOneMoreBoard() {
//        configTestDataHolder.getBoardTestData().setSecondBoardId(
//                getBoardService().createBoard("One more board").jsonPath().getString("id"));
//    }
//
//    @When("I move to do list, from one board, to another")
//    public void iMoveToDoListFromOneBoardToAnother() {
//        configTestDataHolder.getCommonConfigData().setCommonResponseBetweenSteps(
//                getListsService().
//                moveListFromOneBoardToAnother(configTestDataHolder.getListTestData().getToDoListId(),
//                        configTestDataHolder.getBoardTestData().getSecondBoardId()));
//    }
//
//    @Then("I see to do list being moved from one board, to another")
//    public void iSeeToDoListBeingMovedFromOneBoardToAnother() {
//        String boardIdTheListisOn = getListsService().getABoardAListIsOn(configTestDataHolder.getListTestData().getToDoListId()).jsonPath().getString("id");
//
//        Assert.assertEquals(configTestDataHolder.getBoardTestData().getSecondBoardId(), boardIdTheListisOn);
//    }
//}
