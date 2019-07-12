package enums;

public enum Importance {
  HOLIDAYS("Holidays"), LOW("Low"), MEDIUM("Medium"), HIGH("High");

  private String value;


  Importance(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

}
