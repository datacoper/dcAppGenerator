<#assign className = model.className?cap_first>
import {Express} from "express";
import {GenericModule} from "../../arquitetura/generic/GenericModule";
import {${model.entityName}} from "../models/${model.entityName}";

export default class ${model.entityName}Module extends GenericModule<${model.entityName}> {

    constructor(appIntegracao: Express) {
        super({
            collectionName: ${model.collectionName},
            subCollectionNames: []
        });

        this.registerIntegracoes(appIntegracao);
        this.registerCronJobs();
    }
}