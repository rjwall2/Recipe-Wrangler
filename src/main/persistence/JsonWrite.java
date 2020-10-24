package persistence;

import model.RecipeCollection;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


//CITATION: JsonWrite class and all it's methods are based on (CPSC210/JsonSerializationDemo by Paul Carter)
//          class JsonWriter and all it's contained methods


// Represents a writer that writes JSON representation of recipe collection to file
public class JsonWrite {

    String fileHome;
    PrintWriter write;

    // EFFECTS: constructs writer to write to destination file
    public JsonWrite(String fileDestination) {
        fileHome = fileDestination;

    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void createDestination() throws FileNotFoundException {
        write = new PrintWriter(new File(fileHome));
    }


    // MODIFIES: this
    // EFFECTS: writes JSON representation of personal collection to file
    public void saveData(RecipeCollection collection) {
        JSONObject jsonobb = collection.convertToSingleJsonObject();
        transcriptToFile(jsonobb.toString(4));

    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void transcriptToFile(String data) {
        write.print(data);
    }


    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        write.close();
    }
}
