package core;


import java.util.Arrays;

public class Memory {

    private final int[] data;
    private static final int MEMORY_SIZE = 256;

    public Memory() {
        this.data = new int[MEMORY_SIZE];
    }

    public int read(int address) {
        if (address < 0 || address >= MEMORY_SIZE) {
            throw new IndexOutOfBoundsException("Invalid memory address: " + address);
        }
        return data[address];
    }

    public void write(int address, int value) {
        if (address < 0 || address >= MEMORY_SIZE) {
            throw new IndexOutOfBoundsException("Invalid memory address: " + address);
        }
        data[address] = value;
    }

    public int[] getAllMemory() {
        return data.clone();
    }
    public void reset() {
        Arrays.fill(data, 0);
    }
}