package ui;

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

public class Gui implements ActionListener {

    String dataDestination = "./data/personalcollection.json";
    RecipeCollection personalCollection = new RecipeCollection();
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

        setButtons();


        menuWindow.setVisible(true);

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
            menuWindow();
        } else if (e.getActionCommand().equals("time")) {

        } else if (e.getActionCommand().equals("save")) {
            saveRecipeCollection();
        } else if (e.getActionCommand().equals("add")) {

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
}
