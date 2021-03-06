<#assign entityName = model.entityName>
<#assign className = model.className?cap_first>
<#assign classNameFileName = model.classNameFileName>
import 'package:arquitetura_common/generic/generic_service.dart';
import 'package:produtor_common/base/model/${classNameFileName}.dart';

abstract class ${className}Service extends GenericService<${className}> {

}
