//package api.resourcesForTests.configurationData;
//
//import io.restassured.response.Response;
//
//import java.util.HashMap;
//import java.util.List;
//
////This class mostly consist of common static variables that are being used by test classes,
////for non Cucumber part of a project, and static nested classes that are named accordingly to a tests classes for which
////they hold variables.
//
//public class CommonConfigData {
//
//    // I removed all variables from RestAssured part of the project, I left the class exist in case I want to rebuild cucumber part.
//
//    public static final String EMPTY_STRING = "[]";
//    public static final String EXPECTED_EMPTY_STRING_RESULT = "[:]";
//    public static final String FIELD_NAME = "name";
//
//    private Response commonResponseBetweenSteps;
//    private List universalListForResource;
//
//    public Response getCommonResponseBetweenSteps() {
//        return commonResponseBetweenSteps;
//    }
//
//    public void setCommonResponseBetweenSteps(Response commonResponseBetweenSteps) {
//        this.commonResponseBetweenSteps = commonResponseBetweenSteps;
//    }
//
//    public List getUniversalListForResource() {
//        return universalListForResource;
//    }
//
//    public void setUniversalListForResource(List universalListForResource) {
//        universalListForResource = universalListForResource;
//    }
//}