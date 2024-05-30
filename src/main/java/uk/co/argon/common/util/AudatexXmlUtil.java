package uk.co.argon.common.util;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import uk.co.argon.common.exceptions.HttpException;

import javax.xml.namespace.QName;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

public class AudatexXmlUtil {
	private static AudatexXmlUtil instance;
	
	private AudatexXmlUtil() {}
	
	public static AudatexXmlUtil getInstance() {
		if(instance == null) {
			synchronized (AudatexXmlUtil.class) {
				if(instance == null)
					instance = new AudatexXmlUtil();
			}
		}
		return instance;
	}
	
	public <T> String performMarshaling(T pojo) throws HttpException {
        try {
            Marshaller jaxbMarshaller = JaxbContextCache.getInstance().getContext(pojo.getClass()).createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            
            StringWriter sw = new StringWriter();
            
            jaxbMarshaller.marshal(pojo, sw);

            String xml = sw.toString();
            RegExUtils.removeAll(xml, StringUtils.LF);

            return xml;
        }
        catch (JAXBException e) {
            throw new HttpException(e.toString(), 500);
        }
    }
	
	public <T> String performMarshaling(T pojo, Class<T> clazz) throws HttpException {
        try {
            Marshaller jaxbMarshaller = JaxbContextCache.getInstance().getContext(pojo.getClass()).createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            
            StringWriter sw = new StringWriter();
            
            jaxbMarshaller.marshal(new JAXBElement<T>(new QName(clazz.getSimpleName()), clazz, pojo), sw);

            String xml = sw.toString();
            RegExUtils.removeAll(xml, StringUtils.LF);

            return xml;
        }
        catch (JAXBException e) {
            throw new HttpException(e.toString(), 500);
        }
    }

    public <T> T performUnmarshalling(ByteArrayInputStream bais, Class<T> clazz) throws HttpException {
        Unmarshaller unmarshaller = null;

        try {
            unmarshaller = JaxbContextCache.getInstance().getContext(clazz).createUnmarshaller();
            T pojo = clazz.cast(unmarshaller.unmarshal(bais));
            return pojo;
        }catch (JAXBException e) {
            throw new HttpException(e.toString(), 500);
        }
    }

    
}
