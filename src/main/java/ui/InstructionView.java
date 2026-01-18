package ui;

import core.Instruction;
import core.InstructionMemory;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.List;

public class InstructionView extends VBox {

    private ListView<String> instructionList;

    public InstructionView() {
        this.setPadding(new Insets(10));
        this.setSpacing(5);
        this.setPrefWidth(150); // Give it some space on the side

        Label title = new Label("Instruction Memory");
        title.setFont(new Font("Arial", 14));
        title.setStyle("-fx-font-weight: bold");

        instructionList = new ListView<>();

        VBox.setVgrow(instructionList, Priority.ALWAYS);

        this.getChildren().addAll(title, instructionList);
    }


    public void setProgram(InstructionMemory instrMem) {
        instructionList.getItems().clear();
        for (int i = 0; i < instrMem.getSize(); i++) {
            Instruction instr = instrMem.getInstruction(i);
            String line = String.format("%02d: %s %s", i, instr.getOpcode(), java.util.Arrays.toString(instr.getOperands()));
            instructionList.getItems().add(line);
        }
    }

    /**
     * Highlights the instruction currently at the PC address.
     */
    public void highlightPc(int pc) {
        instructionList.getSelectionModel().clearSelection();
        if (pc >= 0 && pc < instructionList.getItems().size()) {
            instructionList.getSelectionModel().select(pc);
            instructionList.scrollTo(pc);
        }
    }
}