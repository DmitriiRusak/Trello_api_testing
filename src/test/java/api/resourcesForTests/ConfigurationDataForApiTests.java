package api.resourcesForTests;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;

//This class mostly consist of common static variables that are being used by test classes,
//for non Cucumber part of a project, and static nested classes that are named accordingly to a tests classes for which
//they hold variables.

final public class ConfigurationDataForApiTests {

    private static final String BASE_BOARD_NAME = "Board for ";
    public static final String EMPTY_STRING = "[]";
    public static final String EXPECTED_EMPTY_STRING_RESULT = "[:]";
    public final static String FIELD_NAME = "name";

    public static String urlForCucumberEventListener="";

    public static Response commonResponseBetweenSteps;
    public static List universalListForResource;

    public final static class BoardTestData {

        public final static String BOARD_NAME = BASE_BOARD_NAME + "Board";
        public final static String BOARD_NAME_CREATED_WITH_SPECIFIC_OPTIONS = BASE_BOARD_NAME + "specific options";
        public final static String NAME_FOR_LIST = "List test API";
        public final static String NAME_FOR_A_LABEL = "Api_Label";
        public final static String COLOR_OF_A_LABEL = "red";
        public final static String EXPECTED_RESULT = "[]";
        public final static String NAME_OF_A_FILTER = "closed";
        public final static String NEW_NAME_FOR_A_BOARD = "New BoardApiTest";
        public final static String PERMISSION_LEVEL_PUBLIC = "public";
        public static String boardId;
        public static String secondBoardId;
        public static String DefiendPermissionBoardId;
        public static String labelId;
        public static String listId;
    }

    public final static class ActionsTestData {

        public final static String BOARD_NAME = BASE_BOARD_NAME + "Actions";
        public static String actiontId;
        public static String cardId;
        public static String actionIdAfterCreatingACard;
        public static String idMemberCreator;
        public static String idOrganizationThatBelongToAnAction;
        public static String idOfReaction;
    }

    public final static class CardsTestData {

        public final static String BOARD_NAME_FOR_CARDS = "Board for cards";
        public static final String NAME_FOR_CHECKLIST_CREATED = "Checklist for CardsAPITest";
        public static String cardId;
        public static String listIdTheCardIsOn;
        public static String currentListId;
        public static String createdAttachmentId;
    }

    public final static class CheckListsTestData {

        public static final String BOARD_NAME_FOR_CHECKLIST = "Board for Checklists";
        public static final String nameOfAChecklistCreated = "First Checklist";
        public static final String NEW_NAME_FOR_CHECKLIST = "New name for checklist";
        public static final String nameForNewCheckItem = "Mark";
        public static String checklistId;
        public static String checkItemId;
    }

    public final static class LabelsTestData {

        public static final String BOARD_NAME = BASE_BOARD_NAME + "Labels";
        public static final String LABEL_NAME = "Label from API";
        public static final String COLOR = "red";
        public static final String NEW_NAME = "New Label from API";
        public static final String NEW_COLOR = "blue";
        public static String labelId;
    }

        public final static class ListsTestData {

            public static final String bordName = "Board for lists";
            public static final String newNameForTheList = "List with Updated name";
            public static final String nameOfTheList = "List from API";
            public static final String nameForSecondBoard = "Board_for_moving_lists";
            public static String toDoListId;
            public static List idsOfAllListsPresentedOnABoard;
            public static String newCreatedListId;
            public static HashMap<String, String> namesAndIdsOfLists = new HashMap<>();
        }

        public final static class MembersTestData {

            public static final String BOARD_NAME_FOR_MEMBERS = BASE_BOARD_NAME + "Members";
            public static final String BOARD_STAR_NOT_FOUND_MESSAGE = "Board star not found";
            public static final String POSITION = "top";
            public static final String UPDATE_POSITION = "bottom";
            public static String firstMemberId;
            public static String backgroundId;
            public static String starId;
        }

}