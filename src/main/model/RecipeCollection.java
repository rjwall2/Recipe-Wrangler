package model;

import java.util.ArrayList;
import java.util.List;

public class RecipeCollection {

    private List<Recipe> recipeCollection;

    public RecipeCollection() {
        recipeCollection = new ArrayList<>();
    }

    public void addRecipeToCollection(String name,Integer time,String instruct,String...ing) {
        Recipe newRecipe = new Recipe(name,time,instruct,ing);
        recipeCollection.add(newRecipe);
    }

    public List<Recipe> filterRecipesByIngredients(String...ing) {

    }

}
