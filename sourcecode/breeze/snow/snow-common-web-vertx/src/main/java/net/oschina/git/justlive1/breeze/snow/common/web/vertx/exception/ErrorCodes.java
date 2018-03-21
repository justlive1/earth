package net.oschina.git.justlive1.breeze.snow.common.web.vertx.exception;

import net.oschina.git.justlive1.breeze.snow.common.base.exception.ErrorCode;
import net.oschina.git.justlive1.breeze.snow.common.base.exception.Exceptions;

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

}
