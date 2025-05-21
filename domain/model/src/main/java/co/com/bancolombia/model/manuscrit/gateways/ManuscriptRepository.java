package co.com.bancolombia.model.manuscrit.gateways;

import co.com.bancolombia.model.manuscrit.Manuscript;
import co.com.bancolombia.model.stats.Stats;
import reactor.core.publisher.Mono;

public interface ManuscriptRepository   {

    Mono<Boolean> saveManuscript(Manuscript manuscript);
    Mono<Stats> stats();
};

