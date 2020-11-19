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

import static java.lang.Integer.parseInt;

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

    JFrame timePopUp;
    JTextField userTime;
    Panel timeTopPanel;
    Panel timeBottomPanel;

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
            loadOldListBegin();
        } else if (e.getActionCommand().equals("new")) {
            newRecipeListBegin();
        } else if (e.getActionCommand().equals("time")) {
            runTimeWindow();
        } else if (e.getActionCommand().equals("save")) {
            savingActivity();
        } else if (e.getActionCommand().equals("add")) {
            addRecipeToPersonalCollection();
        } else if (e.getActionCommand().equals("end")) {

        } else if (e.getActionCommand().equals("ingredient")) {
            String[] unwantedIngredients = ingredientInPut();
            System.out.println(getFilteredNames(personalCollection.filterRecipesByIngredients(unwantedIngredients)));
        } else if (e.getActionCommand().equals("keto")) {
            ketoWindow();
        } else if (e.getActionCommand().equals("veget")) {
            vegetWindow();
        } else if (e.getActionCommand().equals("vegan")) {
            veganWindow();
        } else if (e.getActionCommand().equals("popuptime")) {
            timeBottomPanel.add(popUpTimeButtonProcess());

        }
    }

    private void loadOldListBegin() {
        loadRecipeCollection();
        firstWindow.dispose();
        menuWindow();
    }

    private void newRecipeListBegin() {
        firstWindow.dispose();
        saveRecipeCollection();
        menuWindow();
    }

    private void savingActivity() {
        saveRecipeCollection();
        recipeListPanel.removeAll();
        updatePersonalCollectionDisplay();
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

    //EFFECTS: takes a list of recipes and returns a list of the names of the recipes contained

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
        buttonList.add(vegeB = new JButton("veget"));
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

        vegeB.setActionCommand("veget");

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

    public void runTimeWindow() {
        timePopUp = new JFrame("Time filter");
        timePopUp.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        timePopUp.setSize(700,400);

        timeTopPanel = new Panel();
        timeBottomPanel = new Panel();

        timePopUp.add(timeTopPanel,BorderLayout.NORTH);
        timePopUp.add(timeBottomPanel,BorderLayout.SOUTH);

        JLabel timePopUpQuestion = new JLabel("How long do you want to cook for?");
        timePopUpQuestion.setBounds(150,30,400,150);
        timeTopPanel.add(timePopUpQuestion);

        userTime = new JTextField(22);
        userTime.setBounds(150,55,100,100);
        timeTopPanel.add(userTime);

        JButton filterTime = new JButton("Cook");
        timeTopPanel.add(filterTime);
        filterTime.setBounds(300,150,150,30);

        filterTime.setActionCommand("popuptime");
        filterTime.addActionListener(this);


        timePopUp.setVisible(true);



    }

    public int jtextToInteger() {
        Integer cookingTime = parseInt(userTime.getText());
        return cookingTime;
    }

    public JList popUpTimeButtonProcess() {

        List<String> timeNamesList;
        timeNamesList = getFilteredNames(personalCollection.filterRecipesByTime(jtextToInteger()));

        String[] timeNamesArray = new String[timeNamesList.size()];
        timeNamesArray = timeNamesList.toArray(timeNamesArray);
        JList filteredTimeJList = (new JList(timeNamesArray));
        return filteredTimeJList;
    }


    //EFFECTS: processes the multiple inputs possible for ingredients to filter recipes by

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

    public void vegetWindow() {
        JFrame vegetPopUp = new JFrame("Vegetarian Filter");
        vegetPopUp.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        vegetPopUp.setSize(700,400);



        JList vegetarianJList = listToJlistConverter(getFilteredNames(personalCollection.filterRecipesVegetarian()));

        Panel displayPanel = new Panel();
        vegetPopUp.add(displayPanel);

        displayPanel.add(vegetarianJList);

        vegetPopUp.setVisible(true);
    }

    public void veganWindow() {
        JFrame veganPopUp = new JFrame("Vegan Filter");
        veganPopUp.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        veganPopUp.setSize(700,400);



        JList veganJList = listToJlistConverter(getFilteredNames(personalCollection.filterRecipesVegan()));

        Panel displayPanel = new Panel();
        veganPopUp.add(displayPanel);

        displayPanel.add(veganJList);

        veganPopUp.setVisible(true);
    }

    public void ketoWindow() {
        JFrame ketoPopUp = new JFrame("Ketogenic Filter");
        ketoPopUp.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        ketoPopUp.setSize(700,400);



        JList ketoJList = listToJlistConverter(getFilteredNames(personalCollection.filterRecipesKeto()));

        Panel displayPanel = new Panel();
        ketoPopUp.add(displayPanel);

        displayPanel.add(ketoJList);

        ketoPopUp.setVisible(true);
    }

    public JList listToJlistConverter(List<String> list) {

        String[] newArray = new String[list.size()];
        newArray = list.toArray(newArray);
        JList newJList = (new JList(newArray));
        return newJList;
    }



}
