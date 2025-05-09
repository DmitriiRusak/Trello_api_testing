package api.cucumber.steps;

import api.base.BaseTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import static api.base.TestData.BoardTestData.boardId;
import static api.base.TestData.LabelsTestData.NEW_NAME;
import static api.base.TestData.LabelsTestData.labelId;
import static api.base.TestData.response;

public class CucumberStepsLabels extends BaseTest {

    @When("Я создаю доску {string}")
    public void createBoardRU(String boardName) {
        response = getBoardSteps().createBoard(boardName);
        boardId = response.jsonPath().getString("id");
    }

    @Then("Я получаю код ответа {string}")
    public void checkCode(String code) {
        Assert.assertEquals(response.getStatusCode(), Integer.parseInt(code));
    }

    @And("Я удаляю доску")
    public void delete() {
        getBoardSteps().deleteBoard(boardId);
    }

    @When("I create a new Label on a Board with {string} and {string}")
    public void createLabelWithColor(String labelName, String color) {
        response = getLabelsSteps().createLabel(labelName, color, boardId);
        labelId = response.body().jsonPath().get("id");
    }

    @Then("The response status code should be {string}")
    public void checkStatusCode(String statusCode) {
        Assert.assertEquals(response.getStatusCode(), Integer.parseInt(statusCode));
    }

    @When("I get label")
    public void getLabel() {
        response = getLabelsSteps().getLabel(labelId);
    }

    @And("I update label name {string} and color {string}")
    public void updateLabel(String name, String color) {
        response = getLabelsSteps().updateLabel(labelId, name, color);
    }

    @And("Check name {string}")
    public void checkName(String name) {
        Assert.assertEquals(response.body().jsonPath().getString("name"), name);
    }
}
