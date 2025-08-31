package api.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;

import static io.restassured.RestAssured.given;

public class ApiClient {

    private static ApiClient instance;
    public static ApiClient getInstance() {

        if (instance == null) {
            instance = new ApiClient();
        }

        return instance;
    }

    public Response get(String path, RequestSpecification requestSpecification) {

        requestSpecification = RestAssured.given(requestSpecification);

        Response response = given().
                spec(requestSpecification).
                get(path);
//        Response response = requestSpecification.get(path);

//        restAssuredLogsIntegration(requestSpecification, response);
//        cucumberLogsIntegration(requestSpecification, response);

        return response;
    }

    public Response post(String path, RequestSpecification requestSpecification) {

        requestSpecification = RestAssured.given(requestSpecification);

        Response response = given().
                spec(requestSpecification).
                post(path);
//        Response response = requestSpecification.post(path);

//        restAssuredLogsIntegration(requestSpecification, response);
//        cucumberLogsIntegration(requestSpecification, response);

        return response;
    }

    public Response put(String path, RequestSpecification requestSpecification) {

        requestSpecification = RestAssured.given(requestSpecification);

        Response response = given().
                spec(requestSpecification).
                put(path);
//        Response response = requestSpecification.put(path);

//        restAssuredLogsIntegration(requestSpecification, response);
//        cucumberLogsIntegration(requestSpecification, response);

        return response;
    }

    public Response delete(String path, RequestSpecification requestSpecification) {

        requestSpecification = RestAssured.given(requestSpecification);

        Response response = given().
                spec(requestSpecification).
                delete(path);

//         Response response = requestSpecification.delete();

//        restAssuredLogsIntegration(requestSpecification, response);
//        cucumberLogsIntegration(requestSpecification, response);

        return response;
    }

//    public void restAssuredLogsIntegration(RequestSpecification requestSpecification, Response response) {
//
//        QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);
//
//        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
//        for (StackTraceElement element : stackTrace) {
////            if (element.getClassName().contains("api.tests.")) { //if in a stack presented apiTests, then log them in a file. If it is cucumber scenarios - ignore them, since I use cucumber event listener to log them
////                LogFactory.getLogger().info("Request credentials:");
////                LogFactory.getLogger().info("URL " + queryableRequestSpecification.getURI());
////                LogFactory.getLogger().info("HTTP method - " + queryableRequestSpecification.getMethod());
////                LogFactory.getLogger().info("Content type - " + queryableRequestSpecification.getContentType());
////                LogFactory.getLogger().info("Response credentials:");
////                LogFactory.getLogger().info("response statusCode - " + response.getStatusCode());
//
//                if (response.getStatusCode() < 200 || response.getStatusCode() > 299) {
//
//                }
//
//            }


//    public void cucumberLogsIntegration(RequestSpecification requestSpecification, Response response){
//
//        if (response.getStatusCode() < 200 || response.getStatusCode() > 299) {
//            LogFactory.getLogger().error("Body response - " + response.asPrettyString());
//            LogFactory.getLogger().error("status code " + response.getStatusCode());
//            CucumberHooks.stepStatusPassed = false;
////            LogFactory.getLogger().error("❌");
//        }else {
//            LogFactory.getLogger().info("status code " + response.getStatusCode());
////            LogFactory.getLogger().info("✔\uFE0F");
//        }
//    }
}