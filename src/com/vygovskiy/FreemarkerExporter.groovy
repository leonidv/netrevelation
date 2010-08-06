package com.vygovskiy

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper
import freemarker.template.Template

/**
 *
 * @author leonidv
 */
@Grab(group="org.freemarker", module = "freemarker", version = "2.3.16")
class FreemarkerExporter {
    private Configuration config;

    FreemarkerExporter(String templateDir) {
        config = new Configuration()
        config.directoryForTemplateLoading = new File(templateDir)
        config.objectWrapper = new DefaultObjectWrapper()

    }

    def export(String templateName,  String exportName, Map<String,Object> bindings) {
        Template template = config.getTemplate(templateName)
        Writer writer = new File(exportName).newPrintWriter()
        template.process(bindings, writer)
    }
}


