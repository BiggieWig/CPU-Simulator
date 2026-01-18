package ui;

import core.CPU;
import core.RegisterFile;
import core.Memory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import simulation.Simulator;

import javafx.geometry.Insets;

public class SimulatorFrame extends Application {

    private ControlPanel controlPanel;
    private PipelineView pipelineView;
    private RegisterView registerView;
    private MemoryView memoryView;
    private InstructionView instructionView;
    private PipelineRegistersPanel pipelineRegsPanel;

    private CPU cpu;
    private Simulator simulator;

    @Override
    public void start(Stage primaryStage) {
        cpu = new CPU();
        simulator = new Simulator();

        controlPanel = new ControlPanel(this);
        pipelineView = new PipelineView();
        registerView = new RegisterView();
        memoryView = new MemoryView();
        pipelineRegsPanel = new PipelineRegistersPanel();
        instructionView = new InstructionView();

        VBox bottomContainer = new VBox(10);
        bottomContainer.setPadding(new Insets(10));
        bottomContainer.getChildren().addAll(pipelineRegsPanel, memoryView);

        BorderPane root = new BorderPane();
        root.setTop(controlPanel);
        root.setCenter(pipelineView);
        root.setLeft(registerView);
        root.setBottom(bottomContainer);
        root.setRight(instructionView);

        instructionView.setProgram(simulator.getCpu().getInstructionMemory());


        Scene scene = new Scene(root, 1450, 700);
        primaryStage.setTitle("MIPS CPU Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();

        updateViews();
    }

    public void updateViews() {
        registerView.displayRegisters(simulator.getCpu().getRegisters());
        memoryView.displayMemory(simulator.getCpu().getMemory());
        pipelineView.displayPipeline(simulator.getCpu());
        instructionView.highlightPc(simulator.getCpu().getPc());
        pipelineRegsPanel.update(simulator.getCpu());
    }


    public void handleStep() {
        simulator.step();
        System.out.println("Step executed");
        updateViews();
    }
    public void handleRun() {
        simulator.run();
        System.out.println("Run executed");
        updateViews();
    }
    public void handleReset() {
        simulator.reset();
        System.out.println("Reset executed");
        updateViews();
    }
    public void handleStart() {
        simulator.start(this::updateViews);
    }
    public void handleStop() {
        simulator.stop();
        System.out.println("Stop executed");
    }
}