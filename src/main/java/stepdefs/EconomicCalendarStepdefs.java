package stepdefs;

import static com.codeborne.selenide.Selenide.open;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pages.EconomicCalendarPage;

public class EconomicCalendarStepdefs {
  private static final Logger LOG = LogManager.getLogger(EconomicCalendarStepdefs.class);


  EconomicCalendarPage page;

  @Given("on economic calendar page$")
  public void onEconomicCalendarPage() {
    page = open(new EconomicCalendarPage().getUrl(), EconomicCalendarPage.class);
  }

  @When("filter by current month")
  public void filterByCurrentMonth() {
    page.filterByCurrentMonth();
  }

  @When("filter by currencies")
  public void filterByCurrencies(List<String> currencyList) {
    page.filterByCurrency(currencyList);
  }

  @When("filter by currencies {string}")
  public void filterByCurrencies(String currency) {
    page.filterByCurrency(Arrays.asList(currency));
  }

  @When("filter by importance")
  public void filterByImportance(List<String> importance) {
    page.filterByImportance(importance);
  }

  @When("filter by importance {string}")
  public void filterByImportance(String importance) {
    page.filterByImportance(Arrays.asList(importance));
  }

  @Then("^check country (.*)$")
  public void checkCountry(String country) {
    page.checkCountry(country);
  }

  @Then("^check importance level (.*)$")
  public void checkImportanceLevel(String level) {
    page.checkImportanceLevel(level);
  }

  @When("open {int} event with currency {string}")
  public void openFirstEvent(int i, String currency) {
    page.openEventWithCurrency(currency, i);
  }

  @And("get history for {int} months")
  public void getHistoryForMonths(int arg) {
    page.getHistoryRows(arg);
  }

  @And("click tab history")
  public void clickTabHistory() {
    page.clickTabHistory();
  }


}
