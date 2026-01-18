package core;

public class FetchStage extends PipelineStage {

    private CPU cpu;

    public FetchStage(CPU cpu) {
        this.cpu = cpu;
    }

    @Override
    public void executeInstruction(Instruction instr) {
        this.currentInstruction = instr;
        if (instr != null) {
        }
    }
}