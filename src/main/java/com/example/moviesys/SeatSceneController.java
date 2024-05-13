package com.example.moviesys;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.io.IOException;;
import java.util.ArrayList;

public class SeatSceneController {

    @FXML
    Button seatNextButton,seatBackButton;
    static ArrayList<String> arrayList = new ArrayList<>();

    static int countSilver,countGold=0;

    public void initialize(Parent parent) {

        for (int i = 1; i <= 92; i++) {
            String buttonId = "seatButton" + i;

            Button button = (Button) parent.lookup("#" + buttonId);
            if (button != null) {
                button.setId(buttonId);
                // ... other initialization code for the button ...
                for(int j=0; j < DetailPageConntroller.bookedSeats.size(); j++) {

                    String buttonIdCheck="seatButton"+DetailPageConntroller.bookedSeats.get(j);

                    if(buttonId.equals(buttonIdCheck)) {
                        button.setStyle("-fx-background-color: Red");
                        button.setDisable(true);
                        DetailPageConntroller.bookedSeats.remove(j);
                    }
                }
            }

            }

        }


    String prevSilverButton="Something";
    String prevGoldButton="Something";
    @FXML
    private void handleSilverButtonAction(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();

        if (buttonId.equals(prevSilverButton)) {
            clickedButton.setStyle(null);
            for (int i = 0; i <= arrayList.size(); i++) {
                if (buttonId == arrayList.get(i)) {
                    arrayList.remove(i);
                    countSilver--;
                }
            }
        }
                else {
                    clickedButton.setStyle("-fx-border-color: Blue");
                    arrayList.add(buttonId);
                    countSilver++;
                }


                prevSilverButton = buttonId;
            }


    @FXML
    private void handleGoldButtonAction(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();


        if (buttonId.equals(prevGoldButton)) {
            clickedButton.setStyle(null);
            clickedButton.setStyle("-fx-background-color: Gold");
            for (int i = 0; i <= arrayList.size(); i++) {
                if (buttonId == arrayList.get(i)) {
                    arrayList.remove(i);
                    countGold--;
                }
            }
        }
        else {
            clickedButton.setStyle("-fx-border-color: Blue;-fx-background-color: Gold");
            arrayList.add(buttonId);
            countGold++;
        }


        prevGoldButton = buttonId;
    }


    @FXML
    protected void seatNextButtonActionIn() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Booking.fxml"));

        Scene bookingScene = new Scene(fxmlLoader.load());
        HelloApplication.mainStage.setScene(bookingScene);
        HelloApplication.mainStage.setMaximized(true);
        HelloApplication.mainStage.show();

    }

    @FXML
    protected void seatBackButtonActionIn() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("DetailPage.fxml"));

        Scene detailScene = new Scene(fxmlLoader.load());
        HelloApplication.mainStage.setScene(detailScene);
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


}

