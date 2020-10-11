package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecipeCollectionTest {

    private RecipeCollection testRecipeCollection;

    @BeforeEach
    public void beforeEach() {
        testRecipeCollection = new RecipeCollection();
        testRecipeCollection.addRecipeToCollection("Spaghetti",80,"Boil in pot...",
                "Pasta","Tomato","Beef","Garlic");
        testRecipeCollection.addRecipeToCollection("Toasted Bread",5,"Put toast in toaster",
                "Bread","Butter");
        testRecipeCollection.addRecipeToCollection("Salad",15,"Mix all ingredients in bowl",
                "Spinach","Lettuce","Almond","Tomato");
        testRecipeCollection.addRecipeToCollection("Meat Lover",30,"Grill over fire",
                "Beef","Pork","Broccoli");
    }

    @Test
    public void recipeCollectionConstructorTest() {
        RecipeCollection emptyListObject = new RecipeCollection();
        List<Recipe> emptyList = emptyListObject.getRecipeCollection();

        assertEquals(0, emptyList.size());
    }

    @Test
    public void addRecipeToCollectionTest() {
        List<Recipe> testRecipeCollectionList = testRecipeCollection.getRecipeCollection();
        assertEquals(4,testRecipeCollectionList.size());

        Recipe firstRecipe = testRecipeCollectionList.get(0);
        assertEquals("Spaghetti", firstRecipe.getRecipeName());

        Recipe thirdRecipe = testRecipeCollectionList.get(2);
        assertEquals(15,thirdRecipe.getRecipeTime());
    }


    @Test
    public void filterRecipeByIngredientsNoneTest() {
        List<Recipe> pickyEater = testRecipeCollection.filterRecipesByIngredients("Beef","Bread","Almond");

        assertEquals(0,pickyEater.size());
    }


    @Test
    public void filterRecipesByIngredientsTest() {
        List<Recipe> beefFree = testRecipeCollection.filterRecipesByIngredients("Beef");

        assertEquals(2, beefFree.size());

        Recipe itemOne = beefFree.get(0);
        assertEquals("Toasted Bread",itemOne.getRecipeName());
        Recipe itemTwo = beefFree.get(1);
        assertEquals("Salad", itemTwo.getRecipeName());
    }

    @Test
    public void filterRecipesByVegetarianNone() {

    }

    @Test
    public void filterRecipesByVegetarianTest() {
        List<Recipe> vegetarianList = testRecipeCollection.filterRecipesVegetarian();
        assertEquals(2, vegetarianList.size());

        Recipe firstItem = vegetarianList.get(0);
        Recipe secondItem = vegetarianList.get(1);

        assertEquals("Toasted Bread", firstItem.getRecipeName());
        assertEquals("Salad", secondItem.getRecipeName());
    }

    @Test
    public void filterRecipesByVeganTest() {
        List<Recipe> veganList = testRecipeCollection.filterRecipesVegan();
        assertEquals(1,veganList.size());

        Recipe onlyItem = veganList.get(0);
        assertEquals("Salad",onlyItem.getRecipeName());
    }
}
