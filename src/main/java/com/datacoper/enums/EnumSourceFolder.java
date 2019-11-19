package com.datacoper.enums;

public enum EnumSourceFolder {

	JAVA("src/main/java"),
	ANGULAR("src/main/webapp/app"),
	DART("lib"),
	FUNCTIONS("functions/src"),

	;
	
	private String sourceFolder;

	private EnumSourceFolder(String sourceFolder) {
		this.sourceFolder = sourceFolder;
	}
	
	public String getSourceFolder() {
		return sourceFolder;
	}
	
}
