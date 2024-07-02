package sv.edu.itca.itca.connect.web.scrapping.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sv.edu.itca.itca.connect.web.scrapping.dao.AnalysisDao;
import sv.edu.itca.itca.connect.web.scrapping.dao.SiteDao;
import sv.edu.itca.itca.connect.web.scrapping.dto.analyzedsites.Web;
import sv.edu.itca.itca.connect.web.scrapping.dto.request.GeminiRequestDto;
import sv.edu.itca.itca.connect.web.scrapping.dto.Parts;
import sv.edu.itca.itca.connect.web.scrapping.dto.response.GeminiChatResponseDto;
import sv.edu.itca.itca.connect.web.scrapping.model.Analysis;
import sv.edu.itca.itca.connect.web.scrapping.model.Site;
import sv.edu.itca.itca.connect.web.scrapping.service.feign.GeminiClient;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class SiteServiceImpl implements SiteService {

    @Value("${gemini.apiKey}")
    private String apiKey;

    private final SiteDao siteDao;

    private final AnalysisDao analysisDao;

    private final GeminiClient geminiClient;

    private final ObjectMapper mapper;

    private final ResourceLoader resourceLoader;

    private final ModelMapper modelMapper;

    public SiteServiceImpl(SiteDao siteDao, AnalysisDao analysisDao, GeminiClient geminiClient, ObjectMapper mapper, ResourceLoader resourceLoader, ModelMapper modelMapper) {
        this.siteDao = siteDao;
        this.analysisDao = analysisDao;
        this.geminiClient = geminiClient;
        this.mapper = mapper;
        this.resourceLoader = resourceLoader;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Site> getSites() {


        return siteDao.findAll();
    }


    @Scheduled(cron = "0/10 * * ? * *")
    //@Scheduled(cron = "0 * * * * *")
    public void executeTask() throws JsonProcessingException {
        log.info("Scheduled task start at: {}", ZonedDateTime.now().format(DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy")));

        var scrappingSites = siteDao.getTop5SitesToScrap();

        if (scrappingSites.size() > 0) {
            log.info("sites that will be scrapped: {}", scrappingSites.stream().map(x -> x.getUrl()).toList());

            GeminiRequestDto geminiRequest = new GeminiRequestDto();
            GeminiChatResponseDto analysis = new GeminiChatResponseDto();

            try {
                Resource resource = resourceLoader.getResource("classpath:gemini-request.json");
                ObjectMapper objectMapper = new ObjectMapper();
                geminiRequest = objectMapper.readValue(resource.getInputStream(), GeminiRequestDto.class);

            } catch (Exception e) {
                log.error("Failed to read data from classpath: {}", resourceLoader.toString());
            }

            log.info("Gemini request: {}", geminiRequest.toString());
            var n = geminiRequest.getContents().get(0).getParts().get(0);
            n.setText(n.getText().concat(Parts.builder().text(scrappingSites.stream().map(x -> x.getUrl().toString())
                            .collect(Collectors.joining("\", \"", "{\"Webs\": {\"webs\": [\"", "\"]}}")))
                    .build().toString()));

            //convert gemini Request to json string :: BEGIN
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json;

            try {
                json = ow.writeValueAsString(geminiRequest);
                log.info("json req: {}", json);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            //convert gemini Request to json string :: END

            var scrappingResultResponse = geminiClient.getGeminiChatResponse(apiKey, json).getBody();
            log.info("scrappingResultResponse result: {}", scrappingResultResponse);
            scrappingResultResponse = formatScrappingResult(scrappingResultResponse);

            if (!isValid(scrappingResultResponse)) {
                log.error("String doesn't have a valid JSON format: {}", geminiRequest);
                return;

            } else {
                try {
                    analysis = mapper.readValue(scrappingResultResponse, GeminiChatResponseDto.class);
                } catch (JsonProcessingException e) {
                    log.error("Error parsing Gemini String to Dto: {}", e);
                }

                var analyzedSites = analysis.getCandidates().get(0).getContent().getParts().get(0).getText();
                analyzedSites = formatScrappingResult(analyzedSites);

                var resultSites = Arrays.asList(mapper.readValue(analyzedSites, Web[].class));
                resultSites.forEach(x -> x.setSite(siteDao.getSiteByUrl(x.getUrl())));

                resultSites.forEach(web -> web.setKeywords(web.getKeywords().toLowerCase()));
                log.info("resultSites: {}", resultSites);

                var listToSaved = Arrays.asList(modelMapper.map(resultSites, Analysis[].class));
                listToSaved.forEach(a -> {
                    String lowercaseObjectives = a.getObjectives().toLowerCase();
                    String objectivesWithoutBrackets = lowercaseObjectives
                            .replace("[", "")
                            .replace("]", "");
                    a.setObjectives(objectivesWithoutBrackets);
                });

                analysisDao.saveAll(listToSaved);
                log.info("analysis saved sucessfully!...");
            }
        }

        log.info("Scheduled task end at: {}", ZonedDateTime.now().format(DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy")));
    }

    boolean isValid(String request) {
        try {
            new JSONObject(request);
        } catch (JSONException e) {
            try {
                new JSONArray(request);
            } catch (JSONException ne) {
                return false;
            }
        }
        return true;
    }

    String formatScrappingResult(String request) {
        return request.replace("```json", "")
                .replace("JSON: ", "")
                .replace("**Web 1:**\n\n", "")
                .replace("**Web 2:**\n\n", "")
                .replace("**Web 3:**\n\n", "")
                .replace("**Web 4:**\n\n", "")
                .replace("**Web 5:**\n\n", "")
                .replace("```", "");
    }

}
