package ui;

import core.Memory;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MemoryView extends VBox {

    private TextArea memoryArea;

    public MemoryView() {
        this.setPadding(new Insets(10));
        this.setPrefHeight(150);

        memoryArea = new TextArea();
        memoryArea.setEditable(false);
        memoryArea.setFont(Font.font("Monospaced", 12));

        this.getChildren().add(memoryArea);
    }

    public void displayMemory(Memory mem) {
        StringBuilder sb = new StringBuilder();
        int[] data = mem.getAllMemory();

        for (int i = 0; i < data.length; i++) {
            if (i % 8 == 0) sb.append(String.format("\n%04X: ", i));
            sb.append(String.format("%04X ", data[i]));
        }
        memoryArea.setText(sb.toString());
    }
}