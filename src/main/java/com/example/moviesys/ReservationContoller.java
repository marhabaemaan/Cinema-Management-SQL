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

public class ReservationContoller implements Initializable {

    @FXML
    public Button backButtonRes,searchReservationButton;

    @FXML
    public TextField searchReservationField;

    @FXML
    public VBox entry = new VBox();


    @FXML
    protected void backButtonResActionIn() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ManagerButtonScene1.fxml"));
        Scene employeeScene = new Scene(fxmlLoader.load());
        HelloApplication.mainStage.setScene(employeeScene);
        HelloApplication.mainStage.setMaximized(true);
        HelloApplication.mainStage.show();

    }

    @FXML
    protected void searchReservationButtonActionIn(){

        String reservationID=searchReservationField.getText();

        entry.getChildren().clear();

        try {
            String q = "EXEC searchReservation @ReservationID='"+reservationID+"'";
            ResultSet res = HelloApplication.statement.executeQuery(q);


            while (res.next()) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("reservationsTable.fxml"));
                Parent row = loader.load();

                ReservationTableController al = loader.getController();

                al.reservationID.setText(res.getString("ReservationID"));
                al.movieIDForRes.setText(res.getString("MovieName"));
                al.screenIDForRes.setText(res.getString("ScreenID"));
                al.paymentIDForRes.setText(res.getString("PaymentAmount$"));
                al.customerIDForRes.setText(res.getString("CustomerID"));
                al.customerName.setText(res.getString("Name"));
                al.customerEmail.setText(res.getString("Email"));
                al.customerPhoneNo.setText(res.getString("PhoneNo"));

                entry.getChildren().add(row);

            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
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
    public void loadAll(){
        try {
            String q = "EXEC PrintReservations";
            ResultSet res = HelloApplication.statement.executeQuery(q);


            while (res.next()) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("reservationsTable.fxml"));
                Parent row = loader.load();

                ReservationTableController al = loader.getController();

                al.reservationID.setText(res.getString("ReservationID"));
                al.movieIDForRes.setText(res.getString("MovieName"));
                al.screenIDForRes.setText(res.getString("ScreenID"));
                al.paymentIDForRes.setText(res.getString("PaymentAmount$"));
                al.customerIDForRes.setText(res.getString("CustomerID"));
                al.customerName.setText(res.getString("Name"));
                al.customerEmail.setText(res.getString("Email"));
                al.customerPhoneNo.setText(res.getString("PhoneNo"));

                entry.getChildren().add(row);

        }
            } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }










    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAll();

    }
}
