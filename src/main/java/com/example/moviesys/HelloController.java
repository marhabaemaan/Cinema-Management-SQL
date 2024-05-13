package com.example.moviesys;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.sql.*;



public class HelloController {
    @FXML
    private Button button1;

    @FXML
    private Label welcomeLable;

    @FXML
    private TextField employeeField,showPasswordField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private AnchorPane loginLayout;

    @FXML
    ImageView eyeOpen,eyeClose;
    static String fname;
    static String lname;


    @FXML
    protected void onLoginButtonHoverIn() {
        button1.setStyle("-fx-background-color: #600000; -fx-background-radius: 15px; ");


    }

    @FXML
    protected void onLoginButtonHoverOut() {
        button1.setStyle("-fx-background-color: #FF0000; -fx-background-radius: 15px; ");

    }

    @FXML
    protected void passwordButtonKeyRelease(){

        showPasswordField.setText(passwordField.getText());

    }

    @FXML
    protected void showPasswordButtonKeyRelease(){

        passwordField.setText(showPasswordField.getText());

    }

    @FXML
    protected void showPasswordButtonClickIn(){

        eyeClose.setVisible(false);
        showPasswordField.setVisible(false);
        eyeOpen.setVisible(true);
        passwordField.setVisible(true);

    }

    @FXML
    protected void closePasswordButtonClickIn(){
        eyeOpen.setVisible(false);
        passwordField.setVisible(false);
        eyeClose.setVisible(true);
        showPasswordField.setVisible(true);

    }

    @FXML
    protected void onLoginButtonActionIn() throws SQLException, IOException {


        String employee = employeeField.getText();
        String password = passwordField.getText();

        String q1 = "select * from tb_Employee where EmployeeID='" + employee + "'AND Password ='" + password + "'";
        ResultSet res = HelloApplication.statement.executeQuery(q1);


        if (res.next()) {
            System.out.println("Login Successful");
            welcomeLable.setText("Login Successful");
            welcomeLable.setVisible(true);

            String q2 = "select FirstName, LastName from tb_Employee e where e.EmployeeID='"+employee+"'";

            ResultSet res1 = HelloApplication.statement.executeQuery(q2);
            res1.next();
            System.out.print("Welcome ");
            fname= res1.getString("FirstName");
            lname= res1.getString("LastName");
            System.out.print(res1.getString("FirstName"));
            System.out.print(res1.getString("LastName"));
            System.out.println("!");


            FXMLLoader loader = new FXMLLoader(getClass().getResource("homeScene.fxml"));
            Scene scene = new Scene(loader.load());
            HelloApplication.mainStage.setScene(scene);
            HelloApplication.mainStage.setMaximized(true);
            HelloApplication.mainStage.show();


        } else {
            System.out.println("Login incorrect");
            welcomeLable.setText("Wrong Employee ID or Password");
            welcomeLable.setVisible(true);
        }
    }

    @FXML
    public void initialize()  {

        //un focus

       Platform.runLater( () -> loginLayout.requestFocus());


    }

}