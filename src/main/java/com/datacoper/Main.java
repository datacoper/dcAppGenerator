package com.datacoper;

import com.datacoper.enums.EnumAttributeMode;
import com.datacoper.enums.EnumClassMode;
import com.datacoper.enums.EnumProject;
import com.datacoper.generator.AbstractGenerator;
import com.datacoper.generator.EnumScaffold;
import com.datacoper.metadata.TemplateAttributeModel;
import com.datacoper.metadata.TemplateModel;
import com.datacoper.utils.StringTemplate;
import com.datacoper.utils.Xml;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class Main {

    private static String projectHome = System.getenv("PROJECT_HOME");

    public static void main(String[] args) throws Exception {

        if (projectHome == null) {
            throw new Exception("Variavel de ambiente PROJECT_HOME não definida");
        }

        File file = new File(projectHome + "\\AppProdutor-Spec\\Datacoper\\Desenv");
//        List<String> modelNames = Arrays.asList("Produtor", "AgendaVisita", "Consultor", "Local", "ServicoAtendimento",
//                "EnderecoPostal", "Cidade", "ServicoConsultoria",
//                "User", "UserNotification", "Todo", "UserCommand", "UserCustom", "UserDevice", "UserInfo",
//                "UserInfo", "UserCommand", "UserNotification", "UserDevice", "UserCustom",
//                "Todo", "UserCommandParameter", "UserNotificationParameter" ,
//                "UnidadeFederativa", "ProdutorDadosAdicionais", "Pais", "Atendimento", "RelatorioTempoReal", "VinculoRecomendacaoProduto",
//                "Propriedade", "ServicoRelatorioTempoReal", "RecomendacaoProduto", "Produto", "Cultura", "Safra", "TaxonomiaProduto",
//                "TipoProdutividade", "ProdutoCatalogo", "DoseDiferenciadaProdutoCatalogo", "DepoimentoProdutoCatalogo",
//                "ArquivoGaleriaProdutoCatalogo", "OcorrenciaDegenerativaCatalogo", "TipoOcorrencia",
//                "Clima", "PrevisaoHoraria", "PrevisaoHorariaList", "PrevisaoDiariaList", "PrevisaoFlags", "PrevisaoDiaria",
//                "Command", "RegistroOcorrencia", "NivelComprometimento", "PlanoCultivo", "Produtividade", "ImagemOcorrencia",
//                "TipoOcorrencia", "RomaneioAgricola", "DescontoRomaneioAgricola", "ProdutorRomaneioAgricola",
//                "ProdutoCatalogo", "CulturaCatalogo", "CategoriaProdutoCatalogo", "ClasseProdutoCatalogo", "MarcaCatalogo",
//                "CulturaOcorrenciaDegenerativaCatalogo", "Cotacao", "ProdutoCotacao", "LocalCotacao", "FonteCotacao",
//                "GrupoProdutoCotacao", "TipoProdutoCotacao", "NaturezaFisica", "Coordenada", "UserProfile", "TermosCondicoesUso",
//                "SolicitacaoAcesso", "CatalogoProduto", "CatalogoProdutoCultura", "CatalogoProdutoCulturaProduto"
//        );

        List<String> modelNames = Arrays.asList(
//                "CaseSistemaMenu",
//                "IconeSistema",
//                "CaseSistemaCategoria",
//                "TemplateCatalogoProduto",
//                 "NewsTime",
//                "NewsRecord",
//                "Onboarding",
//                "Todo",
//                "SolicitacaoVisitaTecnica",
//                "PosicaoFinanceiraTitulos",
//                "Pedido",
//                "Vendedor",
//                "Filial",
//                "PedidoItem",
//                "PedidoParcela",
//                "PosicaoSaldoAgricolaProdutor",
//                "PosicaoSaldoAgricolaIndicadores",
//                "CulturaCatalogo",
//                "User", "UserInfo", "UserDevice"
                "UserCadastroDados" //, "ProdutorDadosAdicionais"
//        "CatalogoProdutoCulturas", "Produtividade"
        );

        if (args.length != 0) {
            modelNames = Arrays.asList(args[0].split(","));
        }

//        List<EnumProject> modules = Arrays.asList(EnumProject.FUNCTIONS, EnumProject.COMMON);
        List<EnumProject> modules = Arrays.asList(EnumProject.FUNCTIONS);
//        List<EnumProject> modules = Arrays.asList(EnumProject.COMMON);

        for (String modelName : modelNames) {
            gerarCodigo(modelName, new TemplateModel(file), modules);
        }

    }

    private static void gerarCodigo(String entityName, TemplateModel templateModel, List<EnumProject> modules) {

        templateModel.setClassName(entityName);
        templateModel.setEntityName(entityName);

        File fXmlFile = new File(projectHome + "\\ProdutorAppMDM\\gerador\\target", "classesAPPPRODUTOR.xml");

        try {

            Xml xml = new Xml(new FileInputStream(fXmlFile), "classes");

            if (isComposite(xml, entityName)) {
                templateModel.setMode(EnumClassMode.COMPOSITE);
            } else if(isSubCollection(xml, entityName)) {
                templateModel.setCollectionName(buscarSubCollectionName(xml, entityName));
                templateModel.setMode(EnumClassMode.SUB_DOCUMENT);
            }else {
                templateModel.setCollectionName( buscarCollectionName(xml, entityName));
                templateModel.setMode(EnumClassMode.DOCUMENT);
            }

            Optional<Xml> aClassOptional = xml.children("class")
                    .stream()
                    .filter(x -> x.optString("name").equalsIgnoreCase(entityName))
                    .findFirst();

            if (!aClassOptional.isPresent()) {
                throw new Exception("Nome da classe não foi ecnotrado. {Entidade: " + entityName + "}");
            }

            ArrayList<Xml> aAttibute = aClassOptional.get().child("attributes").children("attribute");

            for (Xml f : aAttibute) {
                addAttribute(f, xml, templateModel);
            }

            for (EnumProject module : modules) {
                gerar(templateModel, module);
            }

            StringTemplate t1 = new StringTemplate("InjectorManager.injector.registerDependency<${class}Repository>((i) => ${class}RepositoryImpl(i.getDependency<AppSession>()));");
            StringTemplate t2 = new StringTemplate("InjectorManager.injector.registerDependency<${class}Service>((i) => ${class}ServiceImpl(i.getDependency<${class}Repository>()));");
            t1.setBlankNull();
            Map<String, String> m = new HashMap<>();
            m.put("class", entityName);
            System.out.println(t1.substitute(m));
            System.out.println(t2.substitute(m));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isSubCollection(Xml xml, String name) {

        for (Xml classe : xml.children("class")) {
            if(classe.string("mode").equalsIgnoreCase("class"))
            for (Xml att : classe.child("attributes").children("attribute")) {
                if (att.optString("type").equalsIgnoreCase(name) && att.optString("mode").equalsIgnoreCase("directMap")) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean isComposite(Xml xml, String name) {
        for (Xml classe : xml.children("class")) {
            if(classe.string("mode").equalsIgnoreCase("class"))
                for (Xml att : classe.child("attributes").children("attribute")) {
                    if (att.optString("type").equalsIgnoreCase(name) && att.optString("mode").equalsIgnoreCase("composite")) {
                        return true;
                    }
                }
        }

        return false;
    }

    private static void addAttribute(Xml attibute, Xml xml, TemplateModel templateModel) {
        if (attibute.optString("name").equalsIgnoreCase("id"))
            return;

        TemplateAttributeModel att = new TemplateAttributeModel();
        att.setName(attibute.optString("name"));
        att.setType(attibute.optString("type"));
        att.setTypeSimpleName(attibute.optString("type"));
        att.setLabel(getProperty(attibute, "label"));

        String mode = attibute.optString("mode");


        switch (mode) {
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

    private static String buscarSubCollectionName(Xml xml, String name) {

        for (Xml classe : xml.children("class")) {
            if(classe.string("mode").equalsIgnoreCase("class"))
                for (Xml att : classe.child("attributes").children("attribute")) {
                    if (att.optString("type").equalsIgnoreCase(name) && att.optString("mode").equalsIgnoreCase("directMap")) {
                        return att.optString("name");
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
                    generator.process();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
