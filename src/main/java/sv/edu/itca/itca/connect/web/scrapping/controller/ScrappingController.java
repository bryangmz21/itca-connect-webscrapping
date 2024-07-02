package sv.edu.itca.itca.connect.web.scrapping.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sv.edu.itca.itca.connect.web.scrapping.model.Site;
import sv.edu.itca.itca.connect.web.scrapping.service.SiteService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/web-scrapping")
public class ScrappingController {

    private final SiteService siteService;

    public ScrappingController(SiteService siteService) {
        this.siteService = siteService;
    }

    @GetMapping
    public List<Site> getSites() {
        return siteService.getSites();
    }

}
