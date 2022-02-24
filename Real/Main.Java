package real;

import real.louis_test_sol.LouisTestSolver;

import java.util.List;

class Main {
    public static void main(String[] args) {
        var input = Parser.parseProblem(args[0]);
        var solver = new LouisTestSolver();
        SolToFile.write(input, solver.solve(input));
    }
}