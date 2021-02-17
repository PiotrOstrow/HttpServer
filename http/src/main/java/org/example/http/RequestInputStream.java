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

	/**
	 *
	 * @param boundary
	 * @param parameter parameter to put data into
	 * @return true if end boundary was read meaning there is no more data to read
	 */
	public boolean readParamData(String boundary, Parameter parameter) throws IOException {
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
		parameter.setData(Arrays.copyOfRange(baos.toByteArray(), 0, baos.size() - 2));
		return boundaryData.length != read;
	}

	private boolean compareBoundary(byte[] boundaryData, int length) {
		// -2 for line break, delta 2 in case the boundary read was the end boundary with 2 extra - characters
		if(Math.abs(boundaryData.length - (length - 2)) > 2)
			return false;

		for(int i = 0; i < boundaryData.length; i++)
			if(buffer[i] != boundaryData[i])
				return false;

		return true;
	}

	@Override
	public synchronized int read() throws IOException {
		int b = super.read();
		if(b == -1)
			throw new IOException("Connection closed");
		return b;
	}

	/**
	 * @return number of bytes read, including break line characters
	 */
	private int readUntilBreakLine() throws IOException {
		int bytesRead = 0;

		while (bytesRead < BUFFER_SIZE - 1) {
			buffer[bytesRead] = (byte) read();
			if (buffer[bytesRead++] == '\r') {
				buffer[bytesRead] = (byte) read();
				if (buffer[bytesRead++] == '\n')
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
