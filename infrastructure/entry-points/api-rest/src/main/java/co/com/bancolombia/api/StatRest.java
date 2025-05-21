package co.com.bancolombia.api;
import co.com.bancolombia.model.stats.Stats;
import co.com.bancolombia.usecase.getstats.GetStatsUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * API Rest controller.
 *
 * Example of how to declare and use a use case:
 * <pre>
 * private final MyUseCase useCase;
 *
 * public String commandName() {
 *     return useCase.execute();
 * }
 * </pre>
 */
@RestController
@RequestMapping(value = "/api")
@AllArgsConstructor
public class StatRest {
    private final GetStatsUseCase getStatsUseCase;
    @GetMapping(path = "/stats")
    public Mono<Stats> analyze() {
        return getStatsUseCase.stats();
    }
}
