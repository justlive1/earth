package net.oschina.git.justlive1.breeze.snow.common.base.exception;

import java.io.Serializable;
import lombok.Data;

/**
 * 异常包装类
 * 
 * @author wubo
 *
 */
@Data
public class ErrorCode implements Serializable {

  private static final long serialVersionUID = -5547379707487165303L;

  /**
   * 模块
   */
  private String module;

  /**
   * 错误编码
   */
  private String code;

  /**
   * 错误信息
   */
  private String message;

  protected ErrorCode(String module, String code) {
    this.module = module;
    this.code = code;
  }

  protected ErrorCode(String module, String code, String message) {
    this.module = module;
    this.code = code;
    this.message = message;
  }

  @Override
  public String toString() {
    if (this.module == null) {
      return this.code;
    }
    return this.module + "." + this.code;
  }
}
