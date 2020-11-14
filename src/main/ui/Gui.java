package ui;



import model.Recipe;
import model.RecipeCollection;
import persistence.JsonRead;
import persistence.JsonWrite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Gui implements ActionListener {

    String dataDestination = "./data/personalcollection.json";
    RecipeCollection personalCollection = new RecipeCollection();
    List<Recipe> currentCollectionRecipeList;
    List<String> currentCollectionRecipeNames;
    String[] recipeNamesArray;
    JsonWrite jsonWriter = new JsonWrite(dataDestination);
    JsonRead jsonReader = new JsonRead(dataDestination);
    JFrame firstWindow;
    JFrame menuWindow;
    Panel operationPanel;
    Panel recipeListPanel;

    JButton timeB;
    JButton saveB;
    JButton addB;
    JButton endB;
    JButton ingredientB;
    JButton ketoB;
    JButton vegeB;
    JButton veganB;


    public Gui() {
        loadOrNewWindow();
    }

    private void menuWindow() {
        menuWindow = new JFrame("Recipe Collection Actions");
        menuWindow.setLayout(new BorderLayout());
        menuWindow.setSize(700,300);
        menuWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        operationPanel = new Panel();
        recipeListPanel = new Panel();

        menuWindow.add(operationPanel, BorderLayout.SOUTH);
        menuWindow.add(recipeListPanel, BorderLayout.NORTH);

        updatePersonalCollectionDisplay();
        setButtons();


        menuWindow.setVisible(true);

    }

    private void updatePersonalCollectionDisplay() {

        currentCollectionRecipeList = personalCollection.filterRecipesByIngredients("");
        currentCollectionRecipeNames = getFilteredNames(currentCollectionRecipeList);
        recipeNamesArray = new String[currentCollectionRecipeNames.size()];
        recipeNamesArray = currentCollectionRecipeNames.toArray(recipeNamesArray);
        recipeListPanel.add(new JList(recipeNamesArray));
    }


    private void loadOrNewWindow() {
        firstWindow = new JFrame("Load or New?");
        firstWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        firstWindow.setSize(600,450);

        Panel firstPanel = new Panel();
        firstWindow.add(firstPanel);
        firstPanel.setLayout(null);

        JLabel firstWindowQuestion = new JLabel("Load previous saved session, or start fresh?");
        firstWindowQuestion.setBounds(150,30,400,150);
        firstPanel.add(firstWindowQuestion);

        JButton loadButton = new JButton("Load");
        firstPanel.add(loadButton);
        loadButton.setBounds(110,150,150,30);

        JButton newButton = new JButton("New");
        firstPanel.add(newButton);
        newButton.setBounds(300,150,150,30);

        loadButton.setActionCommand("load");
        loadButton.addActionListener(this);

        newButton.setActionCommand("new");
        newButton.addActionListener(this);

        firstWindow.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("load")) {
            loadRecipeCollection();
            firstWindow.dispose();
            menuWindow();
        } else if (e.getActionCommand().equals("new")) {
            firstWindow.dispose();
            saveRecipeCollection();
            menuWindow();
        } else if (e.getActionCommand().equals("time")) {

        } else if (e.getActionCommand().equals("save")) {
            saveRecipeCollection();
            recipeListPanel.removeAll();
            updatePersonalCollectionDisplay();
        } else if (e.getActionCommand().equals("add")) {
            addRecipeToPersonalCollection();
        } else if (e.getActionCommand().equals("end")) {

        } else if (e.getActionCommand().equals("ingredient")) {

        } else if (e.getActionCommand().equals("keto")) {

        } else if (e.getActionCommand().equals("vege")) {

        } else if (e.getActionCommand().equals("vegan")) {

        }

    }

    //CITATION: saveRecipeCollection method is based on (CPSC210/JsonSerializationDemo by Paul Carter)
    //          the saveWorkRoom method in the WorkRoomApp class

    //EFFECTS: saves personal collection to file
    private void saveRecipeCollection() {
        try {
            jsonWriter.createDestination();
            jsonWriter.saveData(personalCollection);
            jsonWriter.close();
            System.out.println("Saved");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save");
        }
    }


    //CITATION: saveRecipeCollection method is based on (CPSC210/JsonSerializationDemo by Paul Carter)
    //          the saveWorkRoom method in the WorkRoomApp class

    //EFFECTS: loads personal collection from file
    private void loadRecipeCollection() {
        try {
            personalCollection = jsonReader.read();
            System.out.println("Data loaded");
        } catch (IOException e) {
            System.out.println("Unable to read from file from personal collection");
        }
    }

    private java.util.List<String> getFilteredNames(java.util.List<Recipe> filteredRecipeList) {
        List<String> nameList = new ArrayList<>();
        for (Recipe r : filteredRecipeList) {
            nameList.add(r.getRecipeName());
        }
        return nameList;
    }

    private void setButtons() {

        ArrayList<JButton> buttonList = new ArrayList<JButton>();

        buttonList.add(timeB = new JButton("time"));
        buttonList.add(saveB = new JButton("save"));
        buttonList.add(addB = new JButton("add"));
        buttonList.add(endB = new JButton("end"));
        buttonList.add(ingredientB = new JButton("ingredient"));
        buttonList.add(ketoB = new JButton("keto"));
        buttonList.add(vegeB = new JButton("vege"));
        buttonList.add(veganB = new JButton("vegan"));


        for (JButton b : buttonList) {
            b.addActionListener(this);
            operationPanel.add(b);
        }

        timeB.setActionCommand("time");

        saveB.setActionCommand("save");

        addB.setActionCommand("add");

        endB.setActionCommand("end");

        ingredientB.setActionCommand("ingredient");

        ketoB.setActionCommand("keto");

        vegeB.setActionCommand("vege");

        veganB.setActionCommand("vegan");

    }

    //MODIFIES:this
    //EFFECTS: takes user input and uses it to add a new recipe

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

        System.out.println("Please input time needed to make recipe (in minutes)");
        time = input.nextInt();

        System.out.println("Please input the instructions of the recipe");
        instruct = input.next();
        instruct += input.nextLine();

        System.out.println("Please input an ingredient required");
        singleIngredient = input.next();
        singleIngredient += input.nextLine();

        ingredientsList.add(singleIngredient);
        moreIngredients = true;

        List<String> updatedIngredientsList = addIngredientsLoop(moreIngredients, ingredientsList);

        String[] ingredientsArray = updatedIngredientsList.toArray(new String[0]);
        personalCollection.addRecipeToCollection(name, time, instruct, ingredientsArray);
    }


    //EFFECTS: processes the multiple inputs possible for ingredients to be added to new recipe

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



}
