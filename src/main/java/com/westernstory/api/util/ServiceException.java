package com.westernstory.api.util;


/**   
 *  
 * ClassName: ServiceException <br>
 * Description: Service层抛出此异常<br> 	
 *
 */
@SuppressWarnings("serial")
public class ServiceException extends Exception {

	/**
	 * 
	 */
	public ServiceException(){
		super();
	}
	/**
	 * 
	 * @param msg
	 */
	public ServiceException(String msg){
		super(msg);
	}
	/**
	 * 
	 * @param msg
	 * @param cause
	 */
	public ServiceException(String msg, Throwable cause) {
		super(msg, cause);
	}
	/**
	 * 
	 * @param cause
	 */
	public ServiceException(Throwable cause) {
		super(cause);
	}
}

