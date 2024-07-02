package sv.edu.itca.itca.connect.web.scrapping.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "gemini-chat", url = "${GEMINI_URL:https://generativelanguage.googleapis.com/v1beta/models}")
public interface GeminiClient {

    @PostMapping("/gemini-pro:generateContent")
    ResponseEntity<String> getGeminiChatResponse(@RequestParam String key, @RequestBody String request);

}
