package com.datacoper.generator.impl;

import com.datacoper.generator.AbstractDartGenerator;
import com.datacoper.metadata.TemplateModel;
import com.github.underscore.lodash.U;

public class CommonRepositoryImplGenerator extends AbstractDartGenerator {

    public CommonRepositoryImplGenerator(TemplateModel templateModel) {
		super(templateModel);
	}

	@Override
    public String getTemplateName() {
    	return "common.repository_impl.repository_impl";
    }

    @Override
    public String getPackage() {
        return "base.repository_impl";
    }

	@Override
	public String getClassName() {
		return getEntityName();
	}

	@Override
	public String getFileName() {
		return U.snakeCase(getClassName()).concat("_repository_impl");
	}
}
