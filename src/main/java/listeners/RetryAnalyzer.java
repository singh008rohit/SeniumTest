package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import loggerUtils.LogUtils;
import utlity.ConfigLoader;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int MAX_RETRY = 2;

    @Override
    public boolean retry(ITestResult result) {
        if (!ConfigLoader.getInstance().getRetryFailedTests()
                .equalsIgnoreCase("yes")) {
            return false;
        }
        if (retryCount < MAX_RETRY) {
            retryCount++;
            LogUtils.info("Retrying test: " + result.getName()
                + " | Attempt: " + retryCount);
            return true;
        }
        return false;
    }
}