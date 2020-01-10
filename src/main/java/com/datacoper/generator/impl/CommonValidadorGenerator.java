package com.datacoper.generator.impl;

import com.datacoper.generator.AbstractDartGenerator;
import com.datacoper.metadata.TemplateModel;
import com.github.underscore.lodash.U;

public class CommonValidadorGenerator extends AbstractDartGenerator {

    public CommonValidadorGenerator(TemplateModel templateModel) {
		super(templateModel);
	}

	@Override

    public String getTemplateName() {
    	return "common.validator.validador";
    }

    @Override
    public String getPackage() {
        return "base.validator";
    }

	@Override
	public String getClassName() {
		return getEntityName();
	}

	@Override
	public String getFileName() {
		return U.snakeCase(getClassName()).concat("_validador");
	}
}
