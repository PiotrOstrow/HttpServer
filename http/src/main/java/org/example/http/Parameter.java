package org.example.http;

public class Parameter {

	private String name;
	private byte[] data;

	private String fileName = "";
	private String contentType = "";

	protected Parameter(){}

	protected Parameter(String name, String value) {
		this.name = name;
		this.data = value.getBytes();
	}

	public String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	public byte[] getData() {
		return data;
	}

	protected void setData(byte[] data) {
		this.data = data;
	}

	/**
	 * @return the value of the parameter as a String
	 */
	public String asString() {
		return new String(data);
	}

	public String getFileName() {
		return fileName;
	}

	public String getContentType() {
		return contentType;
	}

	protected void setContentType(String contentType) {
		this.contentType = contentType;
	}

	protected void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
