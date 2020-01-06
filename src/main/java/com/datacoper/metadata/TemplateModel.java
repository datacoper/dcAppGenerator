package com.datacoper.metadata;

import com.datacoper.enums.EnumClassMode;
import com.github.underscore.lodash.U;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class TemplateModel {

    private EnumClassMode mode;

    private String entityName;

    private String className;

    private String collectionName;

    private List<String> attributeImports = new ArrayList<>();

    private File projectParentFile;

    private List<TemplateAttributeModel> attributes = new ArrayList<>();

    private List<TemplateModelDetail> details = new ArrayList<>();

    public TemplateModel(File projectParentFile) {
        this.projectParentFile = projectParentFile;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<TemplateAttributeModel> getAttributes() {
        return attributes;
    }

    public List<TemplateAttributeModel> getAttributesIntegracao() {
        return attributes.stream().filter(a -> !a.isIntegracao()).collect(Collectors.toList());
    }

    public void setAttributes(List<TemplateAttributeModel> attributes) {
        this.attributes = attributes;
        for (TemplateAttributeModel templateAttributeModel : attributes) {
            attributeImports.add(templateAttributeModel.getType());
        }
    }

    public void addAttribute(TemplateAttributeModel attribute) {
        this.attributes.add(attribute);
        if (attribute.isEntity()) {
            addImport(attribute.getType());
        }
    }

    public boolean hasAttributeBoolean() {
        return attributes.stream().anyMatch(TemplateAttributeModel::isBoolean);
    }

    public boolean hasAttributeDate() {
        return attributes.stream().anyMatch(TemplateAttributeModel::isDate);
    }

    public boolean hasAttributeGeoPoint() {
        return attributes.stream().anyMatch(TemplateAttributeModel::isGeoPoint);
    }

    public boolean hasAttributeFileInfo() {
        return attributes.stream().anyMatch(TemplateAttributeModel::isFileInfo);
    }

    public boolean addImport(String importPackage) {
        return attributeImports.add(importPackage);
    }

    public List<String> getAttributeImports() {
        return attributeImports.stream().distinct().collect(Collectors.toList());
    }

    public List<String> getAttributeImportsJava() {
        return attributeImports.stream().filter(a -> a.startsWith("java.")).distinct().collect(Collectors.toList());
    }

    public List<String> getAttributeImportsDart() {
        return attributeImports.stream().map(U::snakeCase).distinct().collect(Collectors.toList());
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public void addDetail(TemplateModelDetail templateModelDetail) {
        details.add(templateModelDetail);
    }

    public boolean hasDetails() {
        return !details.isEmpty();
    }

    public List<TemplateModelDetail> getDetails() {
        return details;
    }

    public void setDetails(List<TemplateModelDetail> details) {
        this.details = details;
    }

    public String getClassNameFileName() {
        return U.snakeCase(this.className);
    }

    public File getProjectParentFile() {
        return projectParentFile;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public boolean isMaster() {
        return true;
    }

    public boolean isIntegracao() {
        return attributes.stream().anyMatch(a -> a.getName().equalsIgnoreCase("chaveIntegracao"));
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((entityName == null) ? 0 : entityName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TemplateModel other = (TemplateModel) obj;
        if (entityName == null) {
            if (other.entityName != null)
                return false;
        } else if (!entityName.equals(other.entityName))
            return false;
        return true;
    }

    public EnumClassMode getMode() {
        return mode;
    }

    public void setMode(EnumClassMode mode) {
        this.mode = mode;
    }

    public boolean isCollection(){
        return isDocument() || isSubDocument();
    }

    public boolean isDocument() {
        return mode == EnumClassMode.DOCUMENT;
    }

    public boolean isSubDocument() {
        return mode == EnumClassMode.SUB_DOCUMENT;
    }

    public boolean isComposite() {
        return mode == EnumClassMode.COMPOSITE;
    }

}
