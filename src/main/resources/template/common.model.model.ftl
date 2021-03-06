<#-- @ftlvariable name="model" type="com.datacoper.metadata.TemplateModel" -->
<#assign className = model.className?cap_first>
<#assign classNameFileName = model.classNameFileName>
import 'dart:async';

import 'package:json_annotation/json_annotation.dart';
import 'package:arquitetura_common/event/change_event.dart';
<#if model.isCollection()>
import 'package:arquitetura_common/event/entity_event.dart';
<#else >
import 'package:arquitetura_common/event/entity_map_event.dart';
</#if>
import 'package:arquitetura_common/proxy/collection.dart';
import 'package:arquitetura_common/interfaces/i_vigencia.dart';
import 'package:arquitetura_common/proxy/reference.dart';
import 'package:arquitetura_common/converters/converter.dart';
<#if model.hasAttributeGeoPoint()>
import 'package:arquitetura_common/converters/geopoint_converter.dart';
<#elseif model.hasAttributeFileInfo()>
import 'package:arquitetura_common/converters/file_converter.dart';
</#if>
import 'package:arquitetura_common/utils/date_util.dart';
<#list model.getAttributeImportsDart() as import>
import 'package:produtor_common/base/model/${import}.dart';
</#list>
import 'package:rxdart/subjects.dart';


part '${classNameFileName}.g.dart';

@JsonSerializable()
<#if model.hasAttributeGeoPoint()>
@GeoPointConverter()
<#elseif model.hasAttributeFileInfo()>
@FileInfoConverter()
</#if>
<#if model.isComposite()>
class ${className} extends EntityMapEvent {
<#else >
class ${className} extends EntityEvent {
</#if>

<#if model.isDocument()>
  @override
  String id;

</#if>
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
  List<${attribute.typeSimpleName}> _${attribute.name?uncap_first} = [];
  List<${attribute.typeSimpleName}> get ${attribute.name?uncap_first} => _${attribute.name?uncap_first};
  set ${attribute.name?uncap_first}(List<${attribute.typeSimpleName}> value) {
    changes.sink.add(new ChangeEvent("${attribute.name?uncap_first}", _${attribute.name?uncap_first}, value));
    _${attribute.name?uncap_first} = value;
  }
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
  ${className}();

  factory ${className}.fromJson(Map<String, dynamic> json) {
    return _$${className}FromJson(json);
  }

  Map<String, dynamic> toJson() {
    return _$${className}ToJson(this);
  }

<#if model.isDocument()>
  @override
    String toString() {
    return '${className} {id: $id}';
  }

  static Future<${className}> fromReference(Reference document) async {
    await document.load();
    return ${className}.fromJson(document.getJson());
  }

  ${className} create(){
    return ${className}.fromJson(joinJson());
  }

</#if>
  @override
  void dispose() {
    super.dispose();
  }
}
