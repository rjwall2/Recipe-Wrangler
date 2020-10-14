package model;

import java.util.ArrayList;
import java.util.List;

//Represents a collection of a users recipes
public class RecipeCollection {

    private List<Recipe> recipeCollection;


    //EFFECTS: Builds a new RecipeCollection, with recipeCollection field initially empty
    public RecipeCollection() {
        recipeCollection = new ArrayList<>();
    }


    //REQUIRES: all parameters must be filled, all strings passed in ing must have the first
    //          letter capitalized, be singular, and must be a food.
    //MODIFIES: this
    //EFFECTS: adds a Recipe to a RecipeCollection
    public void addRecipeToCollection(String name, Integer time, String instruct, String... ing) {
        Recipe newRecipe = new Recipe(name, time, instruct, ing);
        recipeCollection.add(newRecipe);
    }


    //REQUIRES: ing must consist of at least one string, all strings passed in ing must have
    //          the first letter capitalized, be singular, and must be a food.
    //EFFECTS: Takes a RecipeCollection, and creates a new list of Recipes that do not contain
    //         any of the strings passed in ing (foods), in their Ingredients.
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


    //EFFECTS: Takes a RecipeCollection, and creates a new list of vegetarian Recipes from the RecipesCollection
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


    //EFFECTS: Takes a RecipeCollection, and creates a new list of vegan Recipes from the RecipeCollection
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


    //EFFECTS: Takes a RecipeCollection, and creates a new list of ketogenic Recipes from the RecipeCollection
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


    //EFFECTS: Takes a RecipeCollection, and creates a new list of Recipes with time less than or
    //         equal to num from the RecipesCollection
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


    public List<Recipe> getRecipeCollection() {
        return recipeCollection;
    }

}
