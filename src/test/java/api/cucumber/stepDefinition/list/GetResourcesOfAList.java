package api.cucumber.stepDefinition.list;

import api.resourcesForTests.configurationData.CycymberConfigTestData;
import api.resourcesForTests.PathParameters;
import api.services.ListsService;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class GetResourcesOfAList{

    private CycymberConfigTestData cycymberConfigTestData;
    private ListsService listsService;

    public GetResourcesOfAList(CycymberConfigTestData cycymberConfigTestData, ListsService listsService) {
        this.cycymberConfigTestData = cycymberConfigTestData;
        this.listsService = listsService;
    }

    @When("I do request to get {string} of a list")
    public void i_do_request_to_get_of_a_list(String objectName) {

        cycymberConfigTestData.setCommonResponse(
                listsService.getResourcesOfAList(cycymberConfigTestData.getToDoListId(),
                PathParameters.endPoints.get(objectName)));
    }

    @Then("I got back actions of a list")
    public void iGotBackActionsOfAList() {

        String actualActionsOfaList = cycymberConfigTestData.getCommonResponse().body().asString();

        Assert.assertEquals(actualActionsOfaList, cycymberConfigTestData.EMPTY_STRING);
    }

//    @And("I request to get available lists")
//    public void iRequestToGetAvailableLists() {
//        CommonConfigData.ListsTestData.idsOfAllListsPresentedOnABoard = getBoardService().getListOfIdOfAllListsOnABoard(CommonConfigData.BoardTestData.boardId);
//        getListsService()
//
//    }
}
