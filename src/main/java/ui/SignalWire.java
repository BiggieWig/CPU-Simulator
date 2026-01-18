package ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class SignalWire extends HBox {

    private Rectangle line;
    private Polygon arrowHead;

    public SignalWire(String name) {
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(0); // Connect label directly to wire

        // Label
        Label lblName = new Label(name + " ");
        lblName.setFont(new Font("Arial", 9)); // Smaller font
        lblName.setMinWidth(50);
        lblName.setAlignment(Pos.CENTER_RIGHT);

        // Wire Line
        line = new Rectangle(30, 3);
        line.setFill(Color.LIGHTGRAY);

        // Arrow Head
        arrowHead = new Polygon();
        arrowHead.getPoints().addAll(0.0, 0.0,  8.0, 4.0,  0.0, 8.0);
        arrowHead.setFill(Color.LIGHTGRAY);

        this.getChildren().addAll(lblName, line, arrowHead);
    }

    public void setState(boolean isActive) {
        Color c = isActive ? Color.LIMEGREEN : Color.LIGHTGRAY;
        line.setFill(c);
        arrowHead.setFill(c);
    }
}