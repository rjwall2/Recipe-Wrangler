package ui;



import model.Recipe;
import model.RecipeCollection;
import persistence.JsonRead;
import persistence.JsonWrite;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static java.lang.Integer.parseInt;


//Recipe Wrangler application and GUI
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

    JFrame instructionPopUp;
    Panel instructionTopPanel;
    Panel instructionBottomPanel;
    JTextField recipeDesired;

    JButton timeB;
    JButton saveB;
    JButton addB;
    JButton viewRecipesB;
    JButton ingredientB;
    JButton ketoB;
    JButton vegeB;
    JButton veganB;

    //EFFECTS: Launches new gui
    public Gui() {
        loadOrNewWindow();
    }


    //EFFECTS: creates window with buttons for possible operations to be done
    //         to personal collection, also displays all recipes currently saved in personal collection
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

    //MODIFIES: recipeListPanel
    //EFFECTS: converts current personal collection to a Jlist that is displayed on the menu window
    private void updatePersonalCollectionDisplay() {

        currentCollectionRecipeList = personalCollection.filterRecipesByIngredients("");
        currentCollectionRecipeNames = getFilteredNames(currentCollectionRecipeList);
        recipeNamesArray = new String[currentCollectionRecipeNames.size()];
        recipeNamesArray = currentCollectionRecipeNames.toArray(recipeNamesArray);
        recipeListPanel.add(new JList(recipeNamesArray));
    }

    //EFFECTS: creates initial window that prompts the user to load old save file or start a new save file
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
        buttonActivities(e);
        if (e.getActionCommand().equals("GetInformation")) {
            instructionBottomPanel.removeAll();
            instructionBottomPanel.add(popUpLearnButtonActivity());
        } else if (e.getActionCommand().equals("popuptime")) {
            timeBottomPanel.removeAll();
            timeBottomPanel.add(popUpTimeButtonProcess());
        }
    }


    private void buttonActivities(ActionEvent e) {
        if (e.getActionCommand().equals("load")) {
            loadOldListBegin();
        } else if (e.getActionCommand().equals("new")) {
            newRecipeListBegin();
        } else if (e.getActionCommand().equals("time")) {
            timeActivity();
        } else if (e.getActionCommand().equals("save")) {
            savingActivity();
        } else if (e.getActionCommand().equals("add")) {
            addRecipeToPersonalCollection();
        } else if (e.getActionCommand().equals("viewrecipes")) {
            viewRecipesActivities();
        } else if (e.getActionCommand().equals("ingredient")) {
            ingredientActivity();
        } else if (e.getActionCommand().equals("keto")) {
            ketoWindow();
        } else if (e.getActionCommand().equals("veget")) {
            vegetWindow();
        } else if (e.getActionCommand().equals("vegan")) {
            veganWindow();
        }
    }

    //EFFECTS: executes activities for when time button is pressed
    private void timeActivity() {
        playSound("sound/hd-stardust-crusaders-za-warudo_1.wav");
        runTimeWindow();
    }

    //EFFECTS: executes activities for when ingredient button is pressed
    private void ingredientActivity() {
        String[] unwantedIngredients = ingredientInPut();
        System.out.println(getFilteredNames(personalCollection.filterRecipesByIngredients(unwantedIngredients)));
    }

    //EFFECTS: executes activities for when loading an old save file is selected
    private void loadOldListBegin() {
        loadRecipeCollection();
        firstWindow.dispose();
        menuWindow();
    }

    //EFFECTS: executes activities for when starting a new save file has been selected
    private void newRecipeListBegin() {
        firstWindow.dispose();
        saveRecipeCollection();
        menuWindow();
    }

    //EFFECTS: executes activities for when saving current personal collection has been chosen
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

    //MODIFIES:  operationPanel
    //EFFECTS: creates and sets all buttons in the menu window
    private void setButtons() {

        ArrayList<JButton> buttonList = new ArrayList<JButton>();

        buttonList.add(timeB = new JButton("time"));
        buttonList.add(saveB = new JButton("save"));
        buttonList.add(addB = new JButton("add"));
        buttonList.add(viewRecipesB = new JButton("Instructions"));
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

        viewRecipesB.setActionCommand("viewrecipes");

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

    //EFFECTS: creates new window for which the user can use to filter recipes by time
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

    //EFFECTS: converts string from userTime field into an integer
    public int jtextToInteger() {
        return parseInt(userTime.getText());
    }

    //EFFECTS: executes the activities of the cook button that's in the window created by runTimeWindow
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

    //EFFECTS: creates window that displays the vegetarian recipes in personal collection
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

    //EFFECTS: creates window that displays the vegan recipes in personal collection
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

    //EFFECTS: creates window that displays the ketogenic recipes in personal collection
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

    //EFFECTS: converts a list of string into a JList
    public JList listToJlistConverter(List<String> list) {

        String[] newArray = new String[list.size()];
        newArray = list.toArray(newArray);
        JList newJList = (new JList(newArray));
        return newJList;
    }


    //CITATION: playSound method is based on the playMusic method shown in the youtube video
    //          https://www.youtube.com/watch?v=TErboGLHZGA by Max O'Didily
    //REQUIRES: sound file contained in file location passed must be a .wav file
    //EFFECTS: plays the sound clip contained in a specified file
    public void playSound(String fileHome) {

        try {
            File musicLocation = new File(fileHome);
            AudioInputStream foundMusic = AudioSystem.getAudioInputStream(musicLocation);
            Clip clip = AudioSystem.getClip();
            clip.open(foundMusic);
            clip.start();

        } catch (Exception e) {
            System.out.println("Missing audio file");
        }
    }

    //EFFECTS: creates new window for which the user can extract the instructions for recipes
    public void viewRecipesActivities() {

        instructionPopUp = new JFrame("Recipe Instructions");
        instructionPopUp.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        instructionPopUp.setSize(700,400);

        instructionTopPanel = new Panel();
        instructionBottomPanel = new Panel();

        instructionPopUp.add(instructionTopPanel,BorderLayout.NORTH);
        instructionPopUp.add(instructionBottomPanel,BorderLayout.SOUTH);

        JLabel instructionPopUpQuestion = new JLabel("What recipe do you want?");
        instructionPopUpQuestion.setBounds(150,30,400,150);
        instructionTopPanel.add(instructionPopUpQuestion);

        recipeDesired = new JTextField(22);
        recipeDesired.setBounds(150,55,100,100);
        instructionTopPanel.add(recipeDesired);

        JButton learnbutton = new JButton("Learn");
        instructionTopPanel.add(learnbutton);
        learnbutton.setBounds(300,150,150,30);

        learnbutton.setActionCommand("GetInformation");
        learnbutton.addActionListener(this);


        instructionPopUp.setVisible(true);

    }

    //EFFECTS: retrieves value from map according to the key passed as recipeDesired
    public JLabel popUpLearnButtonActivity() {
        String recipeName = recipeDesired.getText();
        Map<String,String> instructionsCollection = personalCollection.getMap();
        String specificInstructions = instructionsCollection.get(recipeName);
        return new JLabel(specificInstructions);
    }



}
