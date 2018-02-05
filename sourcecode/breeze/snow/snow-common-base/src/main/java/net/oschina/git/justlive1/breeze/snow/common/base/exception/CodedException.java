package net.oschina.git.justlive1.breeze.snow.common.base.exception;

import java.util.Arrays;

import lombok.Getter;

/**
 * 编码异常
 * 
 * @author wubo
 *
 */
@Getter
public class CodedException extends RuntimeException {

    private static final long serialVersionUID = -1744884854522700549L;

    /**
     * 该异常的错误码
     */
    private final ErrorCode errorCode;

    /**
     * 异常发生时的参数信息
     */
    private final transient Object[] args;

    protected CodedException(Throwable throwable, ErrorCode errorCode, Object... arguments) {
        super(throwable);
        this.errorCode = errorCode;
        this.args = arguments;
    }

    protected CodedException(ErrorCode errorCode, Object... arguments) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.args = arguments;
    }

    @Override
    public String toString() {
        if (args == null || args.length == 0) {
            return errorCode.toString();
        }
        return errorCode.toString() + ":" + Arrays.toString(args);
    }
}
