package sv.edu.itca.itca.connect.web.scrapping.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeminiChatResponseDto {

    private List<Candidates> candidates;
    private UsageMetadata usageMetadata;

}
