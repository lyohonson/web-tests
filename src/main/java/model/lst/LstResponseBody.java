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
public class LstResponseBody {

  private LstResponseData data;

  @JsonCreator
  public LstResponseBody(@JsonProperty("data") LstResponseData data) {
    this.data = data;
  }

}
