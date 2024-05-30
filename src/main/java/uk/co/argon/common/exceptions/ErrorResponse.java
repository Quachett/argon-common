package uk.co.argon.common.exceptions;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
	"statusCode",
	"message"
})
@XmlRootElement(name = "errorResponse")
public class ErrorResponse {
	@XmlElement
	private int statusCode;
	@XmlElement
	private String message;
	
	public ErrorResponse() {
		
	}
	
	public ErrorResponse(int statusCode,String message) {
		this.statusCode=statusCode;
		this.message=message;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
