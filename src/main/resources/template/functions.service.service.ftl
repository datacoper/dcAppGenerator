import {${model.entityName}} from "../model/${model.entityName}";
import {BaseServiceIntegracao} from "../../arquitetura/generic/BaseServiceIntegracao";

export class ${model.entityName}Service extends BaseServiceIntegracao<${model.entityName}> {

    constructor(exports: any, keys: Array<keyof ${model.entityName}>) {
        super(${model.entityName}, keys);
    }
}