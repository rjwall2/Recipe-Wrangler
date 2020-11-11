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

public class Gui implements ActionListener {

    String dataDestination = "./data/personalcollection.json";
    RecipeCollection personalCollection = new RecipeCollection();
    JsonWrite jsonWriter = new JsonWrite(dataDestination);
    JsonRead jsonReader = new JsonRead(dataDestination);
    JFrame firstWindow;


    public Gui() {
        loadOrNewWindow();
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
        } else if (e.getActionCommand().equals("new")) {
            firstWindow.dispose();

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
}
