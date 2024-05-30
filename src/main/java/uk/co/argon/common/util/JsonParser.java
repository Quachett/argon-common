package uk.co.argon.common.util;

import com.fasterxml.jackson.module.jakarta.xmlbind.JakartaXmlBindAnnotationModule;

public class JsonParser extends DefaultJsonParser {
	
	private static JsonParser instance;
	
	protected JsonParser() {
		super();
		mapper.registerModule(new JakartaXmlBindAnnotationModule());
	}
	
	public static JsonParser getInstance() {
		if(instance == null) {
			synchronized (JsonParser.class) {
				if(instance == null) {
					instance = new JsonParser();
				}
			}
		}
		return instance;
	}
}
