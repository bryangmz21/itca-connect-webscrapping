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
public class GenerationConfig {

    private List<String> stopSequences;
    private double temperature;
    private long maxOutputTokens;
    private double topP;
    private long topK;

}
