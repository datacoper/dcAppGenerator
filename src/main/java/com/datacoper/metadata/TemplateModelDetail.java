package com.datacoper.metadata;

public class TemplateModelDetail extends TemplateModel{

	private TemplateModel templateModelMaster;

	public TemplateModelDetail(String entityName, TemplateModel templateModelMaster) {
		super(templateModelMaster.getProjectParentFile());
		super.setEntityName(entityName);
		this.templateModelMaster = templateModelMaster;
	}

	public String getEntityNameMaster() {
		return templateModelMaster.getEntityName();
	}
	
	public boolean isMaster() {
		return false;
	}
}
