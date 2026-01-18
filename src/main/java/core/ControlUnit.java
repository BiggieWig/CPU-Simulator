package core;


public class ControlUnit {

    private boolean regWrite = false;
    private boolean memRead = false;
    private boolean memWrite = false;
    private boolean branch = false;
    private boolean aluSrc = false;
    private String aluOp = "";

    public void decode(Instruction instr) {

        regWrite = false;
        memRead = false;
        memWrite = false;
        branch = false;
        aluSrc = false;
        aluOp = "";

        switch (instr.getOpcode()) {
            case "ADD": // R-Type
                regWrite = true;
                aluSrc = false;
                aluOp = "ADD";
                break;
            case "SUB": // R-Type
                regWrite = true;
                aluSrc = false;
                aluOp = "SUB";
                break;
            case "AND": // R-Type
                regWrite = true;
                aluSrc = false;
                aluOp = "AND";
                break;
            case "OR": // R-Type
                regWrite = true;
                aluSrc = false;
                aluOp = "OR";
                break;
            case "XOR": // R-Type
                regWrite = true;
                aluSrc = false;
                aluOp = "XOR";
                break;
            case "ADDI": // I-Type
                regWrite = true;
                aluSrc = true;
                aluOp = "ADD";
                break;
            case "LW": // I-Type (Load)
                regWrite = true;
                memRead = true;
                aluSrc = true;
                aluOp = "ADD";
                break;
            case "SW": // I-Type (Store)
                memWrite = true;
                aluSrc = true;
                aluOp = "ADD";
                break;
            case "BEQ": // I-Type (Branch)
                branch = true;
                aluSrc = false;
                aluOp = "SUB";
                break;
            case "NOP":
                break;


            default:
                throw new IllegalArgumentException("Unrecognized instruction opcode: " + instr.getOpcode());
        }
    }

    public boolean isRegWrite() {
        return regWrite;
    }

    public boolean isMemRead() {
        return memRead;
    }

    public boolean isMemWrite() {
        return memWrite;
    }

    public boolean isBranch() {
        return branch;
    }

    public boolean isAluSrc() {
        return aluSrc;
    }

    public String getAluOp() {
        return aluOp;
    }

    public void reset() {
        regWrite = false;
        memRead = false;
        memWrite = false;
        branch = false;
        aluSrc = false;
        aluOp = "NOP";
    }
}