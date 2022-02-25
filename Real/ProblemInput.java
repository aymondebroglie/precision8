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
        public final int level;

        public Skill(String name, int level) {
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

        public Contributor(String name, List<Skill> skills) {
            this.name = name;
            this.skills = skills;
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
    }

    public final List<Contributor> contributors;
    public final List<Project> projects;
}
