package enums;

public enum Currency {
  CHF("CHF");

  private String value;

  Currency(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
