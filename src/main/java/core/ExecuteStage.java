package core;

public class ExecuteStage extends PipelineStage {

    private CPU cpu;

    private int aluResult = 0;
    private int writeData = 0;
    private int destReg = 0;
    private boolean regWrite = false;
    private boolean memRead = false;
    private boolean memWrite = false;

    public ExecuteStage(CPU cpu) {
        this.cpu = cpu;
    }

    @Override
    public void executeInstruction(Instruction instr) {
        this.currentInstruction = instr;

        if (instr == null) {
            regWrite = false;
            memWrite = false;
            memRead = false;
            aluResult = 0;
            writeData = 0;
            return;
        }

        DecodeStage decode = (DecodeStage) cpu.getPipeline()[1];
        ControlUnit cu = cpu.getControlUnit();

        this.destReg = decode.getRdIndex();
        this.regWrite = cu.isRegWrite();
        this.memRead = cu.isMemRead();
        this.memWrite = cu.isMemWrite();


        String op = cu.getAluOp();

        if (op != null && !op.isEmpty()) {
            int val1 = decode.getRsValue();
            int val2 = decode.getRtValue();
            aluResult = cpu.getAlu().execute(op, val1, val2);
        } else {
            // If op is empty (like for NOP), result is 0
            aluResult = 0;
        }

        if (instr.getOpcode().equals("SW")) {
            writeData = decode.getStoreValue();
        } else {
            writeData = decode.getRtValue();
        }
    }


    public int getAluResult() { return aluResult; }
    public int getWriteData() { return writeData; }
    public int getDestReg() { return destReg; }
    public boolean isRegWrite() { return regWrite; }
    public boolean isMemRead() { return memRead; }
    public boolean isMemWrite() { return memWrite; }
}