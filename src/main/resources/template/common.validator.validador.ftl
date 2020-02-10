<#assign entityName = model.entityName>
<#assign className = model.className?cap_first>
<#assign classNameFileName = model.classNameFileName>
import 'package:arquitetura_common/interfaces/validador_generic_crud.dart';
import 'package:produtor_common/base/model/${classNameFileName}.dart';
import 'package:produtor_common/base/repository/${classNameFileName}_repository.dart';
import 'package:arquitetura_common/utils/validate_mandatory_fields.dart';

class ${className}Validador extends ValidatorGenericCRUD<${className}> {
  ${className}Repository repository;

  ${className}Validador(this.repository) : super(repository.session);

  @override
  ValidateMandatoryFields validateRequiredFields(${className} document) {
    ValidateMandatoryFields validateMandatoryFields = ValidateMandatoryFields();

<#list model.attributes as attribute>
  <#if attribute.required>
    validateMandatoryFields.add(document.${attribute.name}, "${attribute.label!}");
  </#if>
</#list>

    return validateMandatoryFields;
  }

  @override
  Future validateDelete(${className} document) async {

  }

  @override
  Future validateInsert(${className} document) async {

  }

  @override
  Future validateUpdate(${className} document) async {

  }
}
