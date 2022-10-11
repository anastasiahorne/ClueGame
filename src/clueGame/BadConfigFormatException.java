package clueGame;

public class BadConfigFormatException extends RuntimeException {
	public BadConfigFormatException (String message, Throwable error) {
		super(message, error);
	}
}
