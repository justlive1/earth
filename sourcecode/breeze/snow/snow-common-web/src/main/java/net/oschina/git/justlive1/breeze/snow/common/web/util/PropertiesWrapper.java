package net.oschina.git.justlive1.breeze.snow.common.web.util;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import net.oschina.git.justlive1.breeze.snow.common.base.util.PlaceHolderHelper;

/**
 * properties包装类<br>
 * 在不能使用@Value注解时使用
 * 
 * @author wubo
 *
 */
public class PropertiesWrapper {

    private static final PlaceHolderHelper HELPER = new PlaceHolderHelper(PlaceHolderHelper.DEFAULT_PLACEHOLDER_PREFIX,
            PlaceHolderHelper.DEFAULT_PLACEHOLDER_SUFFIX, PlaceHolderHelper.DEFAULT_VALUE_SEPARATOR, true);

    private Properties props;

    public PropertiesWrapper(String path) {

        try {

            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            PropertiesFactoryBean props = new PropertiesFactoryBean();
            props.setLocations(resolver.getResources(path));
            props.setIgnoreResourceNotFound(true);
            props.setFileEncoding("utf-8");
            props.afterPropertiesSet();
            this.props = props.getObject();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getProperty(String key) {
        String value = props.getProperty(key);
        if (value == null) {
            return value;
        }
        return HELPER.replacePlaceholders(value, props);
    }

    public String getProperty(String key, String defaultValue) {
        String value = props.getProperty(key, defaultValue);
        if (value == null) {
            return value;
        }
        return HELPER.replacePlaceholders(value, props);
    }

}
