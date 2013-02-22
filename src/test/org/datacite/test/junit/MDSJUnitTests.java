package org.datacite.test.junit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import junit.framework.Assert;

import org.datacite.test.Constants;
import org.datacite.test.client.HTTPClient;
import org.datacite.test.model.HTTPRequest;
import org.datacite.test.model.HTTPResponse;
import org.datacite.test.model.Metadata;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * JUnit tests for DOI and METADATA resources of MDS RESTful service.
 * @author PaluchM
 *
 */
public class MDSJUnitTests {
	
	private static final String url = "http://mds.datacite.org/static/apidoc";
	private static final String testMode = "?testMode=true";
	
	private static String doiTestSuffix;
	private static String username;
	private static String password;
	private static String metadataAddress;
	private static String doiAddress;
	private static String doiTestPrefix;
	private static String unknownTestDOIUrl;
	
	private static HTTPClient client;		
	private static Metadata metadata;
	private static Properties properties;
	
	
	@BeforeClass
	public static void oneTimeSetUp() throws IOException{
		client = new HTTPClient();
		doiTestSuffix = "/"+System.currentTimeMillis();
		
		properties = new Properties();
		properties.load(new FileInputStream(new File(Constants.Property.FILE)));
		username = properties.getProperty(Constants.Property.MDS_USERNAME);
		password = properties.getProperty(Constants.Property.MDS_PASSWORD);
		metadataAddress = properties.getProperty(Constants.Property.MDS_METADATA_URL);
		doiAddress = properties.getProperty(Constants.Property.MDS_DOI_URL);
		doiTestPrefix = properties.getProperty(Constants.Property.TEST_PREFIX);
		unknownTestDOIUrl = properties.getProperty(Constants.Property.UNKNOWN_TEST_URL);
				
		metadata = new Metadata(properties.getProperty(Constants.Property.XMLNS_ATTRIBUTE),properties.getProperty(Constants.Property.SCHEMALOCATION_ATTRIBUTE));
		metadata.addCreator("JUnitTest");
		metadata.addTitle("JUnitTest");
		metadata.setDoi(doiTestPrefix+doiTestSuffix);
		metadata.setPublisher("JUnitTester");
		metadata.setPubYear("9999");
		
	}
	
	@AfterClass
	public static void oneTimeTearDown(){
		client = null;
		metadata = null;
		properties = null;
	}
	
	@Before
	public void setUp(){
		
	}
	
	@After 
	public void tearDown(){
	}
	
	/**
	 * TESTS
	 */	

	@Test
	public void test1GetUnknownTestDOI() throws Exception {
		String doi = doiTestPrefix+doiTestSuffix;
		
		String address = doiAddress;
		address += "/"+doi;
		
		HTTPRequest request = new HTTPRequest(address);
		request.setUsername(username);
		request.setPassword(password);
		request.setMethod(HTTPRequest.Method.GET);
		
		HTTPResponse response = client.makeAPICall(request);
		
		Assert.assertEquals(response.toString(),200, response.getResponseCode());
		Assert.assertEquals(response.toString(),unknownTestDOIUrl, response.getResponseBody());
	}
	
	@Test
	public void test2GetUnknownMetadata() throws Exception{
		String doi = metadata.getDoi();
		
		String address = metadataAddress;
		address += "/"+doi;
		
		HTTPRequest request = new HTTPRequest(address);
		request.setMethod(HTTPRequest.Method.GET);
		request.setAccept("application/xml");		
		request.setUsername(username);
		request.setPassword(password);
		
		HTTPResponse response = client.makeAPICall(request);
		
		Assert.assertEquals(response.toString(),404,response.getResponseCode());
		Assert.assertTrue(response.toString(),response.getResponseBody().contains("DOI is unknown"));		
	}	
	
	@Test
	public void test3CreateMetadataTestMode() throws Exception {
		
		String address = metadataAddress;
		address += testMode;
		
		HTTPRequest request = new HTTPRequest(address);
		request.setMethod(HTTPRequest.Method.POST);
		request.setContentType("application/xml;charset=UTF-8");
		request.setBody(metadata.toXML());
		request.setUsername(username);
		request.setPassword(password);
		
		HTTPResponse response = client.makeAPICall(request);
		
		Assert.assertEquals(response.toString(),201,response.getResponseCode());
		Assert.assertTrue(response.toString(),response.getResponseBody().startsWith("OK"));
		Assert.assertTrue(response.toString(),response.getResponseBody().contains(metadata.getDoi()));		
	}
	
