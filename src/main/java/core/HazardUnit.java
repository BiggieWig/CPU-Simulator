package core;

public class HazardUnit {

    public boolean detectHazard(Instruction idInstr, Instruction exInstr, Instruction memInstr, Instruction wbInstr) {
        if (idInstr == null || idInstr.getOpcode().equals("NOP")) return false;

        // 1. Get registers needed by the instruction in Decode
        int[] sources = idInstr.getSourceRegisters();

        // 2. Check against EX stage (1 cycle ahead)
        if (checkDependency(sources, exInstr)) return true;

        // 3. Check against MEM stage (2 cycles ahead)
        if (checkDependency(sources, memInstr)) return true;

        // 4. Check against WB stage (3 cycles ahead)
        if (checkDependency(sources, wbInstr)) return true;

        return false; // No hazard
    }

    private boolean checkDependency(int[] sources, Instruction targetInstr) {
        if (targetInstr == null) return false;

        int dest = targetInstr.getDestRegister();

        // If dest is -1 (e.g., SW) or 0 (R0 is never a hazard), ignore it
        if (dest <= 0) return false;

        for (int src : sources) {
            if (src == dest) return true; // Match found! Stall needed.
        }
        return false;
    }
}