package real.louis_test_sol;

import real.ProblemInput;
import real.ProblemOutput;
import real.Solver;

import java.util.*;
import java.util.stream.Collectors;

public class LouisTestSolver implements Solver {
    @Override
    public ProblemOutput solve(ProblemInput input) {
        var res = input.projects.stream().map(inProject -> {
            var possiblePeople = new HashMap<ProblemInput.Skill, List<String>>();
            var neededSkills = inProject.roles.stream().map(r -> r.name).collect(Collectors.toSet());
            input.contributors.forEach(c -> {
                c.skills.forEach(skill -> {
                    if (!neededSkills.contains(skill.name)) return;
                    /*possiblePeople.merge(skill.name, new ArrayList<>(Arrays.asList(c.name)), (old, n) -> {
                        var m = new ArrayList<>(old);
                        m.addAll(n);
                        return m;
                    });*/
                });
            });
            var assign = new HashMap<String, String>();
            while (possiblePeople.size() > 0) {
                var minEntry = possiblePeople.entrySet().stream().min((e1, e2) -> e2.getValue().size() - e1.getValue().size()).get();
                if (minEntry.getValue().isEmpty()) {
                    continue;
                }
                var person = minEntry.getValue().get(0);
                assign.put(minEntry.getKey().name, person);
                possiblePeople.forEach((key, value) -> {
                    value.remove(person);
                });
                possiblePeople.remove(minEntry.getKey());
            }
            return new ProblemOutput.CompletedProject(inProject.name, List.of());
        }).collect(Collectors.toList());
        return new ProblemOutput(res);
    }
}
