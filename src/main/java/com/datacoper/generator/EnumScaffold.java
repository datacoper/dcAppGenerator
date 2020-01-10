package com.datacoper.generator;

import com.datacoper.enums.EnumClassMode;
import com.datacoper.enums.EnumProject;
import com.datacoper.generator.impl.*;
import com.datacoper.metadata.TemplateModel;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum EnumScaffold {
    COMMON_MODEL_GENERATOR              (CommonModelGenerator.class,            EnumProject.COMMON, Arrays.asList(EnumClassMode.DOCUMENT, EnumClassMode.SUB_DOCUMENT, EnumClassMode.COMPOSITE)),
    COMMON_REPOSITORY_GENERATOR         (CommonRepositoryGenerator.class,       EnumProject.COMMON, Arrays.asList(EnumClassMode.DOCUMENT, EnumClassMode.SUB_DOCUMENT)),
    COMMON_REPOSITORY_IMPL_GENERATOR    (CommonRepositoryImplGenerator.class,   EnumProject.COMMON, Arrays.asList(EnumClassMode.DOCUMENT)),
    COMMON_SERVICE_GENERATOR            (CommonServiceGenerator.class,          EnumProject.COMMON, Arrays.asList(EnumClassMode.DOCUMENT, EnumClassMode.SUB_DOCUMENT)),
    COMMON_SERVICE_IMPL_GENERATOR       (CommonServiceImplGenerator.class,      EnumProject.COMMON, Arrays.asList(EnumClassMode.DOCUMENT, EnumClassMode.SUB_DOCUMENT)),
    COMMON_VALIDATOR_GENERATOR          (CommonValidadorGenerator.class,        EnumProject.COMMON, Arrays.asList(EnumClassMode.DOCUMENT, EnumClassMode.SUB_DOCUMENT)),

    FUNCTIONS_MODEL_GENERATOR           (FunctionsModelGenerator.class,         EnumProject.FUNCTIONS, Arrays.asList(EnumClassMode.DOCUMENT, EnumClassMode.SUB_DOCUMENT, EnumClassMode.COMPOSITE)),
    FUNCTIONS_MODULE_GENERATOR          (FunctionsModuleGenerator.class,        EnumProject.FUNCTIONS, Arrays.asList(EnumClassMode.DOCUMENT)),
    FUNCTIONS_RESOURCE_GENERATOR        (FunctionsResourceGenerator.class,      EnumProject.FUNCTIONS, Arrays.asList(EnumClassMode.DOCUMENT)),
    FUNCTIONS_SERVICE_GENERATOR         (FunctionsServiceGenerator.class,       EnumProject.FUNCTIONS, Arrays.asList(EnumClassMode.DOCUMENT)),

	;
    private final Class<? extends AbstractGenerator> generator;
    
    private final EnumProject projectType;
    private final List<EnumClassMode> classModes;

    private EnumScaffold(Class<? extends AbstractGenerator> generator, EnumProject projectType, List<EnumClassMode> classModes) {
        this.generator = generator;
        this.projectType = projectType;
        this.classModes = classModes;
    }

    public AbstractGenerator getGenerator(TemplateModel templateModel) {
        try {
			Constructor<? extends AbstractGenerator> constructor = generator.getConstructor(TemplateModel.class);
			return constructor.newInstance(templateModel);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }

    public static EnumProject of(Class<? extends AbstractGenerator> generatorClass) {
    	for (EnumScaffold value : values()) {
            if (value.generator.equals(generatorClass)) {
            	return value.projectType;
            }
        }
    	
    	return null;//EnumScaffoldDetail.of(generatorClass);
    }
    
    public EnumProject getProjectType() {
        return projectType;
    }
    
    public static List<AbstractGenerator> getGenerators(EnumProject projectType, TemplateModel templateModel) {
        List<AbstractGenerator> generators = new ArrayList<>();
        
        for (EnumScaffold value : values()) {
            if (value.getProjectType().equals(projectType) && value.getClassModes().contains(templateModel.getMode())) {
            	generators.add(value.getGenerator(templateModel));
            }
        }
        
        return generators;
    }

    public List<EnumClassMode> getClassModes() {
        return classModes;
    }
}
