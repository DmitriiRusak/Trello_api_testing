package api.cucumber.stepDefinition.list;

import api.resourcesForTests.ConfigurationDataForApiTests;
import api.services.ServiceWorkShop;
import api.utils.LogFactory;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.ArrayList;

public class CreateList extends ServiceWorkShop {


    @When("I create a list with name {string} on the board")
    public void iCreateAListWithNameOnTheBoard(String nameForTheList) {

        Response response = getListsService().createList(nameForTheList, ConfigurationDataForApiTests.BoardTestData.boardId);
        ConfigurationDataForApiTests.ListsTestData.newCreatedListId = response.jsonPath().getString("id");
    }

    @Then("List {string} is created")
    public void listIsCreated(String nameOfTheListThatWasCreatedBefore) {
        Response response = getListsService().getAList(ConfigurationDataForApiTests.ListsTestData.newCreatedListId);
        String actualListsName = response.jsonPath().getString("name");

        Assert.assertEquals(actualListsName, nameOfTheListThatWasCreatedBefore);
    }

    @Then("Lists are presented on a board")
    public void listsArePresentedOnABoard() {
        ArrayList list = (ArrayList) getListsService().getListOfIdOfAllListsOnABoard(ConfigurationDataForApiTests.BoardTestData.boardId);
        Assert.assertFalse(list.isEmpty());
    }
}
