package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientsTest {


    @Test
    void ingredientsConstructorTest() {
        Ingredients ingredientsTest = new Ingredients("Cheese", "Milk", "Apple");

        List<String> listOfIngredientsTest = ingredientsTest.getIngredients();
        assertEquals(3, listOfIngredientsTest.size());

        assertTrue(listOfIngredientsTest.contains("Cheese"));
        assertTrue(listOfIngredientsTest.contains("Milk"));
        assertTrue(listOfIngredientsTest.contains("Apple"));
    }

    @Test
    void containsIngredientTest() {
        Ingredients ingredientsTest = new Ingredients("Peanut", "Fish", "Mango", "Spinach");

        List<String> listOfIngredientsTest = ingredientsTest.getIngredients();
        assertEquals(4,listOfIngredientsTest.size());

        assertTrue(ingredientsTest.containsIngredient("Peanut"));
        assertTrue(ingredientsTest.containsIngredient("Spinach"));
        assertTrue(ingredientsTest.containsIngredient("Mango"));

        assertFalse(ingredientsTest.containsIngredient("Carrot"));
        assertFalse(ingredientsTest.containsIngredient("Peanuts"));
    }

    @Test
    void containsMoreIngredientsTest() {
        Ingredients ingredientsTest = new Ingredients("Peanut", "Fish", "Mango", "Spinach",
                "Peach", "Honey","Bean");

        assertTrue(ingredientsTest.containsMoreIngredients("Peanut","Strawberry","Bread"));
        assertTrue(ingredientsTest.containsMoreIngredients("Pasta","Corn","Spinach"));
        assertTrue(ingredientsTest.containsMoreIngredients("Yogurt","Fish","Cucumber","Cherry"));

        assertFalse(ingredientsTest.containsMoreIngredients("Yogurt","Cucumber","Cherry"));
        assertFalse(ingredientsTest.containsMoreIngredients("Milk","Noodle","Durian","Shrimp"));
    }

    @Test
    void vegetarianTest() {
        Ingredients vegDiet = new Ingredients("Bread","Spinach","Tofu","Bean","Milk");
        Ingredients nonVegDiet = new Ingredients("Pasta","Pork","Tomato","Milk","Lemon");

        assertTrue(vegDiet.vegetarian());
        assertFalse(nonVegDiet.vegetarian());
    }

    @Test
    void veganTest() {
        Ingredients veganDiet = new Ingredients("Bread","Spinach","Tofu","Bean","Peanut");
        Ingredients nonVeganDiet = new Ingredients("Pasta","Tomato","Milk","Lemon","Tofu");

        assertTrue(veganDiet.vegan());
        assertFalse(nonVeganDiet.vegan());
    }

    @Test
    void ketoTest() {
        Ingredients ketoDiet = new Ingredients("Spinach","Tofu","Bean","Milk","Pork","Turkey");
        Ingredients nonKetoDiet = new Ingredients("Pork","Tomato","Milk","Bread","Lemon");

        assertTrue(ketoDiet.keto());
        assertFalse(nonKetoDiet.keto());
    }

}



