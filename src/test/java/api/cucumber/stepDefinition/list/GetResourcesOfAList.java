package api.cucumber.stepDefinition.list;

import api.base.BaseTest;
import api.base.PathParameters;
import api.base.TestData;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class GetResourcesOfAList extends BaseTest {

    @When("I do request to get {string} of a list")
    public void i_do_request_to_get_of_a_list(String objectName) {

        TestData.commonResponseBetweenSteps = getListsService().getResourcesOfAList(TestData.ListsTestData.toDoListId,
                PathParameters.endPoints.get(objectName));
    }

    @Then("I got back actions of a list")
    public void iGotBackActionsOfAList() {

        String actualActionsOfaList = TestData.commonResponseBetweenSteps.body().asString();

        Assert.assertEquals(actualActionsOfaList, TestData.emptyString);
    }
}
