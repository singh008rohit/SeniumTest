 package hooks;

import com.aventstack.extentreports.Status;
import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.TestStepFinished;
import io.cucumber.plugin.event.TestStepStarted;
import reportManager.ExtentReportManager;

public class StepListener implements EventListener {
   public void setEventPublisher(EventPublisher publisher) {
      publisher.registerHandlerFor(TestStepStarted.class, this::handleStepStart);
      publisher.registerHandlerFor(TestStepFinished.class, this::handleStepFinish);
   }

   private void handleStepStart(TestStepStarted event) {
      if (event.getTestStep() instanceof PickleStepTestStep) {
         PickleStepTestStep step = (PickleStepTestStep)event.getTestStep();
       //  String stepText = step.getStep().getKeyword() + step.getStep().getText();
        // if (ExtentReportManager.getTest() != null) {
           // ExtentReportManager.getTest().log(Status.INFO,   stepText);
        // }
      }

   }

   private void handleStepFinish(TestStepFinished event) {
	   if (event.getTestStep() instanceof PickleStepTestStep) {
	      PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
	      String stepText = step.getStep().getKeyword() + step.getStep().getText();

	      if (ExtentReportManager.getTest() != null) {
	         switch (event.getResult().getStatus()) {
	            case PASSED:
	               ExtentReportManager.getTest().log(Status.PASS,   stepText);
	               break;
	            case FAILED:
	               ExtentReportManager.getTest().log(Status.FAIL,   stepText);
	               Throwable error = event.getResult().getError();
	               if (error != null) {
	                  ExtentReportManager.getTest().log(Status.FAIL, error);
	               }
	               break;
	            case SKIPPED:
	               ExtentReportManager.getTest().log(Status.SKIP,  stepText);
	               break;
	            default:
	                ExtentReportManager.getTest().log(Status.INFO,   stepText + " (" + event.getResult().getStatus() + ")");
	         }
	      }
	   }
   }
}
    