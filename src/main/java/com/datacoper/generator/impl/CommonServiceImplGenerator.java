package com.datacoper.generator.impl;

import com.datacoper.generator.AbstractDartGenerator;
import com.datacoper.metadata.TemplateModel;
import com.github.underscore.lodash.U;

public class CommonServiceImplGenerator extends AbstractDartGenerator {

    public CommonServiceImplGenerator(TemplateModel templateModel) {
		super(templateModel);
	}

	@Override
    public String getTemplateName() {
    	return "common.service_impl.service_impl";
    }

    @Override
    public String getPackage() {
        return "base.service_impl";
    }

	@Override
	public String getClassName() {
		return getEntityName();
	}

	@Override
	public String getFileName() {
		return U.snakeCase(getClassName()).concat("_service_impl");
	}
}
