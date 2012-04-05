package org.datacite.test.model;

import java.util.ArrayList;

/**
 * Encapsulates creation of a simple test Metadata record containing only mandatory elements.
 * @author PaluchM
 *
 */
public class Metadata {
	
	private static final String xsi = "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"";
	
	private String schemaLocation = "";
	private String namespace = "";	
	private String doi = "";
	private ArrayList<String> creators = null;
	private ArrayList<String> titles = null;
	private String publisher = "";
	private String pubYear = "";
	
	
	/**
	 * Constructor for Metadata object.
	 * @param xmlns The XMLNS attribute from current DataCite Metadata Schema
	 * @param schemaLoc The XSI:SchemaLocation attribute from the current DataCite Metadata Schema
	 */
	public Metadata(String xmlns, String schemaLoc){
		creators = new ArrayList<String>();
		titles = new ArrayList<String>();
		
		namespace = xmlns;
		schemaLocation = schemaLoc;
	}
	
	public void setDoi(String doi) { this.doi = doi;}
	public String getDoi() {return doi;}
	
	public void setCreators(ArrayList<String> creators) {this.creators = creators;}	
	public ArrayList<String> getCreators() {return creators;}
	public void addCreator(String creator){
		if (creators == null){creators = new ArrayList<String>();}
		creators.add(creator);
	}
			
	public void setTitles(ArrayList<String> titles) {this.titles = titles;}
	public ArrayList<String> getTitles() {return titles;}
	public void addTitle(String title){
		if (titles == null){titles = new ArrayList<String>();}
		titles.add(title);
	}
	
	public void setPublisher(String publisher) {this.publisher = publisher;}
	public String getPublisher() {return publisher;}
	
	public void setPubYear(String pubYear) {this.pubYear = pubYear;}
	public String getPubYear() {return pubYear;}	
	
	
	/**
	 * Writes out record as DataCite XML.
	 * @return
	 */
	public String toXML(){
		StringBuilder sb = new StringBuilder();
		
		String[] attribs = {namespace,xsi,schemaLocation};
		
		sb.append(openTagWithAttributes("resource", attribs));		
		sb.append(addElementWithAttribute("identifier",doi,"identifierType","DOI"));		
		
		sb.append(openTag("creators"));
		for (String creator : creators){
			sb.append(openTag("creator"));
			sb.append(addElement("creatorName",creator));
			sb.append(closeTag("creator"));
		}
		sb.append(closeTag("creators"));
		
		sb.append(openTag("titles"));
		for (String title:titles){
			sb.append(addElement("title",title));
		}
		sb.append(closeTag("titles"));
		
		sb.append(addElement("publisher",publisher));
		sb.append(addElement("publicationYear",pubYear));
		
		sb.append(closeTag("resource"));
		
		return sb.toString();
	}
	
	/***********************************************************************************/	
	
	private String addElement(String element,String value){
		return openTag(element)+value+closeTag(element);
	}
	private String addElementWithAttribute(String element,String value,String attrib,String attribValue){
		return openTagWithAttribute(element,attrib,attribValue)+value+closeTag(element);
	}	
	private String openTag(String element){return "<"+element+">";}
	private String openTagWithAttribute(String element,String attrib,String attribValue){
		return "<"+element+" "+attrib+"=\""+attribValue+"\">";
	}
	private String openTagWithAttributes(String element,String[] attribs){
		StringBuilder sb = new StringBuilder("<");
		sb.append(element);
		sb.append(" ");
		for (String attrib : attribs){
			sb.append(attrib+" ");
		}
		sb.append(">");
		return sb.toString();
	}
	private String closeTag(String element){return "</"+element+">";}
	
}
