package circuits;

public class ColisionException extends Exception {

	private String msg;
	
	ColisionException() {
	}
	
	ColisionException(String msg) {
		this.msg = msg;
	}

	public String getMessage() {
		return msg;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
