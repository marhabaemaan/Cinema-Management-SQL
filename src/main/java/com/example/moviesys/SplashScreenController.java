package com.example.moviesys;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashScreenController implements Initializable {

    @FXML
    private Label statusLabel;

    @FXML
    private ProgressBar progressBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Thread taskThread = new Thread(() -> {
            try {
                // delay for 6 seconds  //
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



            // Switch to the next FXML file
            Platform.runLater(() -> {
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("loginScene.fxml"));


                    Scene scene = new Scene(loader.load());


                    HelloApplication.mainStage.setScene(scene);
                    HelloApplication.splashStage.hide();
                   HelloApplication.mainStage.show();


                    HelloController nextController = loader.getController();
                    nextController.initialize();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });

        taskThread.start();
    }
}
