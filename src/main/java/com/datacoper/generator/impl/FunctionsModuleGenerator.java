package com.datacoper.generator.impl;

import com.datacoper.generator.AbstractTypescriptGenerator;
import com.datacoper.metadata.TemplateModel;

public class FunctionsModuleGenerator extends AbstractTypescriptGenerator {

    public FunctionsModuleGenerator(TemplateModel templateModel) {
		super(templateModel);
	}

	@Override
    public String getTemplateName() {
    	return "functions.modules.module";
    }

    @Override
    public String getPackage() {
        return "app.modules";
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
