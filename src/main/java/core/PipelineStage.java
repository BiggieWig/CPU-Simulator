package core;

public abstract class PipelineStage {

    public PipelineStage nextStage;

    protected Instruction currentInstruction;

    public abstract void executeInstruction(Instruction instr);

    public void setNextStage(PipelineStage nextStage) {
        this.nextStage = nextStage;
    }

    public Instruction getCurrentInstruction() {
        return currentInstruction;
    }
    public void reset() {
        this.currentInstruction = null;
    }
}