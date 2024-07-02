package sv.edu.itca.itca.connect.web.scrapping.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsageMetadata {

    private long promptTokenCount;
    private long candidatesTokenCount;
    private long totalTokenCount;
}
