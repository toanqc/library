package mpp.library.model.dao.impl;

/**
 * @author Toan Quach
 *
 */
public enum SerializationFile {
	MEMBER("./storage/member.ser"), BOOK("./storage/book.ser"), PERIODICAL(
			"./storage/periodical.ser"), COPY("./storage/copy.ser"), CHECKOUT_RECORD(
			"./storage/checkoutrecord.ser"), CHECKOUT_RECORD_ENTRY(
			"./storage/checkoutrecordentry.ser");

	String fileName;

	SerializationFile(String fileName) {
		this.fileName = fileName;
	}

	String getValue() {
		return this.fileName;
	}
}
