package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.manuscrit.Manuscript;
import co.com.bancolombia.model.stats.Stats;
import co.com.bancolombia.r2dbc.dto.ManuscriptAnalysisDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;


@Repository
@RequiredArgsConstructor
public class ManuscriptRepository implements co.com.bancolombia.model.manuscrit.gateways.ManuscriptRepository {

    private final ManuscriptQueryRepository manuscriptQueryRepository;

    @Override
    public Mono<Boolean> saveManuscript(Manuscript manuscript) {
        var manuscriptDto = ManuscriptAnalysisDto.builder()
                .hasClue(manuscript.isHasClue())
                .manuscriptText(manuscript.getContent())
                .analyzedAt(LocalDateTime.now())
                .build();
        return manuscriptQueryRepository.save(manuscriptDto)
                .map(saved -> Boolean.TRUE)
                .onErrorResume(throwable -> {System.out.println("ERROR "+throwable);
                return Mono.just(Boolean.FALSE);}
                );
    }

    @Override
    public Mono<Stats> stats() {
        return manuscriptQueryRepository.getStats()
                .map(statsDto -> Stats.builder()
                        .count_clue_found(statsDto.countClueFound())
                        .count_no_clue(statsDto.countNoClue())
                        .ratio(statsDto.ratio()).build());
    }

}


