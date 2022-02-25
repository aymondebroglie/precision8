package real;

import java.util.*;
import java.util.stream.Collectors;

public class HeuristicBasedSolver implements Solver {

    private List<ProblemInput.Contributor> availableContributors;
    private List<ProblemInput.Project> availableProjects;

    private Map<ProblemInput.Project, List<ProblemInput.Contributor>> assignments = new HashMap<>();
    private Map<ProblemInput.Project, List<ProblemInput.Contributor>> assignmentsHistory = new HashMap<>();
    private Map<ProblemInput.Project, Integer> daysWorked = new HashMap<>();

    public HeuristicBasedSolver() {}

    @Override
    public ProblemOutput solve(ProblemInput input) {
        this.availableContributors = input.contributors;
        this.availableProjects = input.projects.stream().sorted(Comparator.comparingInt(ProblemInput.Project::getWeight)).collect(Collectors.toList());
        int currentDay = 0;
        int currentScore = 0;

        do {
            System.out.println("days worked: " + currentDay + " - projects left: " + availableProjects.size());

            // incr days worked for each project
            for (ProblemInput.Project project: daysWorked.keySet()) {
                daysWorked.put(project, daysWorked.get(project) + 1);
            }

            // free contributors from finished projects
            for (ProblemInput.Project project: new ArrayList<>(daysWorked.keySet())) {
                currentScore += resolveProject(project, daysWorked.get(project), currentDay);
            }

            // filter project which deadline expired
            int finalCurrentDay = currentDay;
            availableProjects = availableProjects.stream()
                    .filter(project -> project.bestBefore + project.score - project.daysRequired > finalCurrentDay)
                    .collect(Collectors.toList());

            // assign contributors to project
            for (ProblemInput.Project project: new ArrayList<>(availableProjects)) {
                assignToProject(project, availableContributors);
            }

            currentDay++;
        } while (!daysWorked.isEmpty());

        System.out.println("Score :" + currentScore + " _ Projects aborted :" + (input.projects.size() - assignmentsHistory.size()));
        return new ProblemOutput(assignmentsHistory.entrySet().stream().map(entry ->
            new ProblemOutput.CompletedProject(entry.getKey().name, entry.getValue().stream().map(contributor -> contributor.name).collect(Collectors.toList()))
        ).collect(Collectors.toList()));
    }

    private boolean assignToProject(ProblemInput.Project project, List<ProblemInput.Contributor> contributors) {
        List<ProblemInput.Contributor> staggedContributors = new ArrayList<>();
        for (ProblemInput.Skill role: project.roles) {
            var filteredContributors = contributors.stream().filter(contributor -> doesContributorSatisfyRole(contributor, role, staggedContributors));
            var expert = filteredContributors.sorted(Comparator.comparingInt(c -> c.getWeightForRole(role))).findFirst();
            if (expert.isEmpty()) return false;
            else staggedContributors.add(expert.get());
        }
        assignments.put(project, staggedContributors);
        assignmentsHistory.put(project, staggedContributors);
        daysWorked.put(project, 0);
        availableContributors.removeAll(staggedContributors);
        availableProjects.remove(project);
        return true;
    }

    private boolean doesContributorSatisfyRole(ProblemInput.Contributor contributor, ProblemInput.Skill role, List<ProblemInput.Contributor> mentors) {
        boolean isUsefulMentors = !mentors.isEmpty() && mentors.stream().anyMatch(mentor -> mentor.getSKill(role.name).level >= role.level);
        return contributor.skills.stream().anyMatch(skill ->  skill.name.equals(role.name) && (skill.level >= role.level || (skill.level == role.level - 1 && isUsefulMentors)));
    }

    private int resolveProject(ProblemInput.Project project, Integer daysSpent, Integer currentDay) {
        if (project.daysRequired > daysSpent) return 0;
        int value = project.bestBefore > currentDay ? project.score : Math.max(project.bestBefore - currentDay, 0);
        List<ProblemInput.Contributor> activeContributors = assignments.get(project);
        for (int i = 0; i < activeContributors.size(); i++) {
            ProblemInput.Skill role = project.roles.get(i);
            ProblemInput.Skill skill = activeContributors.get(i).getSKill(role.name);
            if (role.level == skill.level || role.level - 1 == skill.level) skill.level += 1;
        }
        availableContributors.addAll(activeContributors);
        assignments.remove(project);
        daysWorked.remove(project);
        return value;
    }
}
