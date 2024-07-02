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
public class Candidates {

    private ContentR content;
    private String finishReason;
    private long index;
    private List<SafetyRatings> safetyRatings;
}
