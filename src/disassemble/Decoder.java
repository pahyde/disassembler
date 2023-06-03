package disassemble;

import disassemble.instruction.Instruction;

import java.util.List;
import java.util.stream.Collectors;


public class Decoder {
    public List<String> decode(List<String> hex) {
        return hex.stream()
            .map(hexStr -> {
                Instruction instruction = new Instruction(hexStr);
                return String.format(
                    "%s         %s", 
                    instruction.displayHex(), 
                    instruction.displayAsm()
                );
            })
            .collect(Collectors.toList());
    }
}
