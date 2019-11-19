package com.datacoper.metadata;

import com.github.underscore.lodash.U;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class TemplateModel {

    private String entityName;

    private String className;

    private String collectionName;

    private Set<String> attributeImports = new TreeSet<>();

    private File projectParentFile;

    private Set<TemplateAttributeModel> attributes = new HashSet<>();

    private Set<TemplateModelDetail> details = new HashSet<>();

    public TemplateModel(File projectParentFile) {
        this.projectParentFile = projectParentFile;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Set<TemplateAttributeModel> getAttributes() {
        if(isIntegracao()){
            return getAttributesIntegracao();
        }
        return Collections.unmodifiableSet(attributes);
    }

    public Set<TemplateAttributeModel> getAttributesIntegracao() {
        return Collections.unmodifiableSet(attributes.stream().filter(a -> !a.isIntegracao()).collect(Collectors.toSet()));
    }

    public void setAttributes(Set<TemplateAttributeModel> attributes) {
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

    public boolean addImport(String importPackage) {
        return attributeImports.add(importPackage);
    }

    public Set<String> getAttributeImports() {
        return attributeImports;
    }

    public Set<String> getAttributeImportsJava() {
        return attributeImports.stream().filter(a -> a.startsWith("java.")).collect(Collectors.toSet());
    }

    public Set<String> getAttributeImportsDart() {
        return attributeImports.stream().map(U::snakeCase).collect(Collectors.toSet());
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

    public Set<TemplateModelDetail> getDetails() {
        return details;
    }

    public void setDetails(Set<TemplateModelDetail> details) {
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

    public boolean isIntegracao(){
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

}
