<#-- @ftlvariable name="model" type="com.datacoper.metadata.TemplateModel" -->
<#assign className = model.className?cap_first>
<#assign classNameFileName = model.classNameFileName>
import 'dart:async';

import 'package:bloc/base/datacoper_type/future_resolver.dart';
import 'package:bloc/base/endpoints.dart';
import 'package:bloc/base/interfaces/entity_bloc.dart';
import 'package:json_annotation/json_annotation.dart';
import 'package:produtor_common/arquitetura/interfaces/i_vigencia.dart';
import 'package:produtor_common/arquitetura/proxy/proxy_manager.dart';
import 'package:produtor_common/base/converters/entity_converter.dart';
import 'package:produtor_common/base/utils/date_util.dart';
<#list model.getAttributeImportsDart() as import>
import 'package:produtor_common/base/model/${import}.dart';
</#list>
import 'package:rxdart/rxdart.dart';


part '${classNameFileName}.g.dart';

@JsonSerializable()
class ${className} extends EntityBloc {

  @override
  String id;

<#list model.attributes as attribute>
<#if attribute.isEntity()>
  final _${attribute.name?uncap_first}SB = BehaviorSubject<dynamic>();
<#else>
  final _${attribute.name?uncap_first}SB = BehaviorSubject<${attribute.typeSimpleName}>();
</#if>
<#if attribute.isDate()>
  @JsonKey(fromJson: rawDateTime, toJson: rawDateTime)
</#if>
<#if attribute.isEntity()>
  @JsonKey(fromJson: lazyLoadEntityFromJson, toJson: lazyLoadEntityToJson)
</#if>
<#if attribute.isEntity()>
  FutureOr<${attribute.typeSimpleName}> get ${attribute.name?uncap_first} {
    if(_${attribute.name?uncap_first}SB.value is Lazy)
      return _${attribute.name?uncap_first}SB.value.load();
    return _${attribute.name?uncap_first}SB.value;
  }
<#else>
  ${attribute.typeSimpleName} get ${attribute.name?uncap_first} => _${attribute.name?uncap_first}SB.value;
</#if>
<#if attribute.isEntity()>
  set ${attribute.name?uncap_first}(dynamic value) => _${attribute.name?uncap_first}SB.sink.add(value);
<#else>
  set ${attribute.name?uncap_first}(${attribute.typeSimpleName} value) => _${attribute.name?uncap_first}SB.sink.add(value);
</#if>
<#if attribute.isEntity()>
  Stream<${attribute.typeSimpleName}> get ${attribute.name?uncap_first}Stream => _${attribute.name?uncap_first}SB.stream as Stream<${attribute.typeSimpleName}>;
<#else>
  Stream<${attribute.typeSimpleName}> get ${attribute.name?uncap_first}Stream => _${attribute.name?uncap_first}SB.stream;
</#if>

</#list>
  @JsonKey(ignore: true)
  FutureResolver self;

  @JsonKey(ignore: true)
  Endpoints endpoints;

  ${className}([
    this.self,
  ]);

  factory ${className}.fromJson(Map<String, dynamic> json) {
    return _$${className}FromJson(json);
  }

  Map<String, dynamic> toJson() {
    return _$${className}ToJson(this);
  }


  @override
  String toString() {
    return '${className} {id: $id}';
  }

  @override
  void dispose() {
<#list model.attributes as attribute>
    _${attribute.name?uncap_first}SB.close();
</#list>
  }
}
