package com.example.moviesys;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Objects;

public class HelloApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException, InterruptedException {


        mainStage = stage;  // for all the scenes //

        // Load the splash screen FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("splashScene.fxml"));

        Scene splashScene = new Scene(fxmlLoader.load());

        Stage tempStage = new Stage();

        splashStage = tempStage; // Only for splash scene //

        splashStage.setScene(splashScene);
        splashStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("logoo.png"))));
        splashStage.setTitle("Filmverse Cinema");
        splashStage.initStyle(StageStyle.TRANSPARENT);
        splashStage.show();

        mainStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("logoo.png"))));
        mainStage.setTitle("Filmverse Cinema");


    }

    public static Stage mainStage;
    public static Stage splashStage;

    /// CREATING DATABASE CONNECTION ///
    public static Connection con;

    public static Statement statement;

    {
        try {

            DriverManager.registerDriver(new SQLServerDriver());

            con = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-K4QRGA1;Database=MovieSystem;encrypt=true;trustServerCertificate=true;IntegratedSecurity=true;");

            statement = con.createStatement();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch();
    }

    }
