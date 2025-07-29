 package listeners;

import com.aventstack.extentreports.Status;
import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.TestStepFinished;
import io.cucumber.plugin.event.TestStepStarted;
import reportManager.ExtentManager;
import reportManager.ExtentReportManager;

public class StepListener implements EventListener {
   public void setEventPublisher(EventPublisher publisher) {
      publisher.registerHandlerFor(TestStepStarted.class, this::handleStepStart);
      publisher.registerHandlerFor(TestStepFinished.class, this::handleStepFinish);
   }

   private void handleStepStart(TestStepStarted event) {
	   String stepText =null;
	    if (event.getTestStep() instanceof PickleStepTestStep) {
	        PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
	        stepText   = step.getStep().getKeyword() + step.getStep().getText();

	        if (ExtentManager.getExtentTest() != null) {
	            ExtentManager.getExtentTest().log(Status.INFO, stepText);
	        }
	    }
	    System.out.println(">>> Cucumber Step Started: " + stepText);
	}

   private void handleStepFinish(TestStepFinished event) {
	   if (event.getTestStep() instanceof PickleStepTestStep) {
	      PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
	      String stepText = step.getStep().getKeyword() + step.getStep().getText();

	      if ( ExtentManager.getExtentTest() != null) {
	         switch (event.getResult().getStatus()) {
	            case PASSED:
	            	 ExtentManager.getExtentTest().log(Status.PASS,   stepText);
	               break;
	            case FAILED:
	            	 ExtentManager.getExtentTest().log(Status.FAIL,   stepText);
	               Throwable error = event.getResult().getError();
	               if (error != null) {
	            	   ExtentManager.getExtentTest().log(Status.FAIL, error);
	               }
	               break;
	            case SKIPPED:
	            	 ExtentManager.getExtentTest().log(Status.SKIP,  stepText);
	               break;
	            default:
	            	 ExtentManager.getExtentTest().log(Status.INFO,   stepText + " (" + event.getResult().getStatus() + ")");
	         }
	      }
	   }
   }
}
    