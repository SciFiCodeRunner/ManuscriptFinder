package co.com.bancolombia.api;
import co.com.bancolombia.api.dto.ManuscriptRequestDto;
import co.com.bancolombia.usecase.ManageManuscriptsUseCase.ManageManuscriptsUseCase;
import co.com.bancolombia.usecase.analizemanuscript.AnalizeManuscriptUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Arrays;

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
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ClueRest {
private final AnalizeManuscriptUseCase analizeManuscriptUseCase;
private final ManageManuscriptsUseCase manageManuscriptsUseCase;
    @PostMapping(path = "/clue", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> analyze(@RequestBody Mono<ManuscriptRequestDto> request) {
        return request
                .map(ManuscriptRequestDto::getManuscript)
                .flatMap(manuscript -> analizeManuscriptUseCase.executeAnalysis(manuscript)
                        .flatMap(hasClue ->
                                manageManuscriptsUseCase.saveManuscript(Arrays.toString(manuscript), hasClue)
                                        .thenReturn(hasClue)
                        )
                )
                .map(hasClue -> hasClue
                        ? ResponseEntity.ok().build()
                        : ResponseEntity.status(HttpStatus.FORBIDDEN).build()
                )
                .onErrorResume(IllegalArgumentException.class, ex ->
                        Mono.just(ResponseEntity.badRequest().body("Error: " + ex.getMessage())));
    }
}
