package real;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Parser {
    public static ProblemInput parseProblem(String file) {
        try {
            var scanner = new Scanner(new FileInputStream(file));
            int nbContrib = scanner.nextInt();
            int nbProject = scanner.nextInt();
            var contributors = IntStream.range(0, nbContrib).sequential().mapToObj(i -> {
                var name = scanner.next();
                int nbSkills = scanner.nextInt();
                var skills = IntStream.range(0, nbSkills).sequential().mapToObj(j -> {
                   String skillName = scanner.next();
                   int level = scanner.nextInt();
                   return new ProblemInput.Skill(skillName, level);
                }).collect(Collectors.toList());
                return new ProblemInput.Contributor(name, skills);
            }).collect(Collectors.toList());

            var projects = IntStream.range(0, nbProject).sequential().mapToObj(i -> {
                var name = scanner.next();
                var nbDays = scanner.nextInt();
                var score = scanner.nextInt();
                var bestBefore = scanner.nextInt();
                var nbRoles = scanner.nextInt();
                var roles = IntStream.range(0, nbRoles).sequential().mapToObj(j -> {
                    String skillName = scanner.next();
                    int level = scanner.nextInt();
                    return new ProblemInput.Skill(skillName, level);
                }).collect(Collectors.toList());
                return new ProblemInput.Project(name, nbDays, score, bestBefore, roles);
            }).collect(Collectors.toList());

            return new ProblemInput(contributors, projects);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}