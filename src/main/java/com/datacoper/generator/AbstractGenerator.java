package com.datacoper.generator;

import com.datacoper.ConfigurationFreeMarker;
import com.datacoper.TemplateFreeMarker;
import com.datacoper.enums.EnumProject;
import com.datacoper.generator.impl.EnumScaffold;
import com.datacoper.metadata.TemplateModel;
import com.datacoper.utils.FileUtil;
import com.datacoper.utils.LogUtil;

import java.io.File;

public abstract class AbstractGenerator {

    private TemplateModel templateModel;

    public AbstractGenerator(TemplateModel templateModel) {
        this.templateModel = templateModel;
    }

    public String getEntityName() {
        return templateModel.getEntityName();
    }

    public TemplateModel getTemplateModel() {
        return templateModel;
    }

    public void process() {

        templateModel.setClassName(getClassName());

        ConfigurationFreeMarker config = new ConfigurationFreeMarker();

        TemplateFreeMarker templateFreeMarker = new TemplateFreeMarker(getTemplateName() + ".ftl", config);

        File finalJavaFile = getJavaFile();

        FileUtil.createFolderIfNecessary(finalJavaFile.getParent());

        LogUtil.info("generating class {0}", finalJavaFile);

        templateFreeMarker.add("model", templateModel);

        templateFreeMarker.generateTemplate(finalJavaFile, getCharsetName());

    }

    public File getJavaFile() {
        File sourceFolder = new File(
                new File(templateModel.getProjectParentFile(), getProject().getSuffix()),
                getProject().getSourceFolder().getSourceFolder()
        );

        File finalJavaFile = null;

        if (sourceFolder.exists()) {
            String packageName = getPackage();

            packageName = packageName.replace('.', File.separatorChar);

            File packageFolder = new File(sourceFolder, packageName);

            finalJavaFile = new File(packageFolder, getFileName().concat(getFileExtension()));
        }

        return finalJavaFile;
    }

    public abstract String getTemplateName();

    public final EnumProject getProject() {
        return EnumScaffold.of(getClass());
    }

    public abstract String getPackage();

    public abstract String getClassName();

    public abstract String getFileName();

    public abstract String getFileExtension();

    public abstract String getCharsetName();
}
