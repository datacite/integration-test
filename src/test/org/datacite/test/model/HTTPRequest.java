package org.datacite.test.model;

/**
 * Encapsulates and HTTPRequest and its parameters.
 * @author PaluchM
 *
 */
public class HTTPRequest {

	private String address = null;
	private String body = null;
	private String requestMethod = null;
	private boolean useCaches = false;
	private boolean doInput = true;
	private boolean doOutput = true;
	private String accept = "application/xml";
	private String contentType = "application/xml";
	private String authorization = "Basic ";
	private String username = null;
	private String password = null;

	/** Possible request methods*/
	public static enum Method{
		GET,
		POST,
		PUT,
		DELETE
	}

	public HTTPRequest(String address) {
		setAddress(address);
		setMethod(Method.GET);
	}

	/**
	 * Sets the request method
	 * @param requestMethod
	 */
	public void setMethod(HTTPRequest.Method requestMethod) {
		this.requestMethod = requestMethod.name();
	}

	public String getMethod() {
		return requestMethod;
	}

	public void setBody(String reqBody) {
		this.body = reqBody;
	}

	public String getBody() {
		return body;
	}

	public void setUseCaches(boolean useCaches) {
		this.useCaches = useCaches;
	}

	public boolean useCaches() {
		return useCaches;
	}

	public void setDoInput(boolean doInput) {
		this.doInput = doInput;
	}

	public boolean doInput() {
		return doInput;
	}

	public void setDoOutput(boolean doOutput) {
		this.doOutput = doOutput;
	}

	public boolean doOutput() {
		return doOutput;
	}

	public void setAccept(String reqProp_Accept) {
		this.accept = reqProp_Accept;
	}

	public String getAccept() {
		return accept;
	}

	public void setContentType(String reqProp_ContentType) {
		this.contentType = reqProp_ContentType;
	}

	public String getContentType() {
		return contentType;
	}

	public void setAuthorization(String reqProp_Authorization) {
		this.authorization = reqProp_Authorization;
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setAddress(String apiAddress) {
		this.address = apiAddress;
	}

	public String getAddress() {
		return address;
	}

}