package pages;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;

public abstract class PageBase {

  public abstract String getUrl();

  public static String getBaseUrl() {
    return Configuration.baseUrl;
  }

}
