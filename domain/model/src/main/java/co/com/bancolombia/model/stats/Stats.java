package co.com.bancolombia.model.stats;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Stats {
    private Long count_clue_found;
    private Long count_no_clue;
    private double ratio;
}
