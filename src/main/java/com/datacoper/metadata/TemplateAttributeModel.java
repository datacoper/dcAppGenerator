package com.datacoper.metadata;

import com.datacoper.enums.EnumAttributeMode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class TemplateAttributeModel {

	private TemplateModel templateModel;

    private String name;
    private String type;
    private String typeSimpleName;
    private String label;
    private String format;
    private boolean required;
    private Integer minChar;
	private Integer maxChar;
	private EnumAttributeMode mode;


	public TemplateAttributeModel() {
	}

	public TemplateAttributeModel(TemplateModel templateModel, String name, String type, String label, String format, int precision, int scale,
								  boolean required, boolean updatable) {

        Objects.requireNonNull(templateModel);
        Objects.requireNonNull(name);
        Objects.requireNonNull(type);
        Objects.requireNonNull(label);

        this.templateModel = templateModel;
        this.name = name;
        this.label = label;
        this.format = format;
        this.required = required;

        this.type = type;

    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTypeSimpleName(String typeSimpleName) {
		this.typeSimpleName = getDartType(typeSimpleName);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public TemplateModel getTemplateModel() {
		return templateModel;
	}

	public void setTemplateModel(TemplateModel templateModel) {
		this.templateModel = templateModel;
	}

	public boolean isNumber() {
        return type.equals(Integer.class.getName()) ||
                type.equals(Long.class.getName()) ||
                type.equals(int.class.getName()) ||
                type.equals(long.class.getName()) ||
				type.equals("Integer")
				;
    }

    public boolean isDate() {
        return type.equals(Date.class.getName()) ||
				type.equals("DateTime") ||
                type.equals("Date");
    }

	public boolean isGeoPoint() {
		return type.equals("GeoPoint");
	}

	public boolean isFileInfo() {
		return type.equals("FileInfo");
	}

    public boolean isText() {
        return type.equals(String.class.getName()) ||
				type.equals("String") ||
				type.equals("Image") ||
                type.equals(Character.class.getName());
    }

    public boolean isDecimal() {
        return type.equals(Double.class.getName()) ||
                type.equals(double.class.getName()) ||
				type.equals("Monetary") ||
				type.equals("Double") ||
                type.equals(BigDecimal.class.getName());
    }

    public boolean isBoolean() {
        return type.equals(boolean.class.getName()) ||
				type.equals("Boolean") ||
                type.equals(Boolean.class.getName());
    }

    public boolean isEntity(){
		return !isBoolean() && !isDate() && !isDecimal() && !isNumber() && !isText();
	}

    public String getTypeSimpleName() {
        return typeSimpleName;
    }

	public String getDartType(String type) {
		if (isText()) {
			return "String";
		}
		if (isNumber() || isDecimal()) {
			return "num";
		}

		if (isDate()) {
			return "DateTime";
		}

		if (isBoolean()) {
			return "bool";
		}

		return type;
	}

	public String getTypescriptType(String type) {
		if (isText()) {
			return "string";
		}
		if (isNumber() || isDecimal()) {
			return "number";
		}

		if (isDate()) {
			return "Date";
		}

		if (isBoolean()) {
			return "boolean";
		}

		return type;
	}

    public String getFrontType() {
        if (isText()) {
            return "text";
        }
        if (isNumber() || isDecimal()) {
            return "number";
        }

        if (isDate()) {
            return "date";
        }

        if (isBoolean()) {
            return "boolean";
        }

        return "";
    }

	public Integer getMinChar() {
		return minChar;
	}

	public void setMinChar(Integer minChar) {
		this.minChar = minChar;
	}

	public Integer getMaxChar() {
		return maxChar;
	}

	public void setMaxChar(Integer maxChar) {
		this.maxChar = maxChar;
	}

	public boolean isIntegracao(){
		return name.equalsIgnoreCase("chaveIntegracao");
	}

	public EnumAttributeMode getMode() {
		return mode;
	}

	public void setMode(EnumAttributeMode mode) {
		this.mode = mode;
	}

	public boolean isInternal(){
		return mode == EnumAttributeMode.INTERNAL;
	}

	public boolean isOneToOne(){
		return mode == EnumAttributeMode.ONE_TO_ONE;
	}

	public boolean isOneToMany(){
		return mode == EnumAttributeMode.ONE_TO_MANY;
	}

	public boolean isCollection(){
		return mode == EnumAttributeMode.COLLECTION;
	}

	public boolean isComposite(){
		return mode == EnumAttributeMode.COMPOSITE;
	}
}
