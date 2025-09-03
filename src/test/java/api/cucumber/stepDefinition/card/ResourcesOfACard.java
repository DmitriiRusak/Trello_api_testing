package api.cucumber.stepDefinition.card;

import api.resourcesForTests.configurationData.CycymberConfigTestData;
import api.resourcesForTests.PathParameters;
import api.services.*;
import io.cucumber.java.en.*;
import org.testng.Assert;

import java.io.File;
import java.util.List;

public class ResourcesOfACard{

    private CycymberConfigTestData cycymberConfigTestData;
    private CardsService cardsService;
    private ChecklistsService checklistsService;
    private LabelsService labelsService;
    private BoardService boardService;
    private ListsService listsService;

    public ResourcesOfACard(CycymberConfigTestData cycymberConfigTestData,
                            CardsService cardsService,
                            ChecklistsService checklistsService,
                            LabelsService labelsService,
                            BoardService boardService,
                            ListsService listsService) {

        this.cycymberConfigTestData = cycymberConfigTestData;
        this.cardsService = cardsService;
        this.checklistsService = checklistsService;
        this.labelsService = labelsService;
        this.boardService = boardService;
        this.listsService = listsService;
    }

    @When("I do create a comment {string} on a card")
    public void iDoCreateACommentOnACard(String comment) {
        cycymberConfigTestData.setCommonResponse(
                cardsService.addNewComentToACard(cycymberConfigTestData.getCardId(), comment));
    }

    @When("I delete an attachment on a card")
    public void iDeleteAnAttachmentOnACard() {
        cycymberConfigTestData.setCommonResponse(
                cardsService.deleteAnAttachmentOnACard(
                    cycymberConfigTestData.getCardId(),
                    cycymberConfigTestData.getCreatedAttachmentId()));
    }

    @When("I do create a checklist on a card")
    public void iDoCreateAChecklistOnACard() {
        cycymberConfigTestData.setCommonResponse(
                cardsService.createAChecklistForACard(
                    cycymberConfigTestData.getCardId(),
                    cycymberConfigTestData.NAME_OF_CHECKLIST_CREATED));

        cycymberConfigTestData.setChecklistId(
                cycymberConfigTestData.getCommonResponse().jsonPath().getString("id"));
    }

    @When("I do create a label")
    public void iDoCreateALabel() {
        cycymberConfigTestData.setLabelId(
                labelsService.createLabel(
                    cycymberConfigTestData.LABEL_NAME,
                    "green",
                    cycymberConfigTestData.getBoardId()).jsonPath().getString("id"));
    }

    @And("I delete a checklist off the card")
    public void iDeleteAChecklistOffTheCard() {

        cycymberConfigTestData.setCommonResponse(
                checklistsService.deleteAChecklist(cycymberConfigTestData.getChecklistId()));
    }

//Возможно здесь упадёт из-зи неправильного вызова ендпоинте стр.83, если да то надо поправить сценарий
    @When("I do request to get resource {string} of a {string}")
    public void iDoRequestToGetResourceOfA(String resourceName, String objectType) {
        cycymberConfigTestData.setCommonResponse(
                boardService.getResourceOfAnObject(
                    PathParameters.endPoints.get(objectType),
                    cycymberConfigTestData.getCardId(),
                    resourceName));
    }

    @When("I add an attachment {string} to the card")
    public void iAddAnAttachmentToTheCard(String pathForAnAttachment) {
        cycymberConfigTestData.setCreatedAttachmentId(
                cardsService.createAttachmentOnCard(
                    cycymberConfigTestData.getCardId(),
                    pathForAnAttachment).jsonPath().getString("id"));
    }


    @Then("attachment {string} on a card is presented")
    public void attachmentOnACardIsPresented(String pathToAttachment) {

        String actualAttachmentsName = cardsService.getAnAttachmentOnACard(
                cycymberConfigTestData.getCardId(),
                cycymberConfigTestData.getCreatedAttachmentId()).jsonPath().getString("name");

        File fileThatWasUsedToCreateAnAttachment = new File(pathToAttachment);
        String filesName = fileThatWasUsedToCreateAnAttachment.getName();

        Assert.assertEquals(actualAttachmentsName, filesName);
    }

    @Then("CheckList is created")
    public void checklistIsCreated() {

        String actualNameOfChecklist = cycymberConfigTestData.getCommonResponse().jsonPath().getString("name");
        String expectedNameOfChecklist = cycymberConfigTestData.NAME_OF_CHECKLIST_CREATED;

        Assert.assertEquals(actualNameOfChecklist, expectedNameOfChecklist);
    }

    @Then("Resource is deleted from a card")
    public void resourceIsDeletedFromACard() {
        String jsonResponseAfterResourceIsDeleted = (cycymberConfigTestData.getCommonResponse().jsonPath().getString("limits"));

        Assert.assertEquals(jsonResponseAfterResourceIsDeleted, cycymberConfigTestData.EXPECTED_EMPTY_STRING_RESULT);
    }

    @Then("Comment {string} is created")
    public void commentIsCreated(String comment) {
        String actualTextOfComment = cycymberConfigTestData.getCommonResponse().jsonPath().getString("data.text");

        Assert.assertEquals(actualTextOfComment, comment);
    }

    @And("Add a label to a card")
    public void addALabelToACard() {
        cycymberConfigTestData.setCommonResponse(
                labelsService.addALabelToACard(
                    cycymberConfigTestData.getCardId(),
                    cycymberConfigTestData.getLabelId()));
    }

    @Then("A label is presented on a card")
    public void aLabelIsPresentedOnACard() {
        String actualIdOfALabel = cycymberConfigTestData.getLabelId();

        String expectedIdOfAList = labelsService.getLabel(cycymberConfigTestData.getLabelId()).jsonPath().getString("id");

        Assert.assertEquals(actualIdOfALabel, expectedIdOfAList);
    }

    @Then("{int} Cards are presented on a list {string}")
    public void cardsArePresentedOnAList(int numberOfCards, String listName) {

        String litsIdTheCardsIsOn = listsService.getIdOfAListByName(
                listName,
                cycymberConfigTestData.getBoardId(),
                cycymberConfigTestData);

        cycymberConfigTestData.setCommonResponse(
                listsService.getResourcesOfAList(litsIdTheCardsIsOn, PathParameters.endPoints.get("card")));

        List namesOfACardsPresentedOnAList = cycymberConfigTestData.getCommonResponse().jsonPath().getList("name");

//        LogFactory.getLogger().info("Number of cards presented on a list " + namesOfACardsPresentedOnAList.size());
//        LogFactory.getLogger().info("Number of cards expected " + numberOfCards);

        Assert.assertEquals(numberOfCards, namesOfACardsPresentedOnAList.size());
    }

}
