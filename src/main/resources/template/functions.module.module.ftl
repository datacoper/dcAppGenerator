<#assign className = model.className?cap_first>
import {${model.entityName}} from "../model/${model.entityName}";
import {Express} from "express";
import {${model.entityName}Resource} from "../resource/${model.entityName}Resource";
import {BaseCounter} from "../../arquitetura/generic/BaseCounter";
import {keys} from "ts-transformer-keys";
import {${model.entityName}Service} from "../service/${model.entityName}Service";

export class ${model.entityName}Module {

    constructor(exports: any, expressApp: Express, db: FirebaseFirestore.Firestore) {

        let collectionName = "${model.collectionName}";

        let service  = new ${model.entityName}Service(${model.entityName}, keys<${model.entityName}>());
        let counter  = new BaseCounter(exports, db, collectionName);
        let resource = new ${model.entityName}Resource(expressApp, collectionName, service, counter);
    }
}