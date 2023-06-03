package disassemble.instruction;

public enum Register {
    ZERO,
    AT,
    V0,
    A0,
    A1,
    A2,
    T0,
    T1,
    T2,
    S0,
    S1,
    S2,
    K0,
    SP,
    FP,
    RA;

    @Override
    public String toString() {
        return "$" + name().toLowerCase();
    }
}
