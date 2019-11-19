<#assign className = model.className?cap_first>
import {IEntity} from "../../arquitetura/interface/IEntity";
<#list model.getAttributeImports() as import>
import {${import}} from "./${import}";
</#list>
import {Collection} from "fireorm";
import {Reference} from "../../arquitetura/utils/GenericUtil";

@Collection("${model.collectionName}")
export class ${className} extends IEntity {
<#list model.attributes as attribute>
<#if attribute.isEntity()>
    @Reference(${attribute.typeSimpleName})
</#if>
    ${attribute.name?uncap_first}: ${attribute.getTypescriptType(attribute.typeSimpleName)};
</#list>
}

