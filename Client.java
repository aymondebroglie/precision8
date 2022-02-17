import java.util.List;
import java.util.Set;

public class Client {

    private Set<String> likedIngredients;
    private Set<String> dislikedIngredients;

    public Client(
        Set<String> likedIngredients,
        Set<String> dislikedIngredient
  ){
    this.likedIngredients = likedIngredients;
    this.dislikedIngredients = dislikedIngredient;
  }

    public Set<String> getDislikedIngredients() {
        return dislikedIngredients;
    }

    public Set<String> getLikedIngredients() {
        return likedIngredients;
    }
}
