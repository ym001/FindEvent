package exception;

public class WebServiceException extends Exception{
	
	public WebServiceException(Exception e){
		System.out.println("webService Exception caused by :");
		e.printStackTrace();
	}
}
