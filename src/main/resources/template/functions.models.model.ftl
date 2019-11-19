<#assign className = model.className?cap_first>
import {IEntity} from "../../arquitetura/interface/IEntity";
import {Reference} from "../../arquitetura/utils/GenericUtil";
import {Collection} from "fireorm";
<#list model.getAttributeImports() as import>
import {${import}} from "./${import}";
</#list>

@Collection("${model.collectionName}")
export class ${className} extends IEntity {
<#list model.attributes as attribute>
<#if attribute.isEntity()>
    @Reference(${attribute.typeSimpleName})
</#if>
    ${attribute.name?uncap_first}: ${attribute.getTypescriptType(attribute.typeSimpleName)};

</#list>
}

