package com.datacoper.generator.impl;

import com.datacoper.generator.AbstractTypescriptGenerator;
import com.datacoper.metadata.TemplateModel;

public class FunctionsServiceGenerator extends AbstractTypescriptGenerator {

    public FunctionsServiceGenerator(TemplateModel templateModel) {
		super(templateModel);
	}

	@Override
    public String getTemplateName() {
    	return "functions.service.service";
    }

    @Override
    public String getPackage() {
        return "app.service";
    }

	@Override
	public String getClassName() {
		return getEntityName() + "Service";
	}

	@Override
	public String getFileName() {
		return getClassName();
	}
}
