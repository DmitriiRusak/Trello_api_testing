package api.utils;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.BeforeSuite;

public class TestListener implements ITestListener {

        @BeforeSuite
    public void projectSetUp(){

        LogFactory.getLogger().info("");
        LogFactory.getLogger().info("******************************New run for Rest Assured framework started*************************");
    }

    @Override
    public void onTestStart(ITestResult result) {

        LogFactory.getLogger().info("Test name: " + result.getTestClass().getName()+"."+result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        LogFactory.getLogger().info("Passed successfully ✅ ");
        LogFactory.getLogger().info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        Allure.addAttachment("Test Passed", "text/plain", "Test passed successfully!");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        LogFactory.getLogger().error("❌ "+result.getThrowable().fillInStackTrace());
        LogFactory.getLogger().error("------------------------------------------------------------------------");

        Allure.addAttachment("Test Failed", "text/plain", "Test failed with exception: " + result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        LogFactory.getLogger().warn("Test skipped: " + result.getName());
        LogFactory.getLogger().warn("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
}
