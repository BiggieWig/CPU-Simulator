package core;

public class MemoryStage extends PipelineStage {

    private CPU cpu;

    // OUTPUTS
    private int loadMemoryData = 0;
    private int aluResultPassed = 0;
    private int destReg = 0;
    private boolean regWrite = false;
    private boolean memRead = false;

    public MemoryStage(CPU cpu) {
        this.cpu = cpu;
    }

    @Override
    public void executeInstruction(Instruction instr) {
        this.currentInstruction = instr;
        if (instr == null) {
            regWrite = false;
            return;
        }

        ExecuteStage execute = (ExecuteStage) cpu.getPipeline()[2];

        this.destReg = execute.getDestReg();
        this.regWrite = execute.isRegWrite();
        this.memRead = execute.isMemRead();
        this.aluResultPassed = execute.getAluResult();

        int address = execute.getAluResult();
        int dataToWrite = execute.getWriteData();

        if (execute.isMemWrite()) {
            cpu.getMemory().write(address, dataToWrite);
            System.out.println(" [MEM] Wrote " + dataToWrite + " to address " + address);
        }

        if (this.memRead) {
            loadMemoryData = cpu.getMemory().read(address);
        } else {
            loadMemoryData = 0;
        }
    }

    public int getLoadMemoryData() { return loadMemoryData; }
    public int getAluResultPassed() { return aluResultPassed; }
    public int getDestReg() { return destReg; }
    public boolean isRegWrite() { return regWrite; }
    public boolean isMemRead() { return memRead; }
}