package co.com.bancolombia.usecase.getstats;

import co.com.bancolombia.model.manuscrit.gateways.ManuscriptRepository;
import co.com.bancolombia.model.stats.Stats;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GetStatsUseCase {
    private final ManuscriptRepository manuscriptRepository;
    public Mono<Stats> stats() {
        return  manuscriptRepository.stats();
    }
}
