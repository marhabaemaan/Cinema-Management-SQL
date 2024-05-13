package com.example.moviesys;

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
import javafx.scene.text.Text;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class DetailPageConntroller implements Initializable {

    @FXML
    private Label movieName1;

    @FXML
    Button movieTime,movieYear,movieReleaseCountry,movieGenre;



    @FXML
    private Button movieShowTime1;

    @FXML
    private Button movieShowTime2;

    @FXML
    private Button movieShowTime3;

    @FXML
    private Text movieDescription;

    @FXML
    private Button backButton;

    @FXML
    private Button detailNextButton;

    @FXML
    private ImageView movieImage;

    static Parent parent;

    int countShowButton=0;

    String show1,show2,show3;

    static int seatNo;

    static int screenNo , screenID;

    static ArrayList<Integer> bookedSeats = new ArrayList<>();

    static String selectedShowtime;
    String selectedShowtimeID,numberOnly;

    @FXML
    public void backButtonActionIn() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("homeScene.fxml"));

        Scene homeScene = new Scene(fxmlLoader.load());
        HelloApplication.mainStage.setScene(homeScene);
        HelloApplication.mainStage.setMaximized(true);
        HelloApplication.mainStage.show();


    }

    int j=0;
    @FXML
    private void handleShowtimeButtonAction(ActionEvent event) {
        countShowButton++;

        if(countShowButton==1) {
            Button clickedButton = (Button) event.getSource();
            selectedShowtimeID = clickedButton.getId();
            selectedShowtime=clickedButton.getText();

            numberOnly= selectedShowtimeID.replaceAll("[^0-9]", "");


            j++;
            if (j % 2 != 0) {
                clickedButton.setStyle("-fx-border-color:  White; -fx-background-color:  #FF0000; ");

            } else if (j % 2 == 0) {
                clickedButton.setStyle("-fx-border-color:  Red; -fx-background-color:  #FF0000; ");

            }
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
    private void detailNextButtonActionIn() throws IOException, SQLException {



        if (countShowButton > 0) {


            Scene  seatScene = new Scene( new FXMLLoader(HelloApplication.class.getResource("Seating.fxml")).load());
            HelloApplication.mainStage.setScene(seatScene);
            parent = seatScene.getRoot();

            SeatSceneController seatSceneController = new SeatSceneController();

            HelloApplication.mainStage.show();


            String q9 = "select ScreenID from tb_Screening where Showtime='" + selectedShowtime + "'AND MovieID ='" + HomeSceneController.movieIDforDetail + "'";

            ResultSet res9 = HelloApplication.statement.executeQuery(q9);
            res9.next();
            screenID=res9.getInt("ScreenID");


            String q10="Select SeatNo from tb_Seating Where MovieID ='"+HomeSceneController.movieIDforDetail+"' AND ScreenID='"+screenID+"'";
            ResultSet res10 = HelloApplication.statement.executeQuery(q10);

            while (res10.next()) {

                bookedSeats.add(Integer.parseInt(res10.getString("SeatNo")));

            }


            String q11= "select ScreenNo from tb_Screening where Showtime='" + selectedShowtime + "'AND MovieID ='" + HomeSceneController.movieIDforDetail + "'";

            ResultSet res11 = HelloApplication.statement.executeQuery(q11);
            res11.next();
            screenNo=res11.getInt("ScreenNo");

            seatSceneController.initialize(parent);

        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        String movieName = HomeSceneController.NAME;
        movieName1.setText(movieName);

        try {
            System.out.println(movieName);
            String q1 = "select * from tb_Movie where movieName = '" + movieName + "'";
            ResultSet res1 = HelloApplication.statement.executeQuery(q1);
            res1.next();

            movieYear.setText(String.valueOf(res1.getInt("Year")));
            movieReleaseCountry.setText(res1.getString("CountryRelease"));
            int movieID = res1.getInt("MovieID");
            String q2 = "select * from tb_Genre where MovieID = '" + movieID + "'";
            ResultSet res2 = HelloApplication.statement.executeQuery(q2);
            res2.next();
            movieGenre.setText(res2.getString("genre"));

            String q3 = "select Showtime from tb_Screening where MovieID = '" + movieID + "'";
            ResultSet res3 = HelloApplication.statement.executeQuery(q3);
            res3.next();
            show1=(res3.getString("Showtime"));
            res3.next();
            show2=(res3.getString("Showtime"));
            res3.next();
            show3=(res3.getString("Showtime"));


            String q4="select Duration from tb_Movie where MovieID= '"+movieID+"'";
            ResultSet res4 = HelloApplication.statement.executeQuery(q4);
            res4.next();
            movieTime.setText(res4.getString("Duration"));

            String q5="select Image from tb_MovieImages where MovieID= '"+movieID+"'";
            ResultSet res5 = HelloApplication.statement.executeQuery(q5);
            res5.next();
            InputStream inputStream1 = res5.getBinaryStream("Image");
            movieImage.setImage(new Image(inputStream1));

            String q6="select Description from tb_Movie where MovieID= '"+movieID+"'";
            ResultSet res6 = HelloApplication.statement.executeQuery(q6);
            res6.next();
            String descrip=(res6.getString("Description"));

            int maxString = 120;
            StringBuilder builder = new StringBuilder(descrip);
            int index=maxString;

            while (index < builder.length()) {
                builder.insert(index, "\n");
                index += maxString + 1; // +1 to account for the inserted newline character
            }

            String output = builder.toString();
            movieDescription.setText(output);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



        movieShowTime1.setText(convertDate(show1));
        movieShowTime2.setText(convertDate(show2));
        movieShowTime3.setText(convertDate(show3));
    }

    public static String convertDate(String dateStr) {
        String convertedDateStr=null;

        DateFormat inputFormat = new SimpleDateFormat("HH:mm:ss");
        DateFormat outputFormat = new SimpleDateFormat("hh:mm a");

        try {
            Date date = inputFormat.parse(dateStr);
            convertedDateStr = outputFormat.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertedDateStr;
    }

}



