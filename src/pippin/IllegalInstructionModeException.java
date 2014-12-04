package pippin;

public class IllegalInstructionModeException extends RuntimeException {
	/**
	 * test 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalInstructionModeException() { 
		super(); 
	} 
	
	public IllegalInstructionModeException(String message) {
		super(message);
	}
}
