package api.tests;

import api.resourcesForTests.configurationData.LabelTestData;
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

    private final LabelTestData labelTestData = new LabelTestData();
    private final LabelsService labelsService = new LabelsService();

    @BeforeClass
    public void setUp() {

        LogFactory.getLogger().info("+++++++++++++++ class \uD83D\uDFE1" + this.getClass().getName() + "\uD83D\uDFE1 started +++++++++++++++");
        labelTestData.setBoardId(labelsService.createABord(labelTestData.getBOARD_NAME()));
    }

    @AfterClass
    public void tearDown() {
        labelsService.deleteBoard(labelTestData.getBoardId());
    }

    @Test()
    @Description("Create a new Label on a Board")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateLabel() {
        Response response = labelsService.createLabel(labelTestData.getLABEL_NAME(), labelTestData.getCOLOR(), labelTestData.getBoardId());

        labelTestData.setLabelId(response.body().jsonPath().get("id"));

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().jsonPath().get("name"), labelTestData.getLABEL_NAME());
        Assert.assertEquals(response.body().jsonPath().get("color"), labelTestData.getCOLOR());
    }

    @Test(priority = 1)
    @Description("Get label")
    @Severity(SeverityLevel.NORMAL)
    public void testGetLabel() {
        Response response = labelsService.getLabel(labelTestData.getLabelId());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().jsonPath().getString("id"), labelTestData.getLabelId());
    }

    @Test(priority = 1)
    @Description("Update label")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateLabel() {
        Response response = labelsService.updateLabel(labelTestData.getLabelId(), labelTestData.getNEW_NAME(), labelTestData.getNEW_COLOR());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().jsonPath().getString("name"), labelTestData.getNEW_NAME());
        Assert.assertEquals(response.body().jsonPath().getString("color"),labelTestData.getNEW_COLOR());
    }

    @Test(priority = 2)
    @Description("Delete label")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteLabel() {
        Response response = labelsService.deleteLabel(labelTestData.getLabelId());

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