	@Test
	public void test4CreateMetadata() throws Exception {
		
		String address = metadataAddress;
		
		HTTPRequest request = new HTTPRequest(address);
		request.setMethod(HTTPRequest.Method.POST);
		request.setContentType("application/xml;charset=UTF-8");
		request.setBody(metadata.toXML());
		request.setUsername(username);
		request.setPassword(password);
		
		HTTPResponse response = client.makeAPICall(request);		
		
		Assert.assertEquals(response.toString(),201,response.getResponseCode());
		Assert.assertTrue(response.toString(),response.getResponseBody().startsWith("OK"));
		Assert.assertTrue(response.toString(),response.getResponseBody().contains(metadata.getDoi()));						
	}
	
	@Test
	public void test5CreateDOITestMode() throws Exception {
		String doi = doiTestPrefix+doiTestSuffix;
		
		String address = doiAddress;
		address += testMode;
		
		HTTPRequest request = new HTTPRequest(address);
		request.setMethod(HTTPRequest.Method.POST);
		request.setContentType("text/plain;charset=UTF-8");
		request.setUsername(username);
		request.setPassword(password);
		request.setBody("doi="+doi+"\nurl="+url);
		
		HTTPResponse response = client.makeAPICall(request);
		
		Assert.assertEquals(response.toString(),201,response.getResponseCode());
		Assert.assertTrue(response.toString(),response.getResponseBody().startsWith("OK"));		
	}	
	
	@Test
	public void test6CreateDOI() throws Exception {
		String doi = doiTestPrefix+doiTestSuffix;
		
		String address = doiAddress; 
		
		HTTPRequest request = new HTTPRequest(address);
		request.setMethod(HTTPRequest.Method.POST);
		request.setContentType("text/plain;charset=UTF-8");
		request.setUsername(username);
		request.setPassword(password);
		request.setBody("doi="+doi+"\nurl="+url);
		
		HTTPResponse response = client.makeAPICall(request);
		
		Assert.assertEquals(response.toString(),201,response.getResponseCode());
		Assert.assertTrue(response.toString(),response.getResponseBody().startsWith("OK"));				
	}
	
	@Test
	public void test7GetDOI() throws Exception {
		String doi = doiTestPrefix+doiTestSuffix;
		
		String address = doiAddress;
		address += "/"+doi;
		
		HTTPRequest request = new HTTPRequest(address);
		request.setUsername(username);
		request.setPassword(password);
		request.setMethod(HTTPRequest.Method.GET);
		
		HTTPResponse response = client.makeAPICall(request);
		
		Assert.assertEquals(response.toString(),200, response.getResponseCode());
		Assert.assertEquals(response.toString(),url, response.getResponseBody());
	}	
	
	@Test
	public void test8GetMetadata() throws Exception{
		String doi = metadata.getDoi();
		
		String address = metadataAddress;
		address += "/"+doi;
		
		HTTPRequest request = new HTTPRequest(address);
		request.setMethod(HTTPRequest.Method.GET);
		request.setAccept("application/xml");		
		request.setUsername(username);
		request.setPassword(password);
		
		HTTPResponse response = client.makeAPICall(request);
		
		Assert.assertEquals(response.toString(),200,response.getResponseCode());
		Assert.assertTrue(response.toString(),response.getResponseBody().contains(doi));
	}
	
	@Test
	public void test9DeleteMetadataTestMode() throws Exception {
		String doi = metadata.getDoi();
		
		String address = metadataAddress;
		address += "/"+doi;
		address += testMode;
		
		HTTPRequest request = new HTTPRequest(address);
		request.setMethod(HTTPRequest.Method.DELETE);
		request.setUsername(username);
		request.setPassword(password);
		
		HTTPResponse response = client.makeAPICall(request);
		
		Assert.assertEquals(response.toString(),200,response.getResponseCode());
		Assert.assertTrue(response.toString(),response.getResponseBody().startsWith("OK"));		
	}
	
	@Test
	public void test10DeleteMetadata() throws Exception {
		String doi = metadata.getDoi();
		
		String address = metadataAddress;
		address += "/"+doi;
		
		// Delete record
		HTTPRequest request = new HTTPRequest(address);
		request.setMethod(HTTPRequest.Method.DELETE);
		request.setUsername(username);
		request.setPassword(password);
		
		HTTPResponse response = client.makeAPICall(request);
		
		Assert.assertEquals(response.toString(),200,response.getResponseCode());
		Assert.assertTrue(response.toString(),response.getResponseBody().startsWith("OK"));
		
		// Check that record was deleted
		request = new HTTPRequest(address);
		request.setMethod(HTTPRequest.Method.GET);
		request.setUsername(username);
		request.setPassword(password);
		
		response = client.makeAPICall(request);		
		
		Assert.assertEquals(response.toString(),410,response.getResponseCode());
		Assert.assertTrue(response.toString(),response.getResponseBody().contains("dataset inactive"));				
	}	
	
}
