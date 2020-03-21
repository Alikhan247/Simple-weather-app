package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {



        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("../../resources/sample.fxml"));
        primaryStage.setTitle("Weather");
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
//        new Bounce(stage).play();

//
//        //Search window
//        Parent selectCity = FXMLLoader.load(getClass().getResource("/selectCity.fxml"));
//        Stage stage= new Stage();
//        stage.setTitle("Select City");
//        Scene scene2 = new Scene(selectCity);
//        stage.setScene(scene2);
//
//        stage.show();

        primaryStage.show();

    }


    public static void main(String[] args) {

        launch(args);
    }
}
