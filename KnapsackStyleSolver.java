import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KnapsackStyleSolver {

    List<Ingredient> ingredients = new ArrayList<>();
    Client[] clients;

    public KnapsackStyleSolver(Client[] clients) {
        this.clients = clients;
    }

    public void solve() {
        weightIngredients();
        ingredients.sort(Comparator.naturalOrder());
        List<Ingredient> bucket = new ArrayList<>();
        List<Client> clientPool = Collections.emptyList();
        List<Client> filtered;

        for(Ingredient ingredient: ingredients) {
            System.out.println(ingredient);
            bucket.add(ingredient);
            filtered = Arrays.stream(clients)
                    .filter(client -> isClientSatisfied(client, bucket))
                    .toList();
            if (clientPool.size() <= filtered.size()) clientPool = filtered;
            else {
                System.out.println("Erk! No " + ingredient.name + " please");
                bucket.remove(ingredient);
            }
        }
        System.out.println("Clients left : " + clientPool.size());
        System.out.println(
                bucket.size() + " " + bucket.stream().map(i -> i.name).collect(Collectors.joining(" "))
        );
    }

    private boolean isClientSatisfied(Client client, List<Ingredient> ingredients) {
        boolean containsAllGood = ingredients.stream().map(i -> i.name).toList().containsAll(client.getLikedIngredients());
        boolean containsAnyBad = ingredients.stream().map(i -> i.name).anyMatch(name -> client.getDislikedIngredients().contains(name));
        return containsAllGood && !containsAnyBad;
    }

    private void weightIngredients() {
        for (Client client : clients) {
            List<Ingredient> likedIngredients = client.getLikedIngredients().stream().map(ingredient -> likeIngredient(ingredient, true)).toList();
            List<Ingredient> dislikedIngredients = client.getDislikedIngredients().stream().map(ingredient -> likeIngredient(ingredient, false)).toList();
            Stream.of(likedIngredients, dislikedIngredients).flatMap(List::stream).forEach(
                    ingredient -> {
                        ingredient.addListOfLinkedIngredients(likedIngredients, true);
                        ingredient.addListOfLinkedIngredients(dislikedIngredients, false);
                    }
            );
        }
        ingredients.forEach(Ingredient::setIngredientWeight);
    }

    private Ingredient likeIngredient(String ingredientName, boolean like) {
        Optional<Ingredient> ingredientOpt = ingredients.stream().filter(i -> i.name.equals(ingredientName)).findAny();
        if (ingredientOpt.isPresent()) {
            Ingredient ingredient = ingredientOpt.get();
            if (like) ingredient.likes ++;
            else ingredient.dislikes ++;
            return ingredient;
        }
        else {
            Ingredient newIngredient = new Ingredient(ingredientName);
            if (like) newIngredient.likes ++;
            else newIngredient.dislikes ++;
            ingredients.add(newIngredient);
            return newIngredient;
        }
    }
}
