package api.cucumber.continer;

import api.resourcesForTests.configurationData.*;

public class ConfigTestDataHolder {

    private final ActionTestData actionTestData = new ActionTestData();
    private final CardTestData cardTestData = new CardTestData();
    private final CheckListTestData checkListTestData = new CheckListTestData();
    private final LabelTestData labelTestData = new LabelTestData();
    private final ListTestData listTestData = new ListTestData();
    private final MemberTestData memberTestData = new MemberTestData();

    public ActionTestData getActionTestData() {
        return actionTestData;
    }


    public CardTestData getCardTestData() {
        return cardTestData;
    }

    public CheckListTestData getCheckListTestData() {
        return checkListTestData;
    }

    public LabelTestData getLabelTestData() {
        return labelTestData;
    }

    public ListTestData getListTestData() {
        return listTestData;
    }

    public MemberTestData getMemberTestData() {
        return memberTestData;
    }
}
