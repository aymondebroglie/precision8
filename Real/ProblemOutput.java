package real;

import java.util.Map;

public class ProblemOutput {
    class CompletedProject {
        public final String name;
        // role to person names
        public final Map<String, String> assignments;

        CompletedProject(String name, Map<String, String> assignments) {
            this.name = name;
            this.assignments = assignments;
        }
    }
}
