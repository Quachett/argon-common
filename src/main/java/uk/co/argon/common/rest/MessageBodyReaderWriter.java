package uk.co.argon.common.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.Dependent;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;
import uk.co.argon.common.exceptions.HttpException;
import uk.co.argon.common.util.AudatexXmlUtil;
import uk.co.argon.common.util.JsonParser;

@Provider
@Dependent
@Priority(400)
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class MessageBodyReaderWriter<T> implements MessageBodyWriter<T>, MessageBodyReader<T> {

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void writeTo(T t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
			throws IOException, WebApplicationException {
		try {
			switch (StringUtils.substringBefore(mediaType.toString(), ";")) {
			case MediaType.APPLICATION_XML:
				entityStream.write(AudatexXmlUtil.getInstance().performMarshaling(t, (Class<T>) type.getClass()).getBytes());
				break;
				
			case MediaType.APPLICATION_JSON:
				entityStream.write(JsonParser.getInstance().serialiseReturnEmptyStringOnError(t).getBytes());
				break;

			default:
				throw new WebApplicationException("Unknown MediaType: " + mediaType, Status.UNSUPPORTED_MEDIA_TYPE.getStatusCode());
			}
			
			
		} catch (HttpException e) {
			e.printStackTrace();
			throw new WebApplicationException(e);
		}
	}

	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	public T readFrom(Class<T> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {

		try {
			switch (mediaType.toString()) {
			case MediaType.APPLICATION_XML:
				return AudatexXmlUtil.getInstance().performUnmarshalling(new ByteArrayInputStream(IOUtils.toByteArray(entityStream)), type);
				
			case MediaType.APPLICATION_JSON:
					return JsonParser.getInstance().deserialise(entityStream, type);
	
			default:
				throw new HttpException("Unknown MediaType: " + mediaType, Status.UNSUPPORTED_MEDIA_TYPE.getStatusCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebApplicationException(e);
		}
	}
}
