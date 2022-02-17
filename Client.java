import java.util.List;

public class Client {

    private List<String> likedIngredients;
    private List<String> dislikedIngredients;

    public Client(
        List<String> likedIngredients,
        List<String> dislikedIngredients
    ) {
        this.likedIngredients = likedIngredients;
        this.dislikedIngredients = dislikedIngredients;
    }

    public List<String> getDislikedIngredients() {
        return dislikedIngredients;
    }

    public List<String> getLikedIngredients() {
        return likedIngredients;
    }
}
