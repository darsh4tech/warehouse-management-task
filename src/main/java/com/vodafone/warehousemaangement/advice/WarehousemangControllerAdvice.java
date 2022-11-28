package com.vodafone.warehousemaangement.advice;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.vodafone.warehousemaangement.exception.DeviceNoyFoundException;

@ControllerAdvice
public class WarehousemangControllerAdvice {

	@ExceptionHandler(Exception.class)
	ResponseEntity<ApiError> handleStatusException(Exception ex, WebRequest request) {
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ApiError.builder().status(HttpStatus.INTERNAL_SERVER_ERROR)
						.message(ex.getMessage())
						.timestamp(Instant.now())
						.build());
	}
	
//	@ExceptionHandler(DuplicateFoundException.class)
//	ResponseEntity<ApiError> handleDuplicateFoundException(DuplicateFoundException ex, WebRequest request) {
//		return ResponseEntity
//				.status(HttpStatus.INTERNAL_SERVER_ERROR)
//				.body(ApiError.builder().status(HttpStatus.BAD_REQUEST)
//						.message(ex.getMessage())
//						.timestamp(Instant.now())
//						.build());
//	}
//
//	@ExceptionHandler(AppointmentAddFailedException.class)
//	ResponseEntity<ApiError> handleAppointmentAddFailedException(AppointmentAddFailedException ex, WebRequest request) {
//		return ResponseEntity
//				.status(HttpStatus.INTERNAL_SERVER_ERROR)
//				.body(ApiError.builder().status(HttpStatus.BAD_REQUEST)
//						.message(ex.getMessage())
//						.timestamp(Instant.now())
//						.build());
//	}

	@ExceptionHandler(DeviceNoyFoundException.class)
	ResponseEntity<ApiError> handlePatientNoyFoundException(DeviceNoyFoundException ex, WebRequest request) {
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ApiError.builder().status(HttpStatus.BAD_REQUEST)
						.message(ex.getMessage())
						.timestamp(Instant.now())
						.build());
	}

}
