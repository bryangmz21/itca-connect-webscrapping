package sv.edu.itca.itca.connect.web.scrapping.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SafetySettings {

    private String category;
    private String threshold;

}
