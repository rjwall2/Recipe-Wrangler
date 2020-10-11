package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeTest {

    private Recipe testRecipe;

    @BeforeEach
    void beforeEach(){
        testRecipe = new Recipe("BigMess",75,"Throw everything into a pot" +
                "and hope for the best","Pasta","Tomato","Onion","Turkey");
    }

    @Test
    public void recipeConstructorTest() {
        assertEquals("BigMess", testRecipe.getRecipeName());
        assertEquals(75, testRecipe.getRecipeTime());
    }

    @Test
    public void getRecipeIngredientsTest() {
        List<String> recipeIngredientList = testRecipe.getRecipeIngredients();

        assertEquals(4, recipeIngredientList.size());

        assertTrue(recipeIngredientList.contains("Onion"));
        assertTrue(recipeIngredientList.contains("Pasta"));
        assertTrue(recipeIngredientList.contains("Turkey"));
        assertTrue(recipeIngredientList.contains("Tomato"));

        assertFalse(recipeIngredientList.contains("Bread"));
    }

}