package sv.edu.itca.itca.connect.web.scrapping.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sv.edu.itca.itca.connect.web.scrapping.model.Site;

import java.util.List;

public interface SiteDao extends JpaRepository<Site, Integer> {

    @Query("SELECT s FROM Site s LEFT JOIN Analysis a ON s.id = a.site.id WHERE a.site.id IS NULL ORDER BY s.id LIMIT 5")
    List<Site> getTop5SitesToScrap();

    Site getSiteByUrl(String url);
}
