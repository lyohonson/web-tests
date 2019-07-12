package pages;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static org.assertj.core.api.Assertions.assertThat;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import java.util.ArrayList;
import java.util.List;

public class CheckBox {

  private String labelXpath;
  private String inputXpath;
  private SelenideElement checkbox;
  private String checkedAttr = "checked";

  public CheckBox(String name) {
    labelXpath = "//label[contains(@for,'" + name + "')]";
    inputXpath = labelXpath + "/../input";
    checkbox = $x(labelXpath);
  }

  public String getText() {
    return checkbox.getText();
  }

  public boolean isSelected() {
    checkbox.shouldBe(Condition.visible);
    String classValue = $x(inputXpath).shouldHave(Condition.attribute(checkedAttr))
        .getAttribute(checkedAttr);
    return classValue.equals("true");
  }

  public CheckBox isSelected(boolean isSelected) {
    checkbox.shouldBe(Condition.visible);
    boolean selected = Boolean.valueOf($x(inputXpath).getAttribute(checkedAttr));

    assertThat(selected).as("Checkbox is selected ").isEqualTo(isSelected);

    return this;
  }

  public CheckBox select(boolean select) {
    boolean actualIsSelected = isSelected();
    if (select) {
      if (!actualIsSelected) {
        checkbox.scrollTo().click();
      }
    } else {
      if (actualIsSelected) {
        checkbox.scrollTo().click();
      }
    }
    return this;
  }

  public SelenideElement create(String name) {
    String xpath = "//label[contains(.,'" + name + "')]";
    return $x(xpath);
  }

  public static List<CheckBox> getAllCheckBoxesContains(String forAttr) {
    String xpath = "//label[contains(@for,'" + forAttr + "')]";
    List<CheckBox> checkBoxes = new ArrayList<>();
    $$x(xpath).forEach(it -> checkBoxes.add(new CheckBox(it.getAttribute("for"))));
    return checkBoxes;
  }

  public static ElementsCollection getAsSelenideElementsContains(String name) {
    String xpath = "//label[contains(.,'" + name + "')]";
    return $$x(xpath);
  }

  public CheckBox isDisabled(boolean isDisabled) {
    if (isDisabled) {
      checkbox.shouldBe(Condition.disabled);
    } else {
      checkbox.shouldBe(Condition.visible);
    }
    return this;
  }

}
