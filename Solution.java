import java.util.*;
import java.util.stream.Collectors;

public class Solution {



    public static  Set<String> naiveAlgo(List<Client> clients ){

        Set<String> ingredients = new HashSet<>();
        Long bestScore = 0l;
        Set<String> bestPizza = new HashSet<>();
        JJ

        Set<String> allIngredients = getAllIngredients(clients);

        while (!allIngredients.isEmpty()){
            Map<String, Integer>  baseIngredientCount = allIngredients.stream().collect(Collectors.toMap( s -> s , s->0));
            Map<String, Integer> dislikedIngredientsCount = countDislikedIngredients(clients, baseIngredientCount);
            String minIngredient = getMinIngredient(dislikedIngredientsCount);

            ingredients.add(minIngredient);
            allIngredients.remove(minIngredient);

            clients = filterClients(clients, minIngredient);
            Long score  = computeScore(ingredients, clients);


            if (score > bestScore){
                bestScore = score;
                bestPizza = new HashSet<>(ingredients);
            }
        }
        return bestPizza;
    }

    private static Set<String> getAllIngredients(List<Client> clients) {
        Set<String> allIngredients = new HashSet<>();
        for(Client client : clients){
            allIngredients.addAll(client.getLikedIngredients());
            allIngredients.addAll(client.getDislikedIngredients());
        }
        return allIngredients;
    }

    private static Long computeScore(Set<String> ingredients, List<Client> clients) {
       return clients.stream().filter(c -> ingredients.containsAll(c.getLikedIngredients())).count();
    }

    private static List<Client> filterClients(List<Client> client , String ingredient) {
       return  client.stream().filter(c -> !c.getDislikedIngredients().contains(ingredient)).collect(Collectors.toList());
    }

    private static String getMinIngredient(Map<String, Integer> dislikedIngredientsCount) {
        Integer min = Integer.MAX_VALUE;
        String ing = null;
       for(var e : dislikedIngredientsCount.entrySet()){
            if (e.getValue() < min){
                min = e.getValue();
                ing = e.getKey();
            }
       }
       return ing;
    }

    private static Map<String, Integer> countDislikedIngredients(List<Client> clients, Map<String, Integer> baseIngredientCount) {
        Map<String,Integer> dislikedIngredientsCount = new HashMap<>(baseIngredientCount);
        for(Client client : clients){
            for(String disliked : client.getDislikedIngredients()){
                var s = dislikedIngredientsCount.get(disliked);
                s +=1;
                dislikedIngredientsCount.put(disliked, s);
            }
        }
        return dislikedIngredientsCount;
    }


}
