<#if model.isIntegracao() >
import {BaseResourceIntegracao} from "../../arquitetura/generic/BaseResourceIntegracao";
<#else >
import {BaseResourceCrud} from "../../arquitetura/generic/BaseResourceCrud";
</#if>
import {${model.entityName}} from "../models/${model.entityName}";

<#if model.isIntegracao() >
export class ${model.entityName}Resource extends BaseResourceIntegracao<${model.entityName}>{
<#else >
export class ${model.entityName}Resource extends BaseResourceCrud<${model.entityName}>{
</#if>

}