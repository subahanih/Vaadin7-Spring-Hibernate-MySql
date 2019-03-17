package com.ninox.focus.exception;
public class FocusException extends FocusBaseException {
	private static final long serialVersionUID = 1L;
	
	// Constructor to unknown exception
	public FocusException() {
		super(FocusExceptionCodes.UNKN_ERROR);
	}
	
	// Subclass to handle unknown database exception
	public static class DBGenericException extends FocusBaseException {
		private static final long serialVersionUID = 3555714415375055302L;
		
		public DBGenericException() {
			super(FocusExceptionCodes.DB_ERROR);
		}
	}
	
	// Subclass to handle generic validation exception
	public static class ValidationException extends FocusBaseException {
		private static final long serialVersionUID = 8777415230393628334L;
		
		public ValidationException() {
			super(FocusExceptionCodes.INVALID_DATA);
		}
	}
	
	// Subclass to handle generic Save exception
	public static class SaveException extends FocusBaseException {
		private static final long serialVersionUID = -3987707665150073980L;
		
		public SaveException() {
			super(FocusExceptionCodes.SAVE_FAILED);
		}
	}
	public static class SaveSuccessException extends FocusBaseException {
		private static final long serialVersionUID = -3987707665150073980L;
		
		public SaveSuccessException() {
			super(FocusExceptionCodes.SAVE_SUCCESS);
		}
	}
	
	// Subclass to handle no data found error
	public static class NoDataFoundException extends FocusBaseException {
		private static final long serialVersionUID = 4235225697094262603L;
		
		public NoDataFoundException() {
			super(FocusExceptionCodes.NO_DATA_FOUND);
		}
	}
}
