package startUp;

import Misc.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Misc.DatabaseConnection;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StartUP {

    @FXML
    TextField enterUsername = new TextField(); //username
    @FXML
    TextField enterPassword = new TextField(); //password

    public void loggingIn(ActionEvent actionevent) throws IOException, SQLException {
        //gets the username and password
        String username = enterUsername.getText();
        String password = enterPassword.getText();

        //creates a connection with the database
        DatabaseConnection dbConnect = new DatabaseConnection();
        dbConnect.createConnection();

        //validation - presence check
        if (!(username.equals(""))){
            String queryUsername = "SELECT Username FROM USERNAMESANDPASSWORDS WHERE Username ='" + username + "'"; //sql query 1
            String queryPassword = "SELECT Password FROM USERNAMESANDPASSWORDS WHERE Username ='" + username + "'"; //sql query 2
            //executes a query for the username and its respective password
            ResultSet dbUsername = dbConnect.executeQuery(queryUsername);
            String dbUsernameString = null; //the query is returned in a resultset, so i loop through the resultset to get the respective values
            while (dbUsername.next()){
                dbUsernameString = dbUsername.getString("Username");
            }
            dbConnect.closeStatement();
            ResultSet dbPassword = dbConnect.executeQuery(queryPassword);
            String dbPasswordString = null;
            while (dbPassword.next()){
                dbPasswordString = dbPassword.getString("Password");
            }
            dbConnect.closeStatement();
            //if the username and passwords are present, then it checks if the inputted username and password is correct
            if ((dbUsernameString != null) && (dbPasswordString!=null)) {
                if (dbUsernameString.equals(username) && dbPasswordString.equals(password)){
                    //Changes the fxml file the stage refers to for the user to "log in"
                    DatabaseConnection.setUsername(dbUsernameString);
                    Stage stage = (Stage)((Node) actionevent.getSource()).getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource("/Dashboard/Dashboard.fxml"));
                    Scene signingInScene = new Scene(root);
                    stage.setScene(signingInScene);
                    stage.setTitle("Dashboard");
                }
            } else {
                //if the username/password are wrong or username does not exist, then user is alerted
                AlertBox alertBox = new AlertBox("Username Or Password Is Incorrect", null, "The username or password you have entered is incorrect.");
                alertBox.createAlert();
            }
        } else {
            //if the username/password fields are left blank, then user is alerted
            AlertBox alertBox = new AlertBox("Username Not Entered", null, "You have not entered a username");
            alertBox.createAlert();
        }
        dbConnect.closeConnection(); //closes the database connection
    }

    public void signingIn(ActionEvent actionEvent) throws IOException {
        //creates a new window for the user to enter details to sign up.
        Parent root = FXMLLoader.load(getClass().getResource("signingIn.fxml"));
        Scene signingInScene = new Scene(root);
        Stage signInStage = new Stage();
        signInStage.setScene(signingInScene);
        signInStage.setTitle("Sign Up");
        signInStage.show();
    }
}


/*
Stage stage = (Stage)((Node) actionevent.getSource()).getScene().getWindow();
Parent root = FXMLLoader.load(getClass().getResource("/Dashboard/Dashboard.fxml"));
Scene signingInScene = new Scene(root);
stage.setScene(signingInScene);
stage.setTitle("Dashboard");

Changing scene in a stage ^^
 */
