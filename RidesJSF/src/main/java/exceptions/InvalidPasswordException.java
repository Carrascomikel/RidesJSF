package exceptions;
public class InvalidPasswordException extends Exception {
 private static final long serialVersionUID = 1L;
 
 public InvalidPasswordException()
  {
    super();
  }
  /**This exception is triggered if the question already exists 
  *@param s String of the exception
  */
  public InvalidPasswordException(String s)
  {
    super(s);
  }
}