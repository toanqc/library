package mpp.library.model.dao.db.impl;

public enum EntityType {

	MEMBER("LibraryMember"),
	ADDRESS("Address");

	private String tableName;

	EntityType(String tableName) {
		this.tableName = tableName;
	}

	String getValue() {
		return this.tableName;
	}
}
