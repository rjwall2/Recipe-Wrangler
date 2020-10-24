package persistence;


import model.Recipe;
import model.RecipeCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//CITATION: JsonReadTest class and all it's methods are based on (CPSC210/JsonSerializationDemo by Paul Carter)
//          class JsonReaderTest and all it's contained methods

public class JsonReadTest {

    @Test
    void testReadFileNonExistent() {
        JsonRead reader = new JsonRead("./data/noSuchFile.json");
        try {
            RecipeCollection rc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyRecipeCollection() {
        JsonRead reader = new JsonRead("./data/JsonReadTestEmptyCase.json");
        try {
            RecipeCollection rc = reader.read();
            List<Recipe> rcList = rc.getRecipeCollection();
            assertEquals(0, rcList.size());
        } catch (IOException e) {
            fail("Unable to read from file from personal collection");
        }
    }

    @Test
    void testReaderGeneralRecipeCollection() {
        JsonRead reader = new JsonRead("./data/JsonReadTestNormalCase.json");
        try {
            RecipeCollection rc = reader.read();
            List<Recipe> rcList = rc.getRecipeCollection();
            assertEquals(2, rcList.size());
            Recipe recipeOne = rcList.get(0);
            Recipe recipeTwo = rcList.get(1);

            assertEquals("Tomato Soup", recipeOne.getRecipeName());
            assertEquals("Eggnog", recipeTwo.getRecipeName());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
