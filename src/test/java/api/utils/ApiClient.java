package api.utils;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApiClient {

    private static ApiClient instance;
    private static final Logger logger = LogManager.getLogger(ApiClient.class);

    public static ApiClient getInstance() {

        if (instance == null) {
            logger.info("Initializing new ApiClient instance");
            instance = new ApiClient();
        }

        return instance;
    }

    public Response get(String path, RequestSpecification requestSpecification) {
        logger.info("Executing GET request to: " + path);

        return requestSpecification.get(path);
    }

    public Response post(String path, RequestSpecification requestSpecification) {
        logger.info("Executing POST request to: " + path);

        return requestSpecification.post(path);
    }

    public Response put(String path, RequestSpecification requestSpecification) {
        logger.info("Executing PUT request to: " + path);

        return requestSpecification.put(path);
    }

    public Response patch(String path, RequestSpecification requestSpecification) {
        logger.info("Executing PUT request to: " + path);

        return requestSpecification.patch(path);
    }

    public Response delete(String path, RequestSpecification requestSpecification) {
        logger.info("Executing DELETE request to: " + path);

        return requestSpecification.delete(path);
    }
}