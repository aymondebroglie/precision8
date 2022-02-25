package real;

import java.util.List;
import java.util.Objects;

public class ProblemInput {
    public ProblemInput(List<Contributor> contributors, List<Project> projects) {
        this.contributors = contributors;
        this.projects = projects;
    }

    public static class Skill implements Comparable<Skill> {
        public final String name;
        public int level;

        Skill(String name, int level) {
            this.name = name;
            this.level = level;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Skill skill = (Skill) o;
            return level == skill.level && Objects.equals(name, skill.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, level);
        }

        @Override
        public int compareTo(Skill o) {
            var c1 = name.compareTo(o.name);
            return c1 == 0 ? o.level - level : c1;
        }
    }
    public static class Contributor {
        public final String name;
        public final List<Skill> skills;

        Contributor(String name, List<Skill> skills) {
            this.name = name;
            this.skills = skills;
        }

        public Skill getSKill(String skillName) {
            return skills.stream().filter(skill -> skill.name.equals(skillName)).findAny().orElse(new Skill(skillName, 0));
        }

        public int getWeightForRole(Skill role) {
            var level = getSKill(role.name).level;
            if (level > role.level) return 1; // it's better if the person learn
            return (int)( level / (float)skills.size() + 1 ); // we use expert of one field first not to dry resources
        }
    }

    public static class Project {
        public final String name;
        public final int daysRequired;
        public final int score;
        public final int bestBefore;
        public final List<Skill> roles;

        Project(String name, int daysRequired, int score, int bestBefore, List<Skill> roles) {
            this.name = name;
            this.daysRequired = daysRequired;
            this.score = score;
            this.bestBefore = bestBefore;
            this.roles = roles;
        }

        public Skill getRole(String roleName) {
            return roles.stream().filter(role -> role.name.equals(roleName)).findAny().orElse(null);
        }

        // the lower the fastest it should be done
        public int getWeight() {
            // we value score and deadline and set a priority based on roles level to do the project to ensure we max contributors learning
            // we mitigate the score to have it on the same scale as deadline
            // The more complex projects will be the more its complexity weight and the more we will have contributors learning to face complex projects
            return bestBefore - daysRequired - score / 10
                    + roles.stream().map(skill -> skill.level).reduce(1, Integer::sum);
        }
    }

    public final List<Contributor> contributors;
    public final List<Project> projects;
}
