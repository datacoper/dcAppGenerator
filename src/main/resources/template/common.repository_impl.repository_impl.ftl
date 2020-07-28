<#assign entityName = model.entityName>
<#assign className = model.className?cap_first>
<#assign classNameFileName = model.classNameFileName>
import 'package:arquitetura_common/database/crossfire_base.dart';
import 'package:produtor_common/base/generic/generic_repository_impl.dart';
import 'package:produtor_common/base/model/${classNameFileName}.dart';
import 'package:produtor_common/base/repository/${classNameFileName}_repository.dart';
import 'package:produtor_common/base/session.dart';

class ${className}RepositoryImpl extends GenericRepositoryImpl<${className}> implements ${className}Repository {
  ${className}RepositoryImpl(AppSession session) : super(session, session.getColletionName<${className}>());

  @override
  ${className} fromJson(FirebaseDocument FirebaseDocument) {
    return  ${className}.fromJson(prepareToJson(FirebaseDocument));
  }
}
