package api.tests;

import api.base.BaseTest;
import api.base.TestData;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static api.base.TestData.LabelsTestData.*;

@Epic("API Tests")
@Feature("Labels Validation")
@Owner("Group JavaForwardToOffer")
public class LabelsApiTest extends BaseTest {

    @BeforeClass
    public void setUp() {
        boardId = getLabelsSteps().createABord(BOARD_NAME);
    }

    @AfterClass
    public void tearDown() {
        getLabelsSteps().deleteBoard(boardId);
    }

    @Test()
    @Story("Verify created label")
    @Description("Create a new Label on a Board")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateLabel() {
        Response response = getLabelsSteps().createLabel(LABEL_NAME, COLOR, boardId);

        labelId = response.body().jsonPath().get("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().jsonPath().get("name"), LABEL_NAME);
        Assert.assertEquals(response.body().jsonPath().get("color"), COLOR);
    }

    @Test(priority = 1)
    @Story("Verify get label")
    @Description("Get label")
    @Severity(SeverityLevel.NORMAL)
    public void testGetLabel() {
        Response response = getLabelsSteps().getLabel(labelId);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().jsonPath().getString("id"), labelId);
    }

    @Test(priority = 1)
    @Story("Verify update label")
    @Description("Update label")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateLabel() {
        Response response = getLabelsSteps().updateLabel(labelId, NEW_NAME, NEW_COLOR);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().jsonPath().getString("name"), NEW_NAME);
        Assert.assertEquals(response.body().jsonPath().getString("color"), NEW_COLOR);
    }

    @Test(priority = 2)
    @Story("Verify delete label")
    @Description("Delete label")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteLabel() {
        Response response = getLabelsSteps().deleteLabel(labelId);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 1, dataProvider = "createUpdateFieldLabel", dataProviderClass = TestData.LabelsTestData.class)
    @Story("Verify update field label")
    @Description("Update field label")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateFieldLabel(String field, String value) {
        Response response = getLabelsSteps().updateFieldLabel(labelId, field, value);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().jsonPath().getString(field), value);
    }
}
