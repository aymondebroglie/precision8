import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Ingredient implements Comparable<Ingredient> {
    String name;
    float likes = 0f;
    float dislikes = 0f;
    float weight = 0f;
    Map<Ingredient, Integer> linkedIngredient = new HashMap<>();

    public Ingredient(String name) {
        this.name = name;
    }

    public void addListOfLinkedIngredients(List<Ingredient> linkedIngredientList, boolean isLiked) {
        for (Ingredient ingredient: linkedIngredientList) {
            this.linkedIngredient.computeIfPresent(ingredient, (i, count) -> count + (isLiked ? 1 : -1));
            this.linkedIngredient.putIfAbsent(ingredient, (isLiked ? 1 : -1));
        }
    }

    public void setIngredientWeight() {
        this.weight =  linkedIngredient.entrySet().stream()
                .map((entry) ->  entry.getKey().getLikesRatio() * entry.getValue().floatValue())
                .reduce(0f, Float::sum);
    }

    public float getLikesRatio() {
        return (likes + .1f) / (dislikes + .1f); // avoid if dislike = 0
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                ", likeratio=" + getLikesRatio() +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int compareTo(Ingredient o) {
        if (o != null && !o.equals(this))
            return this.weight > o.weight ? -1 : 1;
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ingredient that = (Ingredient) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
