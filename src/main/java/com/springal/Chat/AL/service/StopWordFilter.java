package com.springal.Chat.AL.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Loại bỏ các từ thừa thãi trong câu văn
 * Chuẩn hóa khoảng trắng  + loại bỏ kí tự đặc biệt
 */
public class StopWordFilter {

  /**
   * Danh sách các từ thừa thãi
   */
  private final Set<String> stopWords = Set.of(
      "của", "và", "trong", "như", "nào", "là", "có", "được",
      "một", "này", "đó", "cho", "với", "về", "từ", "tại",
      "các", "những", "nhiều", "rất", "quá", "thì", "nên"
  );

  public List<String> removeStopWords(List<String> tokens){
     return tokens.stream().filter(stopWords::contains).collect(Collectors.toList());
  }






}
