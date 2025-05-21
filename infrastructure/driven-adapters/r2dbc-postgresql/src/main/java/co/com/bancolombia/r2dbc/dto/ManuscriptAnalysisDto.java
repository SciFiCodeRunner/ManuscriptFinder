package co.com.bancolombia.r2dbc.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("manuscript_analysis")
@Builder
public class ManuscriptAnalysisDto {

    @Id
    private String id;

    @Column(name = "manuscript_hash")
    private String manuscriptHash;

    @Column(name = "manuscript_text")
    private String manuscriptText;

    @Column(name = "has_clue")
    private Boolean hasClue;

    @Column(name = "analyzed_at")
    private LocalDateTime analyzedAt;
}
