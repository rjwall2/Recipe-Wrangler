package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ingredients {
    private static List<String> VEGETARIAN = new ArrayList<>(Arrays.asList("Beef", "Chicken", "Pork",
            "Fish", "Lamb", "Turkey", "Goat", "Crab", "Lobster", "Shrimp"));

    private static List<String> VEGAN_COMPONENTS = new ArrayList<>(Arrays.asList("Cheese", "Milk",
            "Yogurt", "Eggs", "Honey", "Butter", "Cream", "Gelatin"));

    private static List<String> KETO = new ArrayList<>(Arrays.asList("Bread", "Potato", "Bananas", "Corn",
            "Rice", "Honey", "Sugar", "Fruit Juice", "Pasta", "Cereal"));

    private List<String> ingredientList;

    public Ingredients(String... arg) {
        ingredientList = new ArrayList<>();
        ingredientList.addAll(Arrays.asList(arg));
    }

    public Boolean containsIngredient(String ing) {
        for (String s : ingredientList) {
            if (s.equals(ing)) {
                return false;
            }
        }
        return true;
    }

    public Boolean containsMoreIngredients(String... arg) {
        List<String> pickyList = new ArrayList<>();
        pickyList.addAll(Arrays.asList(arg));
        for (String s : pickyList) {
            if (this.containsIngredient(s)) {
                return false;
            }
        }
        return true;
    }

    public Boolean vegetarian() {
        for (String s : ingredientList) {
            if (VEGETARIAN.contains(s)) {
                return false;
            }
        }
        return true;
    }

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

    public Boolean keto() {
        for (String s : ingredientList) {
            if (KETO.contains(s)) {
                return false;
            }
        }
        return true;
    }
}
