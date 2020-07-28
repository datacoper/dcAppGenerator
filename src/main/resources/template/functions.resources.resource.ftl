<#if model.isIntegracao() >
import {GenericIntegrationResource} from "../../arquitetura/generic/GenericIntegrationResource";
<#else >
import {GenericResource} from "../../arquitetura/generic/GenericResource";
</#if>
import {${model.entityName}} from "../models/${model.entityName}";
import {${model.entityName}Service} from "../services/${model.entityName}Service";
import {getCollection} from "../../arquitetura/utils/DatabaseUtils";
import {Path} from "typescript-rest";
import {is} from "easy-injectionjs";

@Path(getCollection(${model.entityName}))
<#if model.isIntegracao() >
export class ${model.entityName}Resource extends GenericIntegrationResource<${model.entityName}>{
<#else >
export class ${model.entityName}Resource extends GenericResource<${model.entityName}>{
</#if>

    constructor() {
        super(${model.entityName}, is(${model.entityName}Service));
    }

}
