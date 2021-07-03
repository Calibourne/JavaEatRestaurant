package Model.Exceptions;

@SuppressWarnings("serial")
/**
 * An Exception that is thrown whether a regular delivery should be converted to express delivery 
 * @author Eddie Kanevsky
 */
public class ConvertToExpressException extends Exception {
	private static final String message = "This regular delivery contains one order, please replace it with an express delivery";
	public ConvertToExpressException()
	{
		super(message);
	}
}