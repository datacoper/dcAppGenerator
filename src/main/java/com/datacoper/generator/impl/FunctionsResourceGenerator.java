package com.datacoper.generator.impl;

import com.datacoper.generator.AbstractTypescriptGenerator;
import com.datacoper.metadata.TemplateModel;

public class FunctionsResourceGenerator extends AbstractTypescriptGenerator {

    public FunctionsResourceGenerator(TemplateModel templateModel) {
		super(templateModel);
	}

	@Override
    public String getTemplateName() {
    	return "functions.resources.resource";
    }

    @Override
    public String getPackage() {
        return "app.resources";
    }

	@Override
	public String getClassName() {
		return getEntityName() + "Resource";
	}

	@Override
	public String getFileName() {
		return getClassName();
	}
}
