package api.cucumber.stepDefinition.list;

import api.resourcesForTests.ConfigurationDataForApiTests;
import api.services.ServiceWorkShop;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class ArchiveOrUnArchiveAList extends ServiceWorkShop {

    @When("I archive a list")
    public void iArchiveAList() {
        ConfigurationDataForApiTests.commonResponseBetweenSteps = getListsService().archiveAList(ConfigurationDataForApiTests.ListsTestData.toDoListId);
    }

    @And("Un archive a list")
    public void unArchiveAList() {
        ConfigurationDataForApiTests.commonResponseBetweenSteps = getListsService().unArchiveAList(ConfigurationDataForApiTests.ListsTestData.toDoListId);
    }

    @Then("A list is archived")
    public void aListIsArchived() {
        String actualListUrchiveStatus = getListsService().getAList(ConfigurationDataForApiTests.ListsTestData.toDoListId).jsonPath().getString("closed");

        Assert.assertEquals(actualListUrchiveStatus, "true");
    }

    @Then("A list is presented on a board")
    public void aListIsPresentedOnABoard() {
        String actualListUrchiveStatus = getListsService().getAList(ConfigurationDataForApiTests.ListsTestData.toDoListId).jsonPath().getString("closed");

        Assert.assertEquals(actualListUrchiveStatus, "false");
    }
}
