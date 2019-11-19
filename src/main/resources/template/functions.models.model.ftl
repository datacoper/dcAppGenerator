<#assign className = model.className?cap_first>
<#if model.isIntegracao() >
import {IEntityIntegracao} from "../../arquitetura/interface/IEntityIntegracao";
<#else >
import {IEntity} from "../../arquitetura/interface/IEntity";
</#if>
import {Reference} from "../../arquitetura/utils/GenericUtil";
import {Collection} from "fireorm";
<#list model.getAttributeImports() as import>
import {${import}} from "./${import}";
</#list>

@Collection("${model.collectionName}")

<#if model.isIntegracao() >
export class ${className} extends IEntityIntegracao {
<#else >
export class ${className} extends IEntity {
</#if>

<#list model.getAttributes() as attribute>
<#if attribute.isEntity()>
    @Reference(${attribute.typeSimpleName})
</#if>
    ${attribute.name?uncap_first}: ${attribute.getTypescriptType(attribute.typeSimpleName)};

</#list>
}

