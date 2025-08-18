package api.cucumber.stepDefinition.list;

import api.cucumber.continer.ConfigTestDataHolder;
import api.resourcesForTests.configurationData.CommonConfigData;
import api.services.ServiceWorkShop;
import api.resourcesForTests.PathParameters;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class GetResourcesOfAList extends ServiceWorkShop {

    private ConfigTestDataHolder configTestDataHolder;

    public GetResourcesOfAList(ConfigTestDataHolder configTestDataHolder) {
        this.configTestDataHolder = configTestDataHolder;
    }

    @When("I do request to get {string} of a list")
    public void i_do_request_to_get_of_a_list(String objectName) {

        configTestDataHolder.getCommonConfigData().setCommonResponseBetweenSteps(
                getListsService().getResourcesOfAList(configTestDataHolder.getListTestData().getToDoListId(),
                PathParameters.endPoints.get(objectName)));
    }

    @Then("I got back actions of a list")
    public void iGotBackActionsOfAList() {

        String actualActionsOfaList = configTestDataHolder.getCommonConfigData().getCommonResponseBetweenSteps().body().asString();

        Assert.assertEquals(actualActionsOfaList, CommonConfigData.EMPTY_STRING);
    }

//    @And("I request to get available lists")
//    public void iRequestToGetAvailableLists() {
//        CommonConfigData.ListsTestData.idsOfAllListsPresentedOnABoard = getBoardService().getListOfIdOfAllListsOnABoard(CommonConfigData.BoardTestData.boardId);
//        getListsService()
//
//    }
}
