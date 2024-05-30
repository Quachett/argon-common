package uk.co.argon.common.util;

import java.util.HashMap;
import java.util.Map;

import jakarta.ws.rs.core.MediaType;

public class ArgonHttpUtil {

	public Map<String, String> getRequestHeaders() {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put(ArgonConstants.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        headers.put("Accept", MediaType.APPLICATION_JSON);
		return headers;
	}

	public Map<String, String> getRequestHeaders(String accept, String content, String authorization) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put(ArgonConstants.CONTENT_TYPE, content);
        headers.put("Accept", accept);
        headers.put(ArgonConstants.AUTHORIZATION, authorization);
		return headers;
	}
}
