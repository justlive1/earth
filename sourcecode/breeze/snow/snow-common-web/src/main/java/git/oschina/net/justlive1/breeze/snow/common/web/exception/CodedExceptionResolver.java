package git.oschina.net.justlive1.breeze.snow.common.web.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import git.oschina.net.justlive1.breeze.snow.common.web.domain.Response;

/**
 * CodedException统一处理<br>
 * <p>
 * 对所有未处理的CodedException类型的异常进行统一的包装处理
 * <ul>
 * <li>对于jsp形式的调用 在Model中放入一个error的对象</li>
 * <li>对于ajax方式的请求 以请求希望的格式（JSON或者XML）方式返回error数据</li>
 * </ul>
 * 
 * @author wubo
 */
@EnableWebMvc
@Component("codedExceptionResolver")
public class CodedExceptionResolver extends SimpleMappingExceptionResolver {

	private static final Logger LOG = LoggerFactory.getLogger(CodedExceptionResolver.class);

	private ObjectMapper objectMapper;

	@Value("${exception.errorPage:/error/coded_error}")
	private String errorPage;

	@PostConstruct
	void init() {

		objectMapper = new ObjectMapper().configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
				.setSerializationInclusion(Include.NON_NULL);

		Properties exceptionMappings = new Properties();
		// 在非ajax方式的情况下，将所有异常转向指定的错误页面
		exceptionMappings.put(Exception.class.getName(), errorPage);
		this.setExceptionMappings(exceptionMappings);
	}

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {

		// 检查应用层是否对异常进行了处理
		String viewName = determineViewName(ex, request);
		if (viewName == null) {
			return null;
		}

		// 包装Response对象
		final Response<Void> error;
		// 确定当前异常是业务异常还是系统故障。为了防止信息丢失，除了明确使用NoStackException，其余的异常都认为是系统故障
		boolean isFault = true;
		if (CodedException.class.isInstance(ex)) {
			error = buildError((CodedException) ex);
			if (NoStackCodedException.class.isInstance(ex)) {
				isFault = false;
			}
		} else {
			error = buildError(ex);
		}

		// 记录日志 TODO
		if (isFault) {

		}

		// 判断请求是否支持json格式数据返回
		if (!isJsonResponseSupported(request)) {
			Integer statusCode = determineStatusCode(request, viewName);
			if (statusCode != null) {
				applyStatusCodeIfPossible(request, response, statusCode);
			}
			ModelAndView model = getModelAndView(viewName, ex, request);
			model.addObject("error", error);
			return model;

		} else {// JSON格式返回
			try {
				response.setStatus(HttpServletResponse.SC_OK);
				response.setHeader("ContentType", "application/json");
				response.setCharacterEncoding("utf-8");
				PrintWriter writer = response.getWriter();
				objectMapper.writeValue(writer, error);
				response.flushBuffer();
			} catch (IOException e) {
				LOG.error("", e);
			}
			ModelAndView model = new ModelAndView();
			return model;
		}
	}

	/**
	 * 判断一个请求是否支持Json方式的返回
	 * 
	 * @param request
	 * @return
	 */
	protected boolean isJsonResponseSupported(HttpServletRequest request) {
		// 检查是否有Accept头
		final String accept = request.getHeader("Accept");
		// 对于没有定义Accept头的非标准HTTP协议请求，缺省认为客户端支持json数据
		if (accept == null) {
			return true;
		}

		// 当客户端是通过ajax方式发送的请求，则缺省认为支持json返回
		final String ajax = request.getHeader("X-Requested-With");
		if (ajax != null && ajax.indexOf("XMLHttpRequest") >= 0) {
			return true;
		}

		// 客户端声明支持JSon
		if (accept.indexOf("*/*") >= 0 || accept.indexOf("application/*") >= 0
				|| accept.indexOf("application/json") >= 0) {
			return true;
		}

		return false;
	}

	/**
	 * 根据CodedException创建返回对象
	 * 
	 * @param ex
	 * @return
	 */
	private Response<Void> buildError(CodedException ex) {
		final Response<Void> err = new Response<>();
		err.setCode(ex.getErrorCode().toString());
		err.setMessage(ex.getMessage());
		return err;
	}

	/**
	 * 包装普通的Exception
	 * 
	 * @param ex
	 * @return
	 */
	private Response<Void> buildError(Exception ex) {
		final Response<Void> err = new Response<>();
		err.setCode(ex.getClass().getSimpleName());
		err.setMessage(ex.getMessage());
		return err;
	}
}
