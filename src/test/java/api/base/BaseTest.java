package api.base;

import api.controllers.*;
import api.utils.ListeningConfig;
import org.testng.annotations.Listeners;

@Listeners(ListeningConfig.class)
public class BaseTest {

    private final BoardSteps boardSteps = new BoardSteps();
    private final ActionsSteps actionsSteps = new ActionsSteps();
    private final CardsSteps cardsSteps = new CardsSteps();
    private final LabelsSteps labelsSteps = new LabelsSteps();
    private final MembersSteps membersSteps = new MembersSteps();
    private final ChecklistsSteps checklistsSteps = new ChecklistsSteps();
    private final ListsSteps listsSteps = new ListsSteps();

    public BoardSteps getBoardSteps() {
        return boardSteps;
    }

    public ChecklistsSteps getChecklistsSteps() {
        return checklistsSteps;
    }

    public ListsSteps getListsSteps() {
        return listsSteps;
    }

    public ActionsSteps getActionsSteps() {
        return actionsSteps;
    }

    public CardsSteps getCardsSteps() {
        return cardsSteps;
    }

    public LabelsSteps getLabelsSteps() {
        return labelsSteps;
    }

    public MembersSteps getMembersSteps() {
        return membersSteps;
    }
}
