package com.datacoper;

import com.datacoper.enums.EnumAttributeMode;
import com.datacoper.enums.EnumClassMode;
import com.datacoper.enums.EnumProject;
import com.datacoper.generator.AbstractGenerator;
import com.datacoper.generator.impl.EnumScaffold;
import com.datacoper.metadata.TemplateAttributeModel;
import com.datacoper.metadata.TemplateModel;
import com.datacoper.utils.Xml;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Main {

    private static String projectHome = System.getenv("PROJECT_HOME");

    public static void main(String[] args) throws Exception {

        if (projectHome == null) {
            throw new Exception("Variavel de ambiente PROJECT_HOME não definida");
        }

        File file = new File(projectHome + "\\AppProdutor-Spec\\Datacoper\\Desenv");
        List<String> modelNames = Arrays.asList("Produtor", "AgendaVisita", "Consultor", "Local", "ServicoAtendimento",
                "EnderecoPostal", "Cidade", "ServicoConsultoria",
                "User", "Notification", "Todo", "UserCommand", "UserCustom", "UserDevice", "UserInfo",
                "UserInfo", "UserCommand", "Notification", "UserDevice", "UserCustom",
                "Todo", "CommandParameter", "NotificationParameter" ,
                "UnidadeFederativa", "ProdutorDadosAdicionais", "Pais", "Atendimento", "RelatorioTempoReal", "VinculoRecomendacaoProduto",
                "Propriedade", "ServicoRelatorioTempoReal", "RecomendacaoProduto", "Produto", "Cultura", "Safra", "TaxonomiaProduto",
                "TipoProdutividade"

        );
        //List<String> modelNames = Arrays.asList();

        if (args.length != 0) {
            modelNames = Arrays.asList(args[0].split(","));
        }

        List<EnumProject> modules = Arrays.asList(EnumProject.FUNCTIONS, EnumProject.COMMON);


        modelNames.forEach(modelName -> gerarCodigo(modelName, new TemplateModel(file), modules));
    }

    private static void gerarCodigo(String entityName, TemplateModel templateModel, List<EnumProject> modules) {

        templateModel.setClassName(entityName);
        templateModel.setEntityName(entityName);

        File fXmlFile = new File(projectHome + "\\ProdutorAppMDM\\gerador\\target", "classesAPPPRODUTOR.xml");

        try {
            //File fXmlFile = new File(Main.class.getClassLoader().getResource("classesAPPPRODUTOR.xml").getFile());

            Xml xml = new Xml(new FileInputStream(fXmlFile), "classes");

            String collectionName = buscarCollectionName(xml, entityName);

            if (collectionName != null) {
                templateModel.setCollectionName(collectionName);
                templateModel.setMode(EnumClassMode.DOCUMENT);
            } else {
                Logger.getAnonymousLogger().severe("Nome da coleção não foi definido. {Entidade: " + entityName + "}");
                templateModel.setMode(EnumClassMode.SUB_DOCUMENT);
            }


            Xml aClass = xml.children("class")
                    .stream()
                    .filter(x -> x.optString("name").equalsIgnoreCase(entityName))
                    .findFirst()
                    .get();

            ArrayList<Xml> aAttibute = aClass.child("attributes").children("attribute");

            aAttibute.forEach(f -> addAttribute(f, templateModel));

            modules.forEach((module) -> gerar(templateModel, module));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addAttribute(Xml attibute, TemplateModel templateModel) {
        if (attibute.optString("name").equalsIgnoreCase("id"))
            return;

        TemplateAttributeModel att = new TemplateAttributeModel();
        att.setName(attibute.optString("name"));
        att.setType(attibute.optString("type"));
        att.setTypeSimpleName(attibute.optString("type"));
        att.setLabel(getProperty(attibute, "label"));

        String mode = attibute.optString("mode");

        switch (mode){
            case "directToField":
                att.setMode(EnumAttributeMode.INTERNAL);
                break;

            case "oneToOne":
                att.setMode(EnumAttributeMode.ONE_TO_ONE);
                break;

            case "oneToMany":
                att.setMode(EnumAttributeMode.ONE_TO_MANY);
                break;

            case "directMap":
                att.setMode(EnumAttributeMode.COLLECTION);
                break;

            case "composite":
                att.setMode(EnumAttributeMode.COMPOSITE);
                break;
        }

        try {
            att.setRequired(attibute.optString("required").equals("S"));
        } catch (Exception ignored) {
        }

        att.setFormat(getProperty(attibute, "format"));
        try {
            att.setMinChar(Integer.parseInt(getProperty(attibute, "min")));
        } catch (NumberFormatException ignored) {

        }
        try {
            att.setMaxChar(Integer.parseInt(getProperty(attibute, "max")));
        } catch (NumberFormatException ignored) {
        }

        templateModel.addAttribute(att);
    }

    private static String getProperty(Xml attibute, String value) {
        ArrayList<Xml> properties = attibute.children("properties");

        for (Xml tmp : properties) {
            ArrayList<Xml> property = tmp.children("property");

            for (Xml xml : property) {
                if (xml.optString("name").equalsIgnoreCase(value)) {
                    return xml.child("value").content();
                }
            }
        }

        return null;
    }

    private static String buscarCollectionName(Xml xml, String entityName) {

        List<Xml> aClass = xml.children("class")
                .stream()
                .filter(x -> x.optString("mode").equalsIgnoreCase("bean"))
                .collect(Collectors.toList());

        for (Xml aClass1 : aClass) {
            ArrayList<Xml> children = aClass1.child("attributes").children("attribute");

            for (Xml child : children) {
                if (child.optString("type").equalsIgnoreCase(entityName)) {
                    return child.optString("name");
                }
            }
        }

        return null;
    }

    private static void gerar(TemplateModel templateModel, EnumProject enumProject) {
        List<AbstractGenerator> generators = EnumScaffold.getGenerators(enumProject, templateModel);

        try {
            if (!generators.isEmpty()) {

                for (AbstractGenerator generator : generators) {
                    System.out.print(generator.getJavaFile());
                    generator.process();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
