package api.tests;

import api.services.ServiceWorkShop;
import api.resourcesForTests.ConfigurationDataForApiTests;
import api.utils.LogFactory;
import api.utils.TestListener;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static api.resourcesForTests.ConfigurationDataForApiTests.LabelsTestData.*;

@Epic("API Tests")
@Feature("Labels")
@Listeners(TestListener.class)
public class LabelsApiTest extends ServiceWorkShop {

    @BeforeClass
    public void setUp() {

        LogFactory.getLogger().info("+++++++++++++++ class \uD83D\uDFE1" + this.getClass().getName() + "\uD83D\uDFE1 started +++++++++++++++");
        ConfigurationDataForApiTests.BoardTestData.boardId = getLabelsService().createABord(BOARD_NAME);
    }

    @AfterClass
    public void tearDown() {
        getLabelsService().deleteBoard(ConfigurationDataForApiTests.BoardTestData.boardId);
    }

    @Test()
    @Description("Create a new Label on a Board")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateLabel() {
        Response response = getLabelsService().createLabel(LABEL_NAME, COLOR, ConfigurationDataForApiTests.BoardTestData.boardId);

        labelId = response.body().jsonPath().get("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().jsonPath().get("name"), LABEL_NAME);
        Assert.assertEquals(response.body().jsonPath().get("color"), COLOR);
    }

    @Test(priority = 1)
    @Description("Get label")
    @Severity(SeverityLevel.NORMAL)
    public void testGetLabel() {
        Response response = getLabelsService().getLabel(labelId);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().jsonPath().getString("id"), labelId);
    }

    @Test(priority = 1)
    @Description("Update label")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateLabel() {
        Response response = getLabelsService().updateLabel(labelId, NEW_NAME, NEW_COLOR);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().jsonPath().getString("name"), NEW_NAME);
        Assert.assertEquals(response.body().jsonPath().getString("color"), NEW_COLOR);
    }

    @Test(priority = 2)
    @Description("Delete label")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteLabel() {
        Response response = getLabelsService().deleteLabel(labelId);

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
