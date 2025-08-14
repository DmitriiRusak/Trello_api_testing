package api.cucumber.stepDefinition.board;

import api.resourcesForTests.ConfigurationDataForApiTests;
import api.services.ServiceWorkShop;
import io.cucumber.java.en.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;

import java.io.File;

public class GetOptionsOfABoard extends ServiceWorkShop {

    @When("I do request for all options of the board")
    public void i_do_request_for_all_options_of_the_board() {

        ConfigurationDataForApiTests.commonResponseBetweenSteps = getBoardService().getBoard(ConfigurationDataForApiTests.BoardTestData.boardId);
    }

    @When("I change {string} of a board to {string}")
    public void i_change_of_a_board_to(String optionName, String newOptionValue) {
        ConfigurationDataForApiTests.commonResponseBetweenSteps = getBoardService().updateBoard(ConfigurationDataForApiTests.BoardTestData.boardId, optionName, newOptionValue);
        System.out.println(ConfigurationDataForApiTests.commonResponseBetweenSteps.asPrettyString());
    }

    @Then("I got back requested {string}")
    public void i_got_back_requested(String string) {

        Response currentResponse = getBoardService().getBoard(ConfigurationDataForApiTests.BoardTestData.boardId);
        String currentResource = currentResponse.jsonPath().getString(string);

        Assert.assertEquals(currentResource, ConfigurationDataForApiTests.commonResponseBetweenSteps.jsonPath().getString("_value"));

        System.out.println(ConfigurationDataForApiTests.commonResponseBetweenSteps.asPrettyString());
    }

    @Then("I got back requested options for {string}")
    public void iGotBackRequestedOptionsFor(String objectName) {

        File getBoardjsonSchema = new File("src/test/resources/get"+objectName+"JSONSchema.json");
        MatcherAssert.assertThat(
                "Validate json schema",
                ConfigurationDataForApiTests.commonResponseBetweenSteps.getBody().asString(),
                JsonSchemaValidator.matchesJsonSchema(getBoardjsonSchema));
    }

    @When("I do request to get {string} Of a board")
    public void iDoRequestToGetOfABoard(String optionName) {

        ConfigurationDataForApiTests.commonResponseBetweenSteps = getBoardService().getOptionOfABoard(ConfigurationDataForApiTests.BoardTestData.boardId, "/"+optionName);
    }
}
