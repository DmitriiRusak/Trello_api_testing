//package api.cucumber.stepDefinition.list;
//
//import api.cucumber.continer.ConfigTestDataHolder;
//import api.resourcesForTests.ListFields;
//import api.services.ServiceWorkShop;
//import api.resourcesForTests.PathParameters;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import io.restassured.response.Response;
//import org.testng.Assert;
//
//public class OptionsOfAList extends ServiceWorkShop {
//
//    private ConfigTestDataHolder configTestDataHolder;
//
//    public OptionsOfAList(ConfigTestDataHolder configTestDataHolder) {
//        this.configTestDataHolder = configTestDataHolder;
//    }
//
//    @When("I request the lists information")
//    public void iRequestTheListsInformation() {
//
//        configTestDataHolder.getCommonConfigData().setCommonResponseBetweenSteps(
//                getListsService().getOptionOfAnObject(PathParameters.endPoints.get("list"),
//                  configTestDataHolder.getListTestData().getToDoListId(),
//        "all", getListsService().getListRequestSpecification()));
//    }
//
//    @Then("I got back requested {string} of a list")
//    public void iGotBackRequestedOfA(String optionName, String resourceName) throws InterruptedException {
//
//        Response actualList = getListsService().getAList(configTestDataHolder.getListTestData().getToDoListId());
//
//        Assert.assertEquals(actualList.jsonPath().getString(optionName),
//                configTestDataHolder.getCommonConfigData().getCommonResponseBetweenSteps().jsonPath().getString(optionName));
//    }
//
//    @When("I do request to get {string} of a {string}")
//    public void iDoRequestToGetOfA(String optionName, String resourceName) {
//
//        String tempResourceId = "";
//        switch (resourceName){
//            case "list": tempResourceId = configTestDataHolder.getListTestData().getToDoListId();
//                break;
//            case "board": tempResourceId = configTestDataHolder.getBoardTestData().getBoardId();
//                break;
//            case "card": tempResourceId = configTestDataHolder.getCardTestData().getCardId();
//                break;
//        }
//
//        configTestDataHolder.getCommonConfigData().setCommonResponseBetweenSteps(getBoardService().
//                getOptionOfAnObject(PathParameters.endPoints.get(resourceName),tempResourceId, optionName, getListsService().getListRequestSpecification()));
//
//    }
//
//    @When("I update a list name {string} with new name {string}")
//    public void iUpdateAListWithNewName(String listName, String newNameForTheList) {
//
//       String idOfTheListToChangeTheName = getListsService().getIdOfAListByName(
//               listName, configTestDataHolder.getBoardTestData().getBoardId(),
//               configTestDataHolder.getListTestData());
//
//       getListsService().updateAFieldOfAList(idOfTheListToChangeTheName, ListFields.name, newNameForTheList);
//    }
//
//    @When("I update a list options {string} with new values {string}")
//    public void iUpdateAListOptionsWithNewValues(String names, String values) {
//        String[] optionNames = names.split(" ");
//        String[] optionValues = values.split(" ");
//
//        getListsService().updateAFieldsOfAList(configTestDataHolder.getListTestData().getToDoListId(), optionNames,optionValues);
//
//    }
//
//    @Then("A list options {string} is updated with values {string}")
//    public void aListOptionsIsUpdatedWithValues(String names, String values) {
//        String[] optionNames = names.split(" ");
//        String[] optionValues = values.split(" ");
//
//        Response currentResponse = getListsService().
//                getOptionOfAnObject(PathParameters.endPoints.get("list"),
//                        configTestDataHolder.getListTestData().getToDoListId(),
//                        "all",
//                        getListsService().getListRequestSpecification());
//        System.out.println(currentResponse.asPrettyString());
//        System.out.println(currentResponse.statusCode());
//
//        for(int i = 0; i < optionNames.length; i++){
//            System.out.println(currentResponse.jsonPath().getString(optionNames[i])+" <-> "+ optionValues[i]);
//            Assert.assertEquals(currentResponse.jsonPath().getString(optionNames[i]), optionValues[i]);
//        }
//
//    }
//
//    @Then("A name of the list {string} is changed to {string}")
//    public void aNameOfTheListIsChangedTo(String oldNameOfTheList, String newNameOfTheList) {
//
//        String idOfTheListWithChangedName = getListsService().getIdOfAListByName(
//                newNameOfTheList,
//                configTestDataHolder.getBoardTestData().getBoardId(),
//                configTestDataHolder.getListTestData());
//
//        String actualNameOfTheListOnBoard = getListsService().getAList(idOfTheListWithChangedName).jsonPath().getString("name");
//
//        Assert.assertEquals(actualNameOfTheListOnBoard, newNameOfTheList);
//        Assert.assertNotEquals(oldNameOfTheList, actualNameOfTheListOnBoard);
//    }
//}
