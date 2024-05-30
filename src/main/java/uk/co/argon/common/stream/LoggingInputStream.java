package uk.co.argon.common.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LoggingInputStream extends InputStream {

	private final InputStream inputStream;
	private final OutputStream outputStream;
	
	public LoggingInputStream(InputStream inputStream, OutputStream outputStream) {
		this.inputStream = inputStream;
		this.outputStream = outputStream;
	}
	
	@Override
	public int read() throws IOException {
		int b = inputStream.read();
		if(b != -1)
			outputStream.write(b);
		else {
			outputStream.flush();
			outputStream.close();
		}
		return b;
	}
	
	public int read(byte[] bytes) throws IOException {
		int b = inputStream.read();
		if(b != -1)
			outputStream.write(bytes, 0, b);
		else {
			outputStream.flush();
			outputStream.close();
		}
		return b;
	}
	
	public int read(byte[] bytes, int off, int len) throws IOException {
		int b = inputStream.read(bytes, off, len);
		if(b != -1)
			outputStream.write(bytes, off, b);
		else {
			outputStream.flush();
			outputStream.close();
		}
		return b;
	}
	
}
