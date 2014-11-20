package pippin;

public class IllegalInstructionModeException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalInstructionModeException() { 
		super(); 
	} 
	
	public IllegalInstructionModeException(String message) {
		super(message);
	}
}
