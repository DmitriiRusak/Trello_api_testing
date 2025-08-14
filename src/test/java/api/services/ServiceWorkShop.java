package api.services;

import api.utils.LogFactory;
import org.testng.annotations.BeforeSuite;

public class ServiceWorkShop {

    private final BoardService boardService = new BoardService();
    private final ActionsService actionsService = new ActionsService();
    private final CardsService cardsService = new CardsService();
    private final LabelsService labelsService = new LabelsService();
    private final MembersService membersService = new MembersService();
    private final ChecklistsService checklistsService = new ChecklistsService();
    private final ListsService listsService = new ListsService();

    @BeforeSuite
    public void projectSetUp(){

        LogFactory.getLogger().info("");
        LogFactory.getLogger().info("******************************New run for Rest Assured framework started*************************");
    }

    public BoardService getBoardService() {
        return boardService;
    }

    public ChecklistsService getChecklistsService() {
        return checklistsService;
    }

    public ListsService getListsService() {
        return listsService;
    }

    public ActionsService getActionsService() {
        return actionsService;
    }

    public CardsService getCardsService() {
        return cardsService;
    }

    public LabelsService getLabelsService() {
        return labelsService;
    }

    public MembersService getMembersService() {
        return membersService;
    }
}
