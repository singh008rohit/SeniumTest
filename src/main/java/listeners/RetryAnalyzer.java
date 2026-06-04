package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import loggerUtils.LogUtils;
import utlity.ConfigLoader;

public class RetryAnalyzer implements IRetryAnalyzer {

    // ThreadLocal because AnnotationTransformer creates one instance per method
    // but under parallel="methods" the same instance CAN be called from different threads
    private final ThreadLocal<Integer> retryCount = 
        ThreadLocal.withInitial(() -> 0);
    
    private static final int MAX_RETRY = 2;

    @Override
    public boolean retry(ITestResult result) {
        if (!ConfigLoader.getInstance().getRetryFailedTests()
                .equalsIgnoreCase("yes")) {
            return false;
        }
        int count = retryCount.get();
        if (count < MAX_RETRY) {
            retryCount.set(count + 1);
            LogUtils.info("Retrying test: " + result.getName()
                + " | Attempt: " + (count + 1));
            return true;
        }
        retryCount.remove(); // clean up ThreadLocal after max retries
        return false;
    }
}