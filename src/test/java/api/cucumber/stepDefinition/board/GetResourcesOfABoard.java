package api.cucumber.stepDefinition.board;

import api.resourcesForTests.configurationData.CycymberConfigTestData;
import api.services.BoardService;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;


public class GetResourcesOfABoard{

    private CycymberConfigTestData cycymberConfigTestData;
    private BoardService boardService;

    public GetResourcesOfABoard(CycymberConfigTestData cycymberConfigTestData, BoardService boardService){
        this.cycymberConfigTestData = cycymberConfigTestData;
        this.boardService = boardService;
    }

    @Given("I create a card on a list")
    public void i_create_a_card_on_a_list() {

        Map<String, String> queryParametersForRequestSpec = new HashMap<>();
//        queryParametersForRequestSpec.put("idList", configTestDataHolder.getListTestData().getToDoListId());
        queryParametersForRequestSpec.put("idList", cycymberConfigTestData.getToDoListId());
        queryParametersForRequestSpec.put("name", "card created from Cucumber");

        Response response = boardService.createACard(queryParametersForRequestSpec);

        cycymberConfigTestData.setCardId(response.jsonPath().getString("id"));
        cycymberConfigTestData.setListIdTheCardIsOn(response.jsonPath().getString("idList"));
        cycymberConfigTestData.setCurrentListId(response.jsonPath().getString("idList"));

    }

//    Method gatAvailableBoard() will retern all the id`s of boards on a workspace.
//    But the logic is to Initialize only one variable. So if there are more than one board on a workspace the variable
//    boardId will consist of all id`s returned back
//    So method will work correctly only if one board is presented. If more than 1 board than variable board id
//    might look like 34242342523, 23435235235

    @When("I request to get available board")
    public void iRequestToGetAvailableBoard() {
        cycymberConfigTestData.setBoardId(boardService.getAllAvailableBoards());
    }

    @Then("I got back requested resource")
    public void i_got_back_requested_resource() {
        String requestedResourceId = cycymberConfigTestData.getCommonResponse().jsonPath().getString("id");

        Assert.assertFalse(requestedResourceId.isEmpty());
        Assert.assertTrue(requestedResourceId.length()>3);

    }

    @Then("I got back available board")
    public void iGotBackAvailableBoard() {

        Assert.assertFalse(cycymberConfigTestData.getBoardId().isEmpty());
    }

    @And("Board is presented on a workSpace")
    public void boardIsPresentedOnAWorkSpace() {

        cycymberConfigTestData.setBoardId(boardService.getAllAvailableBoards()); //<- с использованием my work space ендпоинта.
        System.out.println("From GetresourcesOfABoard, line 69");
        System.out.println("-----> " + cycymberConfigTestData.getBoardId());
        cycymberConfigTestData.setToDoListId(
                boardService.getListOfIdOfAllListsOnABoard(
                        cycymberConfigTestData.getBoardId()). get(0).toString());

    }
}
