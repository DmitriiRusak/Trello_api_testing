package api.utils;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class MyRestAssuredFilter implements Filter {

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        logRequest(requestSpec);
        Response response = ctx.next(requestSpec, responseSpec);//когда мы прервали запрос/ответ, то эта инструкция возобновляет
        //этот запрос/ответ по обычному сценарию.
        logResponse(responseSpec);
        return response;             //will return to the test for assertions
    }

    public void logRequest(FilterableRequestSpecification requestSpec){

//        LogFactory.getLogger().always();
//        LogFactory.getLogger().info("Request ContentType - {}", requestSpec.getContentType());
//        LogFactory.getLogger().info("HTTP method - {}", requestSpec.getMethod());
//        LogFactory.getLogger().info("HTTP body " + requestSpec.getBody());
    }

    public void logResponse(FilterableResponseSpecification responseSpec){


//        LogFactory.getLogger().info("");
//        LogFactory.getLogger().info("Response credentials:");
//        LogFactory.getLogger().info("response statusCode - " + response.getStatusCode());
//        if(response.getStatusCode() < 200 || response.getStatusCode() > 299){
//            CommonConfigData.urlForCucumberEventListener = filterableRequestSpecification.getURI();
//        }
    }
}
