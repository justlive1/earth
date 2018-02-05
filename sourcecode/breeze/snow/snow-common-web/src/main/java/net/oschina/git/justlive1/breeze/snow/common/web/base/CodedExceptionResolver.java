package net.oschina.git.justlive1.breeze.snow.common.web.base;

import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.google.common.collect.Maps;

import net.oschina.git.justlive1.breeze.snow.common.base.constant.BaseConstants;
import net.oschina.git.justlive1.breeze.snow.common.base.exception.CodedException;
import net.oschina.git.justlive1.breeze.snow.common.base.exception.NoStackException;

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
@Component("codedExceptionResolver")
public class CodedExceptionResolver extends SimpleMappingExceptionResolver {

    private static final String APPLICATION_ALL = "application/*";
    private static final String HEAD_X_REQUESTED_WITH = "X-Requested-With";
    private static final String HEAD_XMLHTTPREQUEST = "XMLHttpRequest";

    private MappingJackson2JsonView jsonView = new MappingJackson2JsonView();

    @Value("${exception.errorPage:/error/coded_error}")
    private String errorPage;

    @PostConstruct
    void init() {

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
        final Map<String, String> error;
        // 确定当前异常是业务异常还是系统故障。为了防止信息丢失，除了明确使用NoStackException，其余的异常都认为是系统故障
        boolean isFault = true;
        if (CodedException.class.isInstance(ex)) {
            error = buildError((CodedException) ex);
            if (NoStackException.class.isInstance(ex)) {
                isFault = false;
            }
        } else {
            error = buildError(ex);
        }

        if (isFault) {
            // 记录日志 TODO
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

            return new ModelAndView(jsonView, error);
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
        final String accept = request.getHeader(HttpHeaders.ACCEPT);
        // 对于没有定义Accept头的非标准HTTP协议请求，缺省认为客户端支持json数据
        if (accept == null) {
            return true;
        }

        // 当客户端是通过ajax方式发送的请求，则缺省认为支持json返回
        final String ajax = request.getHeader(HEAD_X_REQUESTED_WITH);
        if (ajax != null && ajax.indexOf(HEAD_XMLHTTPREQUEST) >= 0) {
            return true;
        }

        // 客户端声明支持JSon
        if (accept.indexOf(MediaType.ALL_VALUE) >= 0 || accept.indexOf(APPLICATION_ALL) >= 0
                || accept.indexOf(MediaType.APPLICATION_JSON_VALUE) >= 0) {
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
    private Map<String, String> buildError(CodedException ex) {
        Map<String, String> err = Maps.newHashMap();
        err.put(BaseConstants.RESP_CODE_FIELD, ex.getErrorCode().toString());
        err.put(BaseConstants.RESP_MESSAGE_FIELD, ex.getMessage());
        return err;
    }

    /**
     * 包装普通的Exception
     * 
     * @param ex
     * @return
     */
    private Map<String, String> buildError(Exception ex) {
        Map<String, String> err = Maps.newHashMap();
        err.put(BaseConstants.RESP_CODE_FIELD, ex.getClass().getSimpleName());
        err.put(BaseConstants.RESP_MESSAGE_FIELD, ex.getMessage());
        return err;
    }

}
