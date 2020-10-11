package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//represents the list of ingredients used in a recipe
public class Ingredients {
    private static List<String> VEGETARIAN = new ArrayList<>(Arrays.asList("Beef", "Chicken", "Pork",
            "Fish", "Lamb", "Turkey", "Goat", "Crab", "Lobster", "Shrimp"));

    private static List<String> VEGAN_COMPONENTS = new ArrayList<>(Arrays.asList("Cheese", "Milk",
            "Yogurt", "Egg", "Honey", "Butter", "Cream", "Gelatin"));

    private static List<String> KETO = new ArrayList<>(Arrays.asList("Bread", "Potato", "Banana", "Corn",
            "Rice", "Honey", "Sugar", "Fruit Juice", "Pasta", "Cereal"));

    private List<String> ingredientList;

    //REQUIRES: first letter of each ingredient must be capitalized,singular,
    //          and must be a food. arg must consist of at least one string.
    //EFFECTS: creates a list of ingredients, consisting of arg (the arbitrary number of strings passed)

    public Ingredients(String... arg) {
        ingredientList = new ArrayList<>();
        ingredientList.addAll(Arrays.asList(arg));
    }

    //REQUIRES: ing must be a food,singular, with the first letter capitalized
    //EFFECTS: returns true if the ingredient ing is contained in a list of ingredients,
    //         and returns false if it is not

    public Boolean containsIngredient(String ing) {
        for (String s : ingredientList) {
            if (s.equals(ing)) {
                return true;
            }
        }
        return false;
    }

    //REQUIRES: all strings passed as arg must be a food,singular, and have the first letter capitalized
    //EFFECTS: takes an arbitrary amount of foods and returns true if any one of them is
    //         contained in the list of ingredients, and false if the list of ingredients contains
    //         none of them.

    public Boolean containsMoreIngredients(String... arg) {
        List<String> pickyList = new ArrayList<>(Arrays.asList(arg));
        for (String s : pickyList) {
            if (this.containsIngredient(s)) {
                return true;
            }
        }
        return false;
    }


    //EFFECTS: returns true if list of ingredients is vegetarian, and false if it is not

    public Boolean vegetarian() {
        for (String s : ingredientList) {
            if (VEGETARIAN.contains(s)) {
                return false;
            }
        }
        return true;
    }

    //EFFECTS: returns true if list of ingredients is vegan, and false if it is not

    public Boolean vegan() {
        List<String> vegan = new ArrayList<>(VEGETARIAN);
        vegan.addAll(VEGAN_COMPONENTS);
        for (String s : ingredientList) {
            if (vegan.contains(s)) {
                return false;
            }
        }
        return true;
    }

    //EFFECTS: returns true if list of ingredients is ketogenic, and false if it is not

    public Boolean keto() {
        for (String s : ingredientList) {
            if (KETO.contains(s)) {
                return false;
            }
        }
        return true;
    }

    public List<String> getIngredients() {
        return ingredientList;
    }
}
