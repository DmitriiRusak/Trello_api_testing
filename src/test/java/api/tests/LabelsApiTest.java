package api.tests;

import api.resourcesForTests.configurationData.ConfigTestData;
import api.services.LabelsService;
import api.utils.LogFactory;
import api.utils.TestListener;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Epic("API Tests")
@Feature("Labels")
@Listeners(TestListener.class)
public class LabelsApiTest{

    private ConfigTestData configTestData = new ConfigTestData();
    private final LabelsService labelsService = new LabelsService();

    @BeforeClass
    public void setUp() {

        LogFactory.getLogger().info("+++++++++++++++ class \uD83D\uDFE1" + this.getClass().getName() + "\uD83D\uDFE1 started +++++++++++++++");
        configTestData.setBoardId(labelsService.createABord(configTestData.BOARD_NAME));
    }

    @AfterClass
    public void tearDown() {
        labelsService.deleteBoard(configTestData.getBoardId());
    }

    @Test()
    @Description("Create a new Label on a Board")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateLabel() {
        Response response = labelsService.createLabel(configTestData.LABEL_NAME, configTestData.COLOR, configTestData.getBoardId());

        configTestData.setLabelId(response.body().jsonPath().get("id"));

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().jsonPath().get("name"), configTestData.LABEL_NAME);
        Assert.assertEquals(response.body().jsonPath().get("color"), configTestData.COLOR);
    }

    @Test(priority = 1)
    @Description("Get label")
    @Severity(SeverityLevel.NORMAL)
    public void testGetLabel() {
        Response response = labelsService.getLabel(configTestData.getLabelId());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().jsonPath().getString("id"), configTestData.getLabelId());
    }

    @Test(priority = 1)
    @Description("Update label")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateLabel() {
        Response response = labelsService.updateLabel(configTestData.getLabelId(), configTestData.NEW_NAME, configTestData.NEW_COLOR);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().jsonPath().getString("name"), configTestData.NEW_NAME);
        Assert.assertEquals(response.body().jsonPath().getString("color"),configTestData.NEW_COLOR);
    }

    @Test(priority = 2)
    @Description("Delete label")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteLabel() {
        Response response = labelsService.deleteLabel(configTestData.getLabelId());

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
