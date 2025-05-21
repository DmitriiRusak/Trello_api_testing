package api.cucumber.steps;
import api.controllers.BoardService;
import io.cucumber.java.en.*;

public class BoardStepDefinition {

    BoardService boardService;

    @Given("I am registered user in the Trello app")
    public void i_am_registered_user_in_the_trello_app() {
        boardService = new BoardService();

        throw new io.cucumber.java.PendingException();
    }

    @When("I create a board with default options")
    public void i_create_a_board_with_default_options() {


                throw new io.cucumber.java.PendingException();
    }

    @Then("A board is created")
    public void a_board_is_created() {

        throw new io.cucumber.java.PendingException();
    }

}
