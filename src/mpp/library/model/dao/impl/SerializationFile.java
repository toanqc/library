package mpp.library.model.dao.impl;

public enum SerializationFile {
	MEMBER("./storage/member.ser");

	String fileName;

	SerializationFile(String fileName) {
		this.fileName = fileName;
	}

	String getValue() {
		return this.fileName;
	}
}
