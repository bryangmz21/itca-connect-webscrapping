package sv.edu.itca.itca.connect.web.scrapping.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import sv.edu.itca.itca.connect.web.scrapping.model.Analysis;

public interface AnalysisDao extends JpaRepository<Analysis, Integer> {

}
