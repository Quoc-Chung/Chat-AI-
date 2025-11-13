package com.springal.Chat.AL.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class LocationNormalizer {

  /*- Danh sách các tỉnh thành -*/
  private final Map<String, String> locationMapping = Map.ofEntries(
      Map.entry("hà nội", "TP Hà Nội"),
      Map.entry("ha noi", "TP Hà Nội"),
      Map.entry("huế", "TP Huế"),
      Map.entry("hue", "TP Huế"),
      Map.entry("quảng ninh", "Quảng Ninh"),
      Map.entry("cao bằng", "Cao Bằng"),
      Map.entry("lạng sơn", "Lạng Sơn"),
      Map.entry("lai châu", "Lai Châu"),
      Map.entry("điện biên", "Điện Biên"),
      Map.entry("sơn la", "Sơn La"),
      Map.entry("thanh hóa", "Thanh Hóa"),
      Map.entry("nghệ an", "Nghệ An"),
      Map.entry("hà tĩnh", "Hà Tĩnh"),
      Map.entry("tuyên quang", "Tuyên Quang"),
      Map.entry("lào cai", "Lào Cai"),
      Map.entry("thái nguyên", "Thái Nguyên"),
      Map.entry("phú thọ", "Phú Thọ"),
      Map.entry("bắc ninh", "Bắc Ninh"),
      Map.entry("hưng yên", "Hưng Yên"),
      Map.entry("hải phòng", "TP Hải Phòng"),
      Map.entry("ninh bình", "Ninh Bình"),
      Map.entry("quảng trị", "Quảng Trị"),
      Map.entry("đà nẵng", "TP Đà Nẵng"),
      Map.entry("quảng ngãi", "Quảng Ngãi"),
      Map.entry("gia lai", "Gia Lai"),
      Map.entry("khánh hòa", "Khánh Hòa"),
      Map.entry("lâm đồng", "Lâm Đồng"),
      Map.entry("đắk lắk", "Đắk Lắk"),
      Map.entry("tphcm", "TPHCM"),
      Map.entry("sài gòn", "TPHCM"),
      Map.entry("hồ chí minh", "TPHCM"),
      Map.entry("đồng nai", "Đồng Nai"),
      Map.entry("tây ninh", "Tây Ninh"),
      Map.entry("cần thơ", "TP Cần Thơ"),
      Map.entry("vĩnh long", "Vĩnh Long"),
      Map.entry("đồng tháp", "Đồng Tháp"),
      Map.entry("cà mau", "Cà Mau"),
      Map.entry("an giang", "An Giang")
  );
  /**
   *  Chuẩn hóa đầu vào
   * @param tokens Danh sách các câu truy vấn
   * @return vị trí đã được chuẩn hóa
   */
  public List<String>  normalize (List<String> tokens){
    List<String> locations = new ArrayList<>();
    for (int i = 0; i < tokens.size(); i++) {
         String location = findLocationAtPossition(tokens, i);
         if(location != null){
             /*- Lấy ra value -*/
             String normalized = locationMapping.get(location);
             /*- Kiểm tra trong file chưa có địa điểm này thì mới thêm vào  -*/
             if(!locations.contains(normalized)){
               locations.add(normalized);
             }
         }
    }
    return locations;
  }

  /*- Tim kiem key tu theo 3 tu mot -*/
  private  String findLocationAtPossition (List<String> tokens, int startIndex){
    if(startIndex +  2 < tokens.size() ){
      /*- Lấy ra 3 từ một lúc -*/
      String threeword = String.join("", tokens.get(startIndex), tokens.get(startIndex+ 1), tokens.get(startIndex+ 2 ));
      if(locationMapping.containsKey(threeword)){
        return threeword;
      }
    }
    /* - Lấy ra 2 từ một lúc -*/
    if(startIndex  + 1  <  tokens.size() ){
      String trueword = String.join(" ", tokens.get(startIndex), tokens.get(startIndex+ 1));
      if(locationMapping.containsKey(trueword)){
        return trueword;
      }
    }
    /*- Lấy ra 1 từ một lúc - */
    if(startIndex < tokens.size()){
      String oneword = tokens.get(startIndex);
      if(locationMapping.containsKey(oneword)){
        return oneword;
      }
    }
    return null;
  }







}
