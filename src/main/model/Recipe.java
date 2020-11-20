package model;

import java.util.List;
import org.json.JSONObject;

//represents a recipe consisting of a name, the time needed to complete it, a list of
//ingredients required, and the instructions on how to make it
public class Recipe {

    private String name;
    private Integer time;
    private Ingredients ingredientsNeeded;
    private String instructions;


    //REQUIRES: first letter of each string in ing must be capitalized,singular,
    //          and must be a food. No parameters may be left empty.
    //EFFECTS: new recipe is made where recipe name is set to namee, recipe time is set to
    //         timee, recipe instructions are set to instruct,
    //         and ing (arbitrary number of strings) is set as the ingredient list

    public Recipe(String namee, Integer timee,String instruct, String... ing) {
        name = namee;
        time = timee;
        ingredientsNeeded = new Ingredients(ing);
        instructions = instruct;
    }


    public String getRecipeName() {
        return name;
    }


    public Integer getRecipeTime() {
        return time;
    }


    //EFFECTS: returns the List<String> of ingredients within in a recipe
    public Ingredients getRecipeIngredients() {
        return ingredientsNeeded;
    }



    //CITATION: convertToJson method is based on (CPSC210/JsonSerializationDemo by Paul Carter)
    //          the toJson method in the WorkRoom class

    //EFFECTS: converts and produces a copy of a recipe as a jsonObject
    public JSONObject convertToJson() {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("name",name);
        jsonObj.put("time", time);
        jsonObj.put("instructions",instructions);
        List<String> ingredientsNeededList = ingredientsNeeded.getIngredients();
        String[] ingredientsNeededArray = ingredientsNeededList.toArray(new String[0]);
        jsonObj.put("ingredients",ingredientsNeededArray);

        return jsonObj;
    }

    //EFFECTS: returns the instructions of a recipe
    public String getRecipeInstructions() {
        return this.instructions;
    }
}

