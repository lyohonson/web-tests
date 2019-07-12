package logger;

import com.codeborne.selenide.Screenshots;
import com.google.common.io.Files;
import io.qameta.allure.Attachment;
import java.io.File;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.internal.ExitCodeListener;


public class TestNgListener extends ExitCodeListener {

  private static final Logger LOG = LogManager.getLogger(TestNgListener.class);

  @Override
  public void onTestFailure(ITestResult result) {
    super.onTestFailure(result);
    try {
      screenshot();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Attachment(type = "image/png")
  public static byte[] screenshot() throws IOException {
    File screenshot = Screenshots.takeScreenShotAsFile();
    return Files.toByteArray(screenshot);
  }
}
