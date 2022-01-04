package Misc;

import javafx.scene.control.Alert;

public class AlertBox {

    //local variables to be used in the class
    private String content;
    private String title;
    private String header;

    public AlertBox(){
        this.content = "Error.";
        this.title = "Error.";
        this.header = null;
    }

    public AlertBox(String title, String headerText, String contentText){
        //this constructor sets up all the necessary details for the alert
        this.content = contentText;
        this.title = title;
        this.header = headerText;
    }

    public void createAlert(){
        //creates the alert using the Alert class
        //avoiding extra work from the programmer
        Alert myAlert = new Alert(Alert.AlertType.INFORMATION);
        myAlert.setContentText(content);
        myAlert.setHeaderText(header);
        myAlert.setTitle(title);
        myAlert.showAndWait();
    }
}
