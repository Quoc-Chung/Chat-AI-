package com.springal.Chat.AL.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseInfo {
  private String category;
    private  String name;
    private String  gia_tien;
    private  String description;
}
