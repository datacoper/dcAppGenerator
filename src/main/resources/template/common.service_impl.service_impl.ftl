<#assign entityName = model.entityName>
<#assign className = model.className?cap_first>
<#assign classNameFileName = model.classNameFileName>
import 'package:arquitetura_common/generic_impl/generic_service_impl.dart';
import 'package:produtor_common/base/model/${classNameFileName}.dart';
import 'package:produtor_common/base/repository/${classNameFileName}_repository.dart';
import 'package:produtor_common/base/service/${classNameFileName}_service.dart';
import 'package:produtor_common/base/validator/${classNameFileName}_validador.dart';

class ${className}ServiceImpl extends GenericServiceImpl<${className}> implements ${className}Service {
  ${className}Repository repository;

  ${className}ServiceImpl(this.repository) :
        super(repository, new ${className}Validador(repository));

}
