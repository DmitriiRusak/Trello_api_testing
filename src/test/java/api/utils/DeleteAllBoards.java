package api.utils;

import api.resourcesForTests.PathParameters.*;
import api.services.BaseService;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static api.resourcesForTests.PathParameters.MembersPath.MEMBERS_BASE_PATH;

/// this class deletes all the board on a workspace.
// If you commented out deleteBoard() method in any of the steps for debug purpose, and found a lot of boards created
// than you don`t need delete all the boards manually, just run main() method.///
public class DeleteAllBoards extends BaseService {

    public List getAllTheBoardsId(List list) {
        Response response = apiClient.get(MEMBERS_BASE_PATH + "me" + BoardEndPoints.BOARDS_BASE_PATH, requestSpecification);
        list = response.jsonPath().getList("id");
        return list;
    }

    public static void main(String[] args) {
        DeleteAllBoards deleteAllBoards = new DeleteAllBoards();

        List<String> listOfAllBoardsId = new ArrayList<>();

        listOfAllBoardsId = deleteAllBoards.getAllTheBoardsId(listOfAllBoardsId);

        for (int i = 0; i < listOfAllBoardsId.size(); i++) {
            deleteAllBoards.deleteBoard(listOfAllBoardsId.get(i));
        }
    }
}
