package api.cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CriticalPathUiApiDefinitions {
    @Given("I am logged into Trello via UI")
    public void i_am_logged_into_trello_via_ui() {

    }


    @When("I create a new board named {string} via Trello UI")
    public void i_create_a_new_board_named_via_trello_ui(String string) {

    }
    @Then("the board should exist in Trello API response")
    public void the_board_should_exist_in_trello_api_response() {

    }
    @Then("the API response status code should be {int}")
    public void the_api_response_status_code_should_be(Integer int1) {

    }
    @Then("the board name in API should match {string}")
    public void the_board_name_in_api_should_match(String string) {

    }

    @Given("a board named {string} exists via API")
    public void a_board_named_exists_via_api(String string) {

    }

    @When("I delete the board via API")
    public void i_delete_the_board_via_api() {

    }

}
