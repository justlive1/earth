/**
 * 
 */
package net.oschina.git.justlive1.breeze.storm.cas.client.shiro.web;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuwenlai
 *
 */
@Configuration
public class ShiroConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);

	public EhCacheManager getEhCacheManager() {
		EhCacheManager cacheManager = new EhCacheManager();
		// TODO shiro catch 配置
		cacheManager.setCacheManagerConfigFile("");
		return cacheManager;
	}

}
