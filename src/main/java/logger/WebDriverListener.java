package logger;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.events.WebDriverEventListener;

public class WebDriverListener implements WebDriverEventListener {

  private static final Logger LOG = LogManager.getLogger(WebDriverListener.class);

  public void beforeAlertAccept(WebDriver driver) {

  }

  public void afterAlertAccept(WebDriver driver) {

  }

  public void afterAlertDismiss(WebDriver driver) {

  }

  public void beforeAlertDismiss(WebDriver driver) {

  }

  public void beforeNavigateTo(String url, WebDriver driver) {
    LOG.debug("URL: " + url);
  }

  public void afterNavigateTo(String url, WebDriver driver) {

  }

  public void beforeNavigateBack(WebDriver driver) {

  }

  public void afterNavigateBack(WebDriver driver) {

  }

  public void beforeNavigateForward(WebDriver driver) {

  }

  public void afterNavigateForward(WebDriver driver) {

  }

  public void beforeNavigateRefresh(WebDriver driver) {

  }

  public void afterNavigateRefresh(WebDriver driver) {

  }

  public void beforeFindBy(By by, WebElement element, WebDriver driver) {
    LOG.debug("Try to find element " + by.toString() + " [URL]: " + driver.getCurrentUrl());
  }

  public void afterFindBy(By by, WebElement element, WebDriver driver) {

  }

  @Override
  public void beforeClickOn(WebElement element, WebDriver driver) {
    LOG.debug("Is going to click on element " + element);
  }

  public void afterClickOn(WebElement element, WebDriver driver) {
    LOG.info("Clicked on element " + element);
  }

  public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {

  }

  public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {

  }

  public void beforeScript(String script, WebDriver driver) {

  }

  public void afterScript(String script, WebDriver driver) {

  }

  public void beforeSwitchToWindow(String windowName, WebDriver driver) {

  }

  public void afterSwitchToWindow(String windowName, WebDriver driver) {

  }

  public void onException(Throwable throwable, WebDriver driver) {
    LOG.info("CURRENT URL: " + driver.getCurrentUrl());
  }

  public <X> void beforeGetScreenshotAs(OutputType<X> target) {

  }

  public <X> void afterGetScreenshotAs(OutputType<X> target, X screenshot) {

  }

  public void beforeGetText(WebElement webElement, WebDriver webDriver) {

  }

  public void afterGetText(WebElement webElement, WebDriver webDriver, String s) {

  }

  private void logConsole(WebDriver driver) {
    List<LogEntry> errors = driver.manage().logs().get(LogType.BROWSER).getAll();
    for (LogEntry entry : errors) {
      LOG.info(entry.getMessage());
      System.out.println(entry);
    }
  }

  public static void highlight(WebDriver driver, WebElement element) {
    JavascriptExecutor jse = (JavascriptExecutor) driver;
    jse.executeScript("arguments[0].style.border='2px solid red'", element);
  }
}