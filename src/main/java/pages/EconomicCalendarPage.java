package pages;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static util.LstShortener.getShortLink;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class EconomicCalendarPage extends PageBase {

  private static final Logger LOG = LogManager.getLogger(EconomicCalendarPage.class);


  private static final String ECONOMIC_CALENDAR_URL = "economic-calendar";
  private static final String FILTERDATE_CURRENT_MONTH = "//label[@for='filterDate4'][contains(.,'Current month')]";
  private static final String EVENT_TABLE_IMPORTANCE
      = "//table[@class='economic-calendar__event-table']//td[contains(@class,'event-table__importance')]";
  private static final String HEADER_CONTENT_COUNTRY = "//span[contains(.,'Country:')]/..//a";
  private static final String TAB_MENU_HISTTORY = "//ul[@class='tab__menu']/li[@data-content='history']";

  private ElementsCollection currencyCheckboxes
      = $$x("//label[contains(@for,'filterCurrency')]/../input");
  private ElementsCollection importanceCheckboxes = $$x(
      "//label[contains(@for,'filterImportance')]/../input");

  @Override
  public String getUrl() {
    return getBaseUrl() + "/" + ECONOMIC_CALENDAR_URL;
  }

  public EconomicCalendarPage filterByCurrentMonth() {
    $x(FILTERDATE_CURRENT_MONTH).shouldBe(Condition.visible).click();
    return this;
  }

  public EconomicCalendarPage filterByImportance(List<String> importanceList) {
    List<CheckBox> checkBoxesImportance = CheckBox.getAllCheckBoxesContains("filterImportance");

    checkBoxesImportance.forEach(it -> {
          if (importanceList.stream().noneMatch(cur -> cur.equals(it.getText().trim()
              .replace("\"", "")))) {
            it.select(false);
          } else {
            it.select(true);
          }
        }
    );

    return this;
  }

  @Step
  public EconomicCalendarPage filterByCurrency(List<String> currencies) {
    List<CheckBox> currencyCheckboxes = CheckBox.getAllCheckBoxesContains("filterCurrency");

    currencyCheckboxes.forEach(it -> {
          if (currencies.stream().noneMatch(cur ->
              cur.equals(it.getText().replaceAll("\\s.*", "")))) {
            it.select(false);
          } else {
            it.select(true);
          }
        }
    );
    return this;
  }

  @Step
  public EconomicCalendarPage checkCountry(String country) {
    $x(HEADER_CONTENT_COUNTRY).shouldBe(Condition.text(country));
    return this;
  }

  @Step
  public EconomicCalendarPage checkImportanceLevel(String level) {
    $x(EVENT_TABLE_IMPORTANCE).shouldBe(Condition.text(level));
    return this;
  }

  @Step
  public ElementsCollection getHistoryRows(int count) {
    ElementsCollection historyRows
        = $$x("//div[@id='eventHistory']/div[@class='event-table-history__item']");

    List<List<String>> table = new ArrayList<>();

    historyRows.subList(0, count + 1).forEach(it -> {
      List<String> row = new ArrayList<>();
      row.add(it.$x(".//div[contains(@class,'date')]").should(exist).getText());
      row.add(it.$x(".//div[contains(@class,'actual')]").should(exist).getText());
      row.add(it.$x(".//div[contains(@class,'forecast')]").should(exist).getText());
      row.add(it.$x(".//div[contains(@class,'previous')]").should(exist).getText());
      table.add(row);
    });

    LOG.info("Short link: " + getShortLink(WebDriverRunner.url()));
    LOG.info("Event history for the year:\n" + formatTable(table));

    return historyRows;
  }


  @Step
  public EconomicCalendarPage clickTabHistory() {
    $x(TAB_MENU_HISTTORY).click();
    return this;
  }


  @Step
  public EconomicCalendarPage openEventWithCurrency(String currency, int i) {
    String xpath = String.format("//div[@class='ec-table__item'][%s]"
        + "//div[contains(@class,'curency-name')][.='%s']/../../..//a", i, currency);

    $x(xpath).click();

    return this;
  }


  private String formatTable(List<List<String>> table) {
    StringBuilder formattedTable = new StringBuilder();

    formattedTable
        .append("\n")
        .append("|      Date      |   Actual  |  Forecast  | Previous |");

    table.forEach(row -> {
      formattedTable
          .append("\n")
          .append("---------------------------------------------------")
          .append("\n|");

      row.forEach(cell -> {
        formattedTable
            .append("   ").append(cell).append("   |");
      });
    });
    return formattedTable.toString();
  }
}
