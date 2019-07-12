import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.codeborne.selenide.testng.ScreenShooter;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.qameta.allure.selenide.AllureSelenide;
import logger.TestNgListener;
import logger.WebDriverListener;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

@CucumberOptions(
    features = {"src/test/resources/features"}, glue = {"stepdefs"},
    plugin = {"pretty",
        "io.qameta.allure.cucumber4jvm.AllureCucumber4Jvm",
    "json:build/report.json"}
)
@Listeners({ScreenShooter.class, TestNgListener.class})
public class EconomicCalendarTests extends AbstractTestNGCucumberTests {

  @Override
  @DataProvider(parallel = false)
  public Object[][] scenarios() {
    return super.scenarios();
  }

  @BeforeClass
  public void setPrefs() {
    SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    Configuration.browserVersion = "75";

    ChromeOptions options = new ChromeOptions();
    options.addArguments("user-agent=Mozilla/5.0"
        + " (compatible; Googlebot/2.1; +http://www.google.com/bot.html)");
    EventFiringWebDriver evenHandler = new EventFiringWebDriver(new ChromeDriver(options));
    evenHandler.register(new WebDriverListener());
    WebDriverRunner.setWebDriver(evenHandler);
  }

  @AfterClass
  public void closeWebDriver() {
    WebDriverRunner.getWebDriver().close();
  }
}