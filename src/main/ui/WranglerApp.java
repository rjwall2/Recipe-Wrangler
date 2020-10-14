package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Recipe;
import model.RecipeCollection;

public class WranglerApp {

    RecipeCollection personalCollection = new RecipeCollection();

    public WranglerApp() {
        runWrangler();
    }

    private void runWrangler() {

        Scanner input = new Scanner(System.in);
        Boolean keepGoing = true;

        System.out.println("Hi, welcome to RecipeWrangler, let's add a recipe to your "
                + "personal" + "database!");

        addRecipeToPersonalCollection();

        while (keepGoing) {

            System.out.println("Do you want to: add another recipe(type 'add'),filter by time (type 'time'),"
                    + "filter by diet(type'vegt','vega',or'keto'), filter by ingredients(type 'ingredients'),"
                    + "end session (type 'end')");

            String action = input.next();

            if (action.equals("end")) {
                keepGoing = false;
                System.out.println("Thanks for using RecipeWrangler!");
            }
            runOptions(action);
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

    private List<String> addIngredientsLoop(Boolean moreIng, List<String> ingredient) {
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

    private String[] ingredientInPut() {
        List<String> tempList = new ArrayList<>();
        String[] tempArray;
        Boolean cont = true;
        Scanner input = new Scanner(System.in);

        while (cont) {
            System.out.println("Please type an ingredient you don't want"
                    + "in your recipe, or type 'false' if no more");

            String ingredient = input.next();
            ingredient += input.nextLine();

            if (ingredient.equals("false")) {
                cont = false;

            } else {
                tempList.add(ingredient);
            }
        }

        tempArray = tempList.toArray(new String[0]);
        return tempArray;
    }

    private void runOptions(String actionn) {

        Scanner input = new Scanner(System.in);

        if (actionn.equals("add")) {
            addRecipeToPersonalCollection();
        }
        if (actionn.equals("time")) {
            System.out.println("What's the longest you want to cook for?");
            Integer freeTime = input.nextInt();
            System.out.println(getFilteredNames(personalCollection.filterRecipesByTime(freeTime)));
        }
        if (actionn.equals("ingredients")) {
            String[] unwantedIngredients = ingredientInPut();
            System.out.println(getFilteredNames(personalCollection.filterRecipesByIngredients(unwantedIngredients)));
        }
        if (actionn.equals("vegt")) {
            System.out.println(getFilteredNames(personalCollection.filterRecipesVegetarian()));
        }
        if (actionn.equals("vega")) {
            System.out.println(getFilteredNames(personalCollection.filterRecipesVegan()));
        }
        if (actionn.equals("keto")) {
            System.out.println(getFilteredNames(personalCollection.filterRecipesKeto()));
        }
    }

    //EFFECTS: takes a list of recipes and returns a list of the names of the recipes contained
    private List<String> getFilteredNames(List<Recipe> filteredRecipeList) {
        List<String> nameList = new ArrayList<>();
        for (Recipe r: filteredRecipeList) {
            nameList.add(r.getRecipeName());
        }
        return nameList;
    }
}





