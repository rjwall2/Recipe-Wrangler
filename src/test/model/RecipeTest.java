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
        Ingredients recipeIngredientList = testRecipe.getRecipeIngredients();

        List<String> recipeIngredientAsAList = recipeIngredientList.getIngredients();

        assertEquals(4, recipeIngredientAsAList.size());

        assertTrue(recipeIngredientAsAList.contains("Onion"));
        assertTrue(recipeIngredientAsAList.contains("Pasta"));
        assertTrue(recipeIngredientAsAList.contains("Turkey"));
        assertTrue(recipeIngredientAsAList.contains("Tomato"));

        assertFalse(recipeIngredientAsAList.contains("Bread"));
    }

}