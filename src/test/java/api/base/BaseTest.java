package api.base;

import api.controllers.*;
import api.utils.ListeningConfig;
import org.testng.annotations.Listeners;

@Listeners(ListeningConfig.class)
public class BaseTest {

    private final BoardService boardService = new BoardService();
    private final ActionsService actionsService = new ActionsService();
    private final CardsService cardsService = new CardsService();
    private final LabelsService labelsService = new LabelsService();
    private final MembersService membersService = new MembersService();
    private final ChecklistsService checklistsService = new ChecklistsService();
    private final ListsService listsService = new ListsService();

    public BoardService getBoardService() {
        return boardService;
    }

    public ChecklistsService getChecklistsSteps() {
        return checklistsService;
    }

    public ListsService getListsSteps() {
        return listsService;
    }

    public ActionsService getActionsSteps() {
        return actionsService;
    }

    public CardsService getCardsService() {
        return cardsService;
    }

    public LabelsService getLabelsSteps() {
        return labelsService;
    }

    public MembersService getMembersSteps() {
        return membersService;
    }
}
