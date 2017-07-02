package edu.sibinfo.spring.web.module05.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoogleResponse {

  private boolean success;
  @JsonProperty("challenge_ts")
  private String challengeTs;
  private String hostname;
  @JsonProperty("error-codes")
  private ErrorCode[] errorCodes;

  enum ErrorCode {
    MISSING_SECRET,
    INVALID_SECRET,
    MISSING_RESPONSE,
    INVALID_RESPONSE;

    private static Map<String, ErrorCode> errorsMap = new HashMap<>(4);

    static {
      errorsMap.put("missing-input-secret", MISSING_SECRET);
      errorsMap.put("invalid-input-secret", INVALID_SECRET);
      errorsMap.put("missing-input-response", MISSING_RESPONSE);
      errorsMap.put("invalid-input-response", INVALID_RESPONSE);
    }

    @JsonCreator
    public static ErrorCode forValue(final String value) {
      return errorsMap.get(value.toLowerCase());
    }
  }
}
