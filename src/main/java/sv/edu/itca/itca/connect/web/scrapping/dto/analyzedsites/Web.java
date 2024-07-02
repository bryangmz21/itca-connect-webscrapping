package sv.edu.itca.itca.connect.web.scrapping.dto.analyzedsites;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sv.edu.itca.itca.connect.web.scrapping.model.Site;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Web {

    private String url;

    @JsonProperty("título")
    private String title;

    @JsonProperty("tema")
    private String topic;

    @JsonAlias({"análisis", "analisis"})
    private String analysis;

    @JsonProperty("objetivos")
    private List<String> objectives;

    @JsonProperty("sugerencia")
    private String suggestion;

    @JsonProperty("palabras_claves")
    private String keywords;

    private transient Site site;

}
