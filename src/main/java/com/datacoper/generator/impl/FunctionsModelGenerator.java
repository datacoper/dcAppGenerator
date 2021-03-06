package com.datacoper.generator.impl;

import com.datacoper.generator.AbstractTypescriptGenerator;
import com.datacoper.metadata.TemplateModel;

public class FunctionsModelGenerator extends AbstractTypescriptGenerator {

    public FunctionsModelGenerator(TemplateModel templateModel) {
		super(templateModel);
	}

	@Override
    public String getTemplateName() {
    	return "functions.models.model";
    }

    @Override
    public String getPackage() {
        return "app.models";
    }

	@Override
	public String getClassName() {
		return getEntityName();
	}

	@Override
	public String getFileName() {
		return getClassName();
	}
}
