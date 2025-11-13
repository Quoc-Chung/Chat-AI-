package com.springal.Chat.AL.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BasicCleanText {
  /**
   * Loại bỏ kí tự đặc biệt , chuẩn hóa khoảng trắng
   * @param text đầu vào là một chuỗi
   * @return
   */
  public String basicCleanup(String text) {
    return text.toLowerCase()
        .replaceAll("[^\\p{L}\\p{N}\\s]", "")
        .replaceAll("\\s+", " ")
        .trim();
  }

  /**
   * Tách chuỗi văn bản thành danh sach các từ
   * @param text "  thời   tiết hôm nay  ";
   * @return [thời, tiết, hôm, nay]
   */
  public  List<String> tokenize(String text) {
    return Arrays.stream(text.split("\\s+"))
        .filter(token -> !token.isEmpty())
        .collect(Collectors.toList());
  }

  /**
   * Xac dinh ý định (intent) của câu hỏi dựa trên token đã phân tách
   * @param tokens danh sách từ khóa
   * @return
   */
  public String detectIntent(List<String> tokens){
    if(tokens.contains("thời") && tokens.contains("tiết")){
      return "ask_weather";
    }
    return "unknown";
  }


}
