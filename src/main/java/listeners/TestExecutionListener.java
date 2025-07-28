package listeners;

import org.testng.IExecutionListener;


import reportManager.ExtentReportManager;

public class TestExecutionListener implements IExecutionListener {
    public void onExecutionFinish() {
    	
    	ExtentReportManager.flushReports();
    }}