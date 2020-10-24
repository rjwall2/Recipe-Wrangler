package persistence;

import model.Recipe;
import model.RecipeCollection;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

//CITATION: JsonRead class and all it's methods are based on (CPSC210/JsonSerializationDemo by Paul Carter)
//          class JsonReader and all it's contained methods

public class JsonRead {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonRead(String source) {
        this.source = source;
    }


    // EFFECTS: reads personalcollection from file and returns it;
    // throws IOException if an error occurs reading data from file
    public RecipeCollection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseRecipeCollection(jsonObject);
    }



    // EFFECTS: reads source file as string and returns it
    public String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }


    // EFFECTS: parses recipe collection from JSON object and returns it
    private RecipeCollection parseRecipeCollection(JSONObject jsonObject) {
        RecipeCollection loadedPersonalCollection = new RecipeCollection();
        addMultipleRecipes(loadedPersonalCollection, jsonObject);
        return loadedPersonalCollection;
    }


    // MODIFIES: wr
    // EFFECTS: parses recipe array from JSON object and adds it to recipe collection
    private void addMultipleRecipes(RecipeCollection recipeCollection, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Recipes");
        for (Object json : jsonArray) {
            JSONObject nextRecipe = (JSONObject) json;
            addRecipe(recipeCollection, nextRecipe);
        }
    }


    //CITATION: code for converting ingredients from json into a String[] came from
    //          https://stackoverflow.com/questions/9373398/how-do-i-pull-the-string-array-from-this-json-object
    //          answered by jeet and edited by Andrii Abramov

    // MODIFIES: wr
    // EFFECTS: parses recipes from JSON object and adds them to recipe collection
    private void addRecipe(RecipeCollection recipeCollection, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Integer time = jsonObject.getInt("time");
        String instructions = jsonObject.getString("instructions");
        JSONArray jsonIngredientsArray = jsonObject.getJSONArray("ingredients");
        String[] ingredients = new String[jsonIngredientsArray.length()];
        for (int n = 0; n < jsonIngredientsArray.length(); n++) {
            ingredients[n] = jsonIngredientsArray.getString(n);
        }
        recipeCollection.addRecipeToCollection(name, time, instructions, ingredients);
    }
}
