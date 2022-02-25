package real;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class SolToFile {
    public static void write(File inputFile, ProblemOutput output) {
        try (var writer = new PrintWriter(new File(inputFile.getParent(), inputFile.getName().replace(".in.", ".out.")))) {
            writer.println(output.projects.size());
            output.projects.forEach(project -> {
                writer.println(project.name);
                writer.println(String.join(" ", project.assignments));
            });
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
