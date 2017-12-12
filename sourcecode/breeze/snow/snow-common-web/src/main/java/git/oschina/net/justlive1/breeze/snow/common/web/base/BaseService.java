package git.oschina.net.justlive1.breeze.snow.common.web.base;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * 基础service <br>
 * 提供公共基础方法
 * 
 * @author wubo
 *
 */
public abstract class BaseService {

	/**
	 * 构造json请求体
	 * 
	 * @param request
	 * @return
	 */
	protected <T> HttpEntity<T> buildEntity(T request) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

		return new HttpEntity<T>(request, headers);
	}

	/**
	 * 构造返回类型
	 * 
	 * @return
	 */
	protected <T> TypeReference<T> buildTypeRef() {
		return new TypeReference<T>();
	}

	public static class TypeReference<T> extends ParameterizedTypeReference<T> {

	}
}
