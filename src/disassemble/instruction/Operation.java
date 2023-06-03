package disassemble.instruction;

public enum Operation {
    ADD,
    NAND,
    ADDI,
    LW,
    SW,
    BR,
    JALR,
    HALT,
    SKPLT,
    SKPGT,
    SKPLE,
    SKPGE,
    SKPNE,
    SKPEQ,
    LEA,
    EI,
    DI,
    RETI,
    IN,
    NONE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
