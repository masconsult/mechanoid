package com.robotoworks.mechanoid.net.generator.strategy;

import com.google.common.base.Objects;
import com.robotoworks.mechanoid.net.generator.CodeGenerationContext;
import com.robotoworks.mechanoid.net.generator.ModelExtensions;
import com.robotoworks.mechanoid.net.generator.strategy.JsonWriterGenerator;
import com.robotoworks.mechanoid.net.netModel.BlockType;
import com.robotoworks.mechanoid.net.netModel.BodyBlock;
import com.robotoworks.mechanoid.net.netModel.Client;
import com.robotoworks.mechanoid.net.netModel.ComplexTypeDeclaration;
import com.robotoworks.mechanoid.net.netModel.ComplexTypeLiteral;
import com.robotoworks.mechanoid.net.netModel.EnumTypeDeclaration;
import com.robotoworks.mechanoid.net.netModel.GenericListType;
import com.robotoworks.mechanoid.net.netModel.Header;
import com.robotoworks.mechanoid.net.netModel.HeaderBlock;
import com.robotoworks.mechanoid.net.netModel.HttpMethod;
import com.robotoworks.mechanoid.net.netModel.IntrinsicType;
import com.robotoworks.mechanoid.net.netModel.Member;
import com.robotoworks.mechanoid.net.netModel.Model;
import com.robotoworks.mechanoid.net.netModel.ParamsBlock;
import com.robotoworks.mechanoid.net.netModel.SimpleMember;
import com.robotoworks.mechanoid.net.netModel.SkipMember;
import com.robotoworks.mechanoid.net.netModel.StringType;
import com.robotoworks.mechanoid.net.netModel.Type;
import com.robotoworks.mechanoid.net.netModel.TypedMember;
import com.robotoworks.mechanoid.net.netModel.UserType;
import com.robotoworks.mechanoid.net.netModel.UserTypeDeclaration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class RequestGenerator {
  private CodeGenerationContext context;
  
  public CodeGenerationContext setContext(final CodeGenerationContext context) {
    CodeGenerationContext _context = this.context = context;
    return _context;
  }
  
  private JsonWriterGenerator jsonWriterGenerator;
  
  public JsonWriterGenerator setJsonWriterGenerator(final JsonWriterGenerator jsonWriterGenerator) {
    JsonWriterGenerator _jsonWriterGenerator = this.jsonWriterGenerator = jsonWriterGenerator;
    return _jsonWriterGenerator;
  }
  
  public CharSequence registerImports() {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  public CharSequence generate(final HttpMethod method, final Model module, final Client client) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _packageName = module.getPackageName();
    _builder.append(_packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence body = this.generateRequestClass(method, module, client);
    _builder.newLineIfNotEmpty();
    CharSequence _registerImports = this.registerImports();
    _builder.append(_registerImports, "");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import android.net.Uri;");
    _builder.newLine();
    _builder.append("import java.util.LinkedHashMap;");
    _builder.newLine();
    _builder.append("import java.util.Set;");
    _builder.newLine();
    StringConcatenation _printImports = this.context.printImports();
    _builder.append(_printImports, "");
    _builder.newLineIfNotEmpty();
    this.context.clearImports();
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append(body, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence generateRequestClass(final HttpMethod method, final Model module, final Client client) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public class ");
    String _name = method.getName();
    String _pascalize = ModelExtensions.pascalize(_name);
    _builder.append(_pascalize, "");
    _builder.append("Request {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("private static final String PATH=\"");
    String _pathAsFormatString = ModelExtensions.getPathAsFormatString(method);
    _builder.append(_pathAsFormatString, "	");
    _builder.append("\";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("private LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public void setHeader(String field, String value) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("headers.put(field, value);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public Set<String> getHeaderKeys() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return headers.keySet();");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public String getHeaderValue(String key) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return headers.get(key);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    Iterable<String> pathArgs = ModelExtensions.getArgsFromPath(method);
    _builder.newLineIfNotEmpty();
    {
      int _size = IterableExtensions.size(pathArgs);
      boolean _greaterThan = (_size > 0);
      if (_greaterThan) {
        {
          for(final String segment : pathArgs) {
            _builder.append("\t");
            _builder.append("private final String ");
            String _substring = segment.substring(1);
            String _camelize = ModelExtensions.camelize(_substring);
            _builder.append(_camelize, "	");
            _builder.append("Segment;");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t");
        _builder.newLine();
      }
    }
    {
      ParamsBlock _params = method.getParams();
      boolean _notEquals = (!Objects.equal(_params, null));
      if (_notEquals) {
        {
          ParamsBlock _params_1 = method.getParams();
          EList<SimpleMember> _params_2 = _params_1.getParams();
          for(final SimpleMember param : _params_2) {
            _builder.append("\t");
            _builder.append("private ");
            IntrinsicType _type = param.getType();
            String _signature = ModelExtensions.signature(_type);
            _builder.append(_signature, "	");
            _builder.append(" ");
            String _name_1 = param.getName();
            String _camelize_1 = ModelExtensions.camelize(_name_1);
            _builder.append(_camelize_1, "	");
            _builder.append("Param;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("private boolean ");
            String _name_2 = param.getName();
            String _camelize_2 = ModelExtensions.camelize(_name_2);
            _builder.append(_camelize_2, "	");
            _builder.append("ParamSet;");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t");
        _builder.append("\t");
        _builder.newLine();
      }
    }
    {
      ParamsBlock _params_3 = client.getParams();
      boolean _notEquals_1 = (!Objects.equal(_params_3, null));
      if (_notEquals_1) {
        {
          ParamsBlock _params_4 = client.getParams();
          EList<SimpleMember> _params_5 = _params_4.getParams();
          for(final SimpleMember param_1 : _params_5) {
            _builder.append("\t");
            _builder.append("private ");
            IntrinsicType _type_1 = param_1.getType();
            String _signature_1 = ModelExtensions.signature(_type_1);
            _builder.append(_signature_1, "	");
            _builder.append(" ");
            String _name_3 = param_1.getName();
            String _camelize_3 = ModelExtensions.camelize(_name_3);
            _builder.append(_camelize_3, "	");
            _builder.append("Param;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("private boolean ");
            String _name_4 = param_1.getName();
            String _camelize_4 = ModelExtensions.camelize(_name_4);
            _builder.append(_camelize_4, "	");
            _builder.append("ParamSet;");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t");
        _builder.append("\t");
        _builder.newLine();
      }
    }
    {
      boolean _hasBody = ModelExtensions.hasBody(method);
      if (_hasBody) {
        _builder.append("\t");
        BodyBlock _body = ModelExtensions.getBody(method);
        BlockType _type_2 = _body.getType();
        CharSequence _generateFieldForType = this.generateFieldForType(_type_2);
        _builder.append(_generateFieldForType, "	");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        BodyBlock _body_1 = ModelExtensions.getBody(method);
        BlockType _type_3 = _body_1.getType();
        CharSequence _generateGetterSetterForType = this.generateGetterSetterForType(_type_3);
        _builder.append(_generateGetterSetterForType, "	");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      ParamsBlock _params_6 = method.getParams();
      boolean _notEquals_2 = (!Objects.equal(_params_6, null));
      if (_notEquals_2) {
        {
          ParamsBlock _params_7 = method.getParams();
          EList<SimpleMember> _params_8 = _params_7.getParams();
          for(final SimpleMember param_2 : _params_8) {
            _builder.append("\t");
            _builder.append("public ");
            String _name_5 = method.getName();
            String _pascalize_1 = ModelExtensions.pascalize(_name_5);
            _builder.append(_pascalize_1, "	");
            _builder.append("Request set");
            String _name_6 = param_2.getName();
            String _pascalize_2 = ModelExtensions.pascalize(_name_6);
            _builder.append(_pascalize_2, "	");
            _builder.append("Param(");
            IntrinsicType _type_4 = param_2.getType();
            String _signature_2 = ModelExtensions.signature(_type_4);
            _builder.append(_signature_2, "	");
            _builder.append(" value) {");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("this.");
            String _name_7 = param_2.getName();
            String _camelize_5 = ModelExtensions.camelize(_name_7);
            _builder.append(_camelize_5, "		");
            _builder.append("Param = value;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("this.");
            String _name_8 = param_2.getName();
            String _camelize_6 = ModelExtensions.camelize(_name_8);
            _builder.append(_camelize_6, "		");
            _builder.append("ParamSet = true;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("return this;");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("}");
            _builder.newLine();
            _builder.append("\t");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("public boolean is");
            String _name_9 = param_2.getName();
            String _pascalize_3 = ModelExtensions.pascalize(_name_9);
            _builder.append(_pascalize_3, "	");
            _builder.append("ParamSet() {");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("return ");
            String _name_10 = param_2.getName();
            String _camelize_7 = ModelExtensions.camelize(_name_10);
            _builder.append(_camelize_7, "		");
            _builder.append("ParamSet;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("}");
            _builder.newLine();
          }
        }
        _builder.append("\t");
        _builder.append("\t");
        _builder.newLine();
      }
    }
    {
      ParamsBlock _params_9 = client.getParams();
      boolean _notEquals_3 = (!Objects.equal(_params_9, null));
      if (_notEquals_3) {
        {
          ParamsBlock _params_10 = client.getParams();
          EList<SimpleMember> _params_11 = _params_10.getParams();
          for(final SimpleMember param_3 : _params_11) {
            _builder.append("\t");
            _builder.append("public ");
            String _name_11 = method.getName();
            String _pascalize_4 = ModelExtensions.pascalize(_name_11);
            _builder.append(_pascalize_4, "	");
            _builder.append("Request set");
            String _name_12 = param_3.getName();
            String _pascalize_5 = ModelExtensions.pascalize(_name_12);
            _builder.append(_pascalize_5, "	");
            _builder.append("Param(");
            IntrinsicType _type_5 = param_3.getType();
            String _signature_3 = ModelExtensions.signature(_type_5);
            _builder.append(_signature_3, "	");
            _builder.append(" value) {");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("this.");
            String _name_13 = param_3.getName();
            String _camelize_8 = ModelExtensions.camelize(_name_13);
            _builder.append(_camelize_8, "		");
            _builder.append("Param = value;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("this.");
            String _name_14 = param_3.getName();
            String _camelize_9 = ModelExtensions.camelize(_name_14);
            _builder.append(_camelize_9, "		");
            _builder.append("ParamSet = true;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("return this;");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("}");
            _builder.newLine();
            _builder.append("\t");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("public boolean is");
            String _name_15 = param_3.getName();
            String _pascalize_6 = ModelExtensions.pascalize(_name_15);
            _builder.append(_pascalize_6, "	");
            _builder.append("ParamSet() {");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("return ");
            String _name_16 = param_3.getName();
            String _camelize_10 = ModelExtensions.camelize(_name_16);
            _builder.append(_camelize_10, "		");
            _builder.append("ParamSet;");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("}");
            _builder.newLine();
          }
        }
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.append("public ");
    String _name_17 = method.getName();
    String _pascalize_7 = ModelExtensions.pascalize(_name_17);
    _builder.append(_pascalize_7, "	");
    _builder.append("Request(");
    String _path = method.getPath();
    BodyBlock _body_2 = ModelExtensions.getBody(method);
    String _generateRequestConstructorArgs = this.generateRequestConstructorArgs(_path, _body_2);
    _builder.append(_generateRequestConstructorArgs, "	");
    _builder.append("){");
    _builder.newLineIfNotEmpty();
    {
      HeaderBlock _headers = method.getHeaders();
      boolean _notEquals_4 = (!Objects.equal(_headers, null));
      if (_notEquals_4) {
        {
          HeaderBlock _headers_1 = method.getHeaders();
          EList<Header> _headers_2 = _headers_1.getHeaders();
          for(final Header header : _headers_2) {
            _builder.append("\t\t");
            _builder.append("headers.put(\"");
            String _name_18 = header.getName();
            _builder.append(_name_18, "		");
            _builder.append("\",\"");
            String _value = header.getValue();
            _builder.append(_value, "		");
            _builder.append("\");");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t\t");
        _builder.newLine();
      }
    }
    {
      int _size_1 = IterableExtensions.size(pathArgs);
      boolean _greaterThan_1 = (_size_1 > 0);
      if (_greaterThan_1) {
        {
          for(final String segment_1 : pathArgs) {
            _builder.append("\t\t");
            _builder.append("this.");
            String _substring_1 = segment_1.substring(1);
            String _camelize_11 = ModelExtensions.camelize(_substring_1);
            _builder.append(_camelize_11, "		");
            _builder.append("Segment = ");
            String _substring_2 = segment_1.substring(1);
            String _camelize_12 = ModelExtensions.camelize(_substring_2);
            _builder.append(_camelize_12, "		");
            _builder.append("Segment;");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      boolean _hasBody_1 = ModelExtensions.hasBody(method);
      if (_hasBody_1) {
        _builder.append("\t\t");
        BodyBlock _body_3 = ModelExtensions.getBody(method);
        BlockType _type_6 = _body_3.getType();
        CharSequence _generateConstructorAssignmentForType = this.generateConstructorAssignmentForType(_type_6);
        _builder.append(_generateConstructorAssignmentForType, "		");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    {
      boolean _hasBody_2 = ModelExtensions.hasBody(method);
      if (_hasBody_2) {
        _builder.append("\t");
        this.context.registerImport("com.robotoworks.mechanoid.net.TransformerProvider");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        this.context.registerImport("com.robotoworks.mechanoid.net.TransformException");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        this.context.registerImport("com.robotoworks.mechanoid.util.Closeables");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        this.context.registerImport("java.io.OutputStream");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("void writeBody(TransformerProvider transformerProvider, OutputStream stream) throws TransformException {");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        BodyBlock _body_4 = ModelExtensions.getBody(method);
        BodyBlock _body_5 = ModelExtensions.getBody(method);
        BlockType _type_7 = _body_5.getType();
        CharSequence _generateSerializationStatementForType = this.generateSerializationStatementForType(method, _body_4, _type_7);
        _builder.append(_generateSerializationStatementForType, "		");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.append("public String createUrl(String baseUrl){");
    _builder.newLine();
    {
      String _path_1 = method.getPath();
      boolean _hasArgs = this.hasArgs(_path_1);
      if (_hasArgs) {
        _builder.append("\t\t");
        _builder.append("Uri.Builder uriBuilder = Uri.parse(String.format(baseUrl + PATH");
        String _path_2 = method.getPath();
        String _pathToStringFormatArgs = this.pathToStringFormatArgs(_path_2);
        _builder.append(_pathToStringFormatArgs, "		");
        _builder.append(")).buildUpon();");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t\t");
        _builder.append("Uri.Builder uriBuilder = Uri.parse(baseUrl + PATH).buildUpon();");
        _builder.newLine();
      }
    }
    _builder.append("\t\t\t");
    _builder.newLine();
    {
      ParamsBlock _params_12 = method.getParams();
      boolean _notEquals_5 = (!Objects.equal(_params_12, null));
      if (_notEquals_5) {
        {
          ParamsBlock _params_13 = method.getParams();
          EList<SimpleMember> _params_14 = _params_13.getParams();
          for(final SimpleMember param_4 : _params_14) {
            _builder.append("\t\t");
            _builder.append("if(");
            String _name_19 = param_4.getName();
            String _camelize_13 = ModelExtensions.camelize(_name_19);
            _builder.append(_camelize_13, "		");
            _builder.append("ParamSet){");
            _builder.newLineIfNotEmpty();
            {
              IntrinsicType _type_8 = param_4.getType();
              if ((_type_8 instanceof StringType)) {
                _builder.append("\t\t");
                _builder.append("\t");
                _builder.append("uriBuilder.appendQueryParameter(\"");
                String _name_20 = param_4.getName();
                _builder.append(_name_20, "			");
                _builder.append("\", ");
                String _name_21 = param_4.getName();
                String _camelize_14 = ModelExtensions.camelize(_name_21);
                _builder.append(_camelize_14, "			");
                _builder.append("Param);");
                _builder.newLineIfNotEmpty();
              } else {
                _builder.append("\t\t");
                _builder.append("\t");
                _builder.append("uriBuilder.appendQueryParameter(\"");
                String _name_22 = param_4.getName();
                _builder.append(_name_22, "			");
                _builder.append("\", String.valueOf(");
                String _name_23 = param_4.getName();
                String _camelize_15 = ModelExtensions.camelize(_name_23);
                _builder.append(_camelize_15, "			");
                _builder.append("Param));");
                _builder.newLineIfNotEmpty();
              }
            }
            _builder.append("\t\t");
            _builder.append("}");
            _builder.newLine();
          }
        }
        _builder.append("\t\t");
        _builder.newLine();
      }
    }
    {
      ParamsBlock _params_15 = client.getParams();
      boolean _notEquals_6 = (!Objects.equal(_params_15, null));
      if (_notEquals_6) {
        {
          ParamsBlock _params_16 = client.getParams();
          EList<SimpleMember> _params_17 = _params_16.getParams();
          for(final SimpleMember param_5 : _params_17) {
            _builder.append("\t\t");
            _builder.append("if(");
            String _name_24 = param_5.getName();
            String _camelize_16 = ModelExtensions.camelize(_name_24);
            _builder.append(_camelize_16, "		");
            _builder.append("ParamSet){");
            _builder.newLineIfNotEmpty();
            {
              IntrinsicType _type_9 = param_5.getType();
              if ((_type_9 instanceof StringType)) {
                _builder.append("\t\t");
                _builder.append("\t");
                _builder.append("uriBuilder.appendQueryParameter(\"");
                String _name_25 = param_5.getName();
                _builder.append(_name_25, "			");
                _builder.append("\", ");
                String _name_26 = param_5.getName();
                String _camelize_17 = ModelExtensions.camelize(_name_26);
                _builder.append(_camelize_17, "			");
                _builder.append("Param);");
                _builder.newLineIfNotEmpty();
              } else {
                _builder.append("\t\t");
                _builder.append("\t");
                _builder.append("uriBuilder.appendQueryParameter(\"");
                String _name_27 = param_5.getName();
                _builder.append(_name_27, "			");
                _builder.append("\", String.valueOf(");
                String _name_28 = param_5.getName();
                String _camelize_18 = ModelExtensions.camelize(_name_28);
                _builder.append(_camelize_18, "			");
                _builder.append("Param));");
                _builder.newLineIfNotEmpty();
              }
            }
            _builder.append("\t\t");
            _builder.append("}");
            _builder.newLine();
          }
        }
        _builder.append("\t\t");
        _builder.newLine();
      }
    }
    _builder.append("\t\t");
    _builder.append("return uriBuilder.toString();\t\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence generateSerializationStatementHeader(final boolean withReader) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if (withReader) {
        this.context.registerImport("com.robotoworks.mechanoid.internal.util.JsonWriter");
        _builder.newLineIfNotEmpty();
        this.context.registerImport("java.io.OutputStreamWriter");
        _builder.newLineIfNotEmpty();
        this.context.registerImport("java.nio.charset.Charset");
        _builder.newLineIfNotEmpty();
        _builder.append("JsonWriter target = null;");
        _builder.newLine();
      }
    }
    _builder.append("try {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("if(stream != null) {");
    _builder.newLine();
    {
      if (withReader) {
        _builder.append("\t\t");
        _builder.append("target = new JsonWriter(new OutputStreamWriter(stream, Charset.defaultCharset()));");
        _builder.newLine();
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence generateSerializationStatementFooter(final boolean withReader) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("} catch(Exception x) {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("throw new TransformException(x);");
    _builder.newLine();
    _builder.append("} finally {");
    _builder.newLine();
    {
      if (withReader) {
        _builder.append("\t");
        _builder.append("Closeables.closeSilently(target);");
        _builder.newLine();
      } else {
        _builder.append("\t");
        _builder.append("Closeables.closeSilently(stream);");
        _builder.newLine();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence _generateFieldForType(final ComplexTypeLiteral type) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Member> _members = type.getMembers();
      for(final Member member : _members) {
        CharSequence _generateFieldForMember = this.generateFieldForMember(member);
        _builder.append(_generateFieldForMember, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  protected CharSequence _generateFieldForType(final IntrinsicType type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("private final ");
    String _signature = ModelExtensions.signature(type);
    _builder.append(_signature, "");
    _builder.append(" value;");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _generateFieldForType(final GenericListType type) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Type _elementType = type.getElementType();
      if ((_elementType instanceof IntrinsicType)) {
        _builder.append("private final ");
        String _signature = ModelExtensions.signature(type);
        _builder.append(_signature, "");
        _builder.append(" values;");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("private final ");
        String _signature_1 = ModelExtensions.signature(type);
        _builder.append(_signature_1, "");
        _builder.append(" ");
        String _innerSignature = ModelExtensions.innerSignature(type);
        String _camelize = ModelExtensions.camelize(_innerSignature);
        String _pluralize = ModelExtensions.pluralize(_camelize);
        _builder.append(_pluralize, "");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  protected CharSequence _generateFieldForType(final UserType type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("private final ");
    String _signature = ModelExtensions.signature(type);
    _builder.append(_signature, "");
    _builder.append(" ");
    String _signature_1 = ModelExtensions.signature(type);
    String _camelize = ModelExtensions.camelize(_signature_1);
    _builder.append(_camelize, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _generateFieldForMember(final TypedMember member) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("private final ");
    Type _type = member.getType();
    String _signature = ModelExtensions.signature(_type);
    _builder.append(_signature, "");
    _builder.append(" ");
    String _identifier = ModelExtensions.toIdentifier(member);
    _builder.append(_identifier, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _generateFieldForMember(final SkipMember member) {
    StringConcatenation _builder = new StringConcatenation();
    ComplexTypeLiteral _literal = member.getLiteral();
    CharSequence _generateFieldForType = this.generateFieldForType(_literal);
    _builder.append(_generateFieldForType, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _generateGetterSetterForType(final ComplexTypeLiteral type) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Member> _members = type.getMembers();
      for(final Member member : _members) {
        CharSequence _generateGetterSetterForMember = this.generateGetterSetterForMember(member);
        _builder.append(_generateGetterSetterForMember, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  protected CharSequence _generateGetterSetterForType(final IntrinsicType type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public ");
    String _signature = ModelExtensions.signature(type);
    _builder.append(_signature, "");
    _builder.append(" getValue() {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("return value;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence _generateGetterSetterForType(final GenericListType type) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Type _elementType = type.getElementType();
      if ((_elementType instanceof IntrinsicType)) {
        _builder.append("public ");
        String _signature = ModelExtensions.signature(type);
        _builder.append(_signature, "");
        _builder.append(" getValues() {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("return values;");
        _builder.newLine();
        _builder.append("}");
        _builder.newLine();
      } else {
        _builder.append("public ");
        String _signature_1 = ModelExtensions.signature(type);
        _builder.append(_signature_1, "");
        _builder.append(" get");
        String _innerSignature = ModelExtensions.innerSignature(type);
        String _pascalize = ModelExtensions.pascalize(_innerSignature);
        String _pluralize = ModelExtensions.pluralize(_pascalize);
        _builder.append(_pluralize, "");
        _builder.append("() {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("return ");
        String _innerSignature_1 = ModelExtensions.innerSignature(type);
        String _camelize = ModelExtensions.camelize(_innerSignature_1);
        String _pluralize_1 = ModelExtensions.pluralize(_camelize);
        _builder.append(_pluralize_1, "	");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("}");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  protected CharSequence _generateGetterSetterForType(final UserType type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public ");
    String _signature = ModelExtensions.signature(type);
    _builder.append(_signature, "");
    _builder.append(" get");
    String _signature_1 = ModelExtensions.signature(type);
    String _pascalize = ModelExtensions.pascalize(_signature_1);
    _builder.append(_pascalize, "");
    _builder.append("() {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("return ");
    String _signature_2 = ModelExtensions.signature(type);
    String _camelize = ModelExtensions.camelize(_signature_2);
    _builder.append(_camelize, "	");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence _generateGetterSetterForMember(final TypedMember member) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public ");
    Type _type = member.getType();
    String _signature = ModelExtensions.signature(_type);
    _builder.append(_signature, "");
    _builder.append(" ");
    String _getMethodName = ModelExtensions.toGetMethodName(member);
    _builder.append(_getMethodName, "");
    _builder.append("() {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("return ");
    String _identifier = ModelExtensions.toIdentifier(member);
    _builder.append(_identifier, "	");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence _generateGetterSetterForMember(final SkipMember member) {
    StringConcatenation _builder = new StringConcatenation();
    ComplexTypeLiteral _literal = member.getLiteral();
    CharSequence _generateGetterSetterForType = this.generateGetterSetterForType(_literal);
    _builder.append(_generateGetterSetterForType, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _generateConstructorAssignmentForType(final ComplexTypeLiteral type) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Member> _members = type.getMembers();
      for(final Member member : _members) {
        CharSequence _generateConstructorAssignmentForMember = this.generateConstructorAssignmentForMember(member);
        _builder.append(_generateConstructorAssignmentForMember, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  protected CharSequence _generateConstructorAssignmentForType(final IntrinsicType type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("this.value = value;");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence _generateConstructorAssignmentForType(final GenericListType type) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Type _elementType = type.getElementType();
      if ((_elementType instanceof IntrinsicType)) {
        _builder.append("this.values = values;");
        _builder.newLine();
      } else {
        _builder.append("this.");
        String _innerSignature = ModelExtensions.innerSignature(type);
        String _camelize = ModelExtensions.camelize(_innerSignature);
        String _pluralize = ModelExtensions.pluralize(_camelize);
        _builder.append(_pluralize, "");
        _builder.append(" = ");
        String _innerSignature_1 = ModelExtensions.innerSignature(type);
        String _camelize_1 = ModelExtensions.camelize(_innerSignature_1);
        String _pluralize_1 = ModelExtensions.pluralize(_camelize_1);
        _builder.append(_pluralize_1, "");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  protected CharSequence _generateConstructorAssignmentForType(final UserType type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("this.");
    String _signature = ModelExtensions.signature(type);
    String _camelize = ModelExtensions.camelize(_signature);
    _builder.append(_camelize, "");
    _builder.append(" = ");
    String _signature_1 = ModelExtensions.signature(type);
    String _camelize_1 = ModelExtensions.camelize(_signature_1);
    _builder.append(_camelize_1, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _generateConstructorAssignmentForMember(final TypedMember member) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("this.");
    String _identifier = ModelExtensions.toIdentifier(member);
    _builder.append(_identifier, "");
    _builder.append(" = ");
    String _identifier_1 = ModelExtensions.toIdentifier(member);
    _builder.append(_identifier_1, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _generateConstructorAssignmentForMember(final SkipMember member) {
    StringConcatenation _builder = new StringConcatenation();
    ComplexTypeLiteral _literal = member.getLiteral();
    CharSequence _generateConstructorAssignmentForType = this.generateConstructorAssignmentForType(_literal);
    _builder.append(_generateConstructorAssignmentForType, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _generateSerializationStatementForType(final HttpMethod method, final BodyBlock body, final IntrinsicType type) {
    StringConcatenation _builder = new StringConcatenation();
    this.context.registerImport("java.io.PrintStream");
    _builder.newLineIfNotEmpty();
    CharSequence _generateSerializationStatementHeader = this.generateSerializationStatementHeader(false);
    _builder.append(_generateSerializationStatementHeader, "");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("PrintStream ps = new PrintStream(stream);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ps.print(value);");
    _builder.newLine();
    CharSequence _generateSerializationStatementFooter = this.generateSerializationStatementFooter(false);
    _builder.append(_generateSerializationStatementFooter, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _generateSerializationStatementForType(final HttpMethod method, final BodyBlock body, final ComplexTypeLiteral type) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _generateSerializationStatementHeader = this.generateSerializationStatementHeader(true);
    _builder.append(_generateSerializationStatementHeader, "");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    String _name = method.getName();
    String _pascalize = ModelExtensions.pascalize(_name);
    _builder.append(_pascalize, "	");
    _builder.append("Request source = this;");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    CharSequence _genWriteComplexTypeLiteral = this.jsonWriterGenerator.genWriteComplexTypeLiteral(type);
    _builder.append(_genWriteComplexTypeLiteral, "	");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _generateSerializationStatementFooter = this.generateSerializationStatementFooter(true);
    _builder.append(_generateSerializationStatementFooter, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _generateSerializationStatementForType(final HttpMethod method, final BodyBlock body, final UserType type) {
    UserTypeDeclaration _declaration = type.getDeclaration();
    CharSequence _generateSerializationStatementForUserType = this.generateSerializationStatementForUserType(body, type, _declaration);
    return _generateSerializationStatementForUserType;
  }
  
  protected CharSequence _generateSerializationStatementForUserType(final BodyBlock body, final UserType type, final ComplexTypeDeclaration declaration) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _generateSerializationStatementHeader = this.generateSerializationStatementHeader(true);
    _builder.append(_generateSerializationStatementHeader, "");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("transformerProvider.get(");
    String _signature = ModelExtensions.signature(type);
    _builder.append(_signature, "	");
    _builder.append("OutputTransformer.class).transform(");
    String _signature_1 = ModelExtensions.signature(type);
    String _camelize = ModelExtensions.camelize(_signature_1);
    _builder.append(_camelize, "	");
    _builder.append(", target);");
    _builder.newLineIfNotEmpty();
    CharSequence _generateSerializationStatementFooter = this.generateSerializationStatementFooter(true);
    _builder.append(_generateSerializationStatementFooter, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _generateSerializationStatementForUserType(final BodyBlock body, final UserType type, final EnumTypeDeclaration declaration) {
    StringConcatenation _builder = new StringConcatenation();
    this.context.registerImport("java.io.PrintStream");
    _builder.newLineIfNotEmpty();
    CharSequence _generateSerializationStatementHeader = this.generateSerializationStatementHeader(false);
    _builder.append(_generateSerializationStatementHeader, "");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("PrintStream ps = new PrintStream(stream);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ps.print(");
    String _signature = ModelExtensions.signature(type);
    String _camelize = ModelExtensions.camelize(_signature);
    _builder.append(_camelize, "	");
    _builder.append(".getValue());");
    _builder.newLineIfNotEmpty();
    CharSequence _generateSerializationStatementFooter = this.generateSerializationStatementFooter(false);
    _builder.append(_generateSerializationStatementFooter, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _generateSerializationStatementForType(final HttpMethod method, final BodyBlock body, final GenericListType type) {
    Type _elementType = type.getElementType();
    CharSequence _generateSerializationStatementForGenericListType = this.generateSerializationStatementForGenericListType(body, type, _elementType);
    return _generateSerializationStatementForGenericListType;
  }
  
  protected CharSequence _generateSerializationStatementForGenericListType(final BodyBlock body, final GenericListType type, final IntrinsicType elementType) {
    StringConcatenation _builder = new StringConcatenation();
    this.context.registerImport("com.robotoworks.mechanoid.internal.util.JsonUtil");
    _builder.newLineIfNotEmpty();
    this.context.registerImport("java.util.List");
    _builder.newLineIfNotEmpty();
    CharSequence _generateSerializationStatementHeader = this.generateSerializationStatementHeader(true);
    _builder.append(_generateSerializationStatementHeader, "");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("JsonUtil.write");
    String _boxedTypeSignature = ModelExtensions.getBoxedTypeSignature(elementType);
    _builder.append(_boxedTypeSignature, "	");
    _builder.append("List(target, values);");
    _builder.newLineIfNotEmpty();
    CharSequence _generateSerializationStatementFooter = this.generateSerializationStatementFooter(true);
    _builder.append(_generateSerializationStatementFooter, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _generateSerializationStatementForGenericListType(final BodyBlock body, final GenericListType type, final UserType elementType) {
    UserTypeDeclaration _declaration = elementType.getDeclaration();
    CharSequence _generateSerializationStatementForUserTypeGenericList = this.generateSerializationStatementForUserTypeGenericList(body, type, elementType, _declaration);
    return _generateSerializationStatementForUserTypeGenericList;
  }
  
  protected CharSequence _generateSerializationStatementForUserTypeGenericList(final BodyBlock body, final GenericListType type, final UserType elementType, final ComplexTypeDeclaration declaration) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _generateSerializationStatementHeader = this.generateSerializationStatementHeader(true);
    _builder.append(_generateSerializationStatementHeader, "");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("transformerProvider.get(");
    String _innerSignature = ModelExtensions.innerSignature(type);
    _builder.append(_innerSignature, "	");
    _builder.append("ListOutputTransformer.class).transform(");
    String _innerSignature_1 = ModelExtensions.innerSignature(type);
    String _camelize = ModelExtensions.camelize(_innerSignature_1);
    String _pluralize = ModelExtensions.pluralize(_camelize);
    _builder.append(_pluralize, "	");
    _builder.append(", target);");
    _builder.newLineIfNotEmpty();
    CharSequence _generateSerializationStatementFooter = this.generateSerializationStatementFooter(true);
    _builder.append(_generateSerializationStatementFooter, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _generateSerializationStatementForUserTypeGenericList(final BodyBlock body, final GenericListType type, final UserType elementType, final EnumTypeDeclaration declaration) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _generateSerializationStatementHeader = this.generateSerializationStatementHeader(true);
    _builder.append(_generateSerializationStatementHeader, "");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("target.beginArray();");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("for(");
    String _innerSignature = ModelExtensions.innerSignature(type);
    _builder.append(_innerSignature, "	");
    _builder.append(" element:");
    String _innerSignature_1 = ModelExtensions.innerSignature(type);
    String _camelize = ModelExtensions.camelize(_innerSignature_1);
    String _pluralize = ModelExtensions.pluralize(_camelize);
    _builder.append(_pluralize, "	");
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("target.put(element.getValue());");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("target.endArray();");
    _builder.newLine();
    _builder.newLine();
    CharSequence _generateSerializationStatementFooter = this.generateSerializationStatementFooter(true);
    _builder.append(_generateSerializationStatementFooter, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  /**
   * Converts a path (eg:- /qux/:fooparam/:quxparam/bar
   * into constructor arguments
   */
  public String generateRequestConstructorArgs(final String path, final BodyBlock body) {
    ArrayList<String> _arrayList = new ArrayList<String>();
    ArrayList<String> args = _arrayList;
    Iterable<String> _argsFromPath = ModelExtensions.getArgsFromPath(path);
    for (final String pathArg : _argsFromPath) {
      String _substring = pathArg.substring(1);
      String _camelize = ModelExtensions.camelize(_substring);
      String _plus = ("\tString " + _camelize);
      String _plus_1 = (_plus + "Segment");
      args.add(_plus_1);
    }
    boolean _notEquals = (!Objects.equal(body, null));
    if (_notEquals) {
      BlockType _type = body.getType();
      this.buildConstructorArgsForType(_type, args);
    }
    return IterableExtensions.join(args, ",\n");
  }
  
  protected void _buildConstructorArgsForType(final ComplexTypeLiteral type, final List<String> args) {
    EList<Member> _members = type.getMembers();
    for (final Member member : _members) {
      if ((member instanceof TypedMember)) {
        Type _type = ((TypedMember) member).getType();
        String _signature = ModelExtensions.signature(_type);
        String _plus = (_signature + " ");
        String _identifier = ModelExtensions.toIdentifier(member);
        String _plus_1 = (_plus + _identifier);
        args.add(_plus_1);
      } else {
        if ((member instanceof SkipMember)) {
          ComplexTypeLiteral _literal = ((SkipMember) member).getLiteral();
          this.buildConstructorArgsForType(_literal, args);
        }
      }
    }
  }
  
  protected void _buildConstructorArgsForType(final IntrinsicType type, final List<String> args) {
    String _signature = ModelExtensions.signature(type);
    String _plus = (_signature + " value");
    args.add(_plus);
  }
  
  protected void _buildConstructorArgsForType(final GenericListType type, final List<String> args) {
    Type _elementType = type.getElementType();
    if ((_elementType instanceof IntrinsicType)) {
      String _signature = ModelExtensions.signature(type);
      String _plus = (_signature + " values");
      args.add(_plus);
    } else {
      String _signature_1 = ModelExtensions.signature(type);
      String _plus_1 = (_signature_1 + " ");
      String _innerSignature = ModelExtensions.innerSignature(type);
      String _camelize = ModelExtensions.camelize(_innerSignature);
      String _pluralize = ModelExtensions.pluralize(_camelize);
      String _plus_2 = (_plus_1 + _pluralize);
      args.add(_plus_2);
    }
  }
  
  protected void _buildConstructorArgsForType(final UserType type, final List<String> args) {
    String _signature = ModelExtensions.signature(type);
    String _plus = (_signature + " ");
    String _signature_1 = ModelExtensions.signature(type);
    String _camelize = ModelExtensions.camelize(_signature_1);
    String _plus_1 = (_plus + _camelize);
    args.add(_plus_1);
  }
  
  public String pathToStringFormatArgs(final String path) {
    String[] _split = path.split("/|\\.");
    final Function1<String,Boolean> _function = new Function1<String,Boolean>() {
        public Boolean apply(final String seg) {
          boolean _startsWith = seg.startsWith(":");
          return Boolean.valueOf(_startsWith);
        }
      };
    Iterable<String> _filter = IterableExtensions.<String>filter(((Iterable<String>)Conversions.doWrapArray(_split)), _function);
    final Function1<String,String> _function_1 = new Function1<String,String>() {
        public String apply(final String arg) {
          String _substring = arg.substring(1);
          String _camelize = ModelExtensions.camelize(_substring);
          String _plus = (_camelize + "Segment");
          return _plus;
        }
      };
    String _join = IterableExtensions.<String>join(_filter, ", ", _function_1);
    String _plus = (", " + _join);
    return _plus;
  }
  
  public boolean hasArgs(final String path) {
    boolean _and = false;
    boolean _notEquals = (!Objects.equal(path, null));
    if (!_notEquals) {
      _and = false;
    } else {
      boolean _contains = path.contains(":");
      _and = (_notEquals && _contains);
    }
    return _and;
  }
  
  public CharSequence generateFieldForType(final BlockType type) {
    if (type instanceof GenericListType) {
      return _generateFieldForType((GenericListType)type);
    } else if (type instanceof IntrinsicType) {
      return _generateFieldForType((IntrinsicType)type);
    } else if (type instanceof UserType) {
      return _generateFieldForType((UserType)type);
    } else if (type instanceof ComplexTypeLiteral) {
      return _generateFieldForType((ComplexTypeLiteral)type);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(type).toString());
    }
  }
  
  public CharSequence generateFieldForMember(final Member member) {
    if (member instanceof SkipMember) {
      return _generateFieldForMember((SkipMember)member);
    } else if (member instanceof TypedMember) {
      return _generateFieldForMember((TypedMember)member);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(member).toString());
    }
  }
  
  public CharSequence generateGetterSetterForType(final BlockType type) {
    if (type instanceof GenericListType) {
      return _generateGetterSetterForType((GenericListType)type);
    } else if (type instanceof IntrinsicType) {
      return _generateGetterSetterForType((IntrinsicType)type);
    } else if (type instanceof UserType) {
      return _generateGetterSetterForType((UserType)type);
    } else if (type instanceof ComplexTypeLiteral) {
      return _generateGetterSetterForType((ComplexTypeLiteral)type);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(type).toString());
    }
  }
  
  public CharSequence generateGetterSetterForMember(final Member member) {
    if (member instanceof SkipMember) {
      return _generateGetterSetterForMember((SkipMember)member);
    } else if (member instanceof TypedMember) {
      return _generateGetterSetterForMember((TypedMember)member);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(member).toString());
    }
  }
  
  public CharSequence generateConstructorAssignmentForType(final BlockType type) {
    if (type instanceof GenericListType) {
      return _generateConstructorAssignmentForType((GenericListType)type);
    } else if (type instanceof IntrinsicType) {
      return _generateConstructorAssignmentForType((IntrinsicType)type);
    } else if (type instanceof UserType) {
      return _generateConstructorAssignmentForType((UserType)type);
    } else if (type instanceof ComplexTypeLiteral) {
      return _generateConstructorAssignmentForType((ComplexTypeLiteral)type);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(type).toString());
    }
  }
  
  public CharSequence generateConstructorAssignmentForMember(final Member member) {
    if (member instanceof SkipMember) {
      return _generateConstructorAssignmentForMember((SkipMember)member);
    } else if (member instanceof TypedMember) {
      return _generateConstructorAssignmentForMember((TypedMember)member);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(member).toString());
    }
  }
  
  public CharSequence generateSerializationStatementForType(final HttpMethod method, final BodyBlock body, final BlockType type) {
    if (type instanceof GenericListType) {
      return _generateSerializationStatementForType(method, body, (GenericListType)type);
    } else if (type instanceof IntrinsicType) {
      return _generateSerializationStatementForType(method, body, (IntrinsicType)type);
    } else if (type instanceof UserType) {
      return _generateSerializationStatementForType(method, body, (UserType)type);
    } else if (type instanceof ComplexTypeLiteral) {
      return _generateSerializationStatementForType(method, body, (ComplexTypeLiteral)type);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(method, body, type).toString());
    }
  }
  
  public CharSequence generateSerializationStatementForUserType(final BodyBlock body, final UserType type, final UserTypeDeclaration declaration) {
    if (declaration instanceof ComplexTypeDeclaration) {
      return _generateSerializationStatementForUserType(body, type, (ComplexTypeDeclaration)declaration);
    } else if (declaration instanceof EnumTypeDeclaration) {
      return _generateSerializationStatementForUserType(body, type, (EnumTypeDeclaration)declaration);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(body, type, declaration).toString());
    }
  }
  
  public CharSequence generateSerializationStatementForGenericListType(final BodyBlock body, final GenericListType type, final Type elementType) {
    if (elementType instanceof IntrinsicType) {
      return _generateSerializationStatementForGenericListType(body, type, (IntrinsicType)elementType);
    } else if (elementType instanceof UserType) {
      return _generateSerializationStatementForGenericListType(body, type, (UserType)elementType);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(body, type, elementType).toString());
    }
  }
  
  public CharSequence generateSerializationStatementForUserTypeGenericList(final BodyBlock body, final GenericListType type, final UserType elementType, final UserTypeDeclaration declaration) {
    if (declaration instanceof ComplexTypeDeclaration) {
      return _generateSerializationStatementForUserTypeGenericList(body, type, elementType, (ComplexTypeDeclaration)declaration);
    } else if (declaration instanceof EnumTypeDeclaration) {
      return _generateSerializationStatementForUserTypeGenericList(body, type, elementType, (EnumTypeDeclaration)declaration);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(body, type, elementType, declaration).toString());
    }
  }
  
  public void buildConstructorArgsForType(final BlockType type, final List<String> args) {
    if (type instanceof GenericListType) {
      _buildConstructorArgsForType((GenericListType)type, args);
      return;
    } else if (type instanceof IntrinsicType) {
      _buildConstructorArgsForType((IntrinsicType)type, args);
      return;
    } else if (type instanceof UserType) {
      _buildConstructorArgsForType((UserType)type, args);
      return;
    } else if (type instanceof ComplexTypeLiteral) {
      _buildConstructorArgsForType((ComplexTypeLiteral)type, args);
      return;
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(type, args).toString());
    }
  }
}
