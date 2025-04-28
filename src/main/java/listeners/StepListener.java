 package listeners;

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
         String stepText = step.getStep().getKeyword() + step.getStep().getText();
         if (ExtentReportManager.getTest() != null) {
            ExtentReportManager.getTest().log(Status.INFO, "\ud83d\udd39 Step: " + stepText);
         }
      }

   }

   private void handleStepFinish(TestStepFinished event) {
      if (event.getTestStep() instanceof PickleStepTestStep) {
         PickleStepTestStep step = (PickleStepTestStep)event.getTestStep();
         String stepText = step.getStep().getKeyword() + step.getStep().getText();
         if (ExtentReportManager.getTest() != null) {
            if (event.getResult().getStatus() == io.cucumber.plugin.event.Status.FAILED) {
               ExtentReportManager.getTest().log(Status.FAIL, "❌ Failed Step: " + stepText);
               Throwable error = event.getResult().getError();
               if (error != null) {
                  ExtentReportManager.getTest().log(Status.FAIL, error);
               }
            } else {
               ExtentReportManager.getTest().log(Status.PASS, "✅ Passed Step: " + stepText);
            }
         }
      }

   }
}
    