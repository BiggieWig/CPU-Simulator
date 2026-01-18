package core;

import java.util.ArrayList;
import java.util.List;

public class InstructionMemory {

    private List<Instruction> instructions;

    public InstructionMemory() {
        this.instructions = new ArrayList<>();
    }

    public void loadInstructions(List<Instruction> instructionList) {
        this.instructions = new ArrayList<>(instructionList);
    }

    public Instruction getInstruction(int pc) {
        if (pc < 0 || pc >= instructions.size()) {
            throw new IndexOutOfBoundsException("PC out of bounds: " + pc);
        }
        return instructions.get(pc);
    }

    public int getSize() {
        return instructions.size();
    }
}