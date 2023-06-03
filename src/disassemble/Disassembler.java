package disassemble;

import java.util.List;
import java.io.IOException;

public class Disassembler {
    private String inputFilePath;
    private String outputFilePath;
    private Decoder decoder;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide a file path for the hex file");
            System.exit(1);
        }

        String inputFilePath = args[0];
        String outputFilePath = args.length == 2 ? args[1] : "dissassembled.txt";

        try {
            new Disassembler(inputFilePath, outputFilePath, new Decoder()).disassemble();
        } catch (IOException e) {
            System.err.println("An error occurred while disassembling the file: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    Disassembler(String inputFilePath, String outputFilePath, Decoder decoder) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.decoder = decoder;
    }

    void disassemble() throws IOException {
        List<String> hex = FileHandler.readLines(this.inputFilePath);
        List<String> assembly = decoder.decode(hex);
        FileHandler.writeLines(this.outputFilePath, assembly);
    }
}
