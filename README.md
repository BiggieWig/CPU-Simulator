# MIPS 5-Stage Pipeline Simulator

![Java](https://img.shields.io/badge/Language-Java-orange)
![JavaFX](https://img.shields.io/badge/GUI-JavaFX-blue)
![Architecture](https://img.shields.io/badge/Architecture-MIPS_32--bit-green)

## 📌 Overview
This project is a fully functional **CPU Simulator** that models a 32-bit **MIPS 5-Stage Pipeline** architecture. [cite_start]It is designed to visualize the internal execution of assembly instructions, helping users understand complex concepts like data latching, hazard detection, and control flow handling[cite: 32].

[cite_start]The simulator features a responsive **Graphical User Interface (GUI)** built with JavaFX, which allows for real-time inspection of the pipeline stages, registers, and data memory[cite: 42].

## 🚀 Key Features
* [cite_start]**5-Stage Pipeline:** Visualizes instructions moving through Fetch (IF), Decode (ID), Execute (EX), Memory (MEM), and Write-Back (WB)[cite: 33].
* [cite_start]**Hazard Detection Unit:** Automatically detects **Read-After-Write (RAW)** data hazards (specifically Load-Use hazards) and resolves them by inserting "bubbles" (Stalls)[cite: 35].
* **Control Hazard Handling:** Implements **Early Branch Resolution** in the Decode stage. [cite_start]When a branch (`BEQ`) is taken, the simulator automatically **flushes** the Fetch stage to maintain program integrity[cite: 41].
* [cite_start]**Pipeline Registers View:** A dedicated panel displaying the "latched" data between stages (e.g., `IF/ID`, `ID/EX`), demystifying how data persists across clock cycles[cite: 42].
* [cite_start]**Animation Engine:** Supports automatic step-by-step execution using JavaFX Timelines (1-second delay per cycle)[cite: 42].
* [cite_start]**Memory & Register Inspection:** Real-time hexadecimal dump of Data Memory and General Purpose Registers (GPR)[cite: 42].

## 🛠️ Architecture
The simulator mimics a standard MIPS datapath with the following stages:
1.  [cite_start]**IF (Instruction Fetch):** Retrieves instruction from memory at the current PC[cite: 33].
2.  [cite_start]**ID (Instruction Decode):** Decodes opcode, reads registers, and checks branch conditions[cite: 33].
3.  [cite_start]**EX (Execute):** Performs ALU calculations or address computation[cite: 33].
4.  [cite_start]**MEM (Memory):** Accesses data memory for Load/Store operations[cite: 33].
5.  [cite_start]**WB (Write Back):** Writes results back to the Register File[cite: 33].

### Hazard Resolution Strategies
* [cite_start]**Data Hazards:** Handled via **Stalling** (freezing the PC and inserting a NOP in the Execute stage)[cite: 45].
* [cite_start]**Control Hazards:** Handled via **Flushing** (clearing the IF/ID pipeline register) when a branch is taken[cite: 41].

## 💻 Supported Instruction Set
[cite_start]The simulator supports a subset of the MIPS32 instruction set, sufficient to run complex logic and arithmetic programs[cite: 37]:

| Type | Instructions |
| :--- | :--- |
| **Arithmetic** | `ADD`, `SUB`, `ADDI` |
| **Logic** | `AND`, `OR`, `XOR` |
| **Memory** | `LW` (Load Word), `SW` (Store Word) |
| **Control Flow** | `BEQ` (Branch if Equal) |

## 🧪 Validation: "Secure Data Packet" Program
The project includes a comprehensive validation program hardcoded into the simulator to demonstrate its capabilities. This "Secure Data Packet" processor performs the following:
1.  **Looping:** Calculates the sum of numbers $N=1$ to $6$ using `BEQ` and `SUB`.
2.  **Encryption:** Encrypts the sum using `XOR` with a key ($10$).
3.  **Decryption:** Decrypts it back to verify integrity.
4.  **Packaging:** Adds a "Verified" status flag ($128$) using `OR`.
5.  **Storage:** Saves the final packet ($149$) to memory.

**Expected Results (visible in Memory View):**
* Address `104`: **21** (Raw Sum)
* [cite_start]Address `108`: **149** (Final Packet)[cite: 49].

## 🔧 Installation & Usage

### Prerequisites
* Java Development Kit (JDK) 17 or higher.
* JavaFX SDK (if not bundled with your JDK).
* IntelliJ IDEA or Eclipse (recommended).

### Running the Project
1.  Clone the repository:
    ```bash
    git clone [https://github.com/yourusername/mips-cpu-simulator.git](https://github.com/yourusername/mips-cpu-simulator.git)
    ```
2.  Open the project in your IDE.
3.  Locate the main class: `ui.SimulatorFrame`.
4.  Run the application.

### Controls
* **Start:** Begins automatic execution (1 step/sec).
* **Step:** Executes a single clock cycle.
* **Stop:** Pauses the automatic execution.
* [cite_start]**Reset:** Clears all registers, memory, and pipeline stages, and reloads the program[cite: 42].

## 🔮 Future Work
* Implementation of a **Forwarding Unit** to minimize stalls.
* Addition of `J` (Jump) and `JAL` (Jump and Link) instructions.
* [cite_start]Implementation of L1 Cache simulation[cite: 50].

## 👨‍💻 Author
**Berchisan Bogdan-Aurel**
* Group 30434
* Technical University of Cluj-Napoca
* [cite_start]Date: January 14, 2026 [cite: 32]

---
*This project was developed for the Computer Architecture course.*
