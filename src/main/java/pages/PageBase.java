package pages;

import com.codeborne.selenide.Configuration;

public abstract class PageBase {

  public abstract String getUrl();

  public static String getBaseUrl() {
    return Configuration.baseUrl;
  }

}
