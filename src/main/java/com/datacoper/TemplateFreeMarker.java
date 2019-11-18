package com.datacoper;

import freemarker.template.Template;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class TemplateFreeMarker {

    private Map<String, Object> data;
    private Template template;

    public TemplateFreeMarker(String path, ConfigurationFreeMarker config) {
        this.data = new HashMap<>();
        this.loadTemplate(path, config);
    }

    public void add(String key, Object value) {
        this.data.put(key, value);
    }

    public void generateTemplate(File arquive, String charsetName) {
        try {
            Writer file = new OutputStreamWriter(new FileOutputStream(arquive), charsetName);
            this.template.process(data, file);
            file.flush();
            file.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadTemplate(String path, ConfigurationFreeMarker config) {
        try {
            this.template = config.getConfiguration().getTemplate(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
