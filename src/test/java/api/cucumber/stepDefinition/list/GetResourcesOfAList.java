package api.cucumber.stepDefinition.list;

import api.resourcesForTests.ConfigurationDataForApiTests;
import api.services.ServiceWorkShop;
import api.resourcesForTests.PathParameters;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class GetResourcesOfAList extends ServiceWorkShop {

    @When("I do request to get {string} of a list")
    public void i_do_request_to_get_of_a_list(String objectName) {

        ConfigurationDataForApiTests.commonResponseBetweenSteps = getListsService().getResourcesOfAList(ConfigurationDataForApiTests.ListsTestData.toDoListId,
                PathParameters.endPoints.get(objectName));
    }

    @Then("I got back actions of a list")
    public void iGotBackActionsOfAList() {

        String actualActionsOfaList = ConfigurationDataForApiTests.commonResponseBetweenSteps.body().asString();

        Assert.assertEquals(actualActionsOfaList, ConfigurationDataForApiTests.EMPTY_STRING);
    }

//    @And("I request to get available lists")
//    public void iRequestToGetAvailableLists() {
//        ConfigurationDataForApiTests.ListsTestData.idsOfAllListsPresentedOnABoard = getBoardService().getListOfIdOfAllListsOnABoard(ConfigurationDataForApiTests.BoardTestData.boardId);
//        getListsService()
//
//    }
}
