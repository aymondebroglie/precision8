package real;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

class Parser {
    public static ProblemInput parseProblem(String file) {
        try {
            var scanner = new Scanner(new FileInputStream(file));
            return new ProblemInput(List.of(), List.of());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}