package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ControlPanel extends HBox {

    private SimulatorFrame mainFrame;

    public ControlPanel(SimulatorFrame mainFrame) {
        this.mainFrame = mainFrame;

        this.setPadding(new Insets(15));
        this.setSpacing(10);
        this.setStyle("-fx-background-color: #e0e0e0; -fx-border-color: #a0a0a0;");
        this.setAlignment(Pos.CENTER);


        Button btnStart = new Button("Start");
        Button btnStop = new Button("Stop");
        Button btnStep = new Button("Step");
        Button btnRun = new Button("Run");
        Button btnReset = new Button("Reset");


        btnStep.setOnAction(e -> mainFrame.handleStep());
        btnRun.setOnAction(e -> mainFrame.handleRun());
        btnReset.setOnAction(e -> mainFrame.handleReset());
        btnStart.setOnAction(e ->mainFrame.handleStart());
        btnStop.setOnAction(e -> mainFrame.handleStop());

        this.getChildren().addAll(btnStart, btnStop, btnStep, btnRun, btnReset);
    }

}