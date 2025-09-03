package api.cucumber.stepDefinition.list;

import api.resourcesForTests.CycymberConfigTestData;
import api.services.ListsService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.ArrayList;

public class CreateList{

    private CycymberConfigTestData cycymberConfigTestData;
    private ListsService listsService;

    public CreateList(CycymberConfigTestData cycymberConfigTestData, ListsService listsService) {
        this.cycymberConfigTestData = cycymberConfigTestData;
        this.listsService = listsService;
    }

    @When("I create a list with name {string} on the board")
    public void iCreateAListWithNameOnTheBoard(String nameForTheList) {

        Response response = listsService.createList(nameForTheList, cycymberConfigTestData.getBoardId());
        cycymberConfigTestData.setNewCreatedListId(response.jsonPath().getString("id"));
    }

    @Then("List {string} is created")
    public void listIsCreated(String nameOfTheListThatWasCreatedBefore) {
        Response response = listsService.getAList(cycymberConfigTestData.getNewCreatedListId());
        String actualListsName = response.jsonPath().getString("name");

        Assert.assertEquals(actualListsName, nameOfTheListThatWasCreatedBefore);
    }

    @Then("Lists are presented on a board")
    public void listsArePresentedOnABoard() {
        ArrayList list = (ArrayList) listsService.getListOfIdOfAllListsOnABoard(
                cycymberConfigTestData.getBoardId());
        Assert.assertFalse(list.isEmpty());
    }
}
