package core;

import java.util.HashMap;
import java.util.Map;

public class DecodeStage extends PipelineStage {

    private CPU cpu;
    private Map<String, Integer> decodedData = new HashMap<>();

    private int storeValue = 0;

    private boolean branchTaken = false;
    private int branchOffset = 0;

    public DecodeStage(CPU cpu) {
        this.cpu = cpu;
    }

    @Override
    public void executeInstruction(Instruction instr) {
        this.currentInstruction = instr;

        // Reset flags at the start of every cycle
        branchTaken = false;
        branchOffset = 0;

        if (instr == null) return;

        cpu.getControlUnit().decode(instr);

        int[] operands = instr.getOperands();
        int rsValue = 0;
        int rtValue = 0;
        storeValue = 0;

        if (instr.getOpcode().equals("SW")) {
            storeValue = cpu.getRegisters().read(operands[0]);
            if (operands.length > 1) rsValue = cpu.getRegisters().read(operands[1]);
            if (operands.length > 2) rtValue = operands[2];
        }
        else if (instr.getOpcode().equals("BEQ")) {

            rsValue = cpu.getRegisters().read(operands[0]);
            rtValue = cpu.getRegisters().read(operands[1]);

            if (rsValue == rtValue) {
                branchTaken = true;
                branchOffset = operands[2];
            }
        }
        else {
            // Normal R-Type / I-Type
            if (operands.length > 1) {
                rsValue = cpu.getRegisters().read(operands[1]);
            }
            if (operands.length > 2 && !cpu.getControlUnit().isAluSrc()) {
                rtValue = cpu.getRegisters().read(operands[2]);
            } else if (operands.length > 2) {
                rtValue = operands[2];
            }
        }

        decodedData.put("rs", rsValue);
        decodedData.put("rt", rtValue);

        if (operands.length > 0) {
            decodedData.put("rd_index", operands[0]);
        } else {
            decodedData.put("rd_index", 0);
        }
    }

    @Override
    public void reset() {
        super.reset();

        branchTaken = false;
        branchOffset = 0;

        decodedData.clear();
        decodedData.put("rs", 0);
        decodedData.put("rt", 0);
        decodedData.put("rd_index", 0);

        storeValue = 0;
    }

    public boolean isBranchTaken() { return branchTaken; }
    public int getBranchOffset() { return branchOffset; }

    public int getRsValue() { return decodedData.getOrDefault("rs", 0); }
    public int getRtValue() { return decodedData.getOrDefault("rt", 0); }
    public int getRdIndex() { return decodedData.getOrDefault("rd_index", 0); }
    public int getStoreValue() { return storeValue; }
}