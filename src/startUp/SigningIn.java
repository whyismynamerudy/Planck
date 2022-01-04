package startUp;

import Misc.AlertBox;
import Misc.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.time.LocalDate;

public class SigningIn {

    //JavaFX's List View only displays items through an Observable List
    //The Observable List still acts as a normal arraylist and is used as such
    ObservableList subjectsList = FXCollections.observableArrayList();

    @FXML
    private ListView<String> subjectListViewSignUp; //Subject List
    @FXML
    TextField firstNameSignUp; //First Name
    @FXML
    TextField lastNameSignUp; //Last Name
    @FXML
    DatePicker dateOfBirthSignUp; //Date of Birth
    @FXML
    TextField usernameCreateSignUp; //username
    @FXML
    PasswordField passwordCreateSignUp; //password
    @FXML
    PasswordField passwordConfirmSignUp; //password confirm
    @FXML
    TextField subjectInputSignUp; //subject input
    @FXML
    Button addSubjectSignUp; //add subject button
    @FXML
    Button submitSignUp; //submit the entire sign up form

    String firstName;
    String lastName;
    LocalDate dateOfBirth;
    String username;
    String password;
    String reenterpassword;

    public void addSubjectButton (ActionEvent actionevent){
        //adds the subject to the subject list so it can be displayed.
        String subject = subjectInputSignUp.getText();
        if (!(subject.equals("") || subject.equals(" "))){ //presence check
            subjectsList.add(subject);
            subjectListViewSignUp.getItems().add(subject);
            subjectInputSignUp.clear();
            //adds the subject to the list, updates the listview, and clears the input field
        }
    }

    public void submitSignUpButton (ActionEvent actionevent) throws SQLException {
        username = usernameCreateSignUp.getText();
        password = passwordCreateSignUp.getText();
        reenterpassword = passwordConfirmSignUp.getText();

        if ((!(reenterpassword.equals(password))) || (username.equals("")) || (password.equals(""))){
            //verification: double entry - user is alerted
            AlertBox alertBox = new AlertBox("Please check again!", null, "The data that you input is not valid.");
            alertBox.createAlert();
        } else {
            //creates a database connection and inserts the respective values into the database
            username = usernameCreateSignUp.getText();
            firstName = firstNameSignUp.getText();
            lastName = lastNameSignUp.getText();
            dateOfBirth = dateOfBirthSignUp.getValue();
            DatabaseConnection dbConnect = new DatabaseConnection();
            dbConnect.createConnection();
            String subjectTableName = "subject" + username + firstName; //subjectTable's name
            String tasksTableName = "tasks" + username + firstName; //tasksTable's name
            String values = "insert into usernamesandpasswords values (\"" +username+"\", \"" + password + "\", \"" + firstName +"\", \""+lastName+"\", \"" + dateOfBirth + "\", \""+ subjectTableName+"\", \"" + tasksTableName + "\", null);";
            dbConnect.execute(values);
            dbConnect.closeStatement();
            String creatingNewTable = "create table " + subjectTableName + "(subject varchar(45))";
            dbConnect.execute(creatingNewTable);
            dbConnect.closeStatement();
            for (Object i : subjectsList){
                String insertingSubject = "insert into " + subjectTableName +" values (\"" + i + "\")";
                dbConnect.execute(insertingSubject);
                dbConnect.closeStatement();
            }
            creatingNewTable = "create table " + tasksTableName + "(ID int(100), type varchar(45), title varchar(45), dueDate date, subject varchar(45), priority varchar(45), homeworkType varchar(45), description longtext)";
            dbConnect.execute(creatingNewTable);
            dbConnect.closeStatement();
            dbConnect.closeConnection();
            Stage stage = (Stage)((Node)actionevent.getSource()).getScene().getWindow();
            stage.close();
        }
    }

}
