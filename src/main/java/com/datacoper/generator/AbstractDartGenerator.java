package com.datacoper.generator;

import com.datacoper.metadata.TemplateModel;
import com.github.underscore.lodash.U;

public abstract class AbstractDartGenerator extends AbstractGenerator {

	public AbstractDartGenerator(TemplateModel templateModel) {
		super(templateModel);
	}

	@Override
	public String getFileExtension() {
		return ".dart";
	}
	
	@Override
	public String getCharsetName() {
    	return "UTF-8";
    }


}
