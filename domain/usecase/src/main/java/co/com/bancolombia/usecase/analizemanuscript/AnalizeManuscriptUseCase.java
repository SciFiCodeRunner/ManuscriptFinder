package co.com.bancolombia.usecase.analizemanuscript;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
public class AnalizeManuscriptUseCase {

    private static final int SEQUENCE_LENGTH = 4;

    public Mono<Boolean> executeAnalysis(String[] manuscript) {
        return Mono.fromSupplier(() -> execute(manuscript))
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Boolean execute(String[] manuscript) {

        if (manuscript == null || manuscript.length == 0) {
            return Boolean.FALSE;
        }

        int numRows = manuscript.length;
        int numCols = manuscript[0].length();

        for (String row : manuscript) {
            if (row == null || row.length() != numCols) {
                throw new IllegalArgumentException("Todas las líneas deben tener la misma longitud y no deben ser null");
            }
        }

        char[][] matrix = new char[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            matrix[i] = manuscript[i].toCharArray();
        }

        int[][] directions = {
                {0, 1},
                {1, 0},
                {1, 1},
                {1, -1}
        };

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                char currentChar = matrix[row][col];
                for (int[] direction : directions) {
                    if (hasSequence(matrix, row, col, direction[0], direction[1], currentChar)) {
                        int endRow = row + (SEQUENCE_LENGTH - 1) * direction[0];
                        int endCol = col + (SEQUENCE_LENGTH - 1) * direction[1];
                        System.out.printf("Patrón encontrado: '%c' desde (%d, %d) hasta (%d, %d)%n",
                                currentChar, row, col, endRow, endCol);
                        return Boolean.TRUE;
                    }
                }
            }
        }

        return Boolean.FALSE;
    }

    private boolean hasSequence(char[][] matrix, int row, int col, int dx, int dy, char targetChar) {
        int numRows = matrix.length;
        int numCols = matrix[0].length;

        for (int step = 1; step < SEQUENCE_LENGTH; step++) {
            int nextRow = row + step * dx;
            int nextCol = col + step * dy;

            if (nextRow < 0 || nextRow >= numRows || nextCol < 0 || nextCol >= numCols) {
                return Boolean.FALSE;
            }

            if (matrix[nextRow][nextCol] != targetChar) {
                return Boolean.FALSE;
            }
        }

        return Boolean.TRUE;
    }


}
