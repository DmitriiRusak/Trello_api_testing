package api.cucumber.stepDefinition.card;

import api.cucumber.continer.ConfigTestDataHolder;
import api.resourcesForTests.configurationData.CommonConfigData;
import api.services.ServiceWorkShop;
import api.resourcesForTests.PathParameters;
import api.utils.LogFactory;
import io.cucumber.java.en.*;
import org.testng.Assert;

import java.io.File;
import java.util.List;

public class ResourcesOfACard extends ServiceWorkShop {

    private ConfigTestDataHolder configTestDataHolder;

    public ResourcesOfACard(ConfigTestDataHolder configTestDataHolder) {
        this.configTestDataHolder = configTestDataHolder;
    }

    @When("I do create a comment {string} on a card")
    public void iDoCreateACommentOnACard(String comment) {
        configTestDataHolder.getCommonConfigData().setCommonResponseBetweenSteps(getCardsService().addNewComentToACard(configTestDataHolder.getCardTestData().getCardId(),
                comment,
                PathParameters.CardsEndPoints.COMMENTS_ENDPOINT));
    }

    @When("I delete an attachment on a card")
    public void iDeleteAnAttachmentOnACard() {
        configTestDataHolder.getCommonConfigData().setCommonResponseBetweenSteps(getCardsService().
                deleteAnAttachmentOnACard(configTestDataHolder.getCardTestData().getCardId(), configTestDataHolder.getCardTestData().getCreatedAttachmentId()));
    }

    @When("I do create a checklist on a card")
    public void iDoCreateAChecklistOnACard() {
        configTestDataHolder.getCommonConfigData().setCommonResponseBetweenSteps(getCardsService().createAChecklist(
                configTestDataHolder.getCardTestData().getCardId(),
                configTestDataHolder.getCheckListTestData().NAME_OF_CHECKLIST_CREATED));
        configTestDataHolder.getCheckListTestData().setChecklistId(
                configTestDataHolder.getCommonConfigData().getCommonResponseBetweenSteps().jsonPath().getString("id"));
    }

    @When("I do create a label")
    public void iDoCreateALabel() {
        configTestDataHolder.getLabelTestData().setLabelId(getLabelsService().
                                                createLabel(configTestDataHolder.getLabelTestData().LABEL_NAME,
                                                        "green",
                                                        configTestDataHolder.getBoardTestData().getBoardId()).jsonPath().getString("id"));
    }

    @And("I delete a checklist off the card")
    public void iDeleteAChecklistOffTheCard() {

        configTestDataHolder.getCommonConfigData().setCommonResponseBetweenSteps(
                getChecklistsService().deleteAChecklist(configTestDataHolder.getCheckListTestData().getChecklistId()));
    }


    @When("I do request to get resource {string} of a {string}")
    public void iDoRequestToGetResourceOfA(String resourceName, String objectType) {
        configTestDataHolder.getCommonConfigData().setCommonResponseBetweenSteps(
                getBoardService().getResourceOfAnObject(PathParameters.endPoints.get(objectType),
                        configTestDataHolder.getCardTestData().getCardId(),
                        resourceName));
    }

    @When("I add an attachment {string} to the card")
    public void iAddAnAttachmentToTheCard(String pathForAnAttachment) {
        configTestDataHolder.getCardTestData().setCreatedAttachmentId(getCardsService().createAttachmentOnCard(
                configTestDataHolder.getCardTestData().getCardId(),
                pathForAnAttachment).jsonPath().getString("id"));
    }


    @Then("attachment {string} on a card is presented")
    public void attachmentOnACardIsPresented(String pathToAttachment) {
        String actualAttachmentsName = getCardsService().getAnAttachmentOnACard(configTestDataHolder.getCardTestData().getCardId(),
                configTestDataHolder.getCardTestData().getCreatedAttachmentId()).jsonPath().getString("name");
        File fileThatWasUsedToCreateAnAttachment = new File(pathToAttachment);
        String filesName = fileThatWasUsedToCreateAnAttachment.getName();

        Assert.assertEquals(actualAttachmentsName, filesName);
    }

    @Then("CheckList is created")
    public void checklistIsCreated() {
        String actualNameOfChecklist = configTestDataHolder.getCommonConfigData().getCommonResponseBetweenSteps().jsonPath().getString("name");
        String expectedNameOfChecklist = configTestDataHolder.getCheckListTestData().NAME_OF_CHECKLIST_CREATED;

        Assert.assertEquals(actualNameOfChecklist, expectedNameOfChecklist);
    }

    @Then("Resource is deleted from a card")
    public void resourceIsDeletedFromACard() {
        String jsonResponseAfterResourceIsDeleted = (configTestDataHolder.getCommonConfigData().getCommonResponseBetweenSteps().jsonPath().getString("limits"));

        Assert.assertEquals(jsonResponseAfterResourceIsDeleted, CommonConfigData.EXPECTED_EMPTY_STRING_RESULT);
    }

    @Then("Comment {string} is created")
    public void commentIsCreated(String comment) {
        String actualTextOfComment = configTestDataHolder.getCommonConfigData().getCommonResponseBetweenSteps().jsonPath().getString("data.text");

        Assert.assertEquals(actualTextOfComment, comment);
    }

    @And("Add a label to a card")
    public void addALabelToACard() {
        configTestDataHolder.getCommonConfigData().setCommonResponseBetweenSteps(
                getLabelsService().addALabelToACard(configTestDataHolder.getCardTestData().getCardId(),
                        configTestDataHolder.getLabelTestData().getLabelId()));
    }

    @Then("A label is presented on a card")
    public void aLabelIsPresentedOnACard() {
        String actualIdOfALabel = configTestDataHolder.getLabelTestData().getLabelId();

        String expectedIdOfAList = getLabelsService().getLabel(configTestDataHolder.getLabelTestData().getLabelId()).jsonPath().getString("id");

        Assert.assertEquals(actualIdOfALabel, expectedIdOfAList);
    }

    @Then("{int} Cards are presented on a list {string}")
    public void cardsArePresentedOnAList(int numberOfCards, String listName) {

        String litsIdTheCardsIsOn = getListsService().getIdOfAListByName(
                listName,
                configTestDataHolder.getBoardTestData().getBoardId(),
                configTestDataHolder.getListTestData());
        configTestDataHolder.getCommonConfigData().setCommonResponseBetweenSteps(
                getListsService().getResourcesOfAList(litsIdTheCardsIsOn, PathParameters.endPoints.get("card")));
        List namesOfACardsPresentedOnAList = configTestDataHolder.getCommonConfigData().getCommonResponseBetweenSteps().jsonPath().getList("name");

        LogFactory.getLogger().info("Number of cards presented on a list " + namesOfACardsPresentedOnAList.size());
        LogFactory.getLogger().info("Number of cards expected " + numberOfCards);

        Assert.assertEquals(numberOfCards, namesOfACardsPresentedOnAList.size());
    }

}
