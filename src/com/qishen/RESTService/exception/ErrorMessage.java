package com.qishen.RESTService.exception;

public class ErrorMessage {
	private String verb;
	private String url;
	private String message;
	
	public ErrorMessage(String verb, String url, String message) {
		this.verb = verb;
		this.url = url;
		this.message = message;
	}
	
	public void setVerb(String verb) {
		this.verb = verb;
	}
	
	public String getVerb() {
		return verb;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
