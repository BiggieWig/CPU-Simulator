package core;

public class WriteBackStage extends PipelineStage {

    private CPU cpu;

    public WriteBackStage(CPU cpu) {
        this.cpu = cpu;
    }

    @Override
    public void executeInstruction(Instruction instr) {
        this.currentInstruction = instr;
        if (instr == null) return;

        MemoryStage memStage = (MemoryStage) cpu.getPipeline()[3];


        if (memStage.isRegWrite()) {

            int resultToWrite;

            if (memStage.isMemRead()) {
                resultToWrite = memStage.getLoadMemoryData();
            } else {
                resultToWrite = memStage.getAluResultPassed();
            }


            int destRegIndex = memStage.getDestReg();

            cpu.getRegisters().write(destRegIndex, resultToWrite);

            System.out.println(" [WB] Wrote " + resultToWrite + " to R" + destRegIndex);
        }
    }
}