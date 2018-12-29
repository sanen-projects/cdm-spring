package online.sanen.cdm.springSupport;

/**
 * 
 * <pre>
 *
 * @author online.sanen
 * Date:2017/12/20
 * Time:14：16:11
 * </pre>
 */
public class GeneralOprationException extends RuntimeException{

	private static final long serialVersionUID = 4133396628802802852L;

	public GeneralOprationException(String message, NullPointerException e) {
		super(message, e);
	}
	
	

}
