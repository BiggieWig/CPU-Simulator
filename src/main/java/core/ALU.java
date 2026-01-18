package core;

public class ALU {
    public int execute(String aluOp, int operand1, int operand2) {
        switch (aluOp) {
            case "ADD":
                return operand1 + operand2;
            case "SUB":
                return operand1 - operand2;
            case "AND":
                return operand1 & operand2;
            case "OR":
                return operand1 | operand2;
            case "XOR":
                return operand1 ^ operand2;
            default:
                throw new IllegalArgumentException("Unknown ALU operation: " + aluOp);
        }
    }
}