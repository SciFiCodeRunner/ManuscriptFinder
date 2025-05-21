package co.com.bancolombia.model.manuscrit;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Manuscript {

    private String content;
    private boolean hasClue;
}
