package logger;

import static io.qameta.allure.util.ResultsUtils.md5;

import cucumber.api.HookTestStep;
import cucumber.api.PickleStepTestStep;
import cucumber.api.Result.Type;
import cucumber.api.TestCase;
import cucumber.api.event.ConcurrentEventListener;
import cucumber.api.event.EventHandler;
import cucumber.api.event.EventPublisher;
import cucumber.api.event.TestCaseFinished;
import cucumber.api.event.TestCaseStarted;
import cucumber.api.event.TestSourceRead;
import cucumber.api.event.TestStepFinished;
import cucumber.api.event.TestStepStarted;
import cucumber.runtime.formatter.TestSourcesModelProxy;
import gherkin.ast.Feature;
import io.qameta.allure.model.StepResult;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CucumberStepsToLog implements ConcurrentEventListener {
  private static final Logger LOG = LogManager.getLogger(CucumberStepsToLog.class);

  private final TestSourcesModelProxy testSources = new TestSourcesModelProxy();
  private final ConcurrentHashMap<String, String> scenarioUuids = new ConcurrentHashMap<>();

  private final ThreadLocal<Feature> currentFeature = new InheritableThreadLocal<>();
  private final ThreadLocal<String> currentFeatureFile = new InheritableThreadLocal<>();
  private final ThreadLocal<TestCase> currentTestCase = new InheritableThreadLocal<>();
  private final ThreadLocal<String> currentContainer = new InheritableThreadLocal<>();
  private final ThreadLocal<Boolean> forbidTestCaseStatusChange = new InheritableThreadLocal<>();


  private final EventHandler<TestSourceRead> featureStartedHandler = this::handleFeatureStartedHandler;
  private final EventHandler<TestCaseStarted> caseStartedHandler = this::handleTestCaseStarted;
  private final EventHandler<TestCaseFinished> caseFinishedHandler = this::handleTestCaseFinished;
  private final EventHandler<TestStepStarted> stepStartedHandler = this::handleTestStepStarted;
  private final EventHandler<TestStepFinished> stepFinishedHandler = this::handleTestStepFinished;


  @Override
  public void setEventPublisher(final EventPublisher publisher) {
    publisher.registerHandlerFor(TestSourceRead.class, featureStartedHandler);

    publisher.registerHandlerFor(TestCaseStarted.class, caseStartedHandler);
    publisher.registerHandlerFor(TestCaseFinished.class, caseFinishedHandler);

//    publisher.registerHandlerFor(TestStepStarted.class, stepStartedHandler);
    publisher.registerHandlerFor(TestStepFinished.class, stepFinishedHandler);
  }

  private void handleFeatureStartedHandler(final TestSourceRead event) {
    testSources.addTestSourceReadEvent(event.uri, event);
    LOG.info("Tests for feature {} started", event.uri);
  }

  private void handleTestCaseStarted(final TestCaseStarted event) {
    LOG.info("Test \"" + event.testCase.getName() + "\" started");
  }

  private void handleTestCaseFinished(final TestCaseFinished event) {
    LOG.info("Test \"" + event.testCase.getName() + "\" finished with result " + event.result.getStatus());
  }

  private void handleTestStepStarted(final TestStepStarted event) {

    if (event.testStep instanceof PickleStepTestStep) {
      final PickleStepTestStep pickleStep = (PickleStepTestStep) event.testStep;
      final String stepKeyword = Optional.ofNullable(
          testSources.getKeywordFromSource(currentFeatureFile.get(), pickleStep.getStepLine())
      ).orElse("UNDEFINED");

      final StepResult stepResult = new StepResult()
          .setName(String.format("%s %s", stepKeyword, pickleStep.getPickleStep().getText()))
          .setStart(System.currentTimeMillis());
      LOG.info("Step \"" + pickleStep.getStepText() + "\" started");
    }
  }

  private void handlePickleStep(final TestStepFinished event) {
    final PickleStepTestStep pickleStep = (PickleStepTestStep) event.testStep;
    Type status = event.result.getStatus();
    LOG.info( "Step \"" + pickleStep.getStepText() + "\" " + status.toString());

  }

  private void handleTestStepFinished(final TestStepFinished event) {
    if (event.testStep instanceof HookTestStep) {
//      handleHookStep(event);
    } else {
      handlePickleStep(event);
    }
  }

  private String getTestCaseUuid(final TestCase testCase) {
    return scenarioUuids.computeIfAbsent(getHistoryId(testCase), it -> UUID.randomUUID().toString());
  }

  private String getHistoryId(final TestCase testCase) {
    final String testCaseLocation = testCase.getUri() + ":" + testCase.getLine();
    return md5(testCaseLocation);
  }
}
