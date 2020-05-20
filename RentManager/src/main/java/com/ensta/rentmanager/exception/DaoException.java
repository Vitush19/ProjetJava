package com.ensta.rentmanager.exception;

public class DaoException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DaoException() {
		super();
	}
	
	public DaoException(String msg) {
		super(msg);
	}
}
