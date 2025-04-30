package listeners;

import org.testng.IExecutionListener;
import reportManager.ExtentReportManager;

public class TestExecutionListener implements IExecutionListener {
    @Override
    public void onExecutionFinish() {
        ExtentReportManager.flush(); // called once after all tests
    }
}