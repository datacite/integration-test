package org.datacite.test.model;


/**
 * Encapsulates and HTTPResponse.
 * @author PaluchM
 *
 */
public class HTTPResponse {

	private int responseCode = -1;
	private String responseBody = null;
		
	public HTTPResponse(){}
	
	public HTTPResponse(int responseCode,String responseMessage){
		this.setResponseCode(responseCode);
		this.setResponseBody(responseMessage);
	}


	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}


	public int getResponseCode() {
		return responseCode;
	}


	public void setResponseBody(String responseMessage) {
		this.responseBody = responseMessage;
	}


	public String getResponseBody() {
		return responseBody;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();		
		sb.append("[");
		sb.append("Code: "+this.getResponseCode());
		sb.append(" | ");
		sb.append("Body: "+this.getResponseBody());
		sb.append("]");
		return sb.toString();
	}
	
}
