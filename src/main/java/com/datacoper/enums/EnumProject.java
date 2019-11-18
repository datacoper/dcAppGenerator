package com.datacoper.enums;

public enum EnumProject {
	COMMON("produtor_common", EnumSourceFolder.DART),
    MOBILE("produtor_mobile", EnumSourceFolder.DART),
    CONSOLE("produtor_console", EnumSourceFolder.DART),
    FUNCTIONS("produtor_functions", EnumSourceFolder.FUNCTIONS),

    
    ;
	
    private final String qualifier;
    

    private final EnumSourceFolder sourceFolder;
    
    private EnumProject(String qualifier, EnumSourceFolder sourceFolder) {
        this.qualifier = qualifier;
        this.sourceFolder = sourceFolder;
    }

    public String getSuffix() {
        return qualifier;
    }

    public EnumSourceFolder getSourceFolder() {
		return sourceFolder;
	}
}
