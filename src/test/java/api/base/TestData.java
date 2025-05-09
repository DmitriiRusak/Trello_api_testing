package api.base;

import io.restassured.response.Response;
import org.testng.annotations.DataProvider;

final public class TestData {

    private static final String BASE_BOARD_NAME = "Board for ";
    public static Response response;

    public final static class BoardTestData {
        public final static String BOARD_NAME = BASE_BOARD_NAME + "Board";
        public final static String NAME_FOR_LIST = "List test API";
        public final static String NAME_FOR_A_LABEL = "Api_Label";
        public final static String COLOR_OF_A_LABEL = "red";
        public final static String FIELD_NAME = "/name";
        public final static String EXPECTED_RESULT = "[]";
        public final static String NAME_OF_A_FILTER = "closed";
        public final static String NEW_NAME_FOR_A_BOARD = "New BoardApiTest";

        public static String boardId;
        public static String boardName;
        public static String labelId;
        public static String listId;
    }

    public final static class ActionsTestData {

        public final static String BOARD_NAME = BASE_BOARD_NAME + "Actions";

        public static String boardId;
        public static String toDoListId;
        public static String actiontId;
        public static String cardId;
        public static String actionIdAfterCreatingACard;
        public static String idMemberCreator;
        public static String idOrganizationThatBelongToAnAction;
        public static String idOfReaction;
    }

    public final static class CardsTestData {

        public final static String BOARD_NAME = BASE_BOARD_NAME + "Cards";
        public static final String emptyString = "[]";
        public static final String NAME_FOR_CHECKLIST_CREATED = "Checklist for CardsAPITest";

        public static String cardId;
        public static String boardId;
        public static String listId;
        public static String createdAttachmentId;
    }

    public final static class CheckListsTestData {

        public static final String bordName = "Board for Checklists";
        public static final String nameOfAChecklistCreated = "First Checklist";
        public static final String nameOfAFieldToBeUpdated = "name";
        public static final String valueForAFieldToBeUpdated = "New name for checklist";
        public static final String expectedPosOfAChecklist = "16384";
        public static final String fieldToGetBackFromTheChecklist = "/pos";
        public static final String emptyString = "[]";
        public static final String expectedStringResult = "[:]";
        public static final String nameForNewCheckItem = "Mark";
        public static String boardId;
        public static String cardId;
        public static String checklistId;
        public static String checkItemId;
        public static String toDoListId;
    }

    public final static class LabelsTestData {

        public static final String BOARD_NAME = BASE_BOARD_NAME + "Labels";
        public static final String LABEL_NAME = "Label from API";
        public static final String COLOR = "red";
        public static final String NEW_NAME = "New Label from API";
        public static final String NEW_COLOR = "blue";
        public static String boardId;
        public static String labelId;

        @DataProvider(name = "createUpdateFieldLabel")
        public Object[][] createUpdateFieldLabel() {
            return new Object[][]{
                    {"color", "green"},
                    {"name", "Name field Label from API"},
            };
        }
    }

    public final static class ListsTestData {

        public static final String bordName = "Board for lists";
        public static final String newNameForTheList = "List with Updated name";
        public static final String nameOfTheList = "List from API";
        public static final String nameForSecondBoard = "Board_for_moving_lists";
        public static String boardId;
        public static String toDoListId;
        public static String newCreatedListId;
        public static boolean subscribeValue = true;
    }

    public final static class MembersTestData {

        public static final String BORD_NAME = BASE_BOARD_NAME + "Members";
        public static final String POS = "top";
        public static final String UPDATE_POS = "bottom";
        public static String boardId;
        public static String firstMemberId;
        public static String backgroundId;
        public static String starId;
    }
}