//package api.cucumber.stepDefinition.board;
//
//import api.cucumber.continer.ConfigTestDataHolder;
//import api.resourcesForTests.configurationData.CommonConfigData;
//import api.services.ServiceWorkShop;
//import io.cucumber.java.en.*;
//import io.restassured.module.jsv.JsonSchemaValidator;
//import io.restassured.response.Response;
//import org.hamcrest.MatcherAssert;
//import org.testng.Assert;
//
//import java.io.File;
//
//public class GetOptionsOfABoard extends ServiceWorkShop {
//
//    private ConfigTestDataHolder configTestDataHolder;
//
//    @When("I do request for all options of the board")
//    public void i_do_request_for_all_options_of_the_board() {
//
//        configTestDataHolder.getCommonConfigData().setCommonResponseBetweenSteps(getBoardService().getBoard(configTestDataHolder.getBoardTestData().getBoardId()));
//    }
//
//    @When("I change {string} of a board to {string}")
//    public void i_change_of_a_board_to(String optionName, String newOptionValue) {
//        configTestDataHolder.getCommonConfigData().setCommonResponseBetweenSteps(getBoardService().updateBoard(configTestDataHolder.getBoardTestData().getBoardId(), optionName, newOptionValue));
//    }
//
//    @Then("I got back requested {string}")
//    public void i_got_back_requested(String string) {
//
//        Response currentResponse = getBoardService().getBoard(configTestDataHolder.getBoardTestData().getBoardId());
//        String currentResource = currentResponse.jsonPath().getString(string);
//
//        Assert.assertEquals(currentResource, configTestDataHolder.getCommonConfigData().getCommonResponseBetweenSteps().jsonPath().getString("_value"));
//    }
//
//    @Then("I got back requested options for {string}")
//    public void iGotBackRequestedOptionsFor(String objectName) {
//
//        File getBoardjsonSchema = new File("src/test/resources/get"+objectName+"JSONSchema.json");
//        MatcherAssert.assertThat(
//                "Validate json schema",
//                configTestDataHolder.getCommonConfigData().getCommonResponseBetweenSteps().getBody().asString(),
//                JsonSchemaValidator.matchesJsonSchema(getBoardjsonSchema));
//    }
//
//    @When("I do request to get {string} Of a board")
//    public void iDoRequestToGetOfABoard(String optionName) {
//
//        configTestDataHolder.getCommonConfigData().setCommonResponseBetweenSteps(
//                getBoardService().getOptionOfABoard(configTestDataHolder.getBoardTestData().getBoardId(), "/"+optionName));
//    }
//}
