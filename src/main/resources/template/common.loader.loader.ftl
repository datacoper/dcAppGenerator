<#assign classNameFileName = model.classNameFileName>
<#assign className = model.className?cap_first>
import 'dart:async';

import 'package:bloc_helpers/bloc_helpers.dart';
import 'package:meta/meta.dart';
import 'package:produtor_common/arquitetura/injection/injector.dart';
import 'package:produtor_common/base/model/${classNameFileName}.dart';
import 'package:produtor_common/base/repository/${classNameFileName}_repository.dart';

class ${className}Loader extends CachedRequestBloc<String, ${className}> {
  @override
  @protected
  FutureOr<${className}> request(String documentId) async {
    ${className}Repository repository = InjectorManager.injector.getDependency<${className}Repository>();
    return await repository.find(documentId).first;
  }
}