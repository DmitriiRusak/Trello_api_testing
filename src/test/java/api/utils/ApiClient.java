package api.utils;

import api.cucumber.runners.EndToEndBasicSetUpTest;
import api.cucumber.runners.EndToEndCustomerEnvironmentTest;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;

public class ApiClient {

    private static ApiClient instance;
    private static QueryableRequestSpecification queryableRequestSpecification;
    private static Response response;
    public static ApiClient getInstance() {

        if (instance == null) {
            instance = new ApiClient();
        }

        return instance;
    }

    public Response get(String path, RequestSpecification requestSpecification) {

        queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);
        response = requestSpecification.get(path);

        restAssuredLogsIntegration(queryableRequestSpecification, response);
        cucumberLogsIntegration(queryableRequestSpecification, response);

        return response;
    }

    public Response post(String path, RequestSpecification requestSpecification) {

        queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);
        response = requestSpecification.post(path);

        restAssuredLogsIntegration(queryableRequestSpecification, response);
        cucumberLogsIntegration(queryableRequestSpecification, response);

        return response;
    }

    public Response put(String path, RequestSpecification requestSpecification) {

        queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);
        response = requestSpecification.put(path);

        restAssuredLogsIntegration(queryableRequestSpecification, response);
        cucumberLogsIntegration(queryableRequestSpecification, response);

        return response;
    }

    public Response delete(String path, RequestSpecification requestSpecification) {

        queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);
        response = requestSpecification.delete(path);

        restAssuredLogsIntegration(queryableRequestSpecification, response);
        cucumberLogsIntegration(queryableRequestSpecification, response);

        return response;
    }

    public void restAssuredLogsIntegration(QueryableRequestSpecification queryableRequestSpecification, Response response) {

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTrace) {
            if (element.getClassName().contains("api.tests.")) { //if in a stack presented apiTests, then log them in a file. If it is cucumber scenarios - ignore them, since I use cucumber event listener to log them
                LogFactory.getLogger().info("Request credentials:");
                LogFactory.getLogger().info("URL " + queryableRequestSpecification.getURI());
                LogFactory.getLogger().info("HTTP method - " + queryableRequestSpecification.getMethod());
                LogFactory.getLogger().info("Content type - " + queryableRequestSpecification.getContentType());
                LogFactory.getLogger().info("Response credentials:");
                LogFactory.getLogger().info("response statusCode - " + response.getStatusCode());

                if (response.getStatusCode() < 200 || response.getStatusCode() > 299) {

                    LogFactory.getLogger().error("Request failed");
                    LogFactory.getLogger().error(response.getBody().asPrettyString());
                }

                LogFactory.getLogger().info("END OF REQUEST");
                LogFactory.getLogger().info("");
            }
        }
    }

    public void cucumberLogsIntegration(QueryableRequestSpecification queryableRequestSpecification, Response response){
        LogFactory.getLogger().info("URL - " + queryableRequestSpecification.getURI());
        LogFactory.getLogger().info("HTTP method - " + queryableRequestSpecification.getMethod());

        if (response.getStatusCode() < 200 || response.getStatusCode() > 299) {
            LogFactory.getLogger().error("Body response - " + response.asPrettyString());
            LogFactory.getLogger().error("status code " +  String.valueOf(response.getStatusCode()));
            EndToEndCustomerEnvironmentTest.stepStatusPassed = false;
//            LogFactory.getLogger().error("❌");
        }else {
            LogFactory.getLogger().info("status code " +  String.valueOf(response.getStatusCode()));
//            LogFactory.getLogger().info("✔\uFE0F");
        }
    }
}