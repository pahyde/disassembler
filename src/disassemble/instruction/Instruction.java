package disassemble.instruction;

import java.util.List;
import java.util.ArrayList;

public class Instruction {
    private String hex;
    private int opcode;
    private Operation op;
    private List<String> args;

    public Instruction(String hex) {
        int opcode = Integer.parseInt(hex.substring(0, 1), 16);

        this.opcode = opcode;
        this.hex = hex;
        this.args = new ArrayList<>();
        parseInstruction();
    }

    public String displayHex() {
        return this.hex;
    }

    public String displayAsm() {
        return String.format(
            "%s %s",
            this.op,
            String.join(", ", this.args)
        );
    }

    private void parseInstruction() {
        switch (this.opcode) {
            case 0:
            case 1:
                parseALU();
                break;
            case 2:
                parseAddi();
                break;
            case 3:
            case 4:
                parseLoadStore();
                break;
            case 5: 
                parseBr();
                break;
            case 6:
                parseJalr();
                break;
            case 7:
                parseHalt();
                break;
            case 8:
                parseSkp();
                break;
            case 9:
                parseLea();
                break;
            case 10:
            case 11:
            case 12:
                parseInterrupt();
                break;
            case 13:
                parseIN();
                break;
        } 
    }

    private void parseALU() {
        if (this.opcode == 0) {
            this.op = Operation.ADD;
        } else {
            this.op = Operation.NAND;
        }
        Register dr = regx();
        Register sr1 = regy();
        Register sr2 = regz();
        this.args.add(dr.toString());
        this.args.add(sr1.toString());
        this.args.add(sr2.toString());
    }

    private void parseAddi() {
        this.op = Operation.ADDI;
        Register dr = regx();
        Register sr1 = regy();
        int immval20 = leastSignificant20();
        this.args.add(dr.toString());
        this.args.add(sr1.toString());
    }

    private void parseLoadStore() {
        if (this.opcode == 3) {
            this.op = Operation.LW;
        } else {
            this.op = Operation.SW;
        }
        Register reg = regx();
        Register baseReg = regy();
        String offset = Integer.toString(leastSignificant20());
        this.args.add(reg.toString());
        this.args.add(String.format("%s(%s)", offset, baseReg));
    }

    private void parseBr() {
        this.op = Operation.BR;
        String offset = Integer.toString(leastSignificant20());
        this.args.add(offset);
    }

    private void parseJalr() {
        this.op = Operation.JALR;
        Register ra = regx();
        Register at = regy();
        this.args.add(ra.toString());
        this.args.add(at.toString());
    }

    private void parseHalt() {
        this.op = Operation.HALT;
    }

    private void parseSkp() {
        this.op = parseSkpOp();
        Register sr1 = regx();
        Register sr2 = regy();
        this.args.add(sr1.toString());
        this.args.add(sr2.toString());
    }

    private Operation parseSkpOp() {
        int lastNibble = Integer.parseInt(this.hex.substring(7,8), 16);
        String cmpCode = Integer.toBinaryString(lastNibble & 0b111);
        System.out.println(this.hex);
        switch (cmpCode) {
            case "010":
                return Operation.SKPLT;
            case "000":
                return Operation.SKPGT;
            case "100":
                return Operation.SKPLE;
            case "110":
                return Operation.SKPGE;
            case "101":
                return Operation.SKPNE;
            case "001":
                return Operation.SKPEQ;
            default:
                return Operation.NONE;
        }
    }

    private void parseLea() {
        this.op = Operation.LEA;
        Register dr = regx();
        String pcOffset = Integer.toString(leastSignificant20());
        this.args.add(dr.toString());
        this.args.add(pcOffset);
    }

    private void parseInterrupt() {
        if (this.opcode == 10) {
            this.op = Operation.EI;
        } else if (this.opcode == 11) {
            this.op = Operation.DI;
        } else {
            this.op = Operation.RETI;
        }
    }

    private void parseIN() {
        this.op = Operation.IN;
        Register dr = regx();
        String addr20 = Integer.toString(leastSignificant20());
        this.args.add(dr.toString());
        this.args.add(addr20);
    }

    private Register regx() { return reg(1,2); }
    private Register regy() { return reg(2,3); }
    private Register regz() { return reg(7,8); }

    private Register reg(int start, int end) {
        int index = hexSliceToBase10(start, end);
        return Register.values()[index];
    }

    private int leastSignificant20() {
        return hexSliceToBase10(3, 8);
    }

    private int hexSliceToBase10(int start, int end) {
        String hexSlice = this.hex.substring(start, end);
        return Integer.parseInt(hexSlice, 16);
    }
}
