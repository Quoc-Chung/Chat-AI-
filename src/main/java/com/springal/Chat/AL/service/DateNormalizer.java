package com.springal.Chat.AL.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Component;


@Component
/*- Trả về thời điểm hiện tại -*/
public class DateNormalizer {
  /* - Tách cách câu ra một mảng các từ -*/
   public String normalizeDate(List<String> tokens) {
     /* - Lấy ra thoi gian hien tai -*/
     LocalDate today = LocalDate.now();
     /*- Chuyển đổi nó thành một câu cách nhau bởi dấu cách -*/
     String tokenString = String.join(" ", tokens);
     if(tokenString.contains("hôm nay") || tokenString.contains("today")){
         /*- Trả về ngày hôm nay -*/
         return today.toString();
     }

     if (tokenString.contains("ngày mai") || tokenString.contains("tomorrow")) {
       return today.plusDays(1).toString();
     }

     if (tokenString.contains("hôm qua") || tokenString.contains("yesterday")) {
       return today.minusDays(1).toString();
     }

     if (tokenString.contains("cuối tuần")) {
       /*- Lấy ra thứ 7 trong ngày hiện tại sau đó trích xuất từ thứ 7 đên chủ nhật  -*/
       LocalDate saturday = today.with(DayOfWeek.SATURDAY);
       return saturday + " to " + saturday.plusDays(1);
     }

     if (tokenString.contains("tuần này")) {

       LocalDate monday = today.with(DayOfWeek.MONDAY);
       LocalDate sunday = monday.plusDays(6);
       return monday + " to " + sunday;
     }

     return today.toString();
   }
}
