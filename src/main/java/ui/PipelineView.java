package ui;

import core.CPU;
import core.ExecuteStage;
import core.Instruction;
import core.MemoryStage;
import core.PipelineStage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class PipelineView extends HBox {

    // These labels hold the changing text (e.g., "ADD R1...")
    private Label lblIF, lblID, lblEX, lblMEM, lblWB;

    // Wire Columns
    private List<SignalWire> wiresID_EX = new ArrayList<>();
    private List<SignalWire> wiresEX_MEM = new ArrayList<>();
    private List<SignalWire> wiresMEM_WB = new ArrayList<>();

    public PipelineView() {
        this.setPadding(new Insets(20));
        this.setSpacing(0); // Spacing handled by columns
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-border-color: black; -fx-border-width: 0 0 1 0;");

        // 1. Initialize the Labels first (so they are not null)
        lblIF = new Label("NOP");
        lblID = new Label("NOP");
        lblEX = new Label("NOP");
        lblMEM = new Label("NOP");
        lblWB = new Label("NOP");

        // 2. Create the Blue Boxes WRAPPING the labels
        VBox boxIF = createStageBox("IF - Fetch", lblIF);
        VBox boxID = createStageBox("ID - Decode", lblID);
        VBox boxEX = createStageBox("EX - Execute", lblEX);
        VBox boxMEM = createStageBox("MEM - Memory", lblMEM);
        VBox boxWB = createStageBox("WB - WriteBack", lblWB);

        // 3. Create Wire Columns
        VBox colIdEx = createWireColumn(wiresID_EX, "RegWrite", "MemRead", "MemWrite", "ALUSrc", "Branch");
        VBox colExMem = createWireColumn(wiresEX_MEM, "RegWrite", "MemRead", "MemWrite", "Branch");
        VBox colMemWb = createWireColumn(wiresMEM_WB, "RegWrite", "MemToReg");

        // 4. Add the BOXES (not just labels) and Wires to the view
        this.getChildren().addAll(
                boxIF,
                createSpacer(),
                boxID,
                colIdEx,
                boxEX,
                colExMem,
                boxMEM,
                colMemWb,
                boxWB
        );
    }

    private VBox createStageBox(String title, Label instrLbl) {
        VBox stageBox = new VBox();

        // Styling the Blue Box
        stageBox.setMinWidth(130);
        stageBox.setPrefWidth(130);
        stageBox.setMaxWidth(130);
        stageBox.setPrefHeight(300);
        stageBox.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: navy; -fx-border-radius: 5; -fx-border-width: 2;");
        stageBox.setAlignment(Pos.TOP_CENTER);
        stageBox.setPadding(new Insets(10));
        stageBox.setSpacing(10);

        // Title Label (e.g., "IF - Fetch")
        Label titleLbl = new Label(title);
        titleLbl.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        titleLbl.setWrapText(true);
        titleLbl.setAlignment(Pos.CENTER);

        // Styling the Instruction Label (passed in)
        instrLbl.setWrapText(true);
        instrLbl.setStyle("-fx-text-alignment: center; -fx-font-family: 'Consolas'; -fx-font-size: 12px;");
        instrLbl.setAlignment(Pos.CENTER);

        // Center the instruction vertically in the box
        VBox.setVgrow(instrLbl, Priority.ALWAYS);
        instrLbl.setMaxHeight(Double.MAX_VALUE);

        // Add both to the box
        stageBox.getChildren().addAll(titleLbl, instrLbl);

        return stageBox; // Return the BOX, not the label
    }

    private VBox createWireColumn(List<SignalWire> list, String... signals) {
        VBox box = new VBox(20); // Spacing between wires
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(0, 5, 0, 5));
        box.setMinWidth(120); // Width for wires

        for (String sig : signals) {
            SignalWire wire = new SignalWire(sig);
            list.add(wire);
            box.getChildren().add(wire);
        }
        return box;
    }

    private VBox createSpacer() {
        VBox box = new VBox();
        box.setMinWidth(50);
        return box;
    }

    public void displayPipeline(CPU cpu) {
        PipelineStage[] stages = cpu.getPipeline();

        // 1. Update Text
        updateLabel(lblIF, stages[0]);
        updateLabel(lblID, stages[1]);
        updateLabel(lblEX, stages[2]);
        updateLabel(lblMEM, stages[3]);
        updateLabel(lblWB, stages[4]);

        // 2. Update Wires: ID -> EX
        core.ControlUnit cu = cpu.getControlUnit();
        wiresID_EX.get(0).setState(cu.isRegWrite());
        wiresID_EX.get(1).setState(cu.isMemRead());
        wiresID_EX.get(2).setState(cu.isMemWrite());
        wiresID_EX.get(3).setState(cu.isAluSrc());
        wiresID_EX.get(4).setState(cu.isBranch());

        // 3. Update Wires: EX -> MEM
        ExecuteStage exStage = (ExecuteStage) stages[2];
        wiresEX_MEM.get(0).setState(exStage.isRegWrite());
        wiresEX_MEM.get(1).setState(exStage.isMemRead());
        wiresEX_MEM.get(2).setState(exStage.isMemWrite());
        wiresEX_MEM.get(3).setState(false);

        // 4. Update Wires: MEM -> WB
        MemoryStage memStage = (MemoryStage) stages[3];
        wiresMEM_WB.get(0).setState(memStage.isRegWrite());
        wiresMEM_WB.get(1).setState(memStage.isMemRead());
    }

    private void updateLabel(Label lbl, PipelineStage stage) {
        Instruction instr = stage.getCurrentInstruction();
        lbl.setText(instr != null ? instr.toString() : "NOP");
    }
}