package sv.edu.itca.itca.connect.web.scrapping.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sv.edu.itca.itca.connect.web.scrapping.dto.Parts;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContentR {

    private List<Parts> parts;
    private String role;

}
