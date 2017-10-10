package com.ebest.frame.compilelib.parama.factory;

import com.ebest.frame.compilelib.UtilMgr;
import com.ebest.frame.compilelib.parama.model.Constants;
import com.ebest.frame.compilelib.parama.model.FieldData;
import com.ebest.frame.compilelib.parama.util.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

public class ClassFactory {

    private List<FieldData> list;
    private TypeElement type;

    public ClassFactory (List<FieldData> list, TypeElement type) {
        this.list = list;
        this.type = type;
    }

    /**
     * To generate code.
     * @throws IOException exception
     */
    public void generateCode () throws IOException {
        // create package and class name of generating class
        String packName = Utils.getPackageName(type);
        String clzName = type.getQualifiedName().toString();
        clzName = Utils.isEmpty(packName) ? clzName + Constants.INJECTOR_SUFFIX
                : clzName.substring(packName.length() + 1).replace(".","$") + Constants.INJECTOR_SUFFIX;

        // create injector class builder.
        TypeName superTypeName = ParameterizedTypeName.get(Constants.CLASS_INJECTOR, ClassName.get(type));
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(clzName)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(superTypeName);
        // create method toEntity builder.
        MethodSpec.Builder toEntity = MethodSpec.methodBuilder(Constants.METHOD_TO_ENTITY)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.VOID)
                .addParameter(ParameterSpec.builder(ClassName.get(type), "entity").build())
                .addParameter(ParameterSpec.builder(Constants.CLASS_BUNDLE, "bundle").build())
                .addStatement("Object obj = null");

        // create method toBundle builder.
        MethodSpec.Builder toBundle = MethodSpec.methodBuilder(Constants.METHOD_TO_BUNDLE)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.VOID)
                .addParameter(ParameterSpec.builder(ClassName.get(type), "entity").build())
                .addParameter(ParameterSpec.builder(Constants.CLASS_BUNDLE, "bundle").build());


        toEntity.addStatement("$T.getParentInjectorByClass($T.class).toEntity(entity, bundle)", Constants.CLASS_PARCELER, ClassName.get(type));
        toEntity.addStatement("$T factory = $T.createFactory(bundle).ignoreException(true)", Constants.CLASS_FACTORY, Constants.CLASS_PARCELER);
        toEntity.addStatement("$T type", Type.class);

        toBundle.addStatement("$T.getParentInjectorByClass($T.class).toBundle(entity, bundle)", Constants.CLASS_PARCELER, ClassName.get(type));
        toBundle.addStatement("$T factory = $T.createFactory(bundle).ignoreException(true)", Constants.CLASS_FACTORY, Constants.CLASS_PARCELER);

        for (FieldData fieldData : list) {
            completeInjectToTarget(toEntity,fieldData);
            completeInjectToBundle(toBundle,fieldData);
        }


        classBuilder.addMethod(toEntity.build());
        classBuilder.addMethod(toBundle.build());
        JavaFile.Builder builder = JavaFile.builder(packName, classBuilder.build());
        builder.addFileComment("The class is generated by Parceler, do not modify!");
        JavaFile build = builder.build();

        build.writeTo(UtilMgr.getMgr().getFiler());
    }

    private void completeInjectToBundle(MethodSpec.Builder injectToBundle, FieldData fieldData) {
        if (fieldData.getConverter() != null) {
            injectToBundle.addStatement("factory.setConverter($T.class)", fieldData.getConverter());
        } else {
            injectToBundle.addStatement("factory.setConverter(null)");
        }
        injectToBundle.addStatement("factory.put($S, entity.$N)", fieldData.getKey(), fieldData.getVar().getSimpleName());
    }

    private void completeInjectToTarget(MethodSpec.Builder injectToData, FieldData fieldData) {
        injectToData.addStatement("type = $T.findType($S, $T.class)",
                Constants.CLASS_MANAGER, fieldData.getVar().getSimpleName(), ClassName.get(type));
        if (fieldData.getConverter() != null) {
            injectToData.addStatement("factory.setConverter($T.class)", fieldData.getConverter());
        } else {
            injectToData.addStatement("factory.setConverter(null)");
        }
        injectToData.beginControlFlow("if((obj = factory.get($S, type)) != null)", fieldData.getKey());
        injectToData.addStatement("entity.$N = $T.wrapCast(obj)", fieldData.getVar().getSimpleName(), Constants.CLASS_UTILS)
                .endControlFlow();
        if (fieldData.isNonNull()) {
            injectToData.beginControlFlow("else")
                    .addStatement("throw new $T($S)", RuntimeException.class, "Could not be null")
                    .endControlFlow();
        }
    }

}
