package real;

import real.louis_test_sol.LouisTestSolver;

import java.io.File;
import java.util.Arrays;

class Main {
    public static void main(String[] args) {
        var folder = new File(args[0]);
        Arrays.stream(folder.listFiles()).sorted().forEach(file -> {
            if (!file.getName().contains(".in.")) {
                return;
            }
            System.out.println("Processing " + file.getName());
            var input = Parser.parseProblem(file.getAbsolutePath());
            var solver = new LouisTestSolver();
            SolToFile.write(file, solver.solve(input));
        });
    }
}