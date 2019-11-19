package com.datacoper.generator.impl;

import com.datacoper.generator.AbstractTypescriptGenerator;
import com.datacoper.metadata.TemplateModel;

public class FunctionsModuleGenerator extends AbstractTypescriptGenerator {

    public FunctionsModuleGenerator(TemplateModel templateModel) {
		super(templateModel);
	}

	@Override
    public String getTemplateName() {
    	return "functions.module.module";
    }

    @Override
    public String getPackage() {
        return "app.module";
    }

	@Override
	public String getClassName() {
		return getEntityName() + "Module";
	}

	@Override
	public String getFileName() {
		return getClassName() ;
	}
}
