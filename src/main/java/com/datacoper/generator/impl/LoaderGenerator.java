package com.datacoper.generator.impl;

import com.datacoper.generator.AbstractDartGenerator;
import com.datacoper.metadata.TemplateModel;
import com.github.underscore.lodash.U;

public class LoaderGenerator extends AbstractDartGenerator {

    public LoaderGenerator(TemplateModel templateModel) {
		super(templateModel);
	}

	@Override
    public String getTemplateName() {
    	return "common.loader.loader";
    }

    @Override
    public String getPackage() {
        return "base.loader";
    }

	@Override
	public String getClassName() {
		return getEntityName();
	}

	@Override
	public String getFileName() {
		return U.snakeCase(getClassName()+"_loader");
	}
}
