package com.epilgrim.framework.templating;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Scanner;
import java.util.Vector;

public class TemplateResolver {

    private Vector<Template> templates = new Vector<Template>();
    private String layoutPath;

    public String render(String templateName, String[] values)
    {
        for (Template template : this.templates) {
            if (template.name.equals(templateName)) {
                String content = this.replacePlaceholders(this.getTemplateContent(template.path), values);
                String[] replace = { content };
                return this.replacePlaceholders(this.getTemplateContent(this.layoutPath), replace);
            }
        }

        return "";
    }

    public String render(String templateName)
    {
        String[] values = {};

        return this.render(templateName, values);
    }

    public void addTemplate(String name, String path)
    {
        Template template = new Template();

        template.name = name;
        template.path = path;

        this.templates.add(template);

    }

    public void setLayout(String path)
    {
        this.layoutPath = path;
    }

    private String getTemplateContent(String path)
    {
        InputStream is = TemplateResolver.class.getResourceAsStream(path);
        String inputStreamString = new Scanner(is,"UTF-8").useDelimiter("\\A").next();

        return inputStreamString;
    }

    private String replacePlaceholders(String content, String[] values)
    {
        return MessageFormat.format(content, (Object[]) values);
    }

    private class Template
    {
        public String name;
        public String path;
    }
}
