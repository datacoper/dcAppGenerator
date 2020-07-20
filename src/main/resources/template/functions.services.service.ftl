<#if model.isIntegracao() >
import {BaseServiceIntegracao} from "../../arquitetura/generic/BaseServiceIntegracao";
<#else >
import {BaseServiceCrud} from "../../arquitetura/generic/BaseServiceCrud";
</#if>
import {${model.entityName}} from "../models/${model.entityName}";
import {EasyPrototype} from "easy-injectionjs";

@EasyPrototype()
<#if model.isIntegracao() >
export class ${model.entityName}Service extends BaseServiceIntegracao<${model.entityName}> {
<#else >
export class ${model.entityName}Service extends BaseServiceCrud<${model.entityName}> {
</#if>

    constructor() {
        super(${model.entityName}, [<#list model.getAttributes() as key>"${key.name}", </#list>]);
    }

}