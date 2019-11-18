<#assign entityName = model.entityName>
<#assign className = model.className?cap_first>
<#assign classNameFileName = model.classNameFileName>
import 'package:produtor_common/arquitetura/generic/generic_service.dart';
import 'package:produtor_common/base/model/${classNameFileName}.dart';
import 'package:produtor_common/base/repository/${classNameFileName}_repository.dart';

import '${classNameFileName}_validador.dart';

class ${className}Service extends GenericService<${className}> {

${className}Service(${className}Repository repository) :
        super(repository, new ${className}Validador(repository));

}
