package uk.co.argon.common.util;

import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import uk.co.argon.common.exceptions.HttpException;

public class DefaultJsonParser {
	private static DefaultJsonParser instance;
	protected final ObjectMapper mapper = new ObjectMapper();
	
	protected DefaultJsonParser() {
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
	}
	
	public static DefaultJsonParser getInstance() {
		if(instance == null) {
			synchronized (DefaultJsonParser.class) {
				if(instance == null) {
					instance = new DefaultJsonParser();
				}
			}
		}
		return instance;
	}
	
	public <T> String serialise(T pojo) throws HttpException {
		try {
			return mapper.writeValueAsString(pojo);
		}catch (Exception e) {
			throw new HttpException(StringUtils.isNotBlank(e.getMessage())? e.getMessage():e.toString(), e, 500);
		}
	}
	
	public <T> String serialiseReturnEmptyStringOnError(T pojo) throws HttpException {
		try {
			return mapper.writeValueAsString(pojo);
		}catch (Exception e) {
			return "";
		}
	}
	
	public <T> T deserialise(String json, Class<T> clazz) throws HttpException {
		try {
			return mapper.readValue(json, clazz);
		}catch (Exception e) {
			throw new HttpException(StringUtils.isNotBlank(e.getMessage())? e.getMessage():e.toString(), e, 500);
		}
	}
	
	public <T> T deserialise(InputStream jsonStream, Class<T> clazz) throws HttpException {
		try {
			return mapper.readValue(jsonStream, clazz);
		}catch (Exception e) {
			throw new HttpException(StringUtils.isNotBlank(e.getMessage())? e.getMessage():e.toString(), e, 500);
		}
	}
	
	public <T> T deserialise(String json, TypeReference<T> tRef) throws HttpException {
		try {
			return mapper.readValue(json, tRef);
		}catch (Exception e) {
			throw new HttpException(StringUtils.isNotBlank(e.getMessage())? e.getMessage():e.toString(), e, 500);
		}
	}
}
