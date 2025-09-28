package com.email.email_writer_kt.service;
import com.email.email_writer_kt.model.MailGeneratorModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class MailGeneratorService {

    private final WebClient webClient;
    private final String apikey;

    public MailGeneratorService(WebClient.Builder webClientBuilder,
            @Value("${gemini.api.url}") String baseUrl,
            @Value("${gemini.api.key}") String geminiApiKey) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.apikey = geminiApiKey;
    }

    public String generateMailReply(MailGeneratorModel mailGeneratorModel) {
        //build promp
        String prompt = buildPrompt(mailGeneratorModel);
        //create raw json format
        String requestBody = String.format("""
                {
                    "contents": [
                      {
                        "parts": [
                          {
                            "text": "%s"
                          }
                        ]
                      }
                    ]
                  }
                """,prompt);
        //send req2575
        String response =webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/v1beta/models/gemini-2.5-flash:generateContent")
                        .build())
                .header("X-goog-api-key",apikey)
                .header("Content-Type","application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        //extract json
        return extractResponseContent(response);
    }

    private String extractResponseContent(String response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response);
            return root.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0).
                    path("text")
                    .asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildPrompt(MailGeneratorModel mailGeneratorModel) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("Generate a professional email reply for the following email:");
        if(mailGeneratorModel.getTone() !=null && mailGeneratorModel.getTone().isEmpty()){
            prompt.append("Use a").append(mailGeneratorModel.getTone()).append("tone.");
        }
        prompt.append("Original Mail: \n").append(mailGeneratorModel.getMailContent());
        return prompt.toString();
    }
}
