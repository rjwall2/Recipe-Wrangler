package ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import model.RecipeCollection;

public class WranglerApp {

    RecipeCollection personalCollection = new RecipeCollection();

    public WranglerApp() {
        runWrangler();
    }

    private void runWrangler() {

        Scanner input = new Scanner(System.in);

        System.out.println("Hi, welcome to RecipeWrangler, let's add a recipe to your "
                + "personal" + "database!");

        addRecipeToPersonalCollection();

        System.out.println("Do you want to: add another recipe(type 'add'),filter by time (type 'time'),"
                + "filter by diet(type'vegt','vega',or'keto'), filter by ingredients(type 'ingredients')");

        String action = input.next();

        if (action.equals("add")) {
            addRecipeToPersonalCollection();
        }
        if(action.equals("time")){
            System.out.println("What's the longest you want to cook for?");
            Integer freeTime = input.nextInt();
            personalCollection.filterRecipesByTime(freeTime);
        }
        if(action.equals("ingredients")) {

        }
        }


    }

    private void addRecipeToPersonalCollection() {
        String name;
        Integer time;
        String instruct;
        String singleIngredient;
        List<String> ingredientsList = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        Boolean moreIngredients;

        System.out.println("Please input name of recipe");
        name = input.next();
        name += input.nextLine();

        System.out.println("Please input time needed to make recipe");
        time = input.nextInt();

        System.out.println("Please input the instructions of the recipe");
        instruct = input.next();
        instruct += input.nextLine();

        System.out.println("Please input an ingredient required");
        singleIngredient = input.next();
        singleIngredient += input.nextLine();

        ingredientsList.add(singleIngredient);
        moreIngredients = true;

        List<String> updatedIngredientsList = addIngredientsLoop(moreIngredients,ingredientsList);

        String[] ingredientsArray = updatedIngredientsList.toArray(new String[0]);
        personalCollection.addRecipeToCollection(name, time, instruct, ingredientsArray);
    }

    public List<String> addIngredientsLoop(Boolean moreIng, List<String> ingredient) {
        while (moreIng) {
            System.out.println("Please input an ingredient required, or type 'false'"
                    + "if no more are needed");

            Scanner input = new Scanner(System.in);

            String response = input.next();
            response += input.nextLine();

            if (response.equals("false")) {
                moreIng = false;
            } else {
                ingredient.add(response);
            }
        }
        return ingredient;
    }


}
