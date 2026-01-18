package simulation;

import core.CPU;
import core.HazardUnit;
import core.Instruction;
import core.PipelineStage;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Simulator {

    private CPU cpu;
    private Logger logger;
    private int clockCycle;
    private boolean isRunning;
    private Instruction[] pipelineRegs;
    private HazardUnit hazardUnit;

    private Timeline timeline;

    public Simulator() {
        this.cpu = new CPU();
        this.logger = new Logger();
        this.clockCycle = 0;
        this.pipelineRegs = new Instruction[5];
        this.hazardUnit = new HazardUnit();

        setupPipeline();
        loadProgram();
    }

    private void setupPipeline() {
        PipelineStage[] stages = cpu.getPipeline();
        stages[0] = new core.FetchStage(cpu);
        stages[1] = new core.DecodeStage(cpu);
        stages[2] = new core.ExecuteStage(cpu);
        stages[3] = new core.MemoryStage(cpu);
        stages[4] = new core.WriteBackStage(cpu);
    }

    private void loadProgram() {
        List<Instruction> program = new ArrayList<>();

        program.add(new Instruction("ADDI", new int[]{1, 0, 6}, "I-Type"));

        program.add(new Instruction("SW",   new int[]{1, 0, 100}, "I-Type"));

        program.add(new Instruction("ADDI", new int[]{1, 0, 0}, "I-Type"));

        program.add(new Instruction("LW",   new int[]{1, 0, 100}, "I-Type"));

        program.add(new Instruction("ADDI", new int[]{2, 0, 0}, "I-Type"));

        program.add(new Instruction("ADDI", new int[]{3, 0, 1}, "I-Type"));

        program.add(new Instruction("ADDI", new int[]{4, 0, 10}, "I-Type"));

        program.add(new Instruction("ADDI", new int[]{5, 0, 128}, "I-Type"));
        // --- LOOP START ---
        program.add(new Instruction("BEQ",  new int[]{1, 0, 3}, "I-Type"));

        program.add(new Instruction("ADD",  new int[]{2, 2, 1}, "R-Type"));

        program.add(new Instruction("SUB",  new int[]{1, 1, 3}, "R-Type"));

        program.add(new Instruction("BEQ",  new int[]{0, 0, -4}, "I-Type"));

        program.add(new Instruction("XOR",  new int[]{2, 2, 4}, "R-Type"));

        program.add(new Instruction("XOR",  new int[]{2, 2, 4}, "R-Type"));

        program.add(new Instruction("SW",   new int[]{2, 0, 104}, "I-Type"));

        program.add(new Instruction("OR",   new int[]{2, 2, 5}, "R-Type"));

        program.add(new Instruction("SW",   new int[]{2, 0, 108}, "I-Type"));

        cpu.getInstructionMemory().loadInstructions(program);
    }

    public void step() {
        clockCycle++;

        PipelineStage[] stages = cpu.getPipeline();
        for (int i = 4; i >= 0; i--) {
            stages[i].executeInstruction(pipelineRegs[i]);
        }

        core.DecodeStage decodeStage = (core.DecodeStage) stages[1];

        boolean branchTaken = decodeStage.isBranchTaken();

        if (branchTaken) {
            System.out.println("[Control] Branch Taken! Flushing Fetch Stage.");

            int currentNextPC = cpu.getPc();
            int offset = decodeStage.getBranchOffset();

            int targetPC = currentNextPC + offset - 1;

            cpu.setPc(targetPC);

            pipelineRegs[0] = null;

        }

        boolean stall = false;
        if (!branchTaken) {
            stall = hazardUnit.detectHazard(
                    pipelineRegs[1],
                    pipelineRegs[2],
                    pipelineRegs[3],
                    pipelineRegs[4]
            );
        }

        if (stall) {
            System.out.println("[Hazard] Stall detected at cycle " + clockCycle);

            pipelineRegs[4] = pipelineRegs[3];
            pipelineRegs[3] = pipelineRegs[2];
            pipelineRegs[2] = null;
        }
        else {
            // --- NORMAL PIPELINE FLOW ---
            for (int i = 4; i > 0; i--) {
                pipelineRegs[i] = pipelineRegs[i - 1];
            }

            int pc = cpu.getPc();
            if (pc < cpu.getInstructionMemory().getSize()) {
                pipelineRegs[0] = cpu.getInstructionMemory().getInstruction(pc);
                cpu.setPc(pc + 1);
            } else {
                pipelineRegs[0] = null;
            }
        }

        logger.logCycle(clockCycle, cpu);
    }

    public void run() {
        int maxCycles = 1000;
        isRunning = true;

        while (isRunning && maxCycles > 0) {
            step();
            maxCycles--;
            if (isSimulationFinished()) {
                isRunning = false;
            }
        }
        System.out.println("Run complete.");
    }

    public boolean isSimulationFinished() {
        if (cpu.getPc() < cpu.getInstructionMemory().getSize()) {
            return false;
        }
        for (PipelineStage stage : cpu.getPipeline()) {
            if (stage.getCurrentInstruction() != null) {
                return false;
            }
        }
        return true;
    }

    public void reset() {
        stop();

        clockCycle = 0;
        cpu.setPc(0);

        // Clear pipeline registers
        for(int i=0; i<5; i++) pipelineRegs[i] = null;

        // Reset Hardware
        cpu.getRegisters().reset();
        cpu.getMemory().reset();
        cpu.getControlUnit().reset();

        // Reset Stage Objects (for UI)
        for (PipelineStage stage : cpu.getPipeline()) {
            stage.reset();
        }

        // Reload program
        loadProgram();
    }

    public void start(Runnable onStepCallback) {
        if (timeline != null && timeline.getStatus() == Timeline.Status.RUNNING) {
            return;
        }

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            step();
            if (onStepCallback != null) {
                onStepCallback.run();
            }

            if (isSimulationFinished()) {
                System.out.println("Simulation finished.");
                stop();
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void stop() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    public CPU getCpu() {
        return cpu;
    }
}