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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddHomework implements Initializable {

    @FXML
    TextField addHomeworkTitle; //Title of the homework
    @FXML
    DatePicker addHomeworkDueDate; //due date
    @FXML
    ChoiceBox<String> addHomeworkSubject; //what subject it is
    @FXML
    ChoiceBox<String> addHomeworkPriority; //priority of homework
    @FXML
    ChoiceBox<String> addHomeworkType; //homework type
    @FXML
    TextArea addHomeworkDescription; //homework description

    //The following ObservableLists are used as choices in ChoiceBoxes and thus do not change.
    //Subject List is updated based on the subjects the user has entered previously
    private ObservableList priorities = FXCollections.observableArrayList("High", "Medium", "Low");
    private ObservableList homeworkTypes = FXCollections.observableArrayList("General", "Essay", "Report", "Worksheet", "Reading", "Presentation", "Test", "Study", "Project", "Quiz");
    private ObservableList subjects = FXCollections.observableArrayList();

    String accountUsername; //used while getting subjects
    String title;
    LocalDate dueDate;
    String subject;
    String priority;
    String homeworkType;
    String description;

    //polymorphism - this function runs whenever an object of this class is made
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        //first, the observable array lists are assigned to their respective choice boxes
        addHomeworkPriority.setItems(priorities);
        addHomeworkType.setItems(homeworkTypes);
        //a database connection is formed and the username of the user is obtained
        DatabaseConnection dbConnect = new DatabaseConnection();
        dbConnect.createConnection();
        accountUsername = DatabaseConnection.getUsername();
        //the subjects of that user are searched in the database
        //and retrieved and assigned to a choice box
        try {
            String subjectTable = DatabaseConnection.getSubjectTable();
            ResultSet subjectsRS = dbConnect.executeQuery("select subject from " + subjectTable + ";");
            String subjectName;
            while (subjectsRS.next()){
                subjectName = subjectsRS.getString("subject");
                subjects.add(subjectName);
            }
            dbConnect.closeStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        addHomeworkSubject.setItems(subjects);
        try {
            dbConnect.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addHomework (ActionEvent actionEvent) throws SQLException {
        //gets the respective values in the text boxes
        title = addHomeworkTitle.getText();
        dueDate = addHomeworkDueDate.getValue();
        subject = addHomeworkSubject.getValue();
        priority = addHomeworkPriority.getValue();
        homeworkType = addHomeworkType.getValue();
        description = addHomeworkDescription.getText();
        //creates a database connection and gets the users respective tasks table name
        DatabaseConnection dbConnect = new DatabaseConnection();
        dbConnect.createConnection();
        String tasksTableName;
        tasksTableName = DatabaseConnection.getTasksTable();

        //if certain values in the text boxes are null (empty)
        //then an alert is created and the user is alerted
        //else the new homework is added to the database

        try{
            dbConnect.execute("insert into " + tasksTableName + " (type, title, dueDate, subject, priority, homeworkType, description) values (\"Homework\", \"" + title + "\", \"" + dueDate + "\", \"" + subject + "\", \"" + priority + "\", \"" + homeworkType + "\", \"" + description + "\");");
            dbConnect.closeStatement();
        } catch (SQLException e){
            AlertBox alertBox = new AlertBox("Missing Values", null, "Please input the Title, Due Date, and Priority.");
            alertBox.createAlert();
        }

        //SWITCH TO TRY/CATCH AND IF THERE IS AN ERROR INSTEAD OF IF STATEMENT
        //if (((dueDate.equals(null)) || (priority.equals(null))) || (title.equals(null))){
        //    AlertBox alertBox = new AlertBox("Missing Values", null, "Please input the Title, Due Date, and Priority.");
        //    alertBox.createAlert();
        //} else {
         //   dbConnect.execute("insert into " + tasksTableName + " (type, title, dueDate, subject, priority, homeworkType, description) values (\"Homework\", \"" + title + "\", \"" + dueDate + "\", \"" + subject + "\", \"" + priority + "\", \"" + homeworkType + "\", \"" + description + "\");");
         //   dbConnect.closeStatement();
        //}
        //the connection to the database is shut down and the window is closed
        dbConnect.closeConnection();
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

}
