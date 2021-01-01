package com.addService.Exceptions;

import org.springframework.web.bind.annotation.ResponseBody;


	public class DuplicateRequestException extends RuntimeException {
		
		private String message;
		
		public  DuplicateRequestException(String msg) {
			this.message = msg;
			
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}

