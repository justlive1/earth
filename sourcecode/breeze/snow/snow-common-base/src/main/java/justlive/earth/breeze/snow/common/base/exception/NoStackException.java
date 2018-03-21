package justlive.earth.breeze.snow.common.base.exception;

/**
 * 不带堆栈的异常
 * 
 * @author wubo
 *
 */
public class NoStackException extends CodedException {

  private static final long serialVersionUID = 773740321917587189L;

  protected NoStackException(ErrorCode errorCode, Object... args) {
    super(errorCode, args);
  }

  /**
   * 覆盖该方法，以提高服务层异常Runtime时的执行效率<br>
   * 不需要声明方法为父类的synchronized
   */
  @Override
  public Throwable fillInStackTrace() {
    return this;
  }
}
