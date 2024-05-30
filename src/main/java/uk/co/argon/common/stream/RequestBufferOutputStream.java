package uk.co.argon.common.stream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class RequestBufferOutputStream extends OutputStream {
	private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

	@Override
	public void write(int b) throws IOException {
		baos.write(b);
	}
	
	public void write(byte[] b) throws IOException {
		baos.write(b);
	}
	
	public void write(byte[] b, int off, int len) throws IOException {
		baos.write(b, off, len);
	}
	
	public String getRequest() {
		if(baos.size()>0)
			return new String(baos.toByteArray(), StandardCharsets.UTF_8);
		
		return null;
	}

}
