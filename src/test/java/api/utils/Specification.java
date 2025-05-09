package api.utils;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Specification {

    private static String key;
    private static String token;

    static {
        try {
            key = ConfigLoader.getProperty("key");
            token = ConfigLoader.getProperty("token");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static RequestSpecification installRequest() {
        Map<String, String> authoriazing = new HashMap<>();
        authoriazing.put("key", key);
        authoriazing.put("token", token);

        return new RequestSpecBuilder()
                .log(LogDetail.ALL)
                .addFilter(new AllureRestAssured())
                .setContentType(ContentType.JSON)
                .addQueryParams(authoriazing)
                .setBaseUri("https://api.trello.com/1/")
                .build();
    }

    public static ResponseSpecification installResponse() {
        return new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();
    }
}


