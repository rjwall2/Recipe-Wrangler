package persistence;

import model.Recipe;
import model.RecipeCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


//CITATION: JsonWriterTest class and all it's methods are based on (CPSC210/JsonSerializationDemo by Paul Carter)
//          class JsonWriterTest and all it's contained methods.
public class JsonWriteTest {

    @Test
    void testWriteInvalidFile() {
        try {
            RecipeCollection rc = new RecipeCollection();
            JsonWrite writer = new JsonWrite("./data/my\0illegal:fileName.json");
            writer.createDestination();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriteEmptyRecipeCollection() {
        try {
            RecipeCollection rc = new RecipeCollection();
            JsonWrite writer = new JsonWrite("./data/JsonWriteTestEmptyCase.json");
            writer.createDestination();
            writer.saveData(rc);
            writer.close();

            JsonRead reader = new JsonRead("./data/JsonWriteTestEmptyCase.json");
            rc = reader.read();

            List<Recipe> rcList = rc.getRecipeCollection();
            assertEquals(0, rcList.size());


        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriteGeneralRecipeCollection() {
        try {
            RecipeCollection rc = new RecipeCollection();
            rc.addRecipeToCollection("Pumpkin Pie",20,"Bake","Pumpkin","Spice","Egg");
            rc.addRecipeToCollection("Icecream",5,"mix","Ice","Cream","Sugar");
            JsonWrite writer = new JsonWrite("./data/JsonWriteNormalCase.json");
            writer.createDestination();
            writer.saveData(rc);
            writer.close();

            JsonRead reader = new JsonRead("./data/JsonWriteNormalCase.json");
            rc = reader.read();

            List<Recipe> rcList = rc.getRecipeCollection();

            assertEquals(2, rcList.size());

            Recipe recipeOne = rcList.get(0);
            Recipe recipeTwo = rcList.get(1);


            assertEquals("Pumpkin Pie", recipeOne.getRecipeName());
            assertEquals("Icecream",recipeTwo.getRecipeName());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
