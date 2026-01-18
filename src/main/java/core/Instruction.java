package core;

import java.util.Arrays;

public class Instruction {
    private String opcode;
    private int[] operands;
    private String type;

    public Instruction(String opcode, int[] operands, String type) {
        this.opcode = opcode;
        this.operands = operands;
        this.type = type;
    }

    public String getOpcode() { return opcode; }
    public int[] getOperands() { return operands; }
    public String getType() { return type; }

    @Override
    public String toString() {
        return opcode + " " + Arrays.toString(operands);
    }

    public int getDestRegister() {
        if (opcode.equals("SW") || opcode.equals("BEQ") || opcode.equals("NOP")) {
            return -1;
        }

        return (operands.length > 0) ? operands[0] : -1;
    }

    public int[] getSourceRegisters() {
        if (opcode.equals("NOP")) return new int[]{};

        // SW Rsrc, offset(Rbase) -> Reads operands[0] (src) and operands[1] (base)
        if (opcode.equals("SW")) {
            // Safety check for array length
            if (operands.length > 1) return new int[]{operands[0], operands[1]};
            return new int[]{operands[0]};
        }
        // BEQ Rs, Rt, label -> Reads operands[0] and operands[1]
        else if (opcode.equals("BEQ")) {
            if (operands.length > 1) return new int[]{operands[0], operands[1]};
            return new int[]{operands[0]};
        }
        // R-Type: ADD Rd, Rs, Rt -> Reads operands[1] (Rs) and operands[2] (Rt)
        else if (type.equals("R-Type")) {
            if (operands.length > 2) return new int[]{operands[1], operands[2]};
            return new int[]{};
        }
        // I-Type: ADDI/LW Rd, Rs, Imm -> Reads operands[1] (Rs) only
        else if (type.equals("I-Type")) {
            if (operands.length > 1) return new int[]{operands[1]};
            return new int[]{};
        }

        return new int[]{};
    }
}