package real.louis_test_sol;

import real.ProblemInput;
import real.ProblemOutput;
import real.Solver;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LouisTestSolver implements Solver {
    private List<ProblemInput.Contributor> currentContrib = new ArrayList<>();

    @Override
    public ProblemOutput solve(ProblemInput input) {
        List<ProblemOutput.CompletedProject> finalProjects = new ArrayList<>();
        var toComplete = new ArrayList<>(input.projects);
        currentContrib = input.contributors;
        Runnable fillProject = () -> {
            var inProject = toComplete.get(0);
            toComplete.remove(inProject);
            var possiblePeople = inProject.roles.stream().map(role ->
                    new AbstractMap.SimpleEntry<>(role, new HashSet<ProblemInput.Contributor>())).collect(Collectors.toList());
            var mayBeOkPeople = inProject.roles.stream().map(role ->
                    new AbstractMap.SimpleEntry<>(role, new HashSet<ProblemInput.Contributor>())).collect(Collectors.toList());
            currentContrib.forEach(c -> {
                possiblePeople.stream()
                        .filter(role -> c.skills.stream().anyMatch(s -> s.name.equals(role.getKey().name) && s.level >= role.getKey().level))
                        .forEach(e -> e.getValue().add(c));
                mayBeOkPeople.stream()
                        .filter(role -> c.skills.stream().anyMatch(s -> s.name.equals(role.getKey().name) && s.level == role.getKey().level - 1))
                        .collect(Collectors.toList())
                        .forEach(e -> e.getValue().add(c));
            });
            var rolesToAssignIndex = IntStream.range(0, possiblePeople.size()).boxed().collect(Collectors.toList());
            var assign = inProject.roles.stream().map(p -> "").collect(Collectors.toList());
            while (!rolesToAssignIndex.isEmpty()) {
                var minEntryIndex = rolesToAssignIndex.stream().min(Comparator.comparingInt(
                        e -> possiblePeople.get(e).getValue().size())).get();
                var minEntry = possiblePeople.get(minEntryIndex);
                rolesToAssignIndex.remove(minEntryIndex);
                if (minEntry.getValue().isEmpty()) {
                    toComplete.add(inProject);
                    return;
                }
                var skill = minEntry.getKey();
                Function<ProblemInput.Contributor, Optional<ProblemInput.Skill>> getPersonSkill = p -> p.skills
                        .stream().filter(s -> s.name.equals(skill.name)).findAny();
                var person = minEntry.getValue().stream().min(Comparator.comparingInt(c -> getPersonSkill.apply(c).get().level)).get();
                assign.set(minEntryIndex, person.name);
                possiblePeople.forEach(e -> e.getValue().remove(person));
                getPersonSkill.apply(person)
                        .ifPresent(pSkill -> mayBeOkPeople.forEach(mayBeE -> {
                            var maySkill = mayBeE.getKey();
                            if (maySkill.name.equals(skill.name) && pSkill.level >= maySkill.level) {
                                var possible = possiblePeople.get(mayBeOkPeople.indexOf(mayBeE));
                                possible.getValue().addAll(mayBeE.getValue());
                            }
                        }));
            }
            finalProjects.add(new ProblemOutput.CompletedProject(inProject.name, assign));
            currentContrib = currentContrib.stream().map(c -> new ProblemInput.Contributor(c.name, c.skills.stream().map(s -> {
                var index = assign.indexOf(c.name);
                if (index >= 0 && inProject.roles.get(index).level >= s.level) {
                    return new ProblemInput.Skill(s.name, s.level + 1);
                }
                return s;
            }).collect(Collectors.toList()))).collect(Collectors.toList());
        };
        while (!toComplete.isEmpty()) {
            fillProject.run();
        }
        return new ProblemOutput(finalProjects);
    }
}
