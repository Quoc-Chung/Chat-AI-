package com.springal.Chat.AL.controller;

import com.springal.Chat.AL.dto.ChatRequest;
import com.springal.Chat.AL.dto.ExpenseInfo;
import com.springal.Chat.AL.dto.MatHang;
import com.springal.Chat.AL.service.ChatService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class ChatController {
  private final ChatService chatService;
   @PostMapping("/chatal")
   String chat(@RequestBody ChatRequest chatRequest) {
       return chatService.chat(chatRequest);
   }
   
   @PostMapping("/chat-with-image")
   List<MatHang> chatWithImage(@RequestParam("file")MultipartFile file,
       @RequestParam("message") String mesage) {
        return chatService.chatWithImage(file, mesage);
   }

   @PostMapping("/chat-with-image-v2")
   String chatWidthImage(@RequestParam(value = "file",required = false ) MultipartFile file,
       @RequestParam(value = "message", required = false) String message,
       @RequestParam(value="username") String username) throws IOException {
     return chatService.chatWithImageAsString(file, message, username);
   }

  @PostMapping("/chat-with-video")
  public String chatWithVideo(
      @RequestParam("file") MultipartFile file,
      @RequestParam("message") String message) {
    return chatService.chatWithVideo(file, message);
  }

  @PostMapping("/chat-with-audio")
  public String chatWithAudio(
      @RequestParam("file") MultipartFile file,
      @RequestParam("message") String message) {
    return chatService.chatWithAudio(file, message);
  }

  @PostMapping("/chat-structure")
  List<ExpenseInfo> chatStructure(@RequestBody ChatRequest chatRequest) {
    return chatService.chatStructure(chatRequest);
  }
}
