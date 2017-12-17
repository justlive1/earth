package git.oschina.net.justlive1.breeze.storm.cas.client.util;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.PropertyPlaceholderHelper;

/**
 * properties包装类<br>
 * 在不能使用@Value注解时使用
 * 
 * @author wubo
 *
 */
public class PropertiesWrapper {

	private static final PropertyPlaceholderHelper HELPER = new PropertyPlaceholderHelper(
			PlaceholderConfigurerSupport.DEFAULT_PLACEHOLDER_PREFIX,
			PlaceholderConfigurerSupport.DEFAULT_PLACEHOLDER_SUFFIX);

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

}
