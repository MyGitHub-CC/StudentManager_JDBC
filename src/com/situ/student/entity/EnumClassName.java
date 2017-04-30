package com.situ.student.entity;

public enum EnumClassName {
	
	Java1701("Java1701"), Java1703("Java1703"),HTML1701("HTML1701"),UI1701("UI1701");
	private String value;
	
	private EnumClassName(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
