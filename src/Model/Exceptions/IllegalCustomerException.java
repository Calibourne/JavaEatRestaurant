package Model.Exceptions;

@SuppressWarnings("serial")
/**
 * An Exception that is thrown whether a blacklisted customer wants to make an order 
 * @author Eddie Kanevsky
 */
public class IllegalCustomerException extends Exception {
	private static final String message = "The restaurant is in conflict with this customer so this customer does not will order a new order!";
	public IllegalCustomerException()
	{
		super(message);
	}
}