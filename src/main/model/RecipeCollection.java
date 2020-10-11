package model;

import java.util.ArrayList;
import java.util.List;

public class RecipeCollection {

    private List<Recipe> recipeCollection;

    public RecipeCollection() {
        recipeCollection = new ArrayList<>();
    }

    public void addRecipeToCollection(String name, Integer time, String instruct, String... ing) {
        Recipe newRecipe = new Recipe(name, time, instruct, ing);
        recipeCollection.add(newRecipe);
    }

    public List<Recipe> filterRecipesByIngredients(String... ing) {
        List<Recipe> filteredList = new ArrayList<>();
        for (Recipe r : recipeCollection) {
            Ingredients indvIngredients = r.getRecipeIngredients();
            if (!indvIngredients.containsMoreIngredients(ing)) {
                filteredList.add(r);
            }
        }
        return filteredList;
    }

    public List<Recipe> filterRecipesVegetarian() {
        List<Recipe> filteredList = new ArrayList<>();
        for (Recipe r : recipeCollection) {
            Ingredients indvIngredients = r.getRecipeIngredients();
            if (indvIngredients.vegetarian()) {
                filteredList.add(r);
            }
        }
        return filteredList;
    }

    public List<Recipe> filterRecipesVegan() {
        List<Recipe> filteredList = new ArrayList<>();
        for (Recipe r : recipeCollection) {
            Ingredients indvIngredients = r.getRecipeIngredients();
            if (indvIngredients.vegan()) {
                filteredList.add(r);
            }
        }
        return filteredList;
    }

    public List<Recipe> filterRecipesKeto() {
        List<Recipe> filteredList = new ArrayList<>();
        for (Recipe r : recipeCollection) {
            Ingredients indvIngredients = r.getRecipeIngredients();
            if (indvIngredients.keto()) {
                filteredList.add(r);
            }
        }
        return filteredList;
    }

    public List<Recipe> filterRecipesByTime(Integer num) {
        List<Recipe> filteredList = new ArrayList<>();
        for (Recipe r : recipeCollection) {
            Integer time = r.getRecipeTime();
            if (time <= num) {
                filteredList.add(r);
            }
        }
        return filteredList;
    }

}
