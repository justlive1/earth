package git.oschina.net.justlive1.breeze.snow.common.web.base;

import java.util.Map;

import org.springframework.cglib.beans.BeanMap;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import git.oschina.net.justlive1.breeze.snow.common.base.domain.Response;
import git.oschina.net.justlive1.breeze.snow.common.base.exception.Exceptions;

/**
 * 基础service <br>
 * 提供公共基础方法
 * 
 * @author wubo
 *
 */
public abstract class BaseService {

	protected RestTemplate template;

	/**
	 * 由继承该类的组件注入template
	 * 
	 * <pre class="code">
	 * &#64;Override
	 * protected void setTemplate(@Autowired RestTemplate template) {
	 * 	super.setTemplate(template);
	 * }
	 * </pre>
	 * 
	 * @param template
	 */
	protected void setTemplate(RestTemplate template) {
		this.template = template;
	}

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
	 * 构造表单提交请求体
	 * 
	 * @param request
	 * @return
	 */
	protected HttpEntity<?> buildFormEntity(Object request) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		if (request instanceof MultiValueMap) {
			return new HttpEntity<>(request, headers);
		}

		final MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();

		Map<?, ?> map;
		if (request instanceof Map) {
			map = (Map<?, ?>) request;
		} else {
			map = BeanMap.create(request);
		}

		map.forEach((k, v) -> params.add(k.toString(), v.toString()));

		return new HttpEntity<MultiValueMap<String, String>>(params, headers);
	}

	/**
	 * get方式获取数据并转换类型
	 * 
	 * @param url
	 *            请求地址
	 * @param uriVariables
	 *            uri中的参数
	 * @return
	 */
	protected <T> T getForObject(String url, Object... uriVariables) {

		ParameterizedTypeReference<Response<T>> typeRef = new ParameterizedTypeReference<Response<T>>() {
		};

		ResponseEntity<Response<T>> resp = template.exchange(url, HttpMethod.GET, null, typeRef, uriVariables);

		this.check(resp);

		return resp.getBody().getData();
	}

	/**
	 * post json 方式获取数据并转换类型
	 * 
	 * @param url
	 *            请求地址
	 * @param request
	 *            请求body中的数据（可空）
	 * @param uriVariables
	 *            uri中的参数
	 * @return
	 */
	protected <T, E> T postJsonForObject(String url, @Nullable E request, Object... uriVariables) {

		HttpEntity<E> entity = this.buildEntity(request);

		ParameterizedTypeReference<Response<T>> typeRef = new ParameterizedTypeReference<Response<T>>() {
		};

		ResponseEntity<Response<T>> resp = template.exchange(url, HttpMethod.POST, entity, typeRef, uriVariables);

		this.check(resp);

		return resp.getBody().getData();
	}

	/**
	 * form表单 方式获取数据并转换类型
	 * 
	 * @param url
	 *            请求地址
	 * @param request
	 *            请求body中的数据（可空）
	 * @param uriVariables
	 *            uri中的参数
	 * @return
	 */
	protected <T, E> T formSubmitForObject(String url, @Nullable E request, Object... uriVariables) {

		HttpEntity<?> entity = this.buildFormEntity(request);

		ParameterizedTypeReference<Response<T>> typeRef = new ParameterizedTypeReference<Response<T>>() {
		};

		ResponseEntity<Response<T>> resp = template.exchange(url, HttpMethod.POST, entity, typeRef, uriVariables);

		this.check(resp);

		return resp.getBody().getData();
	}

	/**
	 * 检查返回值
	 * 
	 * @param resp
	 */
	protected <T> void check(ResponseEntity<Response<T>> resp) {

		if (resp.getStatusCode() != HttpStatus.OK) {
			throw Exceptions.fail(Response.FAIL, Response.FAIL);
		}

		Response<T> body = resp.getBody();

		if (!body.isSuccess()) {
			throw Exceptions.fail(body.getCode(), body.getMessage());
		}
	}

}
