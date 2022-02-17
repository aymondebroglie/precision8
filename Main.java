import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numberOfClients = Integer.parseInt(scanner.nextLine());
        Client[] clients = new Client[numberOfClients];

        for (int i = 0; i < numberOfClients; i++) {
            List<String> likedIngredients = Arrays.asList(scanner.nextLine().split(" "));
            List<String> dislikedIngredients = Arrays.asList(scanner.nextLine().split(" "));

            likedIngredients = new ArrayList<>(likedIngredients.subList(1, likedIngredients.size()));
            dislikedIngredients = new ArrayList<>(dislikedIngredients.subList(1, dislikedIngredients.size()));

            clients[i] = new Client(likedIngredients, dislikedIngredients);
        }

        scanner.close();

        KnapsackStyleSolver solver = new KnapsackStyleSolver(clients);
        solver.solve();
    }
}
