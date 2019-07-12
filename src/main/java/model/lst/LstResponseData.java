package model.lst;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LstResponseData {

  @JsonProperty("url")
  String url;
  @JsonProperty("short")
  String shortField;
  @JsonProperty("qr")
  String qr;
  @JsonProperty("type")
  String type;
  @JsonProperty("created")
  String created;
  @JsonProperty("utm")
  String utm;

  @JsonCreator
  public LstResponseData(@JsonProperty("url") String url,
      @JsonProperty("short") String shortField,
      @JsonProperty("qr") String qr,
      @JsonProperty("type") String type,
      @JsonProperty("created") String created,
      @JsonProperty("utm") String utm
  ) {
    this.url = url;
    this.shortField = shortField;
    this.qr = qr;
    this.type = type;
    this.created = created;
    this.utm = utm;
  }

}
