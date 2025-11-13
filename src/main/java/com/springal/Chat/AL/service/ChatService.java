package com.springal.Chat.AL.service;

import static com.springal.Chat.AL.config.SystemTextConfig.systemText;

import com.springal.Chat.AL.dto.ChatRequest;
import com.springal.Chat.AL.dto.ExpenseInfo;
import com.springal.Chat.AL.dto.FigureInfo;
import com.springal.Chat.AL.dto.MatHang;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.content.Media;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ChatService {

  private final ChatClient chatClient;
  private final JdbcChatMemoryRepository chatMemoryRepository;



  public ChatService(ChatClient.Builder builderchat, JdbcChatMemoryRepository chatMemoryRepository) {

    this.chatMemoryRepository = chatMemoryRepository;

    ChatMemory chatMemory =  MessageWindowChatMemory.builder()
        .chatMemoryRepository(chatMemoryRepository)
        .maxMessages(30)
        .build();
    chatClient = builderchat
        .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
        .build();
    initConversation();
  }

  private void initConversation() {
    String systemText = """
            Bạn là trợ lý AI do Quốc Chung tạo ra, hãy nói chuyện vui vẻ, thân thiện.
        """;

    Message systemMessage = new SystemPromptTemplate(systemText)
        .createMessage(Map.of());

    Prompt prompt = new Prompt(List.of(systemMessage));

    chatClient
        .prompt(prompt)
        .call();
  }

  public String chat(ChatRequest chatRequest) {
    String conversationId = "default";
    Message userMessage = new UserMessage(chatRequest.message());

    ChatOptions chatOptions = ChatOptions.builder()
        .temperature(0.2)
        .maxTokens(500)
        .build();

    Prompt prompt = new Prompt(List.of(userMessage), chatOptions);

    return chatClient
        .prompt(prompt)
        .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
        .call()
        .content();
  }



  public List<MatHang> chatWithImage(MultipartFile file, String message) {
    Media media = Media.builder()
        .mimeType(MimeTypeUtils.parseMimeType(file.getContentType()))
        .data(file.getResource())
        .build();

    String systemText = """
          Bạn là trợ lý AI do {name} tạo ra. Hãy trả lời theo phong cách {voice}.
          """;

    Message systemMessage = new SystemPromptTemplate(systemText)
        .createMessage(Map.of("name", "Quốc Chung", "voice", "hài hước"));

    ChatOptions chatOptions = ChatOptions.builder()
        /* Độ sáng tạo càng cao thì độ chính xác của câu trả  lời càng thấp */
        .temperature(0.3)
        .maxTokens(20000)
        .build();


    return chatClient
        .prompt(new Prompt(List.of(systemMessage), chatOptions))
        .user(u -> u.text(message).media(media))

        .call()
        .entity(new ParameterizedTypeReference<List<MatHang>>() {
        });
  }

  public String chatWithVideo(MultipartFile file, String message){
    Media media = Media.builder()
        .mimeType(MimeTypeUtils.parseMimeType(file.getContentType())) // video/mp4 chẳng hạn
        .data(file.getResource())
        .build();

    String systemText = """
        Bạn là trợ lý AI do {name} tạo ra. Hãy phân tích video và trả lời theo phong cách {voice}.
        """;

    Message systemMessage = new SystemPromptTemplate(systemText)
        .createMessage(Map.of("name", "Quốc Chung", "voice", "nghiêm túc"));

    ChatOptions chatOptions = ChatOptions.builder()
        .temperature(0.4)
        .maxTokens(3000)
        .build();

    return chatClient
        .prompt(new Prompt(List.of(systemMessage), chatOptions))
        .user(u -> u.text(message).media(media))
        .call()
        .content();
  }

  public String chatWithAudio(MultipartFile file, String message) {
    Media media = Media.builder()
        .mimeType(MimeTypeUtils.parseMimeType(file.getContentType()))
        .data(file.getResource())
        .build();

    String systemText = """
      Bạn là trợ lý AI do {name} tạo ra. Hãy nghe đoạn âm thanh và trả lời theo phong cách {voice}.
      """;

    Message systemMessage = new SystemPromptTemplate(systemText)
        .createMessage(Map.of("name", "Quốc Chung", "voice", "thân thiện"));

    ChatOptions chatOptions = ChatOptions.builder()
        .temperature(0.3)
        .maxTokens(3000)
        .build();

    return chatClient
        .prompt(new Prompt(List.of(systemMessage), chatOptions))
        .user(u -> u.text(message).media(media))
        .call()
        .content();
  }





   public List<ExpenseInfo> chatStructure (ChatRequest chatRequest){
        String template = """
            Bạn là trợ lý AI do {name} tạo ra. Hãy trả lời thật rõ ràng và pha chút {voice} cho tôi
            """;
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(template);

        Message systemMessage = systemPromptTemplate
                                         .createMessage(Map.of("name", "Quốc Chung","voice","hài hước"));
        Message userMessage = new UserMessage(chatRequest.message());
        ChatOptions chatOptions = ChatOptions.builder()
         .temperature(0.2)   // Mức sáng tạo
         .maxTokens(500) // Tối đa 500 tokens
         .build();

     Prompt prompt = new Prompt(List.of(systemMessage, userMessage), chatOptions);

     List<ExpenseInfo> result = chatClient
         .prompt(prompt)
         .call()
         .entity(new ParameterizedTypeReference<List<ExpenseInfo>>() {
         });
     return result;


   }

  public String chatWithImageAsString(MultipartFile file, String message, String username) throws IOException {
    // UUID dùng cho conversation (dùng username để ghi nhớ)
    String conversationId = username;

    // 1️⃣ Lấy thông tin từ file resources/data/data.txt
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/data.txt");
    String retrievedInfo = "";
    if (inputStream != null) {
      List<String> lines = new BufferedReader(new InputStreamReader(inputStream))
          .lines()
          .collect(Collectors.toList());
      // Lọc theo từ khóa (message)
      retrievedInfo = lines.stream()
          .filter(line -> line.toLowerCase().contains(message.toLowerCase()))
          .collect(Collectors.joining("\n"));
    }

    // 2️⃣ Tạo Media nếu có file
    final Media media;
    if (file != null && !file.isEmpty() && file.getContentType() != null) {
      media = Media.builder()
          .mimeType(MimeTypeUtils.parseMimeType(file.getContentType()))
          .data(file.getResource())
          .build();
    } else {
      media = null;
    }

    // 3️⃣ Tạo system message, kèm info RAG từ file
    String systemTextsss = systemText.formatted(retrievedInfo);
    System.out.print(systemTextsss);

    Message systemMessage = new SystemPromptTemplate(systemTextsss)
        .createMessage(Map.of(
            "name", "Tư vấn viên nhóm 14",
            "experience", "12",
            "voice", "Nhiệt tình, chi tiết, dễ hiểu.Ngắn gọn. Xúc tích Dùng emoji phù hợp 💻✨, hỏi thêm để hiểu nhu cầu trước khi tư vấn."
        ));

    // 4️⃣ Chat options
    ChatOptions chatOptions = ChatOptions.builder()
        .temperature(0.3)
        .maxTokens(1000)
        .build();

    // 5️⃣ Gọi ChatClient và trả về String
    return chatClient
        .prompt(new Prompt(List.of(systemMessage), chatOptions))
        .user(u -> {
          u.text(message);
          if (media != null) u.media(media);
        })
        .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
        .call()
        .content();
  }


}



