package real;

import java.util.*;
import java.util.stream.Collectors;

public class HeuristicBasedSolver implements Solver {

    private List<ProblemInput.Contributor> availableContributors;
    private List<ProblemInput.Project> availableProjects;

    private Map<ProblemInput.Project, List<ProblemInput.Contributor>> assignments = new HashMap<>();
    private Map<ProblemInput.Project, List<ProblemInput.Contributor>> assignmentsHistory = new HashMap<>();
    private Map<ProblemInput.Project, Integer> daysWorked = new HashMap<>();

    public HeuristicBasedSolver(ProblemInput input) {
        this.availableContributors = input.contributors;
        this.availableProjects = input.projects;
    }

    public void solve() {
        var sortedProjectLeft = availableProjects.stream().sorted((p1, p2) -> p2.score - p1.score).collect(Collectors.toList());
        int currentDay = 0;
        int currentScore = 0;

        while (!sortedProjectLeft.isEmpty()) {
            // incr days worked for each project
            for (ProblemInput.Project project: daysWorked.keySet()) {
                daysWorked.put(project, daysWorked.get(project) + 1);
            }

            // free contributors from finished projects
            for (ProblemInput.Project project: daysWorked.keySet()) {
                currentScore += resolveProject(project, daysWorked.get(project), currentDay);
            }

            // assign contributors to project
            for (ProblemInput.Project project: sortedProjectLeft) {
                assignToProject(project, availableContributors);
            }

            currentDay++;
        }

        System.out.println("Score :" + currentScore);
        assignmentsHistory.entrySet().stream().forEach(entry -> {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue().stream().map(skill -> skill.name).collect(Collectors.joining(" ")));
        });
    }

    private boolean assignToProject(ProblemInput.Project project, List<ProblemInput.Contributor> contributors) {
        List<ProblemInput.Contributor> staggedContributors = new ArrayList<>();
        for (ProblemInput.Skill role: project.roles) {
            var filteredContributor = contributors.stream().filter(contributor -> doesContributorSatisfyRole(contributor, role)).findAny();
            if (filteredContributor.isEmpty()) return false;
            else staggedContributors.add(filteredContributor.get());
        }
        assignments.put(project, staggedContributors);
        assignmentsHistory.put(project, staggedContributors);
        daysWorked.put(project, 0);
        availableContributors.removeAll(staggedContributors);
        return true;
    }

    private boolean doesContributorSatisfyRole(ProblemInput.Contributor contributor, ProblemInput.Skill role) {
        return contributor.skills.stream().anyMatch(skill ->  skill.name.equals(role.name) && skill.level > role.level);
    }

    private int resolveProject(ProblemInput.Project project, Integer daysSpent, Integer currentDay) {
        if (project.daysRequired < daysSpent) return 0;
        int value = project.bestBefore > currentDay ? project.score : Math.max(project.bestBefore - currentDay, 0);
        availableContributors.addAll(assignments.get(project));
        assignments.remove(project);
        return value;
    }

    @Override
    public ProblemOutput solve(ProblemInput input) {
        return null;
    }
}
