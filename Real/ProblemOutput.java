package real;

import java.util.List;
import java.util.Map;

public class ProblemOutput {
    public static class CompletedProject {
        public final String name;
        // role to person names
        public final Map<String, String> assignments;

        public CompletedProject(String name, Map<String, String> assignments) {
            this.name = name;
            this.assignments = assignments;
        }
    }

    public ProblemOutput(List<CompletedProject> projects) {
        this.projects = projects;
    }

    public final List<CompletedProject> projects;
}
