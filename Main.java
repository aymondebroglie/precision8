import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*; public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("/Users/aymondebroglie/Downloads/d_difficult.in.txt"));

        int numberOfClients = Integer.parseInt(scanner.nextLine());
        Client[] clients = new Client[numberOfClients];

        for (int i = 0; i < numberOfClients; i++) {
            List<String> likedIngredients = Arrays.asList(scanner.nextLine().split(" "));
            List<String> dislikedIngredients = Arrays.asList(scanner.nextLine().split(" "));

            likedIngredients = new ArrayList<>(likedIngredients.subList(1, likedIngredients.size()));
            dislikedIngredients = new ArrayList<>(dislikedIngredients.subList(1, dislikedIngredients.size()));

            clients[i] = new Client(new HashSet<>(likedIngredients), new HashSet<>(dislikedIngredients));
        }

        scanner.close();

        Set<String> solution = Solution.naiveAlgo(Arrays.asList(clients));

        String submission = "" + solution.size();

        for(String i : solution){
            submission += " " + i;
        }

        System.out.println(submission);

    }


}
