package com.datacoper.generator.impl;

import com.datacoper.generator.AbstractDartGenerator;
import com.datacoper.metadata.TemplateModel;
import com.github.underscore.lodash.U;

public class CommonModelGenerator extends AbstractDartGenerator {

    public CommonModelGenerator(TemplateModel templateModel) {
		super(templateModel);
	}

	@Override
    public String getTemplateName() {
    	return "common.model.model";
    }

    @Override
    public String getPackage() {
        return "base.model";
    }

	@Override
	public String getClassName() {
		return getEntityName();
	}

	@Override
	public String getFileName() {
		return U.snakeCase(getClassName());
	}
}
