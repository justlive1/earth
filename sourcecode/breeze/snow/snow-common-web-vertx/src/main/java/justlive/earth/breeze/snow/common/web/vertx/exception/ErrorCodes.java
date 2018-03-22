package justlive.earth.breeze.snow.common.web.vertx.exception;

import justlive.earth.breeze.snow.common.base.exception.ErrorCode;
import justlive.earth.breeze.snow.common.base.exception.Exceptions;

/**
 * 错误编码
 * 
 * @author wubo
 *
 */
public class ErrorCodes {

  private ErrorCodes() {}

  /**
   * vertx-web模块名
   */
  public static final String MODULE = "VERTX-WEB";

  /**
   * url已绑定
   */
  public static final ErrorCode URL_HAS_BOUND =
      Exceptions.errorMessage(MODULE, "00000", "url[%s]重复绑定");

  /**
   * 类型不能转换
   */
  public static final ErrorCode TYPE_CANNOT_CONVERTER =
      Exceptions.errorMessage(MODULE, "00001", "[%]不能转换为[%s]类型");

}
