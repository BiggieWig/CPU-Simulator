package simulation;

import core.CPU;

public class Logger {

    public void logCycle(int cycle, CPU cpu) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Cycle ").append(cycle).append(" ===\n");

        sb.append("PC: ").append(cpu.getPc()).append("\n");

        sb.append("Registers: [");
        int[] regs = cpu.getRegisters().getAllRegisters();
        for (int i = 0; i < 8; i++) {
            sb.append("R").append(i).append(":").append(regs[i]);
            if(i < 7) sb.append(", ");
        }
        sb.append(", ...]\n");

        sb.append("Pipeline: Checking stages...\n");

        System.out.println(sb.toString());
    }
}