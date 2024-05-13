package com.example.moviesys;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.event.ActionEvent;
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

public class MoviesViewController implements Initializable {

    @FXML
    public VBox entry = new VBox();

    @FXML
    public Button addMovieButton;

    @FXML
    public TextField searchMovieField;

    @FXML
    public Button searchMovieButton;



    @FXML
    public void loadAll(){
        try {
            String q = "select MovieID, MovieName, Language, Duration , Year , Rating, CountryRelease, Price, EmployeeID,Status from tb_Movie";
            ResultSet res = HelloApplication.statement.executeQuery(q);


            while (res.next()) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("MovieTable.fxml"));
                Parent row = loader.load();

                MovieRowController al = loader.getController();

                al.movieIDLabel.setText(res.getString("MovieID"));
                al.movieNameLabel.setText(res.getString("MovieName"));
                al.movieLanguage.setText(res.getString("Language"));
                al.movieDuration.setText(res.getString("Duration"));
                al.movieYear.setText(res.getString("Year"));
                al.movieRatingLabel.setText(res.getString("Rating"));
                al.movieCountryReleaseLabel.setText(res.getString("CountryRelease"));
                al.moviePrice.setText(res.getString("Price"));
                al.employeeIDLabel.setText(res.getString("EmployeeID"));
                al.deleteButton.setId(res.getString("MovieID"));
                al.statusButton.setId(res.getString("MovieID"));
                al.statusButton.setText(res.getString("Status"));

                if (al.statusButton.getText().equals("Live")){
                    al.statusButton.setStyle("-fx-background-color: Green");
                }


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
    protected void backButtonMoviesAddActionIn() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ManagerButtonScene1.fxml"));
        Scene employeeScene = new Scene(fxmlLoader.load());
        HelloApplication.mainStage.setScene(employeeScene);
        HelloApplication.mainStage.setMaximized(true);
        HelloApplication.mainStage.show();

    }

    @FXML
    protected void addButtonMoviesActionIn() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addMovies.fxml"));
        Scene addMoviesScene = new Scene(fxmlLoader.load());
        HelloApplication.mainStage.setScene(addMoviesScene);
        HelloApplication.mainStage.setMaximized(true);
        HelloApplication.mainStage.show();

    }

    @FXML
    protected void searchMovieButtonActionIn(){

        String movieID=searchMovieField.getText();

        entry.getChildren().clear();

        try {
            String q ="exec searchMovie1 @MovieID='"+movieID+"'";
            ResultSet res =HelloApplication.statement.executeQuery(q);
            res.next();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MovieTable.fxml"));
            Parent row = loader.load();

            MovieRowController al = loader.getController();

            al.movieIDLabel.setText(res.getString("MovieID"));
            al.movieNameLabel.setText(res.getString("MovieName"));
            al.movieLanguage.setText(res.getString("Language"));
            al.movieDuration.setText(res.getString("Duration"));
            al.movieYear.setText(res.getString("Year"));
            al.movieRatingLabel.setText(res.getString("Rating"));
            al.movieCountryReleaseLabel.setText(res.getString("CountryRelease"));
            al.moviePrice.setText(res.getString("Price"));
            al.employeeIDLabel.setText(res.getString("EmployeeID"));
            al.deleteButton.setId(res.getString("MovieID"));
            al.statusButton.setId(res.getString("MovieID"));
            al.statusButton.setText(res.getString("Status"));

            if (al.statusButton.getText().equals("Live")){
                al.statusButton.setStyle("-fx-background-color: Green");
            }


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
