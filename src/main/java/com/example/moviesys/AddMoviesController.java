package com.example.moviesys;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class AddMoviesController implements Initializable {

    @FXML
    public Button backButton, addMovieButton,uploadImageButton;

    @FXML
    public ImageView imageView;
    @FXML
    public TextField employeeIDField, movieShowTime1,movieShowTime2, movieShowTime3 ,moviePriceField,movieCountryField,movieYearField,movieDurationField,movieLanField,movieGenre,movieNameField;

    @FXML
    public TextArea movieDescription;

    @FXML
    public ComboBox statusComboBox,movieRatingComboBox,showTimeScreen1,showTimeScreen2,showTimeScreen3;

    @FXML
    public Label movieFinalLabel;

    @FXML
    public DatePicker movieShowTimeDate;

    String filePath;

    String selectedItemComboBox,selectRatingComboBox, selectedScreen1ComboBox,selectedScreen2ComboBox,selectedScreen3ComboBox;

    String formattedDate;
    LocalDate selectedShowTimeDate;
    @FXML
    protected void backButtonActionIn() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MoviesEdit.fxml"));
        Scene editScene = new Scene(fxmlLoader.load());
        HelloApplication.mainStage.setScene(editScene);
        HelloApplication.mainStage.setMaximized(true);
        HelloApplication.mainStage.show();


    }

    @FXML
    protected void uploadMovieButtonActionIn() throws SQLException {

        openFileChooser();



    }

    //@FXML
    //protected void movieShowTimeDateActionIn(){



    //}

    @FXML
    protected void statusComboBoxActionIn(){
        selectedItemComboBox = (String) statusComboBox.getSelectionModel().getSelectedItem();

    }

    @FXML
    protected void showTimeScreen1ComboBoxActionIn(){
        selectedScreen1ComboBox = (String) showTimeScreen1.getSelectionModel().getSelectedItem();
        System.out.println(selectedScreen1ComboBox);
    }

    @FXML
    protected void showTimeScreen2ComboBoxActionIn(){
        selectedScreen2ComboBox = (String) showTimeScreen2.getSelectionModel().getSelectedItem();
        System.out.println(selectedScreen2ComboBox);
    }

    @FXML
    protected void showTimeScreen3ComboBoxActionIn(){
        selectedScreen3ComboBox = (String) showTimeScreen3.getSelectionModel().getSelectedItem();
        System.out.println(selectedScreen3ComboBox);

    }

    @FXML
    protected void ratingComboBoxActionIn(){
        selectRatingComboBox = (String) movieRatingComboBox.getSelectionModel().getSelectedItem();

    }

    @FXML
    protected void addMovieButtonActionIn() throws SQLException {

//        selectedShowTimeDate= movieShowTimeDate.getValue();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMDD");
//        String sqlDate = dateFormat.format(new Date(selectedShowTimeDate1));
//        System.out.println(sqlDate);

//        try {
//            if (Integer.parseInt(moviePriceField.getText()) < 0) {
//                movieFinalLabel.setText("Price can't be negative");
//                movieFinalLabel.setVisible(true);
//            }
//        }
//        catch (NumberFormatException n){
//            movieFinalLabel.setText("Price can't be negative");
//            movieFinalLabel.setVisible(true);
//        }


        try {
            String query = "insert into tb_Movie values ('" + movieNameField.getText() + "','" + movieLanField.getText() + "','" + movieDurationField.getText() + "','" + movieYearField.getText() + "','" +
                    selectRatingComboBox + "','" + movieCountryField.getText() + "','" + moviePriceField.getText() + "','" + movieDescription.getText() + "','" + employeeIDField.getText() + "','"+selectedItemComboBox+"')";

            HelloApplication.statement.executeQuery(query);
        }
        catch (SQLServerException s){
            System.out.println(s.getMessage());
            movieFinalLabel.setText(s.getMessage());
            movieFinalLabel.setVisible(true);
        }


        String query2= "Select MovieID from tb_Movie Where MovieName='"+movieNameField.getText()+"' AND Year='"+movieYearField.getText()+"'";
        ResultSet res2 = HelloApplication.statement.executeQuery(query2);
        res2.next();

        int movieID = res2.getInt("MovieID");


        try {
            String q1 = "insert into tb_Screening values ('" + movieID + "','" + selectedScreen1ComboBox + "','" + movieShowTime1 + "')";
            HelloApplication.statement.executeQuery(q1);
        }
        catch (SQLServerException s){
            System.out.println(s.getMessage());
        }
        try {
            String q2 = "insert into tb_Screening values ('" + movieID + "','" + selectedScreen2ComboBox + "','" + movieShowTime2 +"')";
            HelloApplication.statement.executeQuery(q2);
        }
        catch (SQLServerException s){
            System.out.println(s.getMessage());
        }
        try {
            String q3 = "insert into tb_Screening values ('" + movieID + "','" + selectedScreen3ComboBox + "','" + movieShowTime3 +"')";
            HelloApplication.statement.executeQuery(q3);
        }
        catch (SQLServerException s){
            System.out.println(s.getMessage());
        }

        try {
            String q4 = "insert into tb_Genre values ('"+movieGenre.getText()+"','" + movieID + "')";
            HelloApplication.statement.executeQuery(q4);
        }
        catch (SQLServerException s){

        }

        storeImageAsBlob(filePath,movieID);
        movieFinalLabel.setText("Movie Has been Successfully Entered");
        movieFinalLabel.setVisible(true);

    }



    private void openFileChooser() throws SQLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
            filePath = selectedFile.getAbsolutePath();


        }
    }

    private void storeImageAsBlob(String filePath, int movieID) throws SQLException {

        try {

            String sql = "INSERT INTO tb_MovieImages(MovieID,Image)\n" +
                    "SELECT '" + movieID + "' AS MovieID, BulkColumn\n" +
                    "FROM OPENROWSET(BULK '" + filePath + "', SINGLE_BLOB) AS Image;";

            HelloApplication.statement.executeQuery(sql);
        }
        catch (SQLServerException S){

        }

        }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        statusComboBox.getItems().add("Live");
        statusComboBox.getItems().add("NotLive");

        movieRatingComboBox.getItems().add("1");
        movieRatingComboBox.getItems().add("2");
        movieRatingComboBox.getItems().add("3");
        movieRatingComboBox.getItems().add("4");
        movieRatingComboBox.getItems().add("5");

        showTimeScreen1.getItems().add("1");
        showTimeScreen1.getItems().add("2");
        showTimeScreen1.getItems().add("3");
        showTimeScreen1.getItems().add("4");

        showTimeScreen2.getItems().add("1");
        showTimeScreen2.getItems().add("2");
        showTimeScreen2.getItems().add("3");
        showTimeScreen2.getItems().add("4");

        showTimeScreen3.getItems().add("1");
        showTimeScreen3.getItems().add("2");
        showTimeScreen3.getItems().add("3");
        showTimeScreen3.getItems().add("4");




    }
}
