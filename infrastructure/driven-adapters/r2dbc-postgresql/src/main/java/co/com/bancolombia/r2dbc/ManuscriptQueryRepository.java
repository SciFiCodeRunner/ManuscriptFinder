package co.com.bancolombia.r2dbc;

import co.com.bancolombia.r2dbc.dto.ManuscriptAnalysisDto;
import co.com.bancolombia.r2dbc.dto.StatsDto;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ManuscriptQueryRepository extends ReactiveCrudRepository<ManuscriptAnalysisDto, Integer> {
    @Query("""
    SELECT\s
        COUNT(*) FILTER (WHERE has_clue = true) AS count_clue_found,
        COUNT(*) FILTER (WHERE has_clue = false) AS count_no_clue,
        ROUND(
            COUNT(*) FILTER (WHERE has_clue = true)::decimal\s
            / NULLIF(COUNT(*), 0), 2
        ) AS ratio
    FROM manuscript_analysis
   \s""")
    Mono<StatsDto> getStats();
}
