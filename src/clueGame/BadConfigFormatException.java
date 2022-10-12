package clueGame;

public class BadConfigFormatException extends RuntimeException {
	public BadConfigFormatException () {
		super("Bad Configuration Format Exception");
	}
	
	public BadConfigFormatException (String message) {
		super(message);
	}
}
