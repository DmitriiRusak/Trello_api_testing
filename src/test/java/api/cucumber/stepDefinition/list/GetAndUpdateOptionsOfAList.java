package api.cucumber.stepDefinition.list;

import api.base.BaseTest;
import api.base.PathParameters;
import api.base.TestData;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

public class GetAndUpdateOptionsOfAList extends BaseTest {

    @When("I request the lists information")
    public void iRequestTheListsInformation() {

        TestData.commonResponseBetweenSteps = getListsService().
                getOptionOfAnObject(PathParameters.endPoints.get("list"),
                TestData.ListsTestData.toDoListId,
    "all");
    }

    @Then("I got back requested {string} of a {string}")
    public void iGotBackRequestedOfA(String optionName, String resourceName) throws InterruptedException {

        Response actualList = getListsService().getAList(TestData.ListsTestData.toDoListId);
        System.out.println("From GetAndUpdateOptionsOfAList, line 24");
        System.out.println(actualList.asPrettyString());
        Assert.assertEquals(actualList.jsonPath().getString(optionName), TestData.commonResponseBetweenSteps.jsonPath().getString(optionName));
    }

    @When("I do request to get {string} of a {string}")
    public void iDoRequestToGetOfA(String optionName, String resourceName) {

        String tempResourceId = "";
        switch (resourceName){
            case "list": tempResourceId = TestData.ListsTestData.toDoListId;
                break;
            case "board": tempResourceId = TestData.BoardTestData.boardId;
                break;
//            case "cards": tempResourceId = TestData.ListsTestData.toDoListId;
//                break;
        }

        TestData.commonResponseBetweenSteps = getBoardService().
                getOptionOfAnObject(PathParameters.endPoints.get(resourceName),tempResourceId, optionName);
    }

    @When("I update a list options {string} with new values {string}")
    public void iUpdateAListOptionsWithNewValues(String names, String values) {
        String[] optionNames = names.split(" ");
        String[] optionValues = values.split(" ");

        getListsService().updateAnOptionsOfAList(TestData.ListsTestData.toDoListId, optionNames,optionValues);

    }

    @Then("A list options {string} is updated with values {string}")
    public void aListOptionsIsUpdatedWithValues(String names, String values) {
        String[] optionNames = names.split(" ");
        String[] optionValues = values.split(" ");

        Response currentResponse = getListsService().
                getOptionOfAnObject(PathParameters.endPoints.get("list"),
                        TestData.ListsTestData.toDoListId,
                        "all");
        System.out.println(currentResponse.asPrettyString());
        System.out.println(currentResponse.statusCode());

        for(int i = 0; i < optionNames.length; i++){
            System.out.println(currentResponse.jsonPath().getString(optionNames[i])+" <-> "+ optionValues[i]);
            Assert.assertEquals(currentResponse.jsonPath().getString(optionNames[i]), optionValues[i]);
        }

    }

}
