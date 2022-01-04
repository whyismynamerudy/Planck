package Dashboard;

import Misc.AlertBox;
import Misc.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddTask implements Initializable {

    @FXML
    TextField addTaskTitle; //Title of the homework
    @FXML
    DatePicker addTaskDueDate; //due date
    @FXML
    ChoiceBox<String> addTaskPriority; //priority of homework
    @FXML
    ChoiceBox<String> addTaskType; //homework type
    @FXML
    TextArea addTaskDescription; //homework description

    //These ObservableLists don't change as they are used in ChoiceBoxes while creating Homeworks or Tasks
    ObservableList priorities = FXCollections.observableArrayList("High", "Medium", "Low");
    ObservableList taskTypes = FXCollections.observableArrayList("General", "Essay", "Report", "Worksheet", "Reading", "Presentation", "Test", "Study", "Project", "Quiz");

    String accountUsername; //used while getting subjects
    String title;
    LocalDate dueDate;
    String priority;
    String taskType;
    String description;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Adds the ObservableLists to the ChoiceBoxes and gets the username of the logged in user
        addTaskPriority.setItems(priorities);
        addTaskType.setItems(taskTypes);
        DatabaseConnection dbConnect = new DatabaseConnection();
        dbConnect.createConnection();
        accountUsername = DatabaseConnection.getUsername();
        try {
            dbConnect.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTask(ActionEvent actionEvent) throws SQLException {
        //gets all the necessary values needed for creating a record in the database
        title = addTaskTitle.getText();
        dueDate = addTaskDueDate.getValue();
        priority = addTaskPriority.getValue();
        taskType = addTaskType.getValue();
        description = addTaskDescription.getText();
        DatabaseConnection dbConnect = new DatabaseConnection();
        dbConnect.createConnection();
        String tasksTableName = null;
        tasksTableName = DatabaseConnection.getTasksTable();
        //inserts the values into a new record in the database
        //if the required values are missing, the user is alerted.
        try {
            dbConnect.execute("insert into " + tasksTableName + " (type, title, dueDate, priority, homeworkType, description) values (\"Task\", \"" + title + "\", \"" + dueDate + "\", \"" + priority + "\", \"" + taskType + "\", \"" + description + "\");");
            dbConnect.closeStatement();
        } catch (SQLException e) {
            AlertBox alertBox = new AlertBox("Missing Values", null, "Please input the Title, Due Date, and Priority.");
            alertBox.createAlert();
        }
        //closes the connection and the window open
        dbConnect.closeConnection();
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
