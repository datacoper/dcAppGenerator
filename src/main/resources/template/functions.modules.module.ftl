<#assign className = model.className?cap_first>
import {Express} from "express";
import {BaseCounter} from "../../arquitetura/generic/BaseCounter";
import {GenericModule} from "../../arquitetura/generic/GenericModule";
import {keys} from "ts-transformer-keys";
import {${model.entityName}} from "../models/${model.entityName}";
import {${model.entityName}Resource} from "../resources/${model.entityName}Resource";
import {${model.entityName}Service} from "../services/${model.entityName}Service";

export default class ${model.entityName}Module extends GenericModule<${model.entityName}> {

    constructor(expressApp: Express) {
        super({collectionName: "${model.collectionName}"});

        this.service = new ${model.entityName}Service(keys<${model.entityName}>());
        this.counter = new BaseCounter(this.collectionName);
        new ${model.entityName}Resource(expressApp, this.collectionName, this.service, this.counter);

        this.registerTriggers();
    }
}