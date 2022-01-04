package Dashboard;

import Misc.AlertBox;
import Misc.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.Stack;

public class Dashboard implements Initializable {

    @FXML
    TabPane biggestTabPane; //the main tabPane

    //Dashboard
    String typeOfTask;
    @FXML
    Tab dashboardTab;
    @FXML
    TextField dashboardTaskTitle;
    @FXML
    DatePicker dashboardTaskDueDate;
    @FXML
    ChoiceBox<String> dashboardTaskSubject;
    @FXML
    ChoiceBox<String> dashboardTaskPriority;
    @FXML
    ChoiceBox<String> dashboardTaskType;
    @FXML
    TextArea dashboardTaskDescription;
    @FXML
    ListView<String> dashboardListHomework;
    @FXML
    ListView<String> dashboardListToDo;
    @FXML
    ImageView dashboardTimetableImage;

    //Account Tab
    @FXML
    TextField accountFirstName; //first name
    @FXML
    TextField accountLastName; //last name
    @FXML
    DatePicker accountDateOfBirth; //date of birth
    @FXML
    TextField accountSubject; //subject input
    @FXML
    private ListView<String> accountListView;//subject list view
    @FXML
    Button accountAddSubject; //ad subject button
    @FXML
    Button accountDeleteSubject; // delete subject
    @FXML
    Button accountEditButton; //edit subject
    @FXML
    TextField accountNewPassword; //change password
    @FXML
    TextField accountReEnterPassword; //change password reenter
    @FXML
    Button accountSavePassword;

    //Homework Tab
    @FXML
    ListView<String> homeworkListHomework; //list of homework
    @FXML
    AnchorPane homeworkDetailsPane; //homework detail pane for each homework
    @FXML
    ImageView homeworkCoverImage; //Image that covers the homework detail pane
    @FXML
    TextField homeworkDetailsPaneTitle;
    @FXML
    DatePicker homeworkDetailsPaneDueDate;
    @FXML
    ChoiceBox<String> homeworkDetailsPaneSubject;
    @FXML
    ChoiceBox<String> homeworkDetailsPanePriority;
    @FXML
    ChoiceBox<String> homeworkDetailsPaneHomeworkType;
    @FXML
    TextArea homeworkDetailsPaneDescription;
    @FXML
    AnchorPane homeworkAnchorPane;
    @FXML
    MenuItem prioritySortHomework;
    String unTouchedTitle; //used for updating a record where the title may be changed by the user

    //Todo Tab
    @FXML
    ListView<String> toDoListHomework; //list of homework
    @FXML
    AnchorPane toDoDetailsPane; //homework detail pane for each homework
    @FXML
    ImageView toDoCoverImage; //Image that covers the homework detail pane
    @FXML
    TextField toDoDetailsPaneTitle;
    @FXML
    DatePicker toDoDetailsPaneDueDate;
    @FXML
    ChoiceBox<String> toDoDetailsPanePriority;
    @FXML
    ChoiceBox<String> toDoDetailsPaneHomeworkType;
    @FXML
    TextArea toDoDetailsPaneDescription;
    @FXML
    AnchorPane toDoAnchorPane;
    String toDoUnTouchedTitle; //used for updating a record where the title may be changed by the user

    //Timetable Tab
    @FXML
    ImageView timetableTimetableImage;
    String timetablePath = null;

    String firstName;
    String lastName;
    LocalDate dateOfBirth;
    String subject;
    String accountUsername;
    Date date;
    String password;
    String passwordReEnter;
    String tasksTableName;
    Boolean isEditiable = false;

    //Polymorphism - this function is run when the dashboard is opened
    //When the dashboard is open, an object of this class is initialized.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        biggestTabPane.getSelectionModel().select(dashboardTab); //sets dashboard tab as primary tab at startup

        DatabaseConnection dbConnect = new DatabaseConnection(); //sets up database access
        dbConnect.createConnection();

