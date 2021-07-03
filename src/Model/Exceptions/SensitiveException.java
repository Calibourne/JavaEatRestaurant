package Model.Exceptions;

@SuppressWarnings("serial")
/**
 * An Exception that is thrown whether a sensitive customer tries to order a dish with his allergen
 * @author Eddie Kanevsky
 */
public class SensitiveException extends Exception{
	
	public SensitiveException(String customerName, String dishName)
	{
		this("Customer " + customerName + " is sensitive to one of the components in the dish " + dishName + "!");
	}
	
	private SensitiveException(String message) {
		super(message);
	}
}