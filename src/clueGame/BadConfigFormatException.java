package clueGame;

public class BadConfigFormatException extends RuntimeException {
	public BadConfigFormatException () {
		super();
	}
	
	public BadConfigFormatException (String message) {
		super(message);
	}
}
