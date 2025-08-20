package api.utils;

import api.resourcesForTests.PathParameters;
import api.resourcesForTests.PathParameters.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.List;

import static api.resourcesForTests.PathParameters.MembersPath.MEMBERS_BASE_PATH;

/// this class deletes all the board on a workspace.
// If you commented out deleteBoard() method in any of the steps for debug purpose, and found a lot of boards created
// than you don`t need delete all the boards manually, just run main() method.

//should be updated, since I change the logic of request specification distribution

public class DeleteAllBoards{

    public List getAllTheBoardsId(List list, RequestSpecification requestSpecification, ApiClient apiClient) {
        Response response = apiClient.get(MEMBERS_BASE_PATH + "me" + BoardEndPoints.BOARDS_BASE_PATH, requestSpecification);
        list = response.jsonPath().getList("id");
        return list;
    }

    public static void main(String[] args) {

        Specification specification = new Specification();
        RequestSpecification requestSpecification = RestAssured.given().spec(specification.installRequest());
        ApiClient apiClient = new ApiClient();

        DeleteAllBoards deleteAllBoards = new DeleteAllBoards();

        List<String> listOfAllBoardsId = new ArrayList<>();

        listOfAllBoardsId = deleteAllBoards.getAllTheBoardsId(listOfAllBoardsId, requestSpecification, apiClient);

        for (int i = 0; i < listOfAllBoardsId.size(); i++) {
            apiClient.delete(PathParameters.BoardEndPoints.BOARDS_BASE_PATH + listOfAllBoardsId.get(i), requestSpecification);
        }
    }
}
