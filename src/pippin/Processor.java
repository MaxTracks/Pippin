package pippin;
public class Processor  {
	//Test commit by Tim.
    private int accumulator;
    private int programCounter;
    public int getAccumulator() {
		return accumulator;
	}
	public void setAccumulator(int accumulator) {
		this.accumulator = accumulator;
	}
	public int getProgramCounter() {
		return programCounter;
	}
	public void setProgramCounter(int programCounter) {
		this.programCounter = programCounter;
	}
	public void incrementCounter() {
        programCounter++;
    }
}