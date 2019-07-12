import static com.codeborne.selenide.Configuration.baseUrl;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import logger.WebDriverListener;
import org.openqa.selenium.remote.DesiredCapabilities;

public class TestBase {




  public void setPreferencies() {
    SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    baseUrl = "http://" + System.getProperty("host") + ":"
        + System.getProperty("port") + "/";
    DesiredCapabilities capability = DesiredCapabilities.chrome();
    capability.setCapability("general.useragent.override", "Mozilla/5.0"
        + " (compatible; Googlebot/2.1; +http://www.google.com/bot.html)\"");
    Configuration.browserCapabilities = capability;
    Configuration.startMaximized = true;
    Configuration.browserVersion = "75";

    WebDriverRunner.addListener(new WebDriverListener());
  }
}
