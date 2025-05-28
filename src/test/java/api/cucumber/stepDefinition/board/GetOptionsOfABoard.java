package api.cucumber.stepDefinition.board;

import api.base.BaseTest;
import api.base.TestData;
import io.cucumber.java.en.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;

import java.io.File;

public class GetOptionsOfABoard extends BaseTest {

    @When("I do request for all options of the board")
    public void i_do_request_for_all_options_of_the_board() {

        TestData.BoardTestData.commonResponseBetweenSteps = getBoardService().getBoard(TestData.BoardTestData.boardId);
    }

    @Then("I got back requested options")
    public void i_got_back_requested_options() {

        File getBoardjsonSchema = new File("src/test/resources/getBoardJSONSchema.json");
        MatcherAssert.assertThat(
                "Validate json schema",
                TestData.BoardTestData.commonResponseBetweenSteps.getBody().asString(),
                JsonSchemaValidator.matchesJsonSchema(getBoardjsonSchema));
    }

    @Then("I got back requested {string}")
    public void i_got_back_requested(String string) {

        Response currentResponse = getBoardService().getBoard(TestData.BoardTestData.boardId);
        String currentResource = currentResponse.jsonPath().getString(string);

        Assert.assertEquals(currentResource, TestData.BoardTestData.commonResponseBetweenSteps.jsonPath().getString("_value"));
    }

}
