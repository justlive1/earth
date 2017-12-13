package git.oschina.net.justlive1.breeze.snow.common.web.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 编码异常
 * 
 * @author wubo
 *
 */
@Getter
@Setter
public class CodedException extends RuntimeException {

	private static final long serialVersionUID = -1744884854522700549L;

	/**
	 * 该异常的错误码
	 */
	private ErrorCode errorCode;

	/**
	 * 异常发生时的参数信息
	 */
	private Object[] args;

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
}
