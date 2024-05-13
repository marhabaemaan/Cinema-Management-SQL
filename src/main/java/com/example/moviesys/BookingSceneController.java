package com.example.moviesys;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

import static javax.xml.datatype.DatatypeConstants.DURATION;

public class BookingSceneController implements Initializable {

    @FXML
    Button movieName, movieLang, movieRating, movieDuration, movieCountry, movieTimeLeft, bookButton, backButton,registerButton;

    @FXML
    Label systemDate, finalLabel, moviePrice ,seatIDs ,screenID ,movieShowTime,totalPrice,seatInfo,countSilver,countGold;

    @FXML
    TextField emailField,nameField,phoneField;

    String movieCost = null;

    float goldPrice,silverPrice;

     int remainingTime, registerCount, customerID;
     float movieP,silverP,goldP,finalP;

    int time = 0;

    private static final int DURATION = 2 * 60;


    @FXML
    protected void backButtonActionIn() throws IOException {

        SeatSceneController.countSilver=0;
        SeatSceneController.countGold=0;
        for (int i=0; i<SeatSceneController.arrayList.size(); i++) {
            SeatSceneController.arrayList.remove(i);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Seating.fxml"));

        Scene seatScene = new Scene(fxmlLoader.load());
        HelloApplication.mainStage.setScene(seatScene);
        HelloApplication.mainStage.setMaximized(true);
        HelloApplication.mainStage.show();
    }

    @FXML
    protected void bookButtonActionIn() throws IOException, SQLException {
        if(registerCount > 0 && (movieTimeLeft.getText().equals("Time expired"))==false) {

            for (int i = 0; i < SeatSceneController.arrayList.size(); i++) {

                String tempString = SeatSceneController.arrayList.get(i);

                String numberOnly = tempString.replaceAll("[^0-9]", "");



                if (Integer.parseInt(numberOnly) <= 60) {
                    try {

                        String q1 = "Insert into tb_Seating values('" + HomeSceneController.movieIDforDetail + "', '" + DetailPageConntroller.screenID + "','" + "Silver" + "','" + silverP + "','" + numberOnly+"','"+customerID+"')";
                        ResultSet res1 = HelloApplication.statement.executeQuery(q1);
                    } catch (SQLServerException S) {

                    }
                } else {

                    try {
                        String q2 = "Insert into tb_Seating values('" + HomeSceneController.movieIDforDetail + "', '" + DetailPageConntroller.screenID + "','" + "Gold" + "','" + goldP + "','" + numberOnly +"','"+customerID+"')";
                        ResultSet res2 = HelloApplication.statement.executeQuery(q2);
                    } catch (SQLServerException S) {

                    }
                }
            }
            LocalDate currentDate = LocalDate.now();
            String date=(String.valueOf(currentDate));

            try {
                String q3="Insert into tb_Payment values('"+customerID+"','"+"Cash"+"','"+finalP+"','"+date+"')";
                HelloApplication.statement.executeQuery(q3);
            }
            catch (SQLServerException s){

            }

            try {
                String q4="Select PaymentID from tb_Payment Where CustomerID='"+customerID+"'";
                ResultSet res4 = HelloApplication.statement.executeQuery(q4);
                res4.next();
                int paymentID = res4.getInt("PaymentID");


                String q5="Insert into tb_Reservations values('"+customerID+"','"+HomeSceneController.movieIDforDetail+"','"+DetailPageConntroller.screenID+"','"+paymentID+"')";
                finalLabel.setText("Reservation has been placed successfully");
                finalLabel.setVisible(true);
                HelloApplication.statement.executeQuery(q5);
            }
            catch (SQLServerException s){

            }


        }

        else if(registerCount < 0){
            finalLabel.setText("Kindly register the Customer first");
            finalLabel.setVisible(true);
        }
        else if((movieTimeLeft.getText().equals("Time expired"))==false){
            finalLabel.setText("SYSTEM TIME OUT");
            finalLabel.setVisible(true);
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
    protected void registerButtonActionIn() throws IOException, SQLException {

        char c = '@';
        String temp;
        int hold;
        Boolean test = false;


        /// Checking for valid email address //
        temp=emailField.getText();
        hold=temp.length();

        for(int k=0; k<hold; k++){
            if(emailField.getText().charAt(k) == c){
                test=true;
                break;
            }
            else {
                test=false;
            }
        }

        if (test==true){
            registerCount++;
            try {

                String q1="Select Email from tb_Customer Where Email='"+emailField.getText()+"'";
                ResultSet res1 = HelloApplication.statement.executeQuery(q1);

                if(res1.next()) {
                    finalLabel.setText("User Already Exist. Kindly Proceed to Booking");
                    finalLabel.setVisible(true);
                }
                else {
                    String q2 = "Insert into tb_Customer values( '" + nameField.getText() + "', '" + phoneField.getText() + "','" + emailField.getText() + "')";
                    finalLabel.setText("User Successfully Registered. Kindly Proceed to Booking");
                    finalLabel.setVisible(true);
                    ResultSet res2 = HelloApplication.statement.executeQuery(q2);

                }
            }
            catch (SQLServerException s){

            }
            String q3 ="Select CustomerID from tb_Customer where Email='"+ emailField.getText()+"'";
            ResultSet res3 = HelloApplication.statement.executeQuery(q3);
            res3.next();
            customerID = res3.getInt("CustomerID");


        }
        else {
            finalLabel.setText("Enter a valid Email Address");
            finalLabel.setVisible(true);

        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        movieName.setText(HomeSceneController.NAME);

        String q1 = "select Duration from tb_Movie where MovieID= '" + HomeSceneController.movieIDforDetail + "'";
        String q2 = "select Language from tb_Movie where MovieID= '" + HomeSceneController.movieIDforDetail + "'";
        String q3 = "select CountryRelease from tb_Movie where MovieID= '" + HomeSceneController.movieIDforDetail + "'";
        String q5 = "select Price from tb_Movie where MovieID= '" + HomeSceneController.movieIDforDetail + "'";
        ResultSet res1, res2, res3, res6, res5, res7 = null;

        try {
            res1 = HelloApplication.statement.executeQuery(q1);
            res1.next();
            movieDuration.setText(res1.getString("Duration"));

            res2 = HelloApplication.statement.executeQuery(q2);
            res2.next();
            movieLang.setText(res2.getString("Language"));

            res3 = HelloApplication.statement.executeQuery(q3);
            res3.next();
            movieCountry.setText(res3.getString("CountryRelease"));

            res5 = HelloApplication.statement.executeQuery(q5);
            res5.next();
            movieCost = String.valueOf(res5.getFloat("Price"));

            movieP = res5.getFloat("Price");
            movieCost=movieCost+" $";


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        startTimer();

        LocalDate currentDate = LocalDate.now();
        systemDate.setText(String.valueOf(currentDate));

        movieShowTime.setText(DetailPageConntroller.selectedShowtime);
        String st="";

        for(int i=0; i< SeatSceneController.arrayList.size(); i++) {

            String tempString = SeatSceneController.arrayList.get(i);

            String numberOnly= tempString.replaceAll("[^0-9]", "");

            st = st+" "+numberOnly;


        }

        seatIDs.setText(st);

        screenID.setText(String.valueOf(DetailPageConntroller.screenNo));

        moviePrice.setText(String.valueOf(movieCost));




        if(SeatSceneController.countSilver>0){

            countSilver.setText(String.valueOf(SeatSceneController.countSilver));
            String seat="Silver";

            try {
                String q6 = "select SeatPrice from tb_Seating where  Catagory='"+seat+"'";
                res6 = HelloApplication.statement.executeQuery(q6);
                res6.next();
                silverPrice=res6.getFloat("SeatPrice");
                silverP=silverPrice;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            silverPrice=(silverPrice+movieP)*SeatSceneController.countSilver;

        }

        if(SeatSceneController.countGold>0){

            String seat="Gold";
            countGold.setText(String.valueOf(SeatSceneController.countGold));

            try {
                String q7 = "select SeatPrice from tb_Seating where  Catagory='"+seat+"'";
                res7 = HelloApplication.statement.executeQuery(q7);
                res7.next();
                goldPrice=res7.getFloat("SeatPrice");
                goldP=goldPrice;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            goldPrice=(goldPrice+movieP)*SeatSceneController.countGold;

        }




        String finalPrice= String.valueOf(silverPrice+goldPrice);
        finalP= Float.parseFloat(finalPrice);
        finalPrice=finalPrice+" $";
        totalPrice.setText(finalPrice);



    }

    private void startTimer() {
        remainingTime = DURATION;

        Thread timerThread = new Thread(() -> {
            while (remainingTime > 0) {
                Platform.runLater(() -> {
                    movieTimeLeft.setText("TIMER:      "+remainingTime + " seconds remaining");
                });

                try {
                    Thread.sleep(1000); // Delay for 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                remainingTime--;
            }

            Platform.runLater(() -> {
                movieTimeLeft.setText("Time expired");
            });
        });

        timerThread.start();
    }




}

