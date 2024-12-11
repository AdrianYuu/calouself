package utils;

import enums.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public final class AlertHelper {

    public static void showInfo(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(AlertType.INFO.toString());
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void showWarning(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(AlertType.WARNING.toString());
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void showError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(AlertType.ERROR.toString());
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static boolean showConfirmation(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(AlertType.CONFIRMATION.toString());
        alert.setHeaderText(header);
        alert.setContentText(content);

        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        return result == ButtonType.OK;
    }

}
