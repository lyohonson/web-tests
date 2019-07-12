package util;


import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.specification.RequestSpecification;
import java.io.PrintStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.io.IoBuilder;


public class HttpClient {

  private static final Logger logger = LogManager.getLogger(HttpClient.class);

  public static RequestSpecification rest(String url) {
    PrintStream logStream = IoBuilder.forLogger(logger).buildPrintStream();
    RestAssuredConfig restAssuredConfig = RestAssured.config()
        .logConfig(LogConfig.logConfig().defaultStream(logStream));

    return given()
        .config(restAssuredConfig)
        .baseUri(url)
        .header("User-Agent", "Mozilla/5.0"
            + " (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
        .log().method().log().headers().log().uri().log().body()
        .then()
        .log().headers().log().body().log().status().given();
  }
}
