package real;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

public class SolToFile {
    public static void write(File inputFile, ProblemInput input, ProblemOutput output) {
        try (var writer = new PrintWriter(new File(inputFile.getParent(), inputFile.getName().replace(".in.", ".out.")))) {
            writer.println(output.projects.size());
            output.projects.forEach(project -> {
                writer.println(project.name);
                input.projects.stream().filter(p -> p.name.equals(project.name)).findAny().ifPresent(inProject -> {
                    writer.println(
                            String.join(" ", project.assignments));
                });
            });
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
