Feature: Filter events

  Scenario: Filter events by period, importance and currency
    Given on economic calendar page
    When filter by current month
    When filter by importance
      | Medium |
    When filter by currencies
      | CHF |
    When open 1 event with currency "CHF"
    Then check country Switzerland
    Then check importance level Medium
    And click tab history
    And get history for 12 months
