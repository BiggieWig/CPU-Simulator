package core;


import java.util.Arrays;

public class RegisterFile {


    private final int[] registers;
    private static final int NUM_REGISTERS = 32;

    public RegisterFile() {
        this.registers = new int[NUM_REGISTERS];
    }


    public int read(int index) {
        if (index < 0 || index >= NUM_REGISTERS) {
            throw new IndexOutOfBoundsException("Invalid register index: " + index);
        }
        // Register 0 is hardwired to 0
        if (index == 0) {
            return 0;
        }
        return registers[index];
    }

    public void write(int index, int value) {
        if (index < 0 || index >= NUM_REGISTERS) {
            throw new IndexOutOfBoundsException("Invalid register index: " + index);
        }
        // Register 0 cannot be written to
        if (index > 0) {
            registers[index] = value;
        }
    }

    public int[] getAllRegisters() {
        return registers.clone();
    }

    public void reset() {
        Arrays.fill(registers, 0);
    }
}
