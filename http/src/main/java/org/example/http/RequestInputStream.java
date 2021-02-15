package org.example.http;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class RequestInputStream extends BufferedInputStream {

	private static final int BUFFER_SIZE = 4096;

	private final byte[] buffer = new byte[BUFFER_SIZE];

	public RequestInputStream(InputStream in) {
		super(in);
	}

	public byte[] readParamData(String boundary) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream() {
			@Override
			public synchronized byte[] toByteArray() {
				return buf;
			}
		};
		byte[] boundaryData = boundary.trim().getBytes();
		int read = 0;

		while(!compareBoundary(boundaryData, read)) {
			baos.write(buffer, 0, read);
			read = readUntilBreakLine();
		}

		// -2 for break line characters at the end
		return Arrays.copyOfRange(baos.toByteArray(), 0, baos.size() - 2);
	}

	private boolean compareBoundary(byte[] boundaryData, int length) {
		// -2 for line break
		if(boundaryData.length != length - 2)
			return false;

		for(int i = 0; i < boundaryData.length; i++)
			if(buffer[i] != boundaryData[i])
				return false;

		return true;
	}

	/**
	 * @return number of bytes read, including break line characters
	 */
	private int readUntilBreakLine() throws IOException {
		byte[] b = new byte[2];
		int bytesRead = 0;
		while (bytesRead < BUFFER_SIZE) {
			buffer[bytesRead++] = (byte) read();
			if (buffer[bytesRead - 1] == '\r') {
				buffer[bytesRead++] = (byte) read();
				if( buffer[bytesRead - 1] == '\n')
					break;
			}
		}

		return bytesRead;
	}

	public String readLine() throws IOException {
		int read = readUntilBreakLine();
		return new String(buffer, 0, read - 2, StandardCharsets.UTF_8);
	}
}
