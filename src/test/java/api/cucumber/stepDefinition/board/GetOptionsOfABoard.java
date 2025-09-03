package api.cucumber.stepDefinition.board;

import api.resourcesForTests.configurationData.CycymberConfigTestData;
import api.services.BoardService;
import io.cucumber.java.en.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;

import java.io.File;

public class GetOptionsOfABoard{

    private CycymberConfigTestData cycymberConfigTestData;
    private BoardService boardService;

    public GetOptionsOfABoard (CycymberConfigTestData cycymberConfigTestData, BoardService boardService){
        this.cycymberConfigTestData = cycymberConfigTestData;
        this.boardService = boardService;
    }

    @When("I do request for all options of the board")
    public void i_do_request_for_all_options_of_the_board() {

        cycymberConfigTestData.setCommonResponse(boardService.getBoard(cycymberConfigTestData.getBoardId()));
    }

    @When("I change {string} of a board to {string}")
    public void i_change_of_a_board_to(String optionName, String newOptionValue) {
        cycymberConfigTestData.setCommonResponse(boardService.updateBoard(cycymberConfigTestData.getBoardId(), optionName, newOptionValue));
    }

    @Then("I got back requested {string}")
    public void i_got_back_requested(String string) {

        Response currentResponse = boardService.getBoard(cycymberConfigTestData.getBoardId());
        String currentResource = currentResponse.jsonPath().getString(string);

        Assert.assertEquals(currentResource, cycymberConfigTestData.getCommonResponse().jsonPath().getString("_value"));
    }

    @Then("I got back requested options for {string}")
    public void iGotBackRequestedOptionsFor(String objectName) {

        File getBoardjsonSchema = new File("src/test/resources/get"+objectName+"JSONSchema.json");
        MatcherAssert.assertThat(
                "Validate json schema",
                cycymberConfigTestData.getCommonResponse().getBody().asString(),
                JsonSchemaValidator.matchesJsonSchema(getBoardjsonSchema));
    }

    @When("I do request to get {string} Of a board")
    public void iDoRequestToGetOfABoard(String optionName) {

        cycymberConfigTestData.setCommonResponse(
                boardService.getOptionOfABoard(cycymberConfigTestData.getBoardId(), "/"+optionName));
    }
}
