package com.example.moviesys;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MovieRowController implements Initializable {


    @FXML
    public Label movieIDLabel,employeeIDLabel ,moviePrice,movieCountryReleaseLabel, movieRatingLabel, movieYear, movieDuration, movieLanguage,movieNameLabel;


    public Button deleteButton,statusButton;


    @FXML
    protected void statusButtonActionIn(ActionEvent event){

        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();

        if (clickedButton.getText().equals("NotLive")) {

            try {

                String query = "UPDATE tb_Movie Set Status ='Live' WHERE MovieID ='" + buttonId + "'";
                HelloApplication.statement.executeQuery(query);
            } catch (SQLException s) {
                clickedButton.setText("Live");
                clickedButton.setStyle("-fx-background-color: Green");
            }
        }
        else {
            try {

                String query = "UPDATE tb_Movie Set Status ='NotLive' WHERE MovieID ='" + buttonId + "'";
                HelloApplication.statement.executeQuery(query);
            } catch (SQLException s) {
                clickedButton.setText("NotLive");
                clickedButton.setStyle("-fx-background-color: Red");

            }

        }

    }


    @FXML
    protected void deleteButtonActionIn(ActionEvent event) throws SQLException {

        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();

        try {

            String query = "delete from tb_Movie where MovieID='" + buttonId + "'";
            HelloApplication.statement.executeQuery(query);
        }
        catch (SQLServerException s){
            clickedButton.setText("DONE");
            clickedButton.setStyle("-fx-background-color: Green");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
