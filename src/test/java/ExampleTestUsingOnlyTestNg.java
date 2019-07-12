import static com.codeborne.selenide.Selenide.open;

import enums.Currency;
import enums.Importance;
import java.util.Arrays;
import org.testng.annotations.Test;
import pages.EconomicCalendarPage;

public class ExampleTestUsingOnlyTestNg extends TestBase {

  @Test(enabled = false)
  public void filterByPeriodImportanceCurrency() {

    EconomicCalendarPage page = open(new EconomicCalendarPage().getUrl(),
        EconomicCalendarPage.class);
    page.filterByCurrentMonth()
        .filterByImportance(Arrays.asList(Importance.MEDIUM.getValue()))
        .filterByCurrency(Arrays.asList(Currency.CHF.getValue()));

    page.checkCountry("Switzerland")
        .checkImportanceLevel(Importance.MEDIUM.getValue())
        .openEventWithCurrency(Currency.CHF.getValue(), 1)
        .clickTabHistory()
        .getHistoryRows(12);
  }
}
