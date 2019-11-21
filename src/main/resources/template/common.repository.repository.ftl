<#assign entityName = model.entityName>
<#assign className = model.className?cap_first>
<#assign classNameFileName = model.classNameFileName>
import 'package:produtor_common/arquitetura/generic/generic_repository.dart';
import 'package:produtor_common/base/model/${classNameFileName}.dart';
import 'package:rxdart/subjects.dart';

abstract class ${className}Repository extends GenericRepository<${className}> {


}
