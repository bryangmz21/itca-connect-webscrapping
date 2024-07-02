package sv.edu.itca.itca.connect.web.scrapping.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeminiRequestDto {

    private List<Content> contents;
    private List<SafetySettings> safetySettings;
    private GenerationConfig generationConfig;
}
