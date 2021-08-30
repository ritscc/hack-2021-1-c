package dev.abelab.timestamp.plugin;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

/**
 * Custom MyBatis Mapper Name
 */
public class CustomMapperNamePlugin extends PluginAdapter {

    @Override
    public boolean validate(final List<String> warnings) {
        return true;
    }

    @Override
    public void initialized(final IntrospectedTable introspectedTable) {
        super.initialized(introspectedTable);

        // XxxMapper.java -> XxxBaseMapper.java
        final var javaMapperName = introspectedTable.getMyBatis3JavaMapperType();
        introspectedTable.setMyBatis3JavaMapperType(javaMapperName.replaceAll("Mapper$", "BaseMapper"));

        // XxxMapper.xml -> XxxBaseMapper.xml
        final var xmlMapperName = introspectedTable.getMyBatis3XmlMapperFileName();
        introspectedTable.setMyBatis3XmlMapperFileName(xmlMapperName.replaceAll("Mapper\\.xml", "BaseMapper.xml"));
    }

}
