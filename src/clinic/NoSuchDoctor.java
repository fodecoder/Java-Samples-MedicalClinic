package clinic;

public class NoSuchDoctor extends Exception {
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "No Such Doctor Exception";
	}
	
}
