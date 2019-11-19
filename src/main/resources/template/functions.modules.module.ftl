<#assign className = model.className?cap_first>
import {Express} from "express";
import {BaseCounter} from "../../arquitetura/generic/BaseCounter";
import {keys} from "ts-transformer-keys";
import {${model.entityName}} from "../models/${model.entityName}";
import {${model.entityName}Resource} from "../resources/${model.entityName}Resource";
import {${model.entityName}Service} from "../services/${model.entityName}Service";

export default class ${model.entityName}Module {

    constructor(exports: any, expressApp: Express) {

        let collectionName = "${model.collectionName}";

        let service  = new ${model.entityName}Service(${model.entityName}, keys<${model.entityName}>());
        let counter  = new BaseCounter(exports, collectionName);
        let resource = new ${model.entityName}Resource(expressApp, collectionName, service, counter);
    }
}