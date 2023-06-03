# Hex Disassembler (Java)

Trying to write more java so I can get a job. 
I needed a really simple disassembler for an ISA called LC-902 (An educational ISA you might be familiar with). 
I would pretty much always do this in python but today we're doing it in java. 

I'm using it for ad hoc debugging purposes. It takes a filepath to an LC-902 hex file as command line input and writes a disassembly.txt file in the current directory as output.

**To compile:**

```bash
javac disassemble/Disassembler.java
```


**To run:**

```bash
java disassemble.Disassembler path_to_hex_file
```

