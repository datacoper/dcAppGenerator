<#-- @ftlvariable name="model" type="com.datacoper.metadata.TemplateModel" -->
<#assign className = model.className?cap_first>
<#assign classNameFileName = model.classNameFileName>
import 'dart:async';

import 'package:bloc/base/datacoper_type/future_resolver.dart';
import 'package:bloc/base/endpoints.dart';
import 'package:bloc/base/interfaces/entity_bloc.dart';
import 'package:json_annotation/json_annotation.dart';
import 'package:produtor_common/arquitetura/event/change_event.dart';
import 'package:produtor_common/arquitetura/event/entity_event.dart';
import 'package:produtor_common/arquitetura/proxy/collection.dart';
import 'package:produtor_common/arquitetura/interfaces/i_vigencia.dart';
import 'package:produtor_common/arquitetura/proxy/proxy_manager.dart';
import 'package:produtor_common/arquitetura/proxy/reference.dart';
import 'package:produtor_common/base/converters/converter.dart';
import 'package:produtor_common/base/converters/entity_converter.dart';
<#if model.hasAttributeGeoPoint()>
import 'package:produtor_common/base/converters/geopoint_converter.dart';
</#if>
import 'package:produtor_common/base/utils/date_util.dart';
<#list model.getAttributeImportsDart() as import>
import 'package:produtor_common/base/model/${import}.dart';
</#list>
import 'package:rxdart/subjects.dart';


part '${classNameFileName}.g.dart';

@JsonSerializable()
<#if model.hasAttributeGeoPoint()>
@GeoPointConverter()
</#if>
class ${className} extends EntityEvent {

  @override
  String id;

<#list model.attributes as attribute>
<#if attribute.isInternal()>
  ${attribute.typeSimpleName} _${attribute.name?uncap_first};
<#if attribute.isDate()>
  @JsonKey(fromJson: rawDateTime, toJson: rawDateTime)
</#if>
  ${attribute.typeSimpleName} get ${attribute.name?uncap_first} => _${attribute.name?uncap_first};
  set ${attribute.name?uncap_first}(${attribute.typeSimpleName} value) {
    changes.sink.add(new ChangeEvent("${attribute.name?uncap_first}", _${attribute.name?uncap_first}, value));
    _${attribute.name?uncap_first} = value;
  }
<#elseif attribute.isOneToOne()>
  <#if attribute.isEntity()>
  Reference _${attribute.name?uncap_first};
  @JsonKey(fromJson: referenceFromJson, toJson: referenceToJson)
  Reference get ${attribute.name?uncap_first} => _${attribute.name?uncap_first};
  set ${attribute.name?uncap_first}(Reference value) {
    changes.sink.add(new ChangeEvent("${attribute.name?uncap_first}", _${attribute.name?uncap_first}, value));
    _${attribute.name?uncap_first} = value;
  }
  </#if>
<#elseif attribute.isOneToMany()>
  <#if attribute.isEntity()>
  List<${attribute.typeSimpleName}> _${attribute.name?uncap_first};
  List<${attribute.typeSimpleName}> get ${attribute.name?uncap_first} => _${attribute.name?uncap_first};
  set ${attribute.name?uncap_first}(List<${attribute.typeSimpleName}> value) {
    changes.sink.add(new ChangeEvent("${attribute.name?uncap_first}", _${attribute.name?uncap_first}, value));
    _${attribute.name?uncap_first} = value;
  }
  </#if>
<#elseif attribute.isCollection()>
  Collection _${attribute.name?uncap_first};
  @JsonKey(fromJson: collectionFromJson, toJson: collectionToJson)
  Collection get ${attribute.name?uncap_first} => _${attribute.name?uncap_first};
  set ${attribute.name?uncap_first}(Collection value) {
    changes.sink.add(new ChangeEvent("${attribute.name?uncap_first}", _${attribute.name?uncap_first}, value));
    _${attribute.name?uncap_first} = value;
  }
<#elseif attribute.isComposite()>
  ${attribute.typeSimpleName} _${attribute.name?uncap_first};
  ${attribute.typeSimpleName} get ${attribute.name?uncap_first} => _${attribute.name?uncap_first};
  set ${attribute.name?uncap_first}(${attribute.typeSimpleName} value) {
    changes.sink.add(new ChangeEvent("${attribute.name?uncap_first}", _${attribute.name?uncap_first}, value));
    _${attribute.name?uncap_first} = value;
  }
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
    super.dispose();
  }
}
