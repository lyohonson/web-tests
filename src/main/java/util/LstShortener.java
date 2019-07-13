package util;

import static util.HttpClient.rest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.lst.LstRequestBody;
import model.lst.LstRequestData;
import model.lst.LstResponseBody;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LstShortener {

  private static final String serviceUrl = "https://lst.to";
  private static final String baseBath = serviceUrl + "/api/v1";
  private static final String token = "a73695a7ed66c4454fbb77aa";
  private static final Logger LOG = LogManager.getLogger(LstShortener.class);

  public static String getShortLink(String url) {

    LstRequestBody body = LstRequestBody.builder()
        .data(LstRequestData.builder()
            .type("link")
            .url(url)
            .utm("utm_source=[domain]&utm_referer=[referer]")
            .build())
        .build();

    LOG.info("Get short link from ");
    Response postResponse = rest(baseBath)
        .given()
        .contentType(ContentType.JSON)
        .header("X-AUTH-TOKEN", token)
        .body(body)
        .when()
        .post("/link");

    if (postResponse.getStatusCode() != HttpStatus.SC_OK) {
      LOG.warn("Short link request has status:" + postResponse.getStatusCode());
      return "";
    }

    String shortLink = postResponse.then()
        .extract().as(LstResponseBody.class)
        .getData()
        .getShortField();

    LOG.info("Delete short link from " + serviceUrl);
    Response deleteResponse = rest(baseBath)
        .given()
        .contentType(ContentType.JSON)
        .header("X-AUTH-TOKEN", token)
        .when()
        .delete("/link/" + shortLink.replace(serviceUrl + "/", ""));

    if (deleteResponse.getStatusCode() != HttpStatus.SC_OK) {
      LOG.warn("Short link didn't remove" + deleteResponse.getStatusCode());
    }

    return shortLink;
  }
}
