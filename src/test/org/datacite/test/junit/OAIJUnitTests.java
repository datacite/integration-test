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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * JUnit tests for DataCite OAI service.
 * @author PaluchM
 *
 */
public class OAIJUnitTests {

	private static final String oai_dcFormat = "&metadataPrefix=oai_dc";
	private static final String oai_dataciteFormat = "&metadataPrefix=oai_datacite";
	private static final String resumptionToken = "&resumptionToken=1333137710872,0001-01-01T00:00:00Z,9999-12-31T23:59:59Z,50,null,oai_dc";
			
	private static HTTPClient client;
	private static Properties properties;
	private static String oaiAddress;	
	
	private static enum Verbs{
		Identify,
		ListMetadataFormats,
		ListIdentifiers,
		ListRecords,
		ListSets,
	}
	
	@BeforeClass
	public static void oneTimeSetUp() throws IOException{
		client = new HTTPClient();
		
		properties = new Properties();
		properties.load(new FileInputStream(new File(Constants.Property.FILE)));
		oaiAddress = properties.getProperty(Constants.Property.OAI_URL);
		oaiAddress += "?verb=";
	}
	
	@AfterClass
	public static void oneTimeTearDown(){
		client = null;
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
	public void test1Identify() throws Exception {
		String address = oaiAddress;
		address += Verbs.Identify;
		
		HTTPRequest request = new HTTPRequest(address);
		request.setMethod(HTTPRequest.Method.GET);
		
		HTTPResponse response = client.makeAPICall(request);
		
		Assert.assertEquals(response.toString(),200,response.getResponseCode());
		Assert.assertTrue(response.toString(),response.getResponseBody().contains("oai:oai.datacite.org:12425"));
	}
	
	@Test
	public void test2ListMetadataFormats() throws Exception {
		String address = oaiAddress;
		address += Verbs.ListMetadataFormats;
		
		HTTPRequest request = new HTTPRequest(address);
		request.setMethod(HTTPRequest.Method.GET);
		
		HTTPResponse response = client.makeAPICall(request);
		
		Assert.assertEquals(response.toString(),200,response.getResponseCode());
		Assert.assertTrue(response.toString(),response.getResponseBody().contains("oai_dc"));		
	}
	
	@Test
	public void test3ListSets() throws Exception {
		String address = oaiAddress;
		address += Verbs.ListSets;
		
		HTTPRequest request = new HTTPRequest(address);
		request.setMethod(HTTPRequest.Method.GET);
		
		HTTPResponse response = client.makeAPICall(request);
		
		Assert.assertEquals(response.toString(),200,response.getResponseCode());
		Assert.assertTrue(response.toString(),response.getResponseBody().contains("setSpec"));		
	}
	
	@Test
	public void test4ListSetsResume() throws Exception {
		String address = oaiAddress;
		address += Verbs.ListSets;
		address += resumptionToken;
		
		HTTPRequest request = new HTTPRequest(address);
		request.setMethod(HTTPRequest.Method.GET);
		
		HTTPResponse response = client.makeAPICall(request);
		
		Assert.assertEquals(response.toString(),200,response.getResponseCode());
		Assert.assertTrue(response.toString(),response.getResponseBody().contains("setSpec"));		
	}
	
	@Test
	public void test5ListIdentifiersOAI_dc() throws Exception{
		String address = oaiAddress;
		address += Verbs.ListIdentifiers;
		address += oai_dcFormat;
		
		HTTPRequest request = new HTTPRequest(address);
		request.setMethod(HTTPRequest.Method.GET);
		
		HTTPResponse response = client.makeAPICall(request);
		
		Assert.assertEquals(response.toString(),200,response.getResponseCode());
		Assert.assertTrue(response.toString(),response.getResponseBody().contains("<identifier>oai:oai.datacite.org"));		
		
	}
	
	@Test
	public void test6ListIdentifiersResume() throws Exception{
		String address = oaiAddress;
		address += Verbs.ListIdentifiers;
		address += resumptionToken;
		
		HTTPRequest request = new HTTPRequest(address);
		request.setMethod(HTTPRequest.Method.GET);
		
		HTTPResponse response = client.makeAPICall(request);
		
		Assert.assertEquals(response.toString(),200,response.getResponseCode());
		Assert.assertTrue(response.toString(),response.getResponseBody().contains("<identifier>oai:oai.datacite.org"));		
		
	}	
	
	@Test
	public void test7ListIdentifiersOAI_datacite() throws Exception{
		String address = oaiAddress;
		address += Verbs.ListIdentifiers;
		address += oai_dataciteFormat;
		
		HTTPRequest request = new HTTPRequest(address);
		request.setMethod(HTTPRequest.Method.GET);
		
		HTTPResponse response = client.makeAPICall(request);
		
		Assert.assertEquals(response.toString(),200,response.getResponseCode());
		Assert.assertTrue(response.toString(),response.getResponseBody().contains("<identifier>oai:oai.datacite.org"));		
		
	}
	
	@Test
	public void test8ListRecordsOAI_dc() throws Exception{
		String address = oaiAddress;
		address += Verbs.ListRecords;
		address += oai_dcFormat;
		
		HTTPRequest request = new HTTPRequest(address);
		request.setMethod(HTTPRequest.Method.GET);
		
		HTTPResponse response = client.makeAPICall(request);
		
		Assert.assertEquals(response.toString(),200,response.getResponseCode());
		Assert.assertTrue(response.toString(),response.getResponseBody().contains("<metadata><oai_dc"));		
		
	}
	
	@Test
	public void test9ListRecordsResume() throws Exception{
		String address = oaiAddress;
		address += Verbs.ListRecords;
		address += resumptionToken;
		
		HTTPRequest request = new HTTPRequest(address);
		request.setMethod(HTTPRequest.Method.GET);
		
		HTTPResponse response = client.makeAPICall(request);
		
		Assert.assertEquals(response.toString(),200,response.getResponseCode());
		Assert.assertTrue(response.toString(),response.getResponseBody().contains("<metadata><oai_dc"));		
		
	}
	
	@Test
	public void test10ListRecordsOAI_datacite() throws Exception{
		String address = oaiAddress;
		address += Verbs.ListRecords;
		address += oai_dataciteFormat;
		
		HTTPRequest request = new HTTPRequest(address);
		request.setMethod(HTTPRequest.Method.GET);
		
		HTTPResponse response = client.makeAPICall(request);
		
		Assert.assertEquals(response.toString(),200,response.getResponseCode());
		Assert.assertTrue(response.toString(),response.getResponseBody().contains("<metadata><oai_datacite"));		
		
	}
}
