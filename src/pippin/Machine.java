package pippin;

import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;
import java.lang.Runtime;

public class Machine extends Observable {
    public final Map<String, Instruction> INSTRUCTION_MAP = new TreeMap<>();
    private Memory memory = new Memory();
    private Processor cpu = new Processor();

// ADD DELEGATE METHODS FOR int setData, int getData, and int[] getData from memory
// all the setters and getters of cpu, and the incrementCounter
    public int getAccumulator() {
		return cpu.getAccumulator();
	}
	public void setAccumulator(int accumulator) {
		setAccumulator(accumulator);
	}
	public int getProgramCounter() {
		return cpu.getProgramCounter();
	}
	public void setProgramCounter(int programCounter) {
		cpu.setProgramCounter(programCounter);
	}
    public void incrementCounter() {
    	cpu.incrementCounter();
    }
	
    public void setData(int index, int value) {
    	memory.setData(index, value);
    }
    
    public int getData(int index) {
    	return memory.getData(index);
    }
    
    public int[] getData() {
    	return memory.getData();
    }
    
    public Instruction get(String code) {
    	return INSTRUCTION_MAP.get(code);
    }
    
// Here are two lambda expressions for instructions
    public Machine() {
        INSTRUCTION_MAP.put("ADD",(int arg, boolean immediate, boolean indirect) -> {
            if (immediate) {
                cpu.setAccumulator(cpu.getAccumulator() + arg);
            } else if (indirect) {
                int arg1 = memory.getData(arg);
                cpu.setAccumulator(cpu.getAccumulator() + memory.getData(arg1));                    
            } else {
                cpu.setAccumulator(cpu.getAccumulator() + memory.getData(arg));         
            }
            cpu.incrementCounter();
        });
        INSTRUCTION_MAP.put("CMPZ",(int arg, boolean immediate, boolean indirect) -> {
            int operand = memory.getData(arg);
            if (immediate) {
                throw new IllegalInstructionModeException("attempt to execute indirect AND");
            } else if (indirect) {
                throw new IllegalInstructionModeException("attempt to execute indirect AND");
            } 
            if(operand == 0) {
                cpu.setAccumulator(1);          
            } else {
                cpu.setAccumulator(0);          
            }
            cpu.incrementCounter();
        });
        
        INSTRUCTION_MAP.put("STO",(int arg, boolean immediate, boolean indirect) -> {
            int operand = memory.getData(arg);
            if (immediate) {
                throw new IllegalInstructionModeException("attempt to execute indirect STO");
            } else if (indirect) {
                throw new IllegalInstructionModeException("attempt to execute indirect AND");
            } else {
            	memory.setData(operand, cpu.getAccumulator());
            }
            cpu.incrementCounter();
        });
        
        INSTRUCTION_MAP.put("NOP",(int arg, boolean immediate, boolean indirect) -> {
            if (immediate) {
                throw new IllegalInstructionModeException("attempt to execute immediate NOP");
            } else if (indirect) {
                throw new IllegalInstructionModeException("attempt to execute indirect NOP");
            } 
            cpu.incrementCounter();
        });

        INSTRUCTION_MAP.put("HALT",(int arg, boolean immediate, boolean indirect) -> {
            if (immediate) {
                throw new IllegalInstructionModeException("attempt to execute immediate HALT");
            } else if (indirect) {
                throw new IllegalInstructionModeException("attempt to execute indirect HALT");
            } 
            Runtime.getRuntime().halt(0);
            cpu.incrementCounter();
        });
    }
 }