        //Accounts Tab
        accountUsername = DatabaseConnection.getUsername();
        try {
            //searches for the users subject table in the databse
            ResultSet subjectTableRS = dbConnect.executeQuery("select subjectTable from usernamesandpasswords where username = \"" + accountUsername +"\";");
            String subjectTable = null;
            while (subjectTableRS.next()){
                subjectTable = subjectTableRS.getString("subjectTable");
            }
            dbConnect.closeStatement();
            //assigns the subject table name to a static variable so it can be called anywhere
            DatabaseConnection.setSubjectTable(subjectTable);
            //gets the users subjects and shows them in a choice box
            ResultSet subjectsRS = dbConnect.executeQuery("select subject from " + subjectTable + ";");
            String subjectName;
            while (subjectsRS.next()){
                subjectName = subjectsRS.getString("subject");
                accountListView.getItems().add(subjectName);
            }
            dbConnect.closeStatement();
            //searches for users first name and displays it in a textbox
            ResultSet firstNameRS = dbConnect.executeQuery("select firstName from usernamesandpasswords where username = \"" + accountUsername +"\";");
            while (firstNameRS.next()){
                firstName = firstNameRS.getString("firstName");
            }
            accountFirstName.setText(firstName);
            dbConnect.closeStatement();
            //searches for users last name and displays it in a textbox
            ResultSet lastNameRS = dbConnect.executeQuery("select lastName from usernamesandpasswords where username = \"" + accountUsername +"\";");
            while (lastNameRS.next()){
                lastName = lastNameRS.getString("lastName");
            }
            dbConnect.closeStatement();
            accountLastName.setText(lastName);
            //searches for users date of birth and displays it in a date picker (javafx)
            ResultSet dateOfBirthRS = dbConnect.executeQuery("select dateOfBirth from usernamesandpasswords where username = \"" + accountUsername +"\";");
            while (dateOfBirthRS.next()){
                //Converting the sql date to java's local date requires the use of .toLocalDate()
                date = dateOfBirthRS.getDate("dateOfBirth");
                dateOfBirth = date.toLocalDate();
            }
            dbConnect.closeStatement();
            accountDateOfBirth.setValue(dateOfBirth);

            //Homework Tab
            //sets up the initial display for the homework tab by enabling and disabling certain displays
            homeworkDetailsPane.setVisible(false);
            homeworkCoverImage.setVisible(true);
            //finds the users task table and sets it to a static variable so that it can be used throughout the program
            ResultSet tasksTableNameRS = dbConnect.executeQuery("select tasksTable from usernamesandpasswords where Username = \"" + accountUsername +"\";");
            while (tasksTableNameRS.next()){
                tasksTableName = tasksTableNameRS.getString("tasksTable");
            }
            dbConnect.closeStatement();
            DatabaseConnection.setTasksTable(tasksTableName);
            tasksTableName = DatabaseConnection.getTasksTable();
            String availableHomework;
            //searches all available homeworks in the database and displays them in listviews
            ResultSet availableHomeworksRS = dbConnect.executeQuery("select title from " + tasksTableName + " where type = \"Homework\";");
            while (availableHomeworksRS.next()){
                availableHomework = availableHomeworksRS.getString("title");
                homeworkListHomework.getItems().add(availableHomework);
                dashboardListHomework.getItems().add(availableHomework);
            }
            dbConnect.closeStatement();

            //to do tab
            //sets up the initial display for the to do tab by enabling and disabling certain displays
            toDoDetailsPane.setVisible(false);
            toDoCoverImage.setVisible(true);
            DatabaseConnection.setTasksTable(tasksTableName);
            String availableTask;
            //searches all available tasks in the database and displays them in listviews
            ResultSet availableTaskRS = dbConnect.executeQuery("select title from " + tasksTableName + " where type = \"Task\";");
            while (availableTaskRS.next()){
                availableTask = availableTaskRS.getString("title");
                toDoListHomework.getItems().add(availableTask);
                dashboardListToDo.getItems().add(availableTask);
            }
            dbConnect.closeStatement();

            //timetable tab
            //finds the path of the image presviously uploaded by user (if any) and sets it to image
            ResultSet timetablePathRS = dbConnect.executeQuery("select timetablePath from usernamesandpasswords where Username = \"" + accountUsername + "\";");
            while (timetablePathRS.next()){
                timetablePath = timetablePathRS.getString("timetablePath");
            }
            dbConnect.closeStatement();
            if (timetablePath != null){
                Image image = new Image(timetablePath);
                timetableTimetableImage.setImage(image);
                dashboardTimetableImage.setImage(image);
            }

            dbConnect.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changePassword (ActionEvent actionEvent) throws SQLException {
        DatabaseConnection dbConnect = new DatabaseConnection(); //sets up database access
        password = accountNewPassword.getText();
        passwordReEnter = accountReEnterPassword.getText();
        //validation check - if the passwords are different, then user is alerted.
        //otherwise the passwords as updated
        if (!((password.equals("")) && (passwordReEnter.equals(""))) && password.equals(passwordReEnter)){
            dbConnect.createConnection();
            dbConnect.execute("update usernamesandpasswords set password = \"" + password + "\" where username = \"" + accountUsername + "\";");
            dbConnect.closeStatement();
            dbConnect.closeConnection();
            AlertBox alertBox = new AlertBox("Successfull", null, "Your password has been successfully changed.");
            alertBox.createAlert();
        } else {
            AlertBox alertBox = new AlertBox("Error", null, "Passwords Don't Match");
            alertBox.createAlert();
        }
    }

    public void editAccount (ActionEvent actionEvent) throws SQLException {
        DatabaseConnection dbConnect = new DatabaseConnection(); //sets up database access
        if (isEditiable == false){
            //account should now be made editable because it is not editable right now
            accountEditButton.setText("Save Changes");
            accountFirstName.setEditable(true);
            accountLastName.setEditable(true);
            accountDateOfBirth.setEditable(true);
            accountSubject.setEditable(true);
            accountAddSubject.setDisable(false);
            accountDeleteSubject.setDisable(false);
            accountNewPassword.setEditable(false);
            accountReEnterPassword.setEditable(false);
            accountSavePassword.setDisable(true);
            isEditiable = !isEditiable;
        } else {
            //account should now be save-able because it is being editied
            accountEditButton.setText("Edit");
            firstName = accountFirstName.getText();
            lastName = accountLastName.getText();
            dateOfBirth = accountDateOfBirth.getValue();
            accountFirstName.setEditable(false);
            accountLastName.setEditable(false);
            accountDateOfBirth.setEditable(false);
            accountSubject.setEditable(false);
            accountAddSubject.setDisable(true);
            accountDeleteSubject.setDisable(true);
            accountNewPassword.setEditable(true);
            accountReEnterPassword.setEditable(true);
            accountSavePassword.setDisable(false);
            isEditiable = !isEditiable;
            //database is updated with the new information about a record
            dbConnect.createConnection();
            dbConnect.execute("update usernamesandpasswords set firstName =\"" + firstName + "\" where username = \"" + accountUsername + "\";");
            dbConnect.closeStatement();
            dbConnect.execute("update usernamesandpasswords set lastName =\"" + lastName + "\" where username = \"" + accountUsername + "\";");
            dbConnect.closeStatement();
            dbConnect.execute("update usernamesandpasswords set dateOfBirth =\"" + dateOfBirth + "\" where username = \"" + accountUsername + "\";");
            dbConnect.closeStatement();
            dbConnect.closeConnection();
        }
    }

    public void accountAddSubject(ActionEvent actionEvent) throws SQLException {
        DatabaseConnection dbConnect = new DatabaseConnection(); //sets up database access
        subject = accountSubject.getText();
        //presence check - if the text fields aren't empty then the code proceeds to add the subject to the database
        if (!(subject.equals("") || subject.equals(" "))){
            accountListView.getItems().add(subject);
            accountSubject.clear();
            dbConnect.createConnection();
            String subjectTable = DatabaseConnection.getSubjectTable();
            dbConnect.execute("insert into " + subjectTable + " values (\"" + subject +"\");");
            dbConnect.closeStatement();
            dbConnect.closeConnection();
        }
    }

    public void accountDeleteSubject(ActionEvent actionEvent) throws SQLException {
        DatabaseConnection dbConnect = new DatabaseConnection(); //sets up database access
        //gets the selected item and deletes it from the list view and the database
        String selectedSubject = accountListView.getSelectionModel().getSelectedItem();
        accountListView.getItems().remove(selectedSubject);
        dbConnect.createConnection();
        String subjectTable = DatabaseConnection.getSubjectTable();
        dbConnect.execute("delete from " + subjectTable + " where subject = \"" + selectedSubject + "\";");
        dbConnect.closeStatement();
        dbConnect.closeConnection();
    }

    public void addHomework (ActionEvent actionEvent) throws IOException, SQLException {
        DatabaseConnection dbConnect = new DatabaseConnection(); //sets up database access
        //creates a new stage for adding homework
        Parent root = FXMLLoader.load(getClass().getResource("AddHomework.fxml"));
        Scene signingInScene = new Scene(root);
        Stage addHomeworkStage = new Stage();
        addHomeworkStage.setScene(signingInScene);
        addHomeworkStage.setTitle("Add Homework");
        addHomeworkStage.showAndWait();
        //once the homework is added to the database (see AddHomework Class)
        //the list views are cleared and refilled from the now-updated database
        homeworkListHomework.getItems().clear();
        dashboardListHomework.getItems().clear();
        dbConnect.createConnection();
        String availableHomework;
        ResultSet availableHomeworksRS = dbConnect.executeQuery("select title from " + tasksTableName + " where type = \"Homework\";");
        while (availableHomeworksRS.next()){
            availableHomework = availableHomeworksRS.getString("title");
            homeworkListHomework.getItems().add(availableHomework);
            dashboardListHomework.getItems().add(availableHomework);
        }
        dbConnect.closeStatement();
        dbConnect.closeConnection();
    }

    public void addTask (ActionEvent actionEvent) throws IOException, SQLException {
        DatabaseConnection dbConnect = new DatabaseConnection(); //sets up database access
        //creates a new stage for adding tasks
        Parent root = FXMLLoader.load(getClass().getResource("AddTask.fxml"));
        Scene signingInScene = new Scene(root);
        Stage addTaskStage = new Stage();
        addTaskStage.setScene(signingInScene);
        addTaskStage.setTitle("Add Task");
        addTaskStage.showAndWait();
        //once the task is added to the database (see AddTask Class)
        //the list views are cleared and refilled from the now-updated database
        toDoListHomework.getItems().clear();
        dashboardListToDo.getItems().clear();
        dbConnect.createConnection();
        String availableTask;
        ResultSet availableTaskRS = dbConnect.executeQuery("select title from " + tasksTableName + " where type = \"Task\";");
        while (availableTaskRS.next()){
            availableTask = availableTaskRS.getString("title");
            toDoListHomework.getItems().add(availableTask);
            dashboardListToDo.getItems().add(availableTask);
        }
        dbConnect.closeStatement();
        dbConnect.closeConnection();
    }

    public void beginEditing (MouseEvent mouseEvent) throws SQLException {
        //gets the selected item and makes visible the editing panels
        String selectedHomework = homeworkListHomework.getSelectionModel().getSelectedItem();
        homeworkCoverImage.setVisible(false);
        homeworkDetailsPane.setVisible(true);
        homeworkDetailsPaneTitle.setText(selectedHomework);
        DatabaseConnection dbConnect = new DatabaseConnection();
        dbConnect.createConnection();
        //searches the database for the record of the selected item and displays them in the panel
        LocalDate homeworkDueDate = null; //due date of homework
        ResultSet dueDateRS = dbConnect.executeQuery("select dueDate from " + tasksTableName + " where title = \"" + selectedHomework + "\" and type = \"Homework\";");
        while (dueDateRS.next()){
            Date databaseDate = dueDateRS.getDate("dueDate");
            homeworkDueDate = databaseDate.toLocalDate();
        }
        homeworkDetailsPaneDueDate.setValue(homeworkDueDate);
        dbConnect.closeStatement();
        ObservableList subjects = FXCollections.observableArrayList();
        String subjectTable = DatabaseConnection.getSubjectTable();
        ResultSet subjectsRS = dbConnect.executeQuery("select subject from " + subjectTable + ";");
        String subjectName;
        while (subjectsRS.next()){ //shows subjects of the users
            subjectName = subjectsRS.getString("subject");
            subjects.add(subjectName);
        }
        dbConnect.closeStatement();
        homeworkDetailsPaneSubject.setItems(subjects);
        String homeworkSubject = null; //the subject of the selected homework
        ResultSet subjectRS = dbConnect.executeQuery("select subject from " + tasksTableName + " where title = \"" + selectedHomework + "\" and type = \"Homework\";");
        while (subjectRS.next()){
            homeworkSubject = subjectRS.getString("subject");
        }
        dbConnect.closeStatement();
        homeworkDetailsPaneSubject.setValue(homeworkSubject);
        ObservableList priorities = FXCollections.observableArrayList("High", "Medium", "Low");
        homeworkDetailsPanePriority.setItems(priorities);
        String homeworkPriority = null; //the priority of the selected homework
        ResultSet priorityRS = dbConnect.executeQuery("select priority from " + tasksTableName + " where title = \"" + selectedHomework + "\" and type = \"Homework\";");
        while (priorityRS.next()){
            homeworkPriority = priorityRS.getString("priority");
        }
        dbConnect.closeStatement();
        homeworkDetailsPanePriority.setValue(homeworkPriority);
        ObservableList homeworkTypes = FXCollections.observableArrayList("General", "Essay", "Report", "Worksheet", "Reading", "Presentation", "Test", "Study", "Project", "Quiz");
        homeworkDetailsPaneHomeworkType.setItems(homeworkTypes);
        String homeworkHomeworkType = null; //homework type of the selected homework
        ResultSet homeworkTypeRS = dbConnect.executeQuery("select homeworkType from " + tasksTableName + " where title = \"" + selectedHomework + "\" and type = \"Homework\";");
        while (homeworkTypeRS.next()){
            homeworkHomeworkType = homeworkTypeRS.getString("homeworkType");
        }
        dbConnect.closeStatement();
        homeworkDetailsPaneHomeworkType.setValue(homeworkHomeworkType);
        String homeworkDescription = null; //description of the selected homework
        ResultSet homeworkDescriptionRS = dbConnect.executeQuery("select description from " + tasksTableName + " where title = \"" + selectedHomework + "\" and type = \"Homework\";");
        while (homeworkDescriptionRS.next()){
            homeworkDescription = homeworkDescriptionRS.getString("description");
        }
        dbConnect.closeStatement();
        homeworkDetailsPaneDescription.setText(homeworkDescription);
        dbConnect.closeConnection();
    }

    public void beginEditingToDo (MouseEvent mouseEvent) throws SQLException {
        //gets the selected item and makes visible to selected items
        String selectedTask = toDoListHomework.getSelectionModel().getSelectedItem();
        toDoCoverImage.setVisible(false);
        toDoDetailsPane.setVisible(true);
        toDoDetailsPaneTitle.setText(selectedTask);
        DatabaseConnection dbConnect = new DatabaseConnection();
        dbConnect.createConnection();
        //searches the database for the record of the selected item and displays them in the panel
        LocalDate taskDueDate = null; //due date of task
        ResultSet dueDateRS = dbConnect.executeQuery("select dueDate from " + tasksTableName + " where title = \"" + selectedTask + "\" and type = \"Task\";");
        while (dueDateRS.next()){
            Date databaseDate = dueDateRS.getDate("dueDate");
            taskDueDate = databaseDate.toLocalDate();
        }
        dbConnect.closeStatement();
        toDoDetailsPaneDueDate.setValue(taskDueDate);
        ObservableList priorities = FXCollections.observableArrayList("High", "Medium", "Low");
        toDoDetailsPanePriority.setItems(priorities);
        String taskPriority = null; //priority of task
        ResultSet priorityRS = dbConnect.executeQuery("select priority from " + tasksTableName + " where title = \"" + selectedTask + "\" and type = \"Task\";");
        while (priorityRS.next()){
            taskPriority = priorityRS.getString("priority");
        }
        dbConnect.closeStatement();
        toDoDetailsPanePriority.setValue(taskPriority);
        ObservableList taskTypes = FXCollections.observableArrayList("General", "Essay", "Report", "Worksheet", "Reading", "Presentation", "Test", "Study", "Project", "Quiz");
        toDoDetailsPaneHomeworkType.setItems(taskTypes);
        String toDoTaskType = null; //tasktype of task
        ResultSet toDoTypeRS = dbConnect.executeQuery("select homeworkType from " + tasksTableName + " where title = \"" + selectedTask + "\" and type = \"Task\";");
        while (toDoTypeRS.next()){
            toDoTaskType = toDoTypeRS.getString("homeworkType");
        }
        dbConnect.closeStatement();
        toDoDetailsPaneHomeworkType.setValue(toDoTaskType);
        String toDoDescription = null; //description of task
        ResultSet toDoDescriptionRS = dbConnect.executeQuery("select description from " + tasksTableName + " where title = \"" + selectedTask + "\" and type = \"Task\";");
        while (toDoDescriptionRS.next()){
            toDoDescription = toDoDescriptionRS.getString("description");
        }
        dbConnect.closeStatement();
        toDoDetailsPaneDescription.setText(toDoDescription);
        dbConnect.closeConnection();
    }

    public void noMoreEditing (MouseEvent mouseEvent){
        //when mouse is clicked outside the panel it hides the panel
        homeworkDetailsPane.setVisible(false);
        homeworkCoverImage.setVisible(true);
    }

    public void noMoreEditingToDo (MouseEvent mouseEvent){
        //when mouse is clicked outside the panel it hides the panel
        toDoDetailsPane.setVisible(false);
        toDoCoverImage.setVisible(true);
    }

    public void saveEditedHomework (ActionEvent actionEvent) throws SQLException {
        //gets the selected item and its values in the edit panel's text boxes
        unTouchedTitle = homeworkListHomework.getSelectionModel().getSelectedItem();

        String title;
        LocalDate dueDate;
        String priority;
        String subject = null;
        String description = null;
        String homeworkType = null;

        title = homeworkDetailsPaneTitle.getText();
        dueDate = homeworkDetailsPaneDueDate.getValue();
        priority = homeworkDetailsPanePriority.getValue();
        subject = homeworkDetailsPaneSubject.getValue();
        description = homeworkDetailsPaneDescription.getText();
        homeworkType = homeworkDetailsPaneHomeworkType.getValue();

        //updates the database with the new obtained values
        DatabaseConnection dbConnect = new DatabaseConnection();
        tasksTableName = DatabaseConnection.getTasksTable();
        dbConnect.createConnection();
        dbConnect.execute("update " + tasksTableName + " set dueDate = \"" + dueDate + "\" where title = \"" + unTouchedTitle + "\" and type = \"Homework\";");
        dbConnect.closeStatement();
        dbConnect.execute("update " + tasksTableName + " set priority = \"" + priority + "\" where title = \"" + unTouchedTitle + "\" and type = \"Homework\";");
        dbConnect.closeStatement();
        dbConnect.execute("update " + tasksTableName + " set subject = \"" + subject + "\" where title = \"" + unTouchedTitle + "\" and type = \"Homework\";");
        dbConnect.closeStatement();
        dbConnect.execute("update " + tasksTableName + " set description = \"" + description + "\" where title = \"" + unTouchedTitle + "\" and type = \"Homework\";");
        dbConnect.closeStatement();
        dbConnect.execute("update " + tasksTableName + " set homeworkType = \"" + homeworkType + "\" where title = \"" + unTouchedTitle + "\" and type = \"Homework\";");
        dbConnect.closeStatement();
        dbConnect.execute("update " + tasksTableName + " set title = \"" + title + "\" where title = \"" + unTouchedTitle + "\" and type = \"Homework\";");
        dbConnect.closeStatement();
        dbConnect.closeConnection();
    }

    public void saveEditedTask (ActionEvent actionEvent) throws SQLException {
        //gets the selected item and its values from the edit panel
        toDoUnTouchedTitle = toDoListHomework.getSelectionModel().getSelectedItem();
        DatabaseConnection dbConnect = new DatabaseConnection();

        dbConnect.createConnection();

        String title = null;
        LocalDate dueDate = null;
        String priority = null;
        String description = null;
        String toDoType = null;

        title = toDoDetailsPaneTitle.getText();
        dueDate = toDoDetailsPaneDueDate.getValue();
        priority = toDoDetailsPanePriority.getValue();
        description = toDoDetailsPaneDescription.getText();
        toDoType = toDoDetailsPaneHomeworkType.getValue();

        int ID = -1;

        //finds the id of the selected item so it can be edited easily
        ResultSet taskIDRS = dbConnect.executeQuery("select ID from "+ tasksTableName + " where title = \"" + title + "\" and priority = \"" + priority + "\" and type = \"Task\";");
        while (taskIDRS.next()){
            ID = taskIDRS.getInt("ID");
        }
        dbConnect.closeStatement();

        //task is updated from its ID using the values in the edit panel
        dbConnect.execute("update " + tasksTableName + " set dueDate = \"" + dueDate + "\" where ID = \"" + ID + "\";");
        dbConnect.closeStatement();
        dbConnect.execute("update " + tasksTableName + " set priority = \"" + priority + "\" where ID = \"" + ID + "\";");
        dbConnect.closeStatement();
        dbConnect.execute("update " + tasksTableName + " set description = \"" + description + "\" where ID = \"" + ID + "\";");
        dbConnect.closeStatement();
        dbConnect.execute("update " + tasksTableName + " set homeworkType = \"" + toDoType + "\" where ID = \"" + ID + "\";");
        dbConnect.closeStatement();
        dbConnect.execute("update " + tasksTableName + " set title = \"" + title + "\" where ID = \"" + ID + "\";");
        dbConnect.closeStatement();

        dbConnect.closeConnection();
    }

    public void completeHomework (ActionEvent actionEvent) throws SQLException {
        //deletes the homework from the database using its title and removes it from the listviews
        String title = homeworkDetailsPaneTitle.getText();

        DatabaseConnection dbConnect = new DatabaseConnection();
        dbConnect.createConnection();
        dbConnect.execute("delete from " + tasksTableName + " where title = \"" + title + "\" and type = \"Homework\"");
        dbConnect.closeStatement();
        dbConnect.closeConnection();

        homeworkListHomework.getItems().remove(homeworkListHomework.getSelectionModel().getSelectedIndex());
        dashboardListHomework.getItems().remove(homeworkListHomework.getSelectionModel().getSelectedIndex() + 1);

        homeworkDetailsPane.setVisible(false);
        homeworkCoverImage.setVisible(true);
    }

    public void completeTask (ActionEvent actionEvent) throws SQLException {
        //deletes the task from the database using its title and removes it from the listviews
        String title = toDoDetailsPaneTitle.getText();

        DatabaseConnection dbConnect = new DatabaseConnection();
        dbConnect.createConnection();
        dbConnect.execute("delete from " + tasksTableName + " where title = \"" + title + "\" and type = \"Task\"");
        dbConnect.closeStatement();
        dbConnect.closeConnection();

        toDoListHomework.getItems().remove(toDoListHomework.getSelectionModel().getSelectedIndex());
        dashboardListToDo.getItems().remove(toDoListHomework.getSelectionModel().getSelectedIndex() + 1);

        toDoDetailsPane.setVisible(false);
        toDoCoverImage.setVisible(true);
    }

    public void chooseTimeTable(ActionEvent actionEvent) throws MalformedURLException, SQLException {
        //opens a stage for the user to choose an image
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Timetable");
        File file  = fileChooser.showOpenDialog(new Stage());
        if (file != null){
            //if a file is selected its path is converted to URI, then to URL, and then
            //to a string, which is then stored in the database and
            //used to set an image on the image view in the timetable tab
            String imagepath = file.toURI().toURL().toString();
            Image image = new Image(imagepath);
            timetableTimetableImage.setImage(image);
            dashboardTimetableImage.setImage(image);
            DatabaseConnection dbConnect = new DatabaseConnection();
            dbConnect.createConnection();
            dbConnect.execute("update usernamesandpasswords set timetablePath = \"" + imagepath + "\" where Username = \"" + accountUsername + "\";");
            dbConnect.closeStatement();
            dbConnect.closeConnection();
        } else {
            //if not image is selected then the user is alerted
            AlertBox alertBox = new AlertBox("Error", null, "You have not selected any image.");
            alertBox.createAlert();
        }
    }

    public void beginEditingDashboardHomework (MouseEvent mouseEvent) throws SQLException {
        //allows user to edit homework from the dashboard
        //first the record of the task is searched from the database
        typeOfTask = "Homework";
        dashboardTaskSubject.setDisable(false);
        String selectedHomework = dashboardListHomework.getSelectionModel().getSelectedItem();
        dashboardTaskTitle.setText(selectedHomework);
        DatabaseConnection dbConnect = new DatabaseConnection();
        dbConnect.createConnection();
        LocalDate homeworkDueDate = null; //due date of homework is displayed
        ResultSet dueDateRS = dbConnect.executeQuery("select dueDate from " + tasksTableName + " where title = \"" + selectedHomework + "\" and type = \"Homework\";");
        while (dueDateRS.next()){
            Date databaseDate = dueDateRS.getDate("dueDate");
            homeworkDueDate = databaseDate.toLocalDate();
        }
        dbConnect.closeStatement();
        dashboardTaskDueDate.setValue(homeworkDueDate);
        //need to give all subjects in the choicebox
        ObservableList subjects = FXCollections.observableArrayList();
        String subjectTable = DatabaseConnection.getSubjectTable();
        ResultSet subjectsRS = dbConnect.executeQuery("select subject from " + subjectTable + ";");
        String subjectName;
        while (subjectsRS.next()){
            subjectName = subjectsRS.getString("subject");
            subjects.add(subjectName);
        }
        dbConnect.closeStatement();
        dashboardTaskSubject.setItems(subjects);
        String homeworkSubject = null; //subject of homework is displayed
        ResultSet subjectRS = dbConnect.executeQuery("select subject from " + tasksTableName + " where title = \"" + selectedHomework + "\" and type = \"Homework\";");
        while (subjectRS.next()){
            homeworkSubject = subjectRS.getString("subject");
        }
        dbConnect.closeStatement();
        dashboardTaskSubject.setValue(homeworkSubject);
        ObservableList priorities = FXCollections.observableArrayList("High", "Medium", "Low");
        dashboardTaskPriority.setItems(priorities);
        String homeworkPriority = null; //priority of homework is displayed
        ResultSet priorityRS = dbConnect.executeQuery("select priority from " + tasksTableName + " where title = \"" + selectedHomework + "\" and type = \"Homework\";");
        while (priorityRS.next()){
            homeworkPriority = priorityRS.getString("priority");
        }
        dbConnect.closeStatement();
        dashboardTaskPriority.setValue(homeworkPriority);
        ObservableList homeworkTypes = FXCollections.observableArrayList("General", "Essay", "Report", "Worksheet", "Reading", "Presentation", "Test", "Study", "Project", "Quiz");
        dashboardTaskType.setItems(homeworkTypes);
        String homeworkHomeworkType = null; //homework type of homework is displayed
        ResultSet homeworkTypeRS = dbConnect.executeQuery("select homeworkType from " + tasksTableName + " where title = \"" + selectedHomework + "\" and type = \"Homework\";");
        while (homeworkTypeRS.next()){
            homeworkHomeworkType = homeworkTypeRS.getString("homeworkType");
        }
        dbConnect.closeStatement();
        dashboardTaskType.setValue(homeworkHomeworkType);
        String homeworkDescription = null; //description of homework is displayed
        ResultSet homeworkDescriptionRS = dbConnect.executeQuery("select description from " + tasksTableName + " where title = \"" + selectedHomework + "\" and type = \"Homework\";");
        while (homeworkDescriptionRS.next()){
            homeworkDescription = homeworkDescriptionRS.getString("description");
        }
        dbConnect.closeStatement();
        dashboardTaskDescription.setText(homeworkDescription);
        dbConnect.closeConnection();
    }

    public void beginEditingDashboardToDo(MouseEvent mouseEvent) throws SQLException {
        //allows user to edit a task from the dashboard
        //first it searches the record of that task
        typeOfTask = "ToDo";
        String selectedHomework = dashboardListToDo.getSelectionModel().getSelectedItem();
        dashboardTaskTitle.setText(selectedHomework);
        DatabaseConnection dbConnect = new DatabaseConnection();
        dbConnect.createConnection();
        LocalDate homeworkDueDate = null; //due date of that task is displayed
        ResultSet dueDateRS = dbConnect.executeQuery("select dueDate from " + tasksTableName + " where title = \"" + selectedHomework + "\" and type = \"Task\";");
        while (dueDateRS.next()){
            Date databaseDate = dueDateRS.getDate("dueDate");
            homeworkDueDate = databaseDate.toLocalDate();
        }
        dbConnect.closeStatement();
        dashboardTaskDueDate.setValue(homeworkDueDate);
        //need to give all subjects in the choicebox
        dashboardTaskSubject.setDisable(true);
        ObservableList priorities = FXCollections.observableArrayList("High", "Medium", "Low");
        dashboardTaskPriority.setItems(priorities);
        String homeworkPriority = null; //priority of task is displayed
        ResultSet priorityRS = dbConnect.executeQuery("select priority from " + tasksTableName + " where title = \"" + selectedHomework + "\" and type = \"Task\";");
        while (priorityRS.next()){
            homeworkPriority = priorityRS.getString("priority");
        }
        dbConnect.closeStatement();
        dashboardTaskPriority.setValue(homeworkPriority);
        ObservableList homeworkTypes = FXCollections.observableArrayList("General", "Essay", "Report", "Worksheet", "Reading", "Presentation", "Test", "Study", "Project", "Quiz");
        dashboardTaskType.setItems(homeworkTypes);
        String homeworkHomeworkType = null; //task type of task is displayed
        ResultSet homeworkTypeRS = dbConnect.executeQuery("select homeworkType from " + tasksTableName + " where title = \"" + selectedHomework + "\" and type = \"Task\";");
        while (homeworkTypeRS.next()){
            homeworkHomeworkType = homeworkTypeRS.getString("homeworkType");
        }
        dbConnect.closeStatement();
        dashboardTaskType.setValue(homeworkHomeworkType);
        String homeworkDescription = null; //description of task is displayed
        ResultSet homeworkDescriptionRS = dbConnect.executeQuery("select description from " + tasksTableName + " where title = \"" + selectedHomework + "\" and type = \"Task\";");
        while (homeworkDescriptionRS.next()){
            homeworkDescription = homeworkDescriptionRS.getString("description");
        }
        dbConnect.closeStatement();
        dashboardTaskDescription.setText(homeworkDescription);
        dbConnect.closeConnection();
    }

    public void saveTaskDashboard (ActionEvent actionEvent) throws SQLException {
        //updates the record of a selected task

        String taskType = null;

        //helsp differentiate between the listviews this functoin might get called form
        if (typeOfTask.equals("Homework")){
            unTouchedTitle = dashboardListHomework.getSelectionModel().getSelectedItem();
            taskType = "Homework";
        }
        if (typeOfTask.equals("ToDo")){
            unTouchedTitle = dashboardListToDo.getSelectionModel().getSelectedItem();
            taskType="Task";
        }

        String title;
        LocalDate dueDate;
        String priority;
        String subject = null;
        String description = null;
        String homeworkType = null;

        //gets the updates values from the respective textboxes and datepickers
        title = dashboardTaskTitle.getText();
        dueDate = dashboardTaskDueDate.getValue();
        priority = dashboardTaskPriority.getValue();
        subject = dashboardTaskSubject.getValue();
        description = dashboardTaskDescription.getText();
        homeworkType = dashboardTaskType.getValue();

        DatabaseConnection dbConnect = new DatabaseConnection();
        tasksTableName = DatabaseConnection.getTasksTable();
        //updates the record in the database with the new values
        dbConnect.createConnection();
        dbConnect.execute("update " + tasksTableName + " set dueDate = \"" + dueDate + "\" where title = \"" + unTouchedTitle + "\" and type =\"" + taskType + "\";");
        dbConnect.closeStatement();
        dbConnect.execute("update " + tasksTableName + " set priority = \"" + priority + "\" where title = \"" + unTouchedTitle + "\" and type =\"" + taskType + "\";");
        dbConnect.closeStatement();
        dbConnect.execute("update " + tasksTableName + " set subject = \"" + subject + "\" where title = \"" + unTouchedTitle + "\" and type =\"" + taskType + "\";");
        dbConnect.closeStatement();
        dbConnect.execute("update " + tasksTableName + " set description = \"" + description + "\" where title = \"" + unTouchedTitle + "\" and type = \"" + taskType + "\";");
        dbConnect.closeStatement();
        dbConnect.execute("update " + tasksTableName + " set homeworkType = \"" + homeworkType + "\" where title = \"" + unTouchedTitle + "\" and type =\"" + taskType + "\";");
        dbConnect.closeStatement();
        dbConnect.execute("update " + tasksTableName + " set title = \"" + title + "\" where title = \"" + unTouchedTitle + "\" and type =\"" + taskType + "\";");
        dbConnect.closeStatement();
        dbConnect.closeConnection();
    }

    public void completeTaskDashboard (ActionEvent actionEvent) throws SQLException {
        //deletes the task in the database, from the dashboard.

        String taskType = null;
        String title = null;

        //helps differentiate between the two lists this function might get called on
        if (typeOfTask.equals("Homework")){
            unTouchedTitle = dashboardListHomework.getSelectionModel().getSelectedItem();
            taskType = "Homework";
            title = dashboardTaskTitle.getText();
        }
        if (typeOfTask.equals("ToDo")){
            unTouchedTitle = dashboardListToDo.getSelectionModel().getSelectedItem();
            taskType="Task";
            title = dashboardTaskTitle.getText();
        }

        //deletes the task from the database
        DatabaseConnection dbConnect = new DatabaseConnection();
        dbConnect.createConnection();
        dbConnect.execute("delete from " + tasksTableName + " where title = \"" + title + "\" and type =\"" + typeOfTask + "\";");
        dbConnect.closeStatement();
        dbConnect.closeConnection();

        //removes the task from the listviews
        if (typeOfTask.equals("Homework")){
            dashboardListHomework.getItems().remove(dashboardListHomework.getSelectionModel().getSelectedIndex());
            homeworkListHomework.getItems().remove(dashboardListHomework.getSelectionModel().getSelectedIndex() + 1);
        }
        if (typeOfTask.equals("ToDo")){
            dashboardListToDo.getItems().remove(dashboardListToDo.getSelectionModel().getSelectedIndex());
            toDoListHomework.getItems().remove(dashboardListToDo.getSelectionModel().getSelectedIndex() + 1);
        }

    }

    public void sortPriority (ActionEvent actionEvent) throws SQLException {
        //this function sorts the list view according to the priority of the items inside the list from high to low.
        ObservableList<String> homeworkList = null;
        DatabaseConnection dbConnect = new DatabaseConnection();
        String buttonID = ((MenuItem) actionEvent.getSource()).getId();
        String taskType = null;

        //helps differentiate between the two listviews on which this function is called on
        if (buttonID.equals("prioritySortHomework")){
            homeworkList = homeworkListHomework.getItems();
            taskType = "Homework";
        } else if(buttonID.equals("prioritySortToDo")){
            homeworkList = toDoListHomework.getItems();
            taskType = "Task";
        }
        //a stack is used to sort the items according to priority
        Stack<String> myStack = new Stack<String>();
        dbConnect.createConnection();
        //gets all the items with a low priority in the database and adds them to the stack
        ResultSet lowPriRS = dbConnect.executeQuery("select title from " + tasksTableName + " where priority = \"Low\" and type = \""+ taskType +"\";");
        while (lowPriRS.next()){
            String low = lowPriRS.getString("title");
            myStack.push(low);
        }
        dbConnect.closeStatement();
        //gets all the items with a medium priority in the database and adds them to the stack
        ResultSet medPriRS = dbConnect.executeQuery("select title from " + tasksTableName + " where priority = \"Medium\" and type = \""+ taskType +"\";");
        while (medPriRS.next()){
            String med = medPriRS.getString("title");
            myStack.push(med);
        }
        dbConnect.closeStatement();
        //gets all the items with a high priority in the database and adds them to the stack
        ResultSet highPriRS = dbConnect.executeQuery("select title from " + tasksTableName + " where priority = \"High\" and type = \""+ taskType +"\";");
        while (highPriRS.next()){
            String high = highPriRS.getString("title");
            myStack.push(high);
        }
        dbConnect.closeStatement();
        //at this point, the stack contains all items with a high priority, followed by
        //items with a medium priority, and lastly items with a low priority, in that order
        ObservableList<String> sortedList = FXCollections.observableArrayList();
        if (!(myStack==null)){
            for (String string:homeworkList){
                //takes the items out of the stack in the desired order
                if (!(string == null)){ sortedList.add(myStack.pop()); }
            }
        }
        //updates the respective listview with the sorted priority list.
        if (buttonID.equals("prioritySortHomework")){
            homeworkListHomework.getItems().clear();
            homeworkListHomework.setItems(sortedList);
            dashboardListHomework.getItems().clear();
            dashboardListHomework.setItems(sortedList);
        } else if(buttonID.equals("prioritySortToDo")){
            toDoListHomework.getItems().clear();
            toDoListHomework.setItems(sortedList);
            dashboardListToDo.getItems().clear();
            dashboardListToDo.setItems(sortedList);
        }
        dbConnect.closeConnection();
    }

    public void sortDueDate (ActionEvent actionEvent) throws SQLException {
        //this sorts the listview according to the due date of the items within the list view,
        //from the tasks/homeworks due closest to the ones due later.
        ObservableList<String> homeworkList = FXCollections.observableArrayList();
        ObservableList<LocalDate> dateSortedPartial = FXCollections.observableArrayList(); //used as a queue
        ObservableList<LocalDate> dateSorted = FXCollections.observableArrayList(); //used as a queue
        DatabaseConnection dbConnect = new DatabaseConnection();
        String buttonID = ((MenuItem) actionEvent.getSource()).getId();
        String taskType = null;

        //helps to differentiate between the two types of lists this function could be called upon
        if (buttonID.equals("dueDateSortHomework")){
            taskType = "Homework";
        } else if(buttonID.equals("dueDateSortToDo")){
            taskType = "Task";
        }

        dbConnect.createConnection();

        //gets all the items in the database with a certain type
        ResultSet datesRS = dbConnect.executeQuery("select dueDate from " + tasksTableName + " where type = \"" + taskType + "\";");
        while (datesRS.next()){
            //adds the due date to an unsorted list
            Date date = datesRS.getDate("dueDate");
            LocalDate myDate = date.toLocalDate();
            dateSortedPartial.add(myDate);
        }
        dbConnect.closeStatement();

        //sorts the unsorted list
        dateSortedPartial.sort(Comparator.naturalOrder());

        for (LocalDate localDate:dateSortedPartial){
            if (!(dateSorted.contains(localDate))){
                //adds the localDate items inside the sorted list into a final list while eliminating repetition
                dateSorted.add(localDate);
            }
        }

        ResultSet title;

        //searches the database for an item with a certain due date and returns its title
        //in the sorted order, when it is added to a final list
        for (LocalDate localDate:dateSorted){
            title = dbConnect.executeQuery("select title from " + tasksTableName + " where dueDate =\"" + localDate + "\" and type = \""+taskType+"\";");
            while (title.next()){
                homeworkList.add(title.getString("title"));
            }
            dbConnect.closeStatement();
        }

        //lastly, the list is linked to its respective list view
        if (buttonID.equals("dueDateSortHomework")){
            homeworkListHomework.getItems().clear();
            homeworkListHomework.setItems(homeworkList);
            dashboardListHomework.getItems().clear();
            dashboardListHomework.setItems(homeworkList);
        } else if(buttonID.equals("dueDateSortToDo")){
            toDoListHomework.getItems().clear();
            toDoListHomework.setItems(homeworkList);
            dashboardListToDo.getItems().clear();
            dashboardListToDo.setItems(homeworkList);
        }
        dbConnect.closeConnection();
    }
}