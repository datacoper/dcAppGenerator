<#assign className = model.className?cap_first>
<#if model.isIntegracao()>
    <#assign attributes = model.getAttributesIntegracao()>
<#else>
    <#assign attributes = model.getAttributes()>
</#if>
<#if model.isIntegracao() >
import {IEntityIntegracao} from "../../arquitetura/interface/IEntityIntegracao";
<#else >
import {IEntity} from "../../arquitetura/interface/IEntity";
</#if>
import {Reference} from "../../arquitetura/utils/GenericUtil";
import {Collection, ISubCollection, SubCollection} from "fireorm";
<#list model.getAttributeImports() as import>
import {${import}} from "./${import}";
</#list>

<#if model.isDocument() >
@Collection("${model.collectionName}")
</#if>
<#if model.isIntegracao() >
export class ${className} extends IEntityIntegracao {
<#else >
export class ${className} extends IEntity {
</#if>

<#list attributes as attribute>
<#if attribute.isOneToOne()>
    @Reference(${attribute.typeSimpleName})
</#if>
<#if attribute.isCollection()>
    @SubCollection(${attribute.typeSimpleName})
</#if>
<#if attribute.isCollection()>
    ${attribute.name?uncap_first}?: ISubCollection<${attribute.getTypescriptType(attribute.typeSimpleName)}>;
<#elseif attribute.isOneToMany()>
    ${attribute.name?uncap_first}: ${attribute.getTypescriptType(attribute.typeSimpleName)}[] = [];
<#else>
    ${attribute.name?uncap_first}: ${attribute.getTypescriptType(attribute.typeSimpleName)};
</#if>

</#list>
}

