package uk.co.argon.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import uk.co.argon.common.exceptions.HttpException;

public class XmlUtil {
	public XmlUtil() {
		
	}
	
	public static <T>String performMarshaling(T pojo) throws HttpException {		
		try {
			Marshaller jaxbMarshaller = JaxbContextCache.getInstance().getContext(pojo.getClass()).createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			jaxbMarshaller.marshal(pojo, os);
			os.flush();
			return new String(os.toByteArray(), StandardCharsets.UTF_8);
		}
		catch (JAXBException | IOException e) {
			throw new HttpException(e.toString(), 500);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T>T performUnmarshaling(String xml, Class<T> c) throws HttpException {
		try {
			Unmarshaller jaxbUnmarshaller = JaxbContextCache.getInstance().getContext(c).createUnmarshaller();
			return (T)jaxbUnmarshaller.unmarshal(new StringReader(xml));
		}
		catch (JAXBException e) {
			throw new HttpException(e.toString(), 500);
		}
	}
}
