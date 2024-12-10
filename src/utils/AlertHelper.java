package utils;

import enums.AlertEnum;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public final class AlertHelper {

    public static void showInfo(String header, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(AlertEnum.INFO.name());
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void showWarning(String header, String content) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(AlertEnum.WARNING.name());
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void showError(String header, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(AlertEnum.ERROR.name());
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static boolean showConfirmation(String header, String content) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(AlertEnum.CONFIRMATION.name());
        alert.setHeaderText(header);
        alert.setContentText(content);

        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        return result == ButtonType.OK;
    }

}
