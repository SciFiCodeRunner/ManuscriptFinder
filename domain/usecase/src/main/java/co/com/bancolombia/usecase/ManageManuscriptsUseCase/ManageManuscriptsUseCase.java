package co.com.bancolombia.usecase.ManageManuscriptsUseCase;

import co.com.bancolombia.model.manuscrit.Manuscript;
import co.com.bancolombia.model.manuscrit.gateways.ManuscriptRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ManageManuscriptsUseCase {
    private final ManuscriptRepository repository;

    public Mono<Boolean> saveManuscript(String manuscriptText, Boolean hasClue) {

        Manuscript manuscript = Manuscript.builder()
                .content(manuscriptText)
                .hasClue(hasClue)
                .build();
        return  repository.saveManuscript(manuscript);
    }
}
