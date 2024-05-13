package com.example.moviesys;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import java.io.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManagerPortalController implements Initializable {
        public ImageView imageView,movieImageTopSeller;

        public Label movieNameTopSeller,movieCountTopSeller, ticketSoldCount;

        private static final int DURATION = 5 * 60;

        int remainingTime,ticketsCount;


        @FXML
        public Button backControlButton,managReservationsButton,manageEmployeesButton,manageMoviesButton;



        @FXML
        protected void manageMoviesButtonActionIn() throws IOException {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MoviesEdit.fxml"));
            Scene editScene = new Scene(fxmlLoader.load());
            HelloApplication.mainStage.setScene(editScene);
            HelloApplication.mainStage.setMaximized(true);
            HelloApplication.mainStage.show();

        }

        @FXML
        protected void manageEmployeesButtonActionIn() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EmployeeTable.fxml"));
        Scene employeeScene = new Scene(fxmlLoader.load());
        HelloApplication.mainStage.setScene(employeeScene);
        HelloApplication.mainStage.setMaximized(true);
        HelloApplication.mainStage.show();

    }

        @FXML
        protected void manageReservationsButtonActionIn() throws IOException {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Reservations.fxml"));
            Scene reservationsScene = new Scene(fxmlLoader.load());
            HelloApplication.mainStage.setScene(reservationsScene);
            HelloApplication.mainStage.setMaximized(true);
            HelloApplication.mainStage.show();

        }

        @FXML
        protected void backControlButtonActionIn() throws IOException{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homeScene.fxml"));
            Scene homeScene = new Scene(fxmlLoader.load());
            HelloApplication.mainStage.setScene(homeScene);
            HelloApplication.mainStage.setMaximized(true);
            HelloApplication.mainStage.show();

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


    private void startTimer() {
        remainingTime = DURATION;

        Thread timerThread = new Thread(() -> {
            while (remainingTime > 0) {
                Platform.runLater(() -> {
                    ticketSoldCount.setText(String.valueOf(remainingTime));
                });

                try {
                    Thread.sleep(4); // Delay for 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                remainingTime--;
            }

            Platform.runLater(() -> {
                ticketSoldCount.setText(String.valueOf(ticketsCount));
            });
        });

        timerThread.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        try {
            String q ="select top 1 r.MovieID, Count(r.MovieID) As TopMovieID from tb_Reservations r Group by r.MovieID";
            ResultSet res = HelloApplication.statement.executeQuery(q);
            res.next();

            int movieIDTopSeller= res.getInt("MovieID");
            int movieCountTop=res.getInt("TopMovieID");
            String temp=movieCountTopSeller.getText();
            temp=temp+"  "+movieCountTop;
            movieCountTopSeller.setText(temp);

            String q1= "Select MovieName from tb_Movie Where MovieID= '"+movieIDTopSeller+"'";
            ResultSet res1 = HelloApplication.statement.executeQuery(q1);
            res1.next();
            movieNameTopSeller.setText(res1.getString("MovieName"));

            String q2="Select Image from tb_MovieImages Where MovieID= '"+movieIDTopSeller+"'";
            ResultSet res2=HelloApplication.statement.executeQuery(q2);
            res2.next();
            InputStream inputStream1 = res2.getBinaryStream("Image");
            Image imageMovie1 = new Image(inputStream1);
            movieImageTopSeller.setImage(imageMovie1);

            String q3= "select count(SeatID) As ticketsSold from tb_Seating";
            ResultSet res3 = HelloApplication.statement.executeQuery(q3);
            res3.next();
            ticketsCount=res3.getInt("ticketsSold");

            startTimer();



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

