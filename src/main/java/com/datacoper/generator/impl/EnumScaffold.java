package com.datacoper.generator.impl;

import com.datacoper.enums.EnumProject;
import com.datacoper.generator.AbstractGenerator;
import com.datacoper.metadata.TemplateModel;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public enum EnumScaffold {
    MODEL_GENERATOR                 (ModelGenerator.class, EnumProject.COMMON),
    REPOSITORY_GENERATOR            (RepositoryGenerator.class, EnumProject.COMMON),
    SERVICE_GENERATOR               (ServiceGenerator.class, EnumProject.COMMON),
    VALIDATOR_GENERATOR             (ValidadorGenerator.class, EnumProject.COMMON),
    LOADER_GENERATOR                (LoaderGenerator.class, EnumProject.COMMON),

    FUNCTIONS_MODEL_GENERATOR       (FunctionsModelGenerator.class, EnumProject.FUNCTIONS),
    FUNCTIONS_VALIDATOR_GENERATOR   (FunctionsModuleGenerator.class, EnumProject.FUNCTIONS),
    FUNCTIONS_REPOSITORY_GENERATOR  (FunctionsResourceGenerator.class, EnumProject.FUNCTIONS),
    FUNCTIONS_SERVICE_GENERATOR     (FunctionsServiceGenerator.class, EnumProject.FUNCTIONS),

	;
    private final Class<? extends AbstractGenerator> generator;
    
    private final EnumProject projectType;

    private EnumScaffold(Class<? extends AbstractGenerator> generator, EnumProject projectType) {
        this.generator = generator;
        this.projectType = projectType;
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
            if (value.getProjectType().equals(projectType)) {
            	generators.add(value.getGenerator(templateModel));
            }
        }
        
        return generators;
    }
}
