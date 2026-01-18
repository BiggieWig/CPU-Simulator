package ui;

import core.RegisterFile;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class RegisterView extends ScrollPane {

    private VBox contentBox;

    public RegisterView() {
        this.setFitToWidth(true);
        this.setPrefWidth(200);

        contentBox = new VBox();
        contentBox.setPadding(new Insets(10));
        contentBox.setSpacing(5);

        this.setContent(contentBox);
    }

    public void displayRegisters(RegisterFile rf) {
        contentBox.getChildren().clear();

        Label header = new Label("Registers");
        header.setFont(new Font("Arial", 16));
        header.setStyle("-fx-font-weight: bold");
        contentBox.getChildren().add(header);

        int[] regs = rf.getAllRegisters();
        for (int i = 0; i < regs.length; i++) {
            String text = String.format("R%-2d : %d", i, regs[i]);
            Label lbl = new Label(text);
            lbl.setStyle("-fx-font-family: 'Monospaced';");
            contentBox.getChildren().add(lbl);
        }
    }
}