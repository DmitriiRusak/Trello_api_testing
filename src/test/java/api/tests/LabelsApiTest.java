package api.tests;

import api.resourcesForTests.configurationData.CommonConfigData;
import api.resourcesForTests.configurationData.LabelTestData;
import api.services.ServiceWorkShop;
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
public class LabelsApiTest extends ServiceWorkShop {

    private LabelTestData labelTestData = new LabelTestData();

    @BeforeClass
    public void setUp() {

        LogFactory.getLogger().info("+++++++++++++++ class \uD83D\uDFE1" + this.getClass().getName() + "\uD83D\uDFE1 started +++++++++++++++");
        labelTestData.setBoardId(getLabelsService().createABord(labelTestData.BOARD_NAME));
    }

    @AfterClass
    public void tearDown() {
        getLabelsService().deleteBoard(labelTestData.getBoardId());
    }

    @Test()
    @Description("Create a new Label on a Board")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateLabel() {
        Response response = getLabelsService().createLabel(labelTestData.LABEL_NAME, labelTestData.COLOR, labelTestData.getBoardId());

        labelTestData.setLabelId(response.body().jsonPath().get("id"));

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().jsonPath().get("name"), labelTestData.LABEL_NAME);
        Assert.assertEquals(response.body().jsonPath().get("color"), labelTestData.COLOR);
    }

    @Test(priority = 1)
    @Description("Get label")
    @Severity(SeverityLevel.NORMAL)
    public void testGetLabel() {
        Response response = getLabelsService().getLabel(labelTestData.getLabelId());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().jsonPath().getString("id"), labelTestData.getLabelId());
    }

    @Test(priority = 1)
    @Description("Update label")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateLabel() {
        Response response = getLabelsService().updateLabel(labelTestData.getLabelId(), labelTestData.NEW_NAME, labelTestData.NEW_COLOR);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.body().jsonPath().getString("name"), labelTestData.NEW_NAME);
        Assert.assertEquals(response.body().jsonPath().getString("color"),labelTestData.NEW_COLOR);
    }

    @Test(priority = 2)
    @Description("Delete label")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteLabel() {
        Response response = getLabelsService().deleteLabel(labelTestData.getLabelId());

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
