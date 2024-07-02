package sv.edu.itca.itca.connect.web.scrapping.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "analisis", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Analysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "sitio_id", nullable = false)
    private Site site;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "titulo")
    private String title;

    @Column(name = "tema")
    private String topic;

    @Column(name = "analisis")
    private String analysis;

    @Column(name = "sugerencia")
    private String suggestion;

    @Column(name = "palabras_claves")
    private String keyWords;

    @Column(name = "objetivos")
    private String objectives;

    @PrePersist
    void setCreatedAt() {
        createdAt = LocalDateTime.now();
    }


}
