package ui;

import core.CPU;
import core.DecodeStage;
import core.ExecuteStage;
import core.MemoryStage;
import core.Instruction;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;

public class PipelineRegistersPanel extends HBox {

    // We now store the "Content" labels separately so we can update them
    private Label lblContentIF_ID, lblContentID_EX, lblContentEX_MEM, lblContentMEM_WB;

    public PipelineRegistersPanel() {
        this.setPadding(new Insets(10));
        this.setSpacing(10);
        this.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc;");

        // Create the 4 register views
        // We capture the "Content" label returned by the helper method
        lblContentIF_ID = createRegisterBox("IF / ID");
        lblContentID_EX = createRegisterBox("ID / EX");
        lblContentEX_MEM = createRegisterBox("EX / MEM");
        lblContentMEM_WB = createRegisterBox("MEM / WB");
    }

    /**
     * Creates a VBox containing a Bold Title and a Monospace Content label.
     * Returns the Content label so we can update it later.
     */
    private Label createRegisterBox(String title) {
        VBox container = new VBox(5); // 5px spacing between title and content
        container.setPadding(new Insets(5));
        container.setStyle("-fx-background-color: white; -fx-border-color: #444; -fx-border-radius: 3; -fx-background-radius: 3;");
        container.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(container, Priority.ALWAYS); // Allow box to grow width-wise

        // --- 1. THE TITLE (Bold & Colored) ---
        Label lblTitle = new Label(title);
        lblTitle.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblTitle.setTextFill(Color.DARKBLUE); // Change color here
        lblTitle.setStyle("-fx-border-color: transparent transparent #ccc transparent; -fx-border-width: 0 0 1 0;"); // Bottom border separator
        lblTitle.setMaxWidth(Double.MAX_VALUE);

        // --- 2. THE CONTENT (Monospace) ---
        Label lblContent = new Label("[Empty]");
        lblContent.setFont(Font.font("Consolas", 11));
        lblContent.setWrapText(true);

        container.getChildren().addAll(lblTitle, lblContent);
        this.getChildren().add(container);

        return lblContent;
    }

    public void update(CPU cpu) {
        // --- 1. IF/ID ---
        Instruction instrIF = cpu.getPipeline()[0].getCurrentInstruction();

        // Check if reset/empty (Instruction is null)
        if (instrIF == null) {
            lblContentIF_ID.setText("[Empty]");
        } else {
            String if_text = "Instr: " + instrIF.toString() + "\n" +
                    "PC: " + cpu.getPc();
            lblContentIF_ID.setText(if_text);
        }

        // --- 2. ID/EX ---
        // Check if the previous stage (Fetch) provided an instruction,
        // OR check if specific fields are 0 to detect reset.
        DecodeStage decode = (DecodeStage) cpu.getPipeline()[1];
        if (decode.getCurrentInstruction() == null) {
            lblContentID_EX.setText("[Empty]");
        } else {
            String id_text = "Rs Val: " + decode.getRsValue() + "\n" +
                    "Rt Val: " + decode.getRtValue() + "\n" +
                    "Dest Reg: R" + decode.getRdIndex() + "\n" +
                    "Control: " + (cpu.getControlUnit().getAluOp().isEmpty() ? "NOP" : cpu.getControlUnit().getAluOp());
            lblContentID_EX.setText(id_text);
        }

        // --- 3. EX/MEM ---
        ExecuteStage exec = (ExecuteStage) cpu.getPipeline()[2];
        if (exec.getCurrentInstruction() == null) {
            lblContentEX_MEM.setText("[Empty]");
        } else {
            String ex_text = "ALU Out: " + exec.getAluResult() + "\n" +
                    "Write Data: " + exec.getWriteData() + "\n" +
                    "Dest Reg: R" + exec.getDestReg();
            lblContentEX_MEM.setText(ex_text);
        }

        // --- 4. MEM/WB ---
        MemoryStage mem = (MemoryStage) cpu.getPipeline()[3];
        if (mem.getCurrentInstruction() == null) {
            lblContentMEM_WB.setText("[Empty]");
        } else {
            String mem_text = "Mem Out: " + mem.getLoadMemoryData() + "\n" +
                    "ALU Pass: " + mem.getAluResultPassed() + "\n" +
                    "Dest Reg: R" + mem.getDestReg();
            lblContentMEM_WB.setText(mem_text);
        }
    }
}