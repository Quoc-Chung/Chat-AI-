package com.springal.Chat.AL.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NormalizedText {
  private String originalText;

  private String cleanedText;

  private String intent;

  private List<String> locations;

  private String date;

  public Map<String , Object> toJson () {
    Map<String , Object> map = new HashMap<>();
    map.put("originalText", originalText);
    map.put("cleanedText", cleanedText);
    map.put("intent", intent);
    map.put("locations", locations);
    map.put("date", date);
    return map;
  }
}
