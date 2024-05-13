package com.example.moviesys;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeeTableController implements Initializable {

    @FXML
    public VBox entry = new VBox();

    @FXML
    public Button backButton,searchEmployeeButton;

    @FXML
    public TextField searchEmployeeField;




    @FXML
    public void loadAll(){
        try {
            String q = " Select * from tb_Employee";
            ResultSet res = HelloApplication.statement.executeQuery(q);


            while (res.next()) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeeRow.fxml"));
                Parent row = loader.load();

                EmployeeRowController al = loader.getController();

                al.employeeIDLabel.setText(res.getString("EmployeeID"));
                al.employeeFNameLabel.setText(res.getString("FirstName"));
                al.employeeLNameLabel.setText(res.getString("LastName"));
                al.employeeAddressLabel.setText(res.getString("Address"));
                al.employeeDOBLabel.setText(res.getString("DOB"));
                al.employeeUsernameLabel.setText(res.getString("Username"));
                al.employeePasswordLabel.setText(res.getString("Password"));
                al.employeeDesigLabel.setText(res.getString("Designation"));
                al.employeeAccessIDLabel.setText(res.getString("AccessID"));



                entry.getChildren().add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void ButtonsHoverIn(Event event) {
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();
        clickedButton.setStyle("-fx-background-color: #600000; -fx-background-radius: 15px; ");


    }

    @FXML
    protected void ButtonsHoverOut(Event event) {
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();
        clickedButton.setStyle("-fx-background-color: #FF0000; -fx-background-radius: 15px; ");

    }


    @FXML
    protected void backButtonActionIn() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ManagerButtonScene1.fxml"));
        Scene employeeScene = new Scene(fxmlLoader.load());
        HelloApplication.mainStage.setScene(employeeScene);
        HelloApplication.mainStage.setMaximized(true);
        HelloApplication.mainStage.show();

    }

    @FXML
    protected void searchEmployeeButtonActionIn() throws IOException {

        String employeeID=searchEmployeeField.getText();

        entry.getChildren().clear();

        try {
            String q ="exec searchEmployee  @EmployeeID='"+employeeID+"'";
            ResultSet res =HelloApplication.statement.executeQuery(q);
            res.next();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeeRow.fxml"));
            Parent row = loader.load();

            EmployeeRowController al = loader.getController();

            al.employeeIDLabel.setText(res.getString("EmployeeID"));
            al.employeeFNameLabel.setText(res.getString("FirstName"));
            al.employeeLNameLabel.setText(res.getString("LastName"));
            al.employeeAddressLabel.setText(res.getString("Address"));
            al.employeeDOBLabel.setText(res.getString("DOB"));
            al.employeeUsernameLabel.setText(res.getString("Username"));
            al.employeePasswordLabel.setText(res.getString("Password"));
            al.employeeDesigLabel.setText(res.getString("Designation"));
            al.employeeAccessIDLabel.setText(res.getString("AccessID"));



            entry.getChildren().add(row);

        }
        catch (SQLException s){
            System.out.println(s.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAll();
    }
}

