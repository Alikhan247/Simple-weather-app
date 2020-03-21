package sample.factory;

import javafx.scene.control.Alert;

public class WarningButton implements Button {
    @Override
    public void create() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText("No such city in the database");
        alert.setContentText("Incorrect city name ");

        alert.showAndWait();
    }

}
