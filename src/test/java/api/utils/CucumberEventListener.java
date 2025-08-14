package api.utils;

import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.*;

//This class represents EventListener (analog for TestNg listeners)
// I was intended to use it for logs, but it appears to be not working synchronized with
// hooks, and cucumber native (by default) logs, so I decided do not use it, but keep it for
// learning and experiments.
//Update: - it appear to have specific behavior, it works after all the scenarios passed, only then it
// does his work. So if you plan to log information after every step, it will first run all the steps, and
//only then display logs, which might at that time, not be correctly (not after the step) put in a logFile.

public class CucumberEventListener implements EventListener {

    private String failedStepName="has not been initialized";
    private String errorMessageInCaseOfScenarioFailed = "has not been initialized";

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {

        eventPublisher.registerHandlerFor(TestStepFinished.class, this::stepFinished);
        eventPublisher.registerHandlerFor(TestCaseFinished.class, this::scenarioFinished);
    }

    private void stepFinished (TestStepFinished testStepFinished){

        if(testStepFinished.getResult().getStatus() == Status.FAILED){
            PickleStepTestStep pickleStepTestStep = (PickleStepTestStep) testStepFinished.getTestStep();
            failedStepName = pickleStepTestStep.getStep().getKeyword()+" "+ pickleStepTestStep.getStep().getText() + ", located on line " + pickleStepTestStep.getStep().getLine();
            errorMessageInCaseOfScenarioFailed = testStepFinished.getResult().getError().getMessage();
        }
    }

    private void scenarioFinished(TestCaseFinished testCaseFinished){

        LogFactory.getLogger().info("");

        if (testCaseFinished.getResult().getStatus() == Status.FAILED) {
            LogFactory.getLogger().error("Scenario "+ testCaseFinished.getTestCase().getName() + " - failed ❌");
            LogFactory.getLogger().error("on a step - " + failedStepName);
            LogFactory.getLogger().error(errorMessageInCaseOfScenarioFailed);
        }else{
            LogFactory.getLogger().info("Scenario " + testCaseFinished.getTestCase().getName() + " - passed ✔\uFE0F");
        }
    }

}
