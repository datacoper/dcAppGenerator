import {BaseServiceIntegracao} from "../../arquitetura/generic/BaseServiceIntegracao";
import {${model.entityName}} from "../models/${model.entityName}";

export class ${model.entityName}Service extends BaseServiceIntegracao<${model.entityName}> {

    constructor(exports: any, keys: Array<keyof ${model.entityName}>) {
        super(${model.entityName}, keys);
    }
}