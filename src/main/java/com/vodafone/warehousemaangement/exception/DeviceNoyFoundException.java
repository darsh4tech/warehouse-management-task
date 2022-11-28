package com.vodafone.warehousemaangement.exception;

public class DeviceNoyFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public DeviceNoyFoundException(String errMSG) {
		super(errMSG);
	}
	
	public DeviceNoyFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
	
}
