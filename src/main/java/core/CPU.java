package core;

public class CPU {


    private final RegisterFile registers;
    private final ControlUnit controlUnit;
    private final ALU alu;
    private final Memory memory;
    private final PipelineStage[] pipeline;

    private final InstructionMemory instructionMemory;
    private int programCounter;

    public CPU() {

        this.registers = new RegisterFile();
        this.controlUnit = new ControlUnit();
        this.alu = new ALU();
        this.memory = new Memory();
        this.instructionMemory = new InstructionMemory();

        this.pipeline = new PipelineStage[5];

        this.programCounter = 0;

    }

    public RegisterFile getRegisters() {
        return registers;
    }

    public Memory getMemory() {
        return memory;
    }

    public ControlUnit getControlUnit() {
        return controlUnit;
    }

    public ALU getAlu() {
        return alu;
    }

    public PipelineStage[] getPipeline() {
        return pipeline;
    }

    public InstructionMemory getInstructionMemory() {
        return instructionMemory;
    }

    public int getPc() {
        return programCounter;
    }

    public void setPc(int newPc) {
        this.programCounter = newPc;
    }
}