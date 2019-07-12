package stepdefs;

import static com.codeborne.selenide.Selenide.open;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import pages.EconomicCalendarPage;

public class EconomicCalendarStepdefs {

  EconomicCalendarPage page;

  @Given("on economic calendar page$")
  public void onEconomicCalendarPage() {
    page = open(new EconomicCalendarPage().getUrl(), EconomicCalendarPage.class);
  }

  @When("^filter by current month")
  public void filterByCurrentMonth() {
    page.filterByCurrentMonth();
  }

  @When("filter by currencies")
  public void filterByCurrencies(List<String> currencyList) {
    page.filterByCurrency(currencyList);
  }

  @When("filter by importance")
  public void filterByImportance(List<String> importanceList) {
    page.filterByImportance(importanceList);
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
