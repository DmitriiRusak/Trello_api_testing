package api.utils;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class MyRestAssuredFilter implements Filter {

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {

        logRequest(requestSpec);// <- в этом методе я собираю данные риквеста.

        //Далее, если требуется собрать данные риспонса, надо, чтобы программа выполнила риквест и записать это всё во временный респонс.

        Response response = ctx.next(requestSpec, responseSpec);//выполняем HTTP запрос и записываем всё во временный респонс.

        logResponse(response); //Используем респонс для сбора информации по респонсу.

        return response;             //will return to the test for assertions
    }

    public void logRequest(FilterableRequestSpecification requestSpec){

        LogFactory.getLogger().info("Request credentials:");

        LogFactory.getLogger().info("URL - {}", requestSpec.getURI());
        LogFactory.getLogger().info("HTTP method - {}", requestSpec.getMethod());
        LogFactory.getLogger().info("Request ContentType - {}", requestSpec.getContentType());
//        LogFactory.getLogger().info("HTTP body " + requestSpec.getBody());
    }

    public void logResponse(Response response){

        LogFactory.getLogger().info("Response credentials:");

        if (response.getStatusCode() < 200 || response.getStatusCode() > 299) {
            LogFactory.getLogger().error("Something wrong with configuration of a request");
            LogFactory.getLogger().error("Body response - " + response.asPrettyString());
            LogFactory.getLogger().error("status code " + response.getStatusCode());
        }
        //            CucumberHooks.stepStatusPassed = false;
//            LogFactory.getLogger().error("❌");
        else {
            LogFactory.getLogger().info("status code " + response.getStatusCode());
            LogFactory.getLogger().info("END OF REQUEST");
            LogFactory.getLogger().info("");

//            LogFactory.getLogger().info("✔\uFE0F");
        }

    }
}
