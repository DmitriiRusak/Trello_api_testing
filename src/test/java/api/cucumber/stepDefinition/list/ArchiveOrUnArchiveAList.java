package api.cucumber.stepDefinition.list;

import api.resourcesForTests.configurationData.CycymberConfigTestData;
import api.services.ListsService;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class ArchiveOrUnArchiveAList{

    private CycymberConfigTestData cycymberConfigTestData;
    private ListsService listsService;

    public ArchiveOrUnArchiveAList(CycymberConfigTestData cycymberConfigTestData, ListsService listsService) {
        this.cycymberConfigTestData = cycymberConfigTestData;
        this.listsService = listsService;
    }

    @When("I archive a list")
    public void iArchiveAList() {
        cycymberConfigTestData.setCommonResponse(
                listsService.archiveAList(cycymberConfigTestData.getToDoListId()));
    }

    @And("Un archive a list")
    public void unArchiveAList() {
        cycymberConfigTestData.setCommonResponse(
                listsService.unArchiveAList(cycymberConfigTestData.getToDoListId()));
    }

    @Then("A list is archived")
    public void aListIsArchived() {
        String actualListUrchiveStatus = listsService.getAList(cycymberConfigTestData.getToDoListId()).jsonPath().getString("closed");

        Assert.assertEquals(actualListUrchiveStatus, "true");
    }

    @Then("A list is presented on a board")
    public void aListIsPresentedOnABoard() {
        String actualListUrchiveStatus = listsService.getAList(cycymberConfigTestData.getToDoListId()).jsonPath().getString("closed");

        Assert.assertEquals(actualListUrchiveStatus, "false");
    }
}
