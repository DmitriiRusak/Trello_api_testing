package api.tests;

import api.resourcesForTests.CheckListFields;
import api.resourcesForTests.configurationData.CheckListTestData;
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
import java.util.HashMap;

import static api.resourcesForTests.configurationData.CommonConfigData.EMPTY_STRING;
import static api.resourcesForTests.configurationData.CommonConfigData.EXPECTED_EMPTY_STRING_RESULT;

@Epic("API Tests")
@Feature("Checklist")
@Listeners(TestListener.class)
public class ChecklistsAPITest extends ServiceWorkShop {

    CheckListTestData checkListTestData = new CheckListTestData();

    @BeforeClass
    public void setUp() {

        LogFactory.getLogger().info("+++++++++++++++ class \uD83D\uDFE1" + this.getClass().getName() + "\uD83D\uDFE1 started +++++++++++++++");
        checkListTestData.setBoardId(getChecklistsService().createABord(checkListTestData.BOARD_NAME_FOR_CHECKLIST));
        checkListTestData.setToDoListId(getChecklistsService().getListOfIdOfAllListsOnABoard(checkListTestData.getBoardId()).get(0).toString());

        checkListTestData.setCardId(getChecklistsService().
                createACard(new HashMap<>() {{
                    put("idList", checkListTestData.getToDoListId());
                    put("name", "card");
                }})
                .jsonPath().getString("id"));
    }

    @AfterClass
    public void tearDown() {
        getChecklistsService().deleteBoard(checkListTestData.getBoardId());
    }

    @Test(priority = 0)
    @Description("Create a checklist on a card")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateAChecklist() {
        Response response = getChecklistsService().createAChecklist(checkListTestData.getCardId(), checkListTestData.NAME_OF_CHECKLIST_CREATED);

        String actualNameOfChecklistReceived = response.jsonPath().getString("name");
        checkListTestData.setChecklistId(response.jsonPath().getString("id"));

        Assert.assertEquals(actualNameOfChecklistReceived, checkListTestData.NAME_OF_CHECKLIST_CREATED);
    }

    @Test(priority = 1)
    @Description("Get a checklist on a card")
    @Severity(SeverityLevel.NORMAL)
    public void testGetAChecklist() {
        Response response = getChecklistsService().getCheckList(checkListTestData.getChecklistId());
        String actualIdOfChecklistReceived = response.jsonPath().getString("id");

        Assert.assertEquals(actualIdOfChecklistReceived, checkListTestData.getChecklistId());
    }

    @Test(priority = 2)
    @Description("Update a name of a checklist")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateAChecklist() {
        Response response = getChecklistsService().updateAFieldOfCheckList(checkListTestData.getChecklistId(),
                CheckListFields.name, checkListTestData.NEW_NAME_FOR_CHECKLIST);

        String actualNameOfChecklistReceived = response.jsonPath().getString("name");

        Assert.assertEquals(actualNameOfChecklistReceived, checkListTestData.NEW_NAME_FOR_CHECKLIST);
    }

    @Test(priority = 3)
    @Description("Get a 'name' field on a checklist")
    @Severity(SeverityLevel.NORMAL)
    public void testGetANameOfAChecklist() {
        Response response = getChecklistsService().getFieldOnAChecklist(checkListTestData.getChecklistId(), CheckListFields.name);
        String actualNameOfAChecklistReceivedBack = response.jsonPath().getString("_value");

        Assert.assertEquals(actualNameOfAChecklistReceivedBack, checkListTestData.NEW_NAME_FOR_CHECKLIST);

    }

    @Test(priority = 3)
    @Description("Get a board checklist is on")
    @Severity(SeverityLevel.NORMAL)
    public void testGetABoardTheChecklistIsOn() {
        Response response = getChecklistsService().getTheBoardTheChecklistIsOn(checkListTestData.getChecklistId());
        String actualIdOfABoardReceived = response.jsonPath().getString("id");

        Assert.assertEquals(actualIdOfABoardReceived, checkListTestData.getBoardId());
    }

    @Test(priority = 3)
    @Description("Get the card checklist is on")
    @Severity(SeverityLevel.NORMAL)
    public void testGetACardTheChecklistIsOn() {
        Response response = getChecklistsService().getTheCardAChecklistIsOn(checkListTestData.getChecklistId());
        String actualIdOfACardReceived = response.jsonPath().getString("id");
        actualIdOfACardReceived = actualIdOfACardReceived.substring(1, actualIdOfACardReceived.length() - 1);  //have to remove square brackets

        Assert.assertEquals(actualIdOfACardReceived, checkListTestData.getCardId());
    }

    @Test(priority = 3)
    @Description("Get all checkItems presented on a checklist")
    @Severity(SeverityLevel.NORMAL)
    public void testGetCheckitemsOnAChecklist() {
        Response response = getChecklistsService().getCheckitemsOnAChecklist(checkListTestData.getChecklistId());
        String adtualCheckItemsOnAChecklist = response.body().asString();

        Assert.assertEquals(adtualCheckItemsOnAChecklist, EMPTY_STRING);
    }

    @Test(priority = 4)
    @Description("Create new checkItem on a checklist")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateCheckitemOnChecklist() {
        Response response = getChecklistsService().createCheckitemOnChecklist(checkListTestData.getChecklistId(), checkListTestData.NAME_FOR_NEW_CHECKITEM);
        String actualNameOfNewCheckItem = response.jsonPath().getString("name");
        checkListTestData.setCheckItemId(response.jsonPath().getString("id"));

        Assert.assertEquals(actualNameOfNewCheckItem, checkListTestData.NAME_FOR_NEW_CHECKITEM);
    }

    @Test(priority = 5)
    @Description("Get specific checkItem on a checklist")
    @Severity(SeverityLevel.NORMAL)
    public void testGetACheckitemOnAChecklist() {
        Response response = getChecklistsService().getACheckitemOnAChecklist(checkListTestData.getChecklistId(), checkListTestData.getCheckItemId());
        String actualCheckItemIdReceived = response.jsonPath().getString("id");

        Assert.assertEquals(actualCheckItemIdReceived, checkListTestData.getCheckItemId());
    }

    @Test(priority = 6)
    @Description("Delete specific checkItem from checklist")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteCheckitemFromChecklist() {
        Response response = getChecklistsService().deleteCheckitemFromChecklist(checkListTestData.getChecklistId(), checkListTestData.getCheckItemId());
        String emptyBody = response.jsonPath().getString("limits");

        Assert.assertEquals(emptyBody, EXPECTED_EMPTY_STRING_RESULT);
    }

    @Test(priority = 7)
    @Description("Delete checklist")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteAChecklist() {
        Response response = getChecklistsService().deleteAChecklist(checkListTestData.getChecklistId());
        String emptyBody = response.jsonPath().getString("limits");

        Assert.assertEquals(emptyBody, EXPECTED_EMPTY_STRING_RESULT);
    }
}
