package api.cucumber.stepDefinition.list;

import api.cucumber.continer.ConfigTestDataHolder;
import api.resourcesForTests.configurationData.CommonConfigData;
import api.services.ServiceWorkShop;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.ArrayList;

public class CreateList extends ServiceWorkShop {

    private ConfigTestDataHolder configTestDataHolder;

    public CreateList(ConfigTestDataHolder configTestDataHolder) {
        this.configTestDataHolder = configTestDataHolder;
    }

    @When("I create a list with name {string} on the board")
    public void iCreateAListWithNameOnTheBoard(String nameForTheList) {

        Response response = getListsService().createList(nameForTheList, configTestDataHolder.getBoardTestData().getBoardId());
        configTestDataHolder.getListTestData().setNewCreatedListId(response.jsonPath().getString("id"));
    }

    @Then("List {string} is created")
    public void listIsCreated(String nameOfTheListThatWasCreatedBefore) {
        Response response = getListsService().getAList(configTestDataHolder.getListTestData().getNewCreatedListId());
        String actualListsName = response.jsonPath().getString("name");

        Assert.assertEquals(actualListsName, nameOfTheListThatWasCreatedBefore);
    }

    @Then("Lists are presented on a board")
    public void listsArePresentedOnABoard() {
        ArrayList list = (ArrayList) getListsService().getListOfIdOfAllListsOnABoard(configTestDataHolder.getBoardTestData().getBoardId());
        Assert.assertFalse(list.isEmpty());
    }
}
