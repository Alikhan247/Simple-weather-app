package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchController extends Weather implements Initializable {

    ObservableList list = FXCollections.observableArrayList();

    @FXML
    private TextField search;

    public SearchController (){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        loadData();
        search.setText("Almaty");
    }

    @FXML
    public void sendCityName(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample.fxml"));
            Parent root = loader.load();
            Controller controller = loader.<Controller>getController();
            controller.updateByCityName(search.getText());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Controller controller = new Controller();
    }


}
