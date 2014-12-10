package pippin;

import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;
import java.lang.Runtime;

public class Machine extends Observable {
    public final Map<String, Instruction> INSTRUCTION_MAP = new TreeMap<>();
    private Memory memory = new Memory();
    private Processor cpu = new Processor();
    private Code code = new Code();
    private States state;

// ADD DELEGATE METHODS FOR int setData, int getData, and int[] getData from memory
// all the setters and getters of cpu, and the incrementCounter
    public int getAccumulator() {
		return cpu.getAccumulator();
	}
	public void setAccumulator(int accumulator) {
		cpu.setAccumulator(accumulator);
	}
	public int getProgramCounter() {
		return cpu.getProgramCounter();
	}
	
	public States getState(){
		return state;
	}
	
	public Memory getMemory(){
		return memory;
	}
	
	public Code getCode(){
		return code;
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
    
    public Processor getCpu(){
    	return cpu;
    }
    
    public int[] getData() {
    	return memory.getData();
    }
    
    
    public Instruction get(String code) {
    	return INSTRUCTION_MAP.get(code);
    }
    
    public void step(){ }
    public void clearAll(){ }
    public void reload(){ }
    public void toggleAutoStep(){ }
    
// Here are two lambda expressions for instructions
    public Machine(){
    	INSTRUCTION_MAP.put("0x3",(int arg, boolean immediate, boolean indirect) -> {
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
    	
    	/**
    	 * @author Tim
    	 **/
    	INSTRUCTION_MAP.put("0x4",(int arg, boolean immediate, boolean indirect) -> {
    	    if (immediate) {
    	        cpu.setAccumulator(cpu.getAccumulator() - arg);
    	    } else if (indirect) {
    	        int arg1 = memory.getData(arg);
    	        cpu.setAccumulator(cpu.getAccumulator() - memory.getData(arg1));                    
    	    } else {
    	        cpu.setAccumulator(cpu.getAccumulator() - memory.getData(arg));         
    	    }
    	    cpu.incrementCounter();
    	});

    	/**
    	 * @author Tim
    	 **/
    	INSTRUCTION_MAP.put("0x5",(int arg, boolean immediate, boolean indirect) -> {
    	    if (immediate) {
    	        cpu.setAccumulator(cpu.getAccumulator() * arg);
    	    } else if (indirect) {
    	        int arg1 = memory.getData(arg);
    	        cpu.setAccumulator(cpu.getAccumulator() * memory.getData(arg1));                    
    	    } else {
    	        cpu.setAccumulator(cpu.getAccumulator() * memory.getData(arg));         
    	    }
    	    cpu.incrementCounter();
    	});
    	/**
    	 * @author Tim
    	 **/
    	INSTRUCTION_MAP.put("0x6",(int arg, boolean immediate, boolean indirect) -> {
    	    if (immediate) {
    	        if(arg == 0){throw new DivideByZeroException("attempt to divide by zero");}
    	        else{cpu.setAccumulator(cpu.getAccumulator() / arg);}
    	    } else if (indirect) {
    	        int arg1 = memory.getData(arg);
    	        if(memory.getData(arg1) == 0){throw new DivideByZeroException("attempt to divide by zero");}
    	        else{cpu.setAccumulator(cpu.getAccumulator() / memory.getData(arg1));}                  
    	    } else {
    	    	if(memory.getData(arg) == 0){throw new DivideByZeroException("attempt to divide by zero");}
    	        else{cpu.setAccumulator(cpu.getAccumulator() / memory.getData(arg));}        
    	    }
    	    cpu.incrementCounter();
    	});
       
        INSTRUCTION_MAP.put("0x9",(int arg, boolean immediate, boolean indirect) -> {
            int operand = memory.getData(arg);
            if (immediate) {
                throw new IllegalInstructionModeException("attempt to execute indirect CMPZ");
            } else if (indirect) {
                throw new IllegalInstructionModeException("attempt to execute indirect CMPZ");
            } 
            if(operand == 0) {
                cpu.setAccumulator(1);          
            } else {
                cpu.setAccumulator(0);          
            }
            cpu.incrementCounter();
        });
        
        INSTRUCTION_MAP.put("0x2",(int arg, boolean immediate, boolean indirect) -> {
            int operand = memory.getData(arg);
            if (immediate) {
                throw new IllegalInstructionModeException("attempt to execute indirect STO");
            } else if (indirect) {
            	memory.setData(operand, cpu.getAccumulator());
            } else {
            	memory.setData(arg, cpu.getAccumulator());
            }
            cpu.incrementCounter();
        });
       
        INSTRUCTION_MAP.put("0x0",(int arg, boolean immediate, boolean indirect) -> {
            if (immediate) {
                throw new IllegalInstructionModeException("attempt to execute immediate NOP");
            } else if (indirect) {
                throw new IllegalInstructionModeException("attempt to execute indirect NOP");
            } 
            cpu.incrementCounter();
        });

        INSTRUCTION_MAP.put("0xF",(int arg, boolean immediate, boolean indirect) -> {
            if (immediate) {
                throw new IllegalInstructionModeException("attempt to execute immediate HALT");
            } else if (indirect) {
                throw new IllegalInstructionModeException("attempt to execute indirect HALT");
            } 
            Runtime.getRuntime().halt(0);
            cpu.incrementCounter();
        });
        /**
         * @author Alex
         **/
        INSTRUCTION_MAP.put("0x8",(int arg, boolean immediate, boolean indirect) -> {
            if (immediate) {
                throw new IllegalInstructionModeException("attempt to execute indirect NOT");
            } else if (indirect) {
                throw new IllegalInstructionModeException("attempt to execute indirect NOT");
            } 
            if(cpu.getAccumulator() == 0) {
                cpu.setAccumulator(1);          
            } else {
                cpu.setAccumulator(0);          
            }
            cpu.incrementCounter();
        });
        
        /**
         * @author Alex
         **/
        INSTRUCTION_MAP.put("0xC",(int arg, boolean immediate, boolean indirect) -> {
            if (immediate) {
            	throw new IllegalInstructionModeException("attempt to execute immediate JMPZ");
            } else if (indirect) {
            	if(cpu.getAccumulator() == 0){
            		int arg1 = memory.getData(arg);
            		cpu.setProgramCounter(arg1);
            	}
            	else cpu.incrementCounter();
            } else {
                if(cpu.getAccumulator() == 0){
                	cpu.setProgramCounter(arg);
                }
                else cpu.incrementCounter();
            }
        });
        /**
         * @author Alex
         **/
        INSTRUCTION_MAP.put("0xB",(int arg, boolean immediate, boolean indirect) -> {
            if (immediate) {
            	throw new IllegalInstructionModeException("attempt to execute immediate JUMP");
            } else if (indirect) {
            	int arg1 = memory.getData(arg);
        		cpu.setProgramCounter(arg1);
            } else {
                cpu.setProgramCounter(arg);
            }
        });
        /**
         * @author Alex
         **/
        INSTRUCTION_MAP.put("0x1",(int arg, boolean immediate, boolean indirect) -> {
            if (immediate) {
            	cpu.setAccumulator(arg);
            } else if (indirect) {
            	int arg1 = memory.getData(arg);
            	int arg2 = memory.getData(arg1);
        		cpu.setAccumulator(arg2);
            } else {
            	int arg1 = memory.getData(arg);
        		cpu.setAccumulator(arg1);
            }
            cpu.incrementCounter();
        });
        /**
         * @author Alex
         **/
        INSTRUCTION_MAP.put("0x7",(int arg, boolean immediate, boolean indirect) -> {
            if (immediate) {
            	if(arg != 0 && cpu.getAccumulator()!=0){
            		cpu.setAccumulator(1);
            	} else cpu.setAccumulator(0);
            } else if (indirect) {
            	throw new IllegalInstructionModeException("attempt to execute indirect JUMP");
            } else {
            	int arg1 = memory.getData(arg);
            	if(arg1 != 0 && cpu.getAccumulator()!=0){
            		cpu.setAccumulator(1);
            	} else cpu.setAccumulator(0);
            }
            cpu.incrementCounter();
        });
        /**
         * @author Alex
         **/
        INSTRUCTION_MAP.put("0xA",(int arg, boolean immediate, boolean indirect) -> {
            int operand = memory.getData(arg);
            if (immediate) {
                throw new IllegalInstructionModeException("attempt to execute indirect CMPZ");
            } else if (indirect) {
                throw new IllegalInstructionModeException("attempt to execute indirect CMPZ");
            } 
            if(operand < 0) {
                cpu.setAccumulator(1);          
            } else {
                cpu.setAccumulator(0);          
            }
            cpu.incrementCounter();
        });
    }
 }
