package real.louis_test_sol;

import real.ProblemInput;
import real.ProblemOutput;
import real.Solver;

import java.util.List;
import java.util.Map;

public class LouisTestSolver implements Solver {
    @Override
    public ProblemOutput solve(ProblemInput input) {
        return new ProblemOutput(List.of(
                new ProblemOutput.CompletedProject(input.projects.get(0).name, Map.of(
                        input.projects.get(0).roles.get(0).name, "BobTest"
                ))
        ));
    }
}
