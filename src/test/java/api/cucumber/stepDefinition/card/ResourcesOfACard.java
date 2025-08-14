package api.cucumber.stepDefinition.card;

import api.resourcesForTests.ConfigurationDataForApiTests;
import api.services.ServiceWorkShop;
import api.resourcesForTests.PathParameters;
import api.utils.LogFactory;
import io.cucumber.java.en.*;
import org.testng.Assert;

import java.io.File;
import java.util.List;

public class ResourcesOfACard extends ServiceWorkShop {

    @When("I do create a comment {string} on a card")
    public void iDoCreateACommentOnACard(String comment) {
        ConfigurationDataForApiTests.commonResponseBetweenSteps = getCardsService().addNewComentToACard(ConfigurationDataForApiTests.CardsTestData.cardId,
                comment,
                PathParameters.CardsEndPoints.COMMENTS_ENDPOINT);
    }

    @When("I delete an attachment on a card")
    public void iDeleteAnAttachmentOnACard() {
        ConfigurationDataForApiTests.commonResponseBetweenSteps = getCardsService().
                deleteAnAttachmentOnACard(ConfigurationDataForApiTests.CardsTestData.cardId, ConfigurationDataForApiTests.CardsTestData.createdAttachmentId);
    }

    @When("I do create a checklist on a card")
    public void iDoCreateAChecklistOnACard() {
        ConfigurationDataForApiTests.commonResponseBetweenSteps = getCardsService().createAChecklist(
                ConfigurationDataForApiTests.CardsTestData.cardId,
                ConfigurationDataForApiTests.CheckListsTestData.nameOfAChecklistCreated);
        ConfigurationDataForApiTests.CheckListsTestData.checklistId = ConfigurationDataForApiTests.commonResponseBetweenSteps.jsonPath().getString("id");
    }

    @When("I do create a label")
    public void iDoCreateALabel() {
        ConfigurationDataForApiTests.LabelsTestData.labelId = getLabelsService().
                                                createLabel(ConfigurationDataForApiTests.LabelsTestData.LABEL_NAME,
                                                        "green",
                                                        ConfigurationDataForApiTests.BoardTestData.boardId).jsonPath().getString("id");
    }

    @And("I delete a checklist off the card")
    public void iDeleteAChecklistOffTheCard() {

        ConfigurationDataForApiTests.commonResponseBetweenSteps = getChecklistsService().deleteAChecklist(ConfigurationDataForApiTests.CheckListsTestData.checklistId);
    }


    @When("I do request to get resource {string} of a {string}")
    public void iDoRequestToGetResourceOfA(String resourceName, String objectType) {
        ConfigurationDataForApiTests.commonResponseBetweenSteps =
                getBoardService().getResourceOfAnObject(PathParameters.endPoints.get(objectType),
                        ConfigurationDataForApiTests.CardsTestData.cardId,
                        resourceName);
    }

    @When("I add an attachment {string} to the card")
    public void iAddAnAttachmentToTheCard(String pathForAnAttachment) {
        ConfigurationDataForApiTests.CardsTestData.createdAttachmentId = getCardsService().createAttachmentOnCard(
                ConfigurationDataForApiTests.CardsTestData.cardId,
                pathForAnAttachment).jsonPath().getString("id");
    }


    @Then("attachment {string} on a card is presented")
    public void attachmentOnACardIsPresented(String pathToAttachment) {
        String actualAttachmentsName = getCardsService().getAnAttachmentOnACard(ConfigurationDataForApiTests.CardsTestData.cardId,
                ConfigurationDataForApiTests.CardsTestData.createdAttachmentId).jsonPath().getString("name");
        File fileThatWasUsedToCreateAnAttachment = new File(pathToAttachment);
        String filesName = fileThatWasUsedToCreateAnAttachment.getName();

        Assert.assertEquals(actualAttachmentsName, filesName);
    }

    @Then("CheckList is created")
    public void checklistIsCreated() {
        String actualNameOfChecklist = ConfigurationDataForApiTests.commonResponseBetweenSteps.jsonPath().getString("name");
        String expectedNameOfChecklist = ConfigurationDataForApiTests.CheckListsTestData.nameOfAChecklistCreated;

        Assert.assertEquals(actualNameOfChecklist, expectedNameOfChecklist);
    }

    @Then("Resource is deleted from a card")
    public void resourceIsDeletedFromACard() {
        String jsonResponseAfterResourceIsDeleted = (ConfigurationDataForApiTests.commonResponseBetweenSteps.jsonPath().getString("limits"));

        Assert.assertEquals(jsonResponseAfterResourceIsDeleted, ConfigurationDataForApiTests.EXPECTED_EMPTY_STRING_RESULT);
    }

    @Then("Comment {string} is created")
    public void commentIsCreated(String comment) {
        String actualTextOfComment = ConfigurationDataForApiTests.commonResponseBetweenSteps.jsonPath().getString("data.text");

        Assert.assertEquals(actualTextOfComment, comment);
    }

    @And("Add a label to a card")
    public void addALabelToACard() {
        ConfigurationDataForApiTests.commonResponseBetweenSteps =
                getLabelsService().addALabelToACard(ConfigurationDataForApiTests.CardsTestData.cardId, ConfigurationDataForApiTests.LabelsTestData.labelId);
    }

    @Then("A label is presented on a card")
    public void aLabelIsPresentedOnACard() {
        String actualIdOfALabel = ConfigurationDataForApiTests.LabelsTestData.labelId;

        String expectedIdOfAList = getLabelsService().getLabel(ConfigurationDataForApiTests.LabelsTestData.labelId).jsonPath().getString("id");

        Assert.assertEquals(actualIdOfALabel, expectedIdOfAList);
    }

    @Then("{int} Cards are presented on a list {string}")
    public void cardsArePresentedOnAList(int numberOfCards, String listName) {

        String litsIdTheCardsIsOn = getListsService().getIdOfAListByName(listName);
        ConfigurationDataForApiTests.commonResponseBetweenSteps = getListsService().getResourcesOfAList(litsIdTheCardsIsOn, PathParameters.endPoints.get("card"));
        List namesOfACardsPresentedOnAList = ConfigurationDataForApiTests.commonResponseBetweenSteps.jsonPath().getList("name");

        LogFactory.getLogger().info("Number of cards presented on a list " + namesOfACardsPresentedOnAList.size());
        LogFactory.getLogger().info("Number of cards expected " + numberOfCards);

        Assert.assertEquals(numberOfCards, namesOfACardsPresentedOnAList.size());
    }

}
