package api.cucumber.stepDefinition.list;

import api.resourcesForTests.CycymberConfigTestData;
import api.resourcesForTests.ListFields;
import api.resourcesForTests.PathParameters;
import api.services.BoardService;
import api.services.ListsService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

public class OptionsOfAList{

    private CycymberConfigTestData cycymberConfigTestData;
    private ListsService listsService;
    private BoardService boardService;

    public OptionsOfAList(CycymberConfigTestData cycymberConfigTestData, ListsService listsService, BoardService boardService) {
        this.cycymberConfigTestData = cycymberConfigTestData;
        this.listsService = listsService;
        this.boardService = boardService;
    }

    @When("I request the lists information")
    public void iRequestTheListsInformation() {

        cycymberConfigTestData.setCommonResponse(
                listsService.getOptionOfAnObject(PathParameters.endPoints.get("list"),
                cycymberConfigTestData.getToDoListId(),
      "all"));
    }

    @Then("I got back requested {string} of a list")
    public void iGotBackRequestedOfA(String optionName) throws InterruptedException {

        Response actualList = listsService.getAList(cycymberConfigTestData.getToDoListId());

        Assert.assertEquals(actualList.jsonPath().getString(optionName),
                cycymberConfigTestData.getCommonResponse().jsonPath().getString(optionName));
    }

    @When("I do request to get {string} of a {string}")
    public void iDoRequestToGetOfA(String optionName, String resourceName) {

        String tempResourceId = "";
        switch (resourceName){
            case "list": tempResourceId = cycymberConfigTestData.getToDoListId();
                break;
            case "board": tempResourceId = cycymberConfigTestData.getBoardId();
                break;
            case "card": tempResourceId = cycymberConfigTestData.getCardId();
                break;
        }

        cycymberConfigTestData.setCommonResponse(
                boardService.getOptionOfAnObject(
                    PathParameters.endPoints.get(resourceName),
                    tempResourceId,
                    optionName));

    }

    @When("I update a list name {string} with new name {string}")
    public void iUpdateAListWithNewName(String listName, String newNameForTheList) {

       String idOfTheListToChangeTheName = listsService.getIdOfAListByName(
               listName,
               cycymberConfigTestData.getBoardId(),
               cycymberConfigTestData);

       listsService.updateAFieldOfAList(idOfTheListToChangeTheName, ListFields.name, newNameForTheList);
    }

    @When("I update a list options {string} with new values {string}")
    public void iUpdateAListOptionsWithNewValues(String names, String values) {
        String[] optionNames = names.split(" ");
        String[] optionValues = values.split(" ");

        listsService.updateAFieldsOfAList(cycymberConfigTestData.getToDoListId(), optionNames,optionValues);

    }

    @Then("A list options {string} is updated with values {string}")
    public void aListOptionsIsUpdatedWithValues(String names, String values) {
        String[] optionNames = names.split(" ");
        String[] optionValues = values.split(" ");

        Response currentResponse = listsService.
                getOptionOfAnObject(PathParameters.endPoints.get("list"),
                        cycymberConfigTestData.getToDoListId(),
                        "all");

        for(int i = 0; i < optionNames.length; i++){
            Assert.assertEquals(currentResponse.jsonPath().getString(optionNames[i]), optionValues[i]);
        }

    }

    @Then("A name of the list {string} is changed to {string}")
    public void aNameOfTheListIsChangedTo(String oldNameOfTheList, String newNameOfTheList) {

        String idOfTheListWithChangedName = listsService.getIdOfAListByName(
                newNameOfTheList,
                cycymberConfigTestData.getBoardId(),
                cycymberConfigTestData);

        String actualNameOfTheListOnBoard = listsService.getAList(idOfTheListWithChangedName).jsonPath().getString("name");

        Assert.assertEquals(actualNameOfTheListOnBoard, newNameOfTheList);
        Assert.assertNotEquals(oldNameOfTheList, actualNameOfTheListOnBoard);
    }
}
