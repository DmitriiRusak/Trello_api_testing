package api.utils;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Specification {

    private String key;
    private String token;


    public Specification () {
        try {
            key = ConfigLoader.getProperty("key");
            token = ConfigLoader.getProperty("token");
        } catch (IOException e) {
//            throw new RuntimeException(e);
            System.out.println("Something wrong with key and token for Trello API");
        }
    }

    public RequestSpecification installRequest() {

        Map<String, String> authoriazing = new HashMap<>();
        authoriazing.put("key", key);
        authoriazing.put("token", token);

            return new RequestSpecBuilder().
                addFilter(new AllureRestAssured()).
                addFilter(new MyRestAssuredFilter()).
                setContentType(ContentType.JSON).
                addQueryParams(authoriazing).
                setBaseUri("https://api.trello.com/1/").
                build();

    }

}


