package api.tests;

import api.resourcesForTests.CheckListFields;
import api.resourcesForTests.configurationData.ConfigTestData;
import api.services.ChecklistsService;
import api.utils.LogFactory;
import api.utils.TestListener;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.HashMap;


@Epic("API Tests")
@Feature("Checklist")
@Listeners(TestListener.class)
public class ChecklistsAPITest{

    private ConfigTestData configTestData = new ConfigTestData();
    private final ChecklistsService checklistsService = new ChecklistsService();


    @BeforeClass
    public void setUp() {

        LogFactory.getLogger().info("+++++++++++++++ class \uD83D\uDFE1" + this.getClass().getName() + "\uD83D\uDFE1 started +++++++++++++++");
        configTestData.setBoardId(checklistsService.createABord(configTestData.BOARD_NAME));
        configTestData.setToDoListId(checklistsService.getListOfIdOfAllListsOnABoard(configTestData.getBoardId()).get(0).toString());

        configTestData.setCardId(checklistsService.
                createACard(new HashMap<>() {{
                    put("idList", configTestData.getToDoListId());
                    put("name", "card");
                }})
                .jsonPath().getString("id"));
    }

    @AfterClass
    public void tearDown() {
        checklistsService.deleteBoard(configTestData.getBoardId());
    }

    @Test(priority = 0)
    @Description("Create a checklist on a card")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateAChecklist() {
        Response response = checklistsService.createChecklistFromCheckListService(configTestData.getCardId(), configTestData.NAME_OF_CHECKLIST_CREATED);

        String actualNameOfChecklistReceived = response.jsonPath().getString("name");
        configTestData.setChecklistId(response.jsonPath().getString("id"));

        Assert.assertEquals(actualNameOfChecklistReceived, configTestData.NAME_OF_CHECKLIST_CREATED);
    }

    @Test(priority = 1)
    @Description("Get a checklist on a card")
    @Severity(SeverityLevel.NORMAL)
    public void testGetAChecklist() {
        Response response = checklistsService.getCheckList(configTestData.getChecklistId());
        String actualIdOfChecklistReceived = response.jsonPath().getString("id");

        Assert.assertEquals(actualIdOfChecklistReceived, configTestData.getChecklistId());
    }

    @Test(priority = 2)
    @Description("Update a name of a checklist")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateAChecklist() {
        Response response = checklistsService.updateAFieldOfCheckList(configTestData.getChecklistId(),
                CheckListFields.name, configTestData.NEW_NAME_FOR_CHECKLIST);

        String actualNameOfChecklistReceived = response.jsonPath().getString("name");

        Assert.assertEquals(actualNameOfChecklistReceived, configTestData.NEW_NAME_FOR_CHECKLIST);
    }

    @Test(priority = 3)
    @Description("Get a 'name' field on a checklist")
    @Severity(SeverityLevel.NORMAL)
    public void testGetANameOfAChecklist() {
        Response response = checklistsService.getFieldOnAChecklist(configTestData.getChecklistId(), CheckListFields.name);
        String actualNameOfAChecklistReceivedBack = response.jsonPath().getString("_value");

        Assert.assertEquals(actualNameOfAChecklistReceivedBack, configTestData.NEW_NAME_FOR_CHECKLIST);

    }

    @Test(priority = 3)
    @Description("Get a board checklist is on")
    @Severity(SeverityLevel.NORMAL)
    public void testGetABoardTheChecklistIsOn() {
        Response response = checklistsService.getTheBoardTheChecklistIsOn(configTestData.getChecklistId());
        String actualIdOfABoardReceived = response.jsonPath().getString("id");

        Assert.assertEquals(actualIdOfABoardReceived, configTestData.getBoardId());
    }

    @Test(priority = 3)
    @Description("Get the card checklist is on")
    @Severity(SeverityLevel.NORMAL)
    public void testGetACardTheChecklistIsOn() {
        Response response = checklistsService.getTheCardAChecklistIsOn(configTestData.getChecklistId());
        String actualIdOfACardReceived = response.jsonPath().getString("id");
        actualIdOfACardReceived = actualIdOfACardReceived.substring(1, actualIdOfACardReceived.length() - 1);  //have to remove square brackets

        Assert.assertEquals(actualIdOfACardReceived, configTestData.getCardId());
    }

    @Test(priority = 3)
    @Description("Get all checkItems presented on a checklist")
    @Severity(SeverityLevel.NORMAL)
    public void testGetCheckitemsOnAChecklist() {
        Response response = checklistsService.getCheckitemsOnAChecklist(configTestData.getChecklistId());
        String adtualCheckItemsOnAChecklist = response.body().asString();

        Assert.assertEquals(adtualCheckItemsOnAChecklist, configTestData.EXPECTED_RESULT);
    }

    @Test(priority = 4)
    @Description("Create new checkItem on a checklist")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateCheckitemOnChecklist() {
        Response response = checklistsService.createCheckitemOnChecklist(configTestData.getChecklistId(), configTestData.NAME_FOR_NEW_CHECKITEM);
        String actualNameOfNewCheckItem = response.jsonPath().getString("name");
        configTestData.setCheckItemId(response.jsonPath().getString("id"));

        Assert.assertEquals(actualNameOfNewCheckItem, configTestData.NAME_FOR_NEW_CHECKITEM);
    }

    @Test(priority = 5)
    @Description("Get specific checkItem on a checklist")
    @Severity(SeverityLevel.NORMAL)
    public void testGetACheckitemOnAChecklist() {
        Response response = checklistsService.getACheckitemOnAChecklist(configTestData.getChecklistId(), configTestData.getCheckItemId());
        String actualCheckItemIdReceived = response.jsonPath().getString("id");

        Assert.assertEquals(actualCheckItemIdReceived, configTestData.getCheckItemId());
    }

    @Test(priority = 6)
    @Description("Delete specific checkItem from checklist")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteCheckitemFromChecklist() {
        Response response = checklistsService.deleteCheckitemFromChecklist(configTestData.getChecklistId(), configTestData.getCheckItemId());
        String emptyBody = response.jsonPath().getString("limits");

        Assert.assertEquals(emptyBody, configTestData.EXPECTED_EMPTY_STRING_RESULT);
    }

    @Test(priority = 7)
    @Description("Delete checklist")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteAChecklist() {
        Response response = checklistsService.deleteAChecklist(configTestData.getChecklistId());
        String emptyBody = response.jsonPath().getString("limits");

        Assert.assertEquals(emptyBody, configTestData.EXPECTED_EMPTY_STRING_RESULT);
    }
}
