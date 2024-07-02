package sv.edu.itca.itca.connect.web.scrapping.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SafetyRatings {

    private String category;
    private String probability;

}
