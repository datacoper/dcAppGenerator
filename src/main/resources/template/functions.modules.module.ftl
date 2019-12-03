<#assign className = model.className?cap_first>
import {Express} from "express";
import {BaseCounter} from "../../arquitetura/generic/BaseCounter";
import {GenericModule} from "../../arquitetura/generic/GenericModule";
import {${model.entityName}} from "../models/${model.entityName}";
import {${model.entityName}Resource} from "../resources/${model.entityName}Resource";
import {${model.entityName}Service} from "../services/${model.entityName}Service";

export default class ${model.entityName}Module extends GenericModule<${model.entityName}> {

    constructor(appWebApi: Express, appIntegracao: Express, appCronJob: Express) {
        super({collectionName: "${model.collectionName}"});

        let keys = [<#list model.getAttributes() as key>"${key.name}", </#list>]

        this.service = new ${model.entityName}Service(keys);
        this.counter = new BaseCounter(this.collectionName);
        new ${model.entityName}Resource(appWebApi, this.collectionName, this.service, this.counter);

        this.registerTriggers();
        this.registerIntegracoes(appIntegracao);
        this.registerCronJobs(appCronJob);
    }
}