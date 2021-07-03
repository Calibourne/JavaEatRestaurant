package Model.Exceptions;

import Model.Dish;

@SuppressWarnings("serial")
/**
 * An Exception that is thrown whether there are nocomponents in a dish 
 * @author Eddie Kanevsky
 */
public class NoComponentsException extends Exception{
	
	private Dish dish;
	public NoComponentsException(Dish dish)
	{
		this("The dish " + dish + " does not include any components!");
		this.dish = dish;
	}
	
	public NoComponentsException(String message) {
		super(message);
	}

	public Dish getDish() {
		return dish;
	}
}