package aero.nettracer.lf.services.exception;

public class NonUniqueBarcodeException extends UpdateException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2953767327714713684L;

	public NonUniqueBarcodeException(){
		super();
	}
	
	public NonUniqueBarcodeException(String message){
		super(message);
	}
	
}
