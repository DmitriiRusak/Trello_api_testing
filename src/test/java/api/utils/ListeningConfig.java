package api.utils;

import io.qameta.allure.Allure;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListeningConfig implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        LoggerUtil.info("Test started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        LoggerUtil.info("Test passed: " + result.getName() + "\n Time: " + (result.getStartMillis() - result.getEndMillis()));
        Allure.addAttachment("Test Passed", "text/plain", "Test passed successfully!");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LoggerUtil.error("Test failed: " + result.getName());
        Allure.addAttachment("Test Failed", "text/plain", "Test failed with exception: " + result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LoggerUtil.warn("Test skipped: " + result.getName());
    }
}
