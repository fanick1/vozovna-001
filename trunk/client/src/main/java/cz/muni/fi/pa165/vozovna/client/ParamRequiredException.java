package cz.muni.fi.pa165.vozovna.client;

public class ParamRequiredException extends RuntimeException {

	private static final long serialVersionUID = -7019105658779864656L;

	public ParamRequiredException() {
	}

	public ParamRequiredException(String arg0) {
		super(arg0);
	}

	public ParamRequiredException(Throwable arg0) {
		super(arg0);
	}

	public ParamRequiredException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	public ParamRequiredException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
