package tn.esprit.tpfoyer.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private String status; // PLANIFIÉ, EN COURS, TERMINÉ
    private LocalDate startDate;
    private LocalDate endDate;
    private Double latitude;
    private Double longitude;

    @Lob
    private String images; // Store only filename
}
