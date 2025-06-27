package api.cucumber.stepDefinition.list;

import api.base.BaseTest;
import api.base.TestData;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class ArchiveOrUnArchiveAList extends BaseTest {

    @When("I archive a list")
    public void iArchiveAList() {
        TestData.commonResponseBetweenSteps = getListsService().archiveAList(TestData.ListsTestData.toDoListId);
    }

    @And("Un archive a list")
    public void unArchiveAList() {
        TestData.commonResponseBetweenSteps = getListsService().unArchiveAList(TestData.ListsTestData.toDoListId);
    }

    @Then("A list is archived")
    public void aListIsArchived() {
        String actualListUrchiveStatus = getListsService().getAList(TestData.ListsTestData.toDoListId).jsonPath().getString("closed");

        Assert.assertEquals(actualListUrchiveStatus, "true");

    }

    @Then("A list is presented on a board")
    public void aListIsPresentedOnABoard() {
        String actualListUrchiveStatus = getListsService().getAList(TestData.ListsTestData.toDoListId).jsonPath().getString("closed");

        Assert.assertEquals(actualListUrchiveStatus, "false");
    }
}
