package com.datacoper.generator;

import com.datacoper.metadata.TemplateModel;

public abstract class AbstractTypescriptGenerator extends AbstractGenerator {

	public AbstractTypescriptGenerator(TemplateModel templateModel) {
		super(templateModel);
	}

	@Override
	public String getFileExtension() {
		return ".ts";
	}
	
	@Override
	public String getCharsetName() {
    	return "UTF-8";
    }


}
