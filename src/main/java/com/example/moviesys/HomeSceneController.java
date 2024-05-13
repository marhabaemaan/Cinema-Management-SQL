package com.example.moviesys;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class HomeSceneController implements Initializable {


    @FXML
    private Button logoutButton;

    static String NAME;

    @FXML
    private RadioButton rd1,rd2,rd3,rd4,rd5;

    int id1,id2,id3,id4,id5;
    @FXML
    private ImageView slideImageView,movieImage1,movieImage2,ratingImage1,ratingImage2,movieImage3,ratingImage3,movieImage4,ratingImage4,movieImage5,ratingImage5;

    @FXML
    public Label label,designation,acessid,movieName1,movieName2,movieName3,movieName4,movieName5;


    @FXML
    public Button managerButton1;

    static int movieIDforDetail;

    @FXML
    protected void onImageViewMouseIn()  {
        slideImageView.setImage(new Image(getClass().getResourceAsStream("maula.jpg")));
    }

    @FXML
    protected void onImageViewMouseOut (){
        slideImageView.setImage(new Image(getClass().getResourceAsStream("lastofus.jpg")));
    }

    @FXML
    protected void onRd1action(){
        slideImageView.setImage(new Image(getClass().getResourceAsStream("pandawide3.png")));
    }

    @FXML
    protected void onRd2action(){
        slideImageView.setImage(new Image(getClass().getResourceAsStream("maula.jpg")));
    }

    @FXML
    protected void onRd3action(){
        slideImageView.setImage(new Image(getClass().getResourceAsStream("lastofus.jpg")));
    }

    @FXML
    protected void onRd4action(){
        slideImageView.setImage(new Image(getClass().getResourceAsStream("peaky.jpg")));
    }

    @FXML
    protected void onRd5action(){
        slideImageView.setImage(new Image(getClass().getResourceAsStream("hod.jpg")));
    }

    @FXML
    protected void MovieImage1ActionIn() throws IOException {

        NAME = movieName1.getText();
        movieIDforDetail=1;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailPage.fxml"));
        Scene detailScene = new Scene(loader.load());
        HelloApplication.mainStage.setScene(detailScene);
        HelloApplication.mainStage.setMaximized(true);
        HelloApplication.mainStage.show();

    }

    @FXML
    protected void MovieImage2ActionIn() throws IOException {

        NAME = movieName2.getText();
        movieIDforDetail=2;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailPage.fxml"));
        Scene detailScene = new Scene(loader.load());
        HelloApplication.mainStage.setScene(detailScene);
        HelloApplication.mainStage.setMaximized(true);
        HelloApplication.mainStage.show();

    }

    @FXML
    protected void MovieImage3ActionIn() throws IOException {

        NAME = movieName3.getText();
        movieIDforDetail=3;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailPage.fxml"));
        Scene detailScene = new Scene(loader.load());
        HelloApplication.mainStage.setScene(detailScene);
        HelloApplication.mainStage.setMaximized(true);
        HelloApplication.mainStage.show();

    }

    @FXML
    protected void MovieImage4ActionIn() throws IOException {

        NAME = movieName4.getText();
        movieIDforDetail=4;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailPage.fxml"));
        Scene detailScene = new Scene(loader.load());
        HelloApplication.mainStage.setScene(detailScene);
        HelloApplication.mainStage.setMaximized(true);
        HelloApplication.mainStage.show();

    }

    @FXML
    protected void MovieImage5ActionIn() throws IOException {

        NAME = movieName5.getText();
        movieIDforDetail=5;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailPage.fxml"));
        Scene detailScene = new Scene(loader.load());
        HelloApplication.mainStage.setScene(detailScene);
        HelloApplication.mainStage.setMaximized(true);
        HelloApplication.mainStage.show();

    }




    @FXML
    protected void onLogoutButtonActionIn() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginScene.fxml"));
        Scene scene = new Scene(loader.load());
        HelloApplication.mainStage.setScene(scene);
        HelloApplication.mainStage.setResizable(false);
        HelloApplication.mainStage.show();
    }

    @FXML
    protected void managerButtonActionIn() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ManagerButtonScene1.fxml"));
        Scene employeeScene = new Scene(fxmlLoader.load());
        HelloApplication.mainStage.setScene(employeeScene);
        HelloApplication.mainStage.setMaximized(true);
        HelloApplication.mainStage.show();
    }

    @FXML
    protected void ButtonsHoverIn(Event event) {
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();
        clickedButton.setStyle("-fx-background-color: #600000");


    }

    @FXML
    protected void ButtonsHoverOut(Event event) {
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();
        clickedButton.setStyle("-fx-background-color: #FF0000");

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Image rating1 = new Image(getClass().getResourceAsStream("1star.png"));
        Image rating2 = new Image(getClass().getResourceAsStream("2star.png"));
        Image rating3 = new Image(getClass().getResourceAsStream("3star.png"));
        Image rating4 = new Image(getClass().getResourceAsStream("4star.png"));
        Image rating5 = new Image(getClass().getResourceAsStream("5star.png"));

        String space = " ";
        String name = HelloController.fname.concat(space.concat(HelloController.lname));

        label.setText(name.toUpperCase());

        String e ="select Designation, AccessID from tb_Employee e where e.FirstName='"+HelloController.fname+"'";
        ResultSet res = null;
        try {
            res = HelloApplication.statement.executeQuery(e);
            res.next();
            String desig = res.getString("Designation");
            String acess = res.getString("AccessID");
            designation.setText(desig);
            acessid.setText(acess);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        try {
            String query = "SELECT *\n" +
                    "FROM tb_Movie WITH (INDEX(idx_Movie_Status))\n" +
                    "WHERE [Status] = 'Live';";
            ResultSet result = HelloApplication.statement.executeQuery(query);
            result.next();
            id1=result.getInt("MovieID");
            result.next();
            id2=result.getInt("MovieID");
            result.next();
            id3=result.getInt("MovieID");
            result.next();
            id4=result.getInt("MovieID");
            result.next();
            id5=result.getInt("MovieID");


        }
        catch (SQLException S){

        }
        String query1 = "SELECT Image FROM tb_MovieImages WHERE MovieID ='"+id1+"'";
        String query2 = "SELECT MovieName FROM tb_Movie WHERE MovieID ='"+id1+"'";
        String query3 = "SELECT Image FROM tb_MovieImages WHERE MovieID ='"+id2+"'";
        String query4 = "SELECT MovieName FROM tb_Movie WHERE MovieID ='"+id2+"'";
        String query5 = "SELECT Rating FROM tb_Movie WHERE MovieID ='"+id1+"'";
        String query6 = "SELECT Rating FROM tb_Movie WHERE MovieID ='"+id2+"'";
        String query7 = "SELECT Image FROM tb_MovieImages WHERE MovieID ='"+id3+"'";
        String query8 = "SELECT MovieName FROM tb_Movie WHERE MovieID ='"+id3+"'";
        String query9 = "SELECT Image FROM tb_MovieImages WHERE MovieID='"+id4+"'";
        String query10 = "SELECT MovieName FROM tb_Movie WHERE MovieID ='"+id4+"'";
        String query11 = "SELECT Image FROM tb_MovieImages WHERE MovieID ='"+id5+"'";
        String query12 = "SELECT MovieName FROM tb_Movie WHERE MovieID ='"+id5+"'";
        String query13 = "SELECT Rating FROM tb_Movie WHERE MovieID ='"+id3+"'";
        String query14 = "SELECT Rating FROM tb_Movie WHERE MovieID ='"+id4+"'";
        String query15 = "SELECT Rating FROM tb_Movie WHERE MovieID ='"+id5+"'";

        ResultSet res1,res2,res3,res4,res5,res6,res7,res8,res9,res10,res11,res12,res13,res14,res15=null;


        try {
            res1=HelloApplication.statement.executeQuery(query1);
            res1.next();
            InputStream inputStream1 = res1.getBinaryStream("Image");
            Image imageMovie1 = new Image(inputStream1);
            movieImage1.setImage(imageMovie1);

            res2=HelloApplication.statement.executeQuery(query2);
            res2.next();
            String movieNameSt1=res2.getString("MovieName");
            movieName1.setText(movieNameSt1);

            res3=HelloApplication.statement.executeQuery(query3);
            res3.next();
            InputStream inputStream2 = res3.getBinaryStream("Image");
            Image imageMovie2 = new Image(inputStream2);
            movieImage2.setImage(imageMovie2);

            res4=HelloApplication.statement.executeQuery(query4);
            res4.next();
            String movieNameSt2=res4.getString("MovieName");
            movieName2.setText(movieNameSt2);

            res5=HelloApplication.statement.executeQuery(query5);
            res5.next();
            int movieRating1=res5.getInt("Rating");
            switch (movieRating1){
                case 1:
                    ratingImage1.setImage(rating1);
                    break;
                case 2:
                    ratingImage1.setImage(rating2);
                    break;
                case 3:
                    ratingImage1.setImage(rating3);
                    break;
                case 4:
                    ratingImage1.setImage(rating4);
                    break;
                case 5:
                    ratingImage1.setImage(rating5);
                    break;
            }


            res6=HelloApplication.statement.executeQuery(query6);
            res6.next();
            int movieRating2=res6.getInt("Rating");
            switch (movieRating2){
                case 1:
                    ratingImage2.setImage(rating1);
                    break;
                case 2:
                    ratingImage2.setImage(rating2);
                    break;
                case 3:
                    ratingImage2.setImage(rating3);
                    break;
                case 4:
                    ratingImage2.setImage(rating4);
                    break;
                case 5:
                    ratingImage2.setImage(rating5);
                    break;
            }

            res7=HelloApplication.statement.executeQuery(query7);
            res7.next();
            InputStream inputStream3 = res7.getBinaryStream("Image");
            Image imageMovie3 = new Image(inputStream3);
            movieImage3.setImage(imageMovie3);

            res8=HelloApplication.statement.executeQuery(query8);
            res8.next();
            String movieNameSt3=res8.getString("MovieName");
            movieName3.setText(movieNameSt3);

            res9=HelloApplication.statement.executeQuery(query9);
            res9.next();
            InputStream inputStream4 = res9.getBinaryStream("Image");
            Image imageMovie4 = new Image(inputStream4);
            movieImage4.setImage(imageMovie4);

            res10=HelloApplication.statement.executeQuery(query10);
            res10.next();
            String movieNameSt4=res10.getString("MovieName");
            movieName4.setText(movieNameSt4);

            res11=HelloApplication.statement.executeQuery(query11);
            res11.next();
            InputStream inputStream5 = res11.getBinaryStream("Image");
            Image imageMovie5 = new Image(inputStream5);
            movieImage5.setImage(imageMovie5);

            res12=HelloApplication.statement.executeQuery(query12);
            res12.next();
            String movieNameSt5=res12.getString("MovieName");
            movieName5.setText(movieNameSt5);

            res13=HelloApplication.statement.executeQuery(query13);
            res13.next();
            int movieRating3=res13.getInt("Rating");
            switch (movieRating3){
                case 1:
                    ratingImage3.setImage(rating1);
                    break;
                case 2:
                    ratingImage3.setImage(rating2);
                    break;
                case 3:
                    ratingImage3.setImage(rating3);
                    break;
                case 4:
                    ratingImage3.setImage(rating4);
                    break;
                case 5:
                    ratingImage3.setImage(rating5);
                    break;
            }

            res14=HelloApplication.statement.executeQuery(query14);
            res14.next();
            int movieRating4=res14.getInt("Rating");
            switch (movieRating4){
                case 1:
                    ratingImage4.setImage(rating1);
                    break;
                case 2:
                    ratingImage4.setImage(rating2);
                    break;
                case 3:
                    ratingImage4.setImage(rating3);
                    break;
                case 4:
                    ratingImage4.setImage(rating4);
                    break;
                case 5:
                    ratingImage4.setImage(rating5);
                    break;
            }

            res15=HelloApplication.statement.executeQuery(query15);
            res15.next();
            int movieRating5=res15.getInt("Rating");
            switch (movieRating5){
                case 1:
                    ratingImage5.setImage(rating1);
                    break;
                case 2:
                    ratingImage5.setImage(rating2);
                    break;
                case 3:
                    ratingImage5.setImage(rating3);
                    break;
                case 4:
                    ratingImage5.setImage(rating4);
                    break;
                case 5:
                    ratingImage5.setImage(rating5);
                    break;
            }
            if(Integer.parseInt(acessid.getText()) == 2){
                managerButton1.setVisible(true);
            }
            else {
                managerButton1.setVisible(false);
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }
}
