package api.cucumber.stepDefinition.list;

import api.base.BaseTest;
import api.base.TestData;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

public class CreateList extends BaseTest {

    @When("I create a list with name {string} on the board")
    public void iCreateAListWithNameOnTheBoard(String nameForTheList) {

        Response response = getListsService().createList(nameForTheList, TestData.BoardTestData.boardId);
        TestData.ListsTestData.newCreatedListId = response.jsonPath().getString("id");
    }

    @Then("List {string} is created")
    public void listIsCreated(String nameOfTheListThatWasCreatedBefore) {
        Response response = getListsService().getAList(TestData.ListsTestData.newCreatedListId);
        String actualListsName = response.jsonPath().getString("name");

        Assert.assertEquals(actualListsName, nameOfTheListThatWasCreatedBefore);
    }
}
