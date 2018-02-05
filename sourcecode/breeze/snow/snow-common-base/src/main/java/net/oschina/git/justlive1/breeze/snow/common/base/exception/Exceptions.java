package net.oschina.git.justlive1.breeze.snow.common.base.exception;

/**
 * 用于创建CodedException<br>
 * <p>
 * fail方法创建的异常为业务逻辑异常，不含堆栈信息； fault方法创建的异常为故障型异常，包含堆栈信息
 * 
 * @author wubo
 *
 */
public class Exceptions {

    private Exceptions() {
    }

    /**
     * 抛出unchecked异常
     * 
     * @param e
     */
    public static CodedException wrap(Throwable e) {
        return new CodedException(e, null);
    }

    public static CodedException wrap(Throwable e, String code, String message) {
        return new CodedException(e, errorMessage(null, code, message));
    }

    public static CodedException wrap(Throwable e, ErrorCode errorCode, Object... arguments) {
        return new CodedException(e, errorCode, arguments);
    }

    /**
     * 创建ErrorCode
     * 
     * @param module
     * @param code
     * @return
     */
    public static ErrorCode errorCode(String module, String code) {
        return new ErrorCode(module, code);
    }

    /**
     * 创建带错误提示信息的ErrorCode
     * 
     * @param module
     * @param code
     * @param message
     * @return
     */
    public static ErrorCode errorMessage(String module, String code, String message) {
        return new ErrorCode(module, code, message);
    }

    /**
     * 创建可带参数的业务逻辑异常
     * 
     * @param errCode
     * @param params
     *            参数
     * @return
     */
    public static CodedException fail(ErrorCode errCode, Object... params) {
        return new NoStackException(errCode, params);
    }

    public static CodedException fail(String code, String message, Object... params) {
        return fail(errorMessage(null, code, message), params);
    }

    /**
     * 创建可带参数的故障异常
     * 
     * @param errCode
     * @return
     */
    public static CodedException fault(ErrorCode errCode, Object... params) {
        return new CodedException(errCode, params);
    }

    public static CodedException fault(Throwable e, ErrorCode errCode, Object... params) {
        return new CodedException(e, errCode, params);
    }

    public static CodedException fault(String code, String message, Object... params) {
        return fault(errorMessage(null, code, message), params);
    }

    public static CodedException fault(Throwable e, String code, String message, Object... params) {
        return fault(e, errorMessage(null, code, message), params);
    }
}
