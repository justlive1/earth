package net.oschina.git.justlive1.breeze.snow.common.web.vertx.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 参数解析包装
 * 
 * @author wubo
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParamWrap {

  /**
   * 参数名
   */
  private String value;

  /**
   * 是否必须
   */
  private boolean required;

  /**
   * resolve方法下标
   */
  private int index;

  /**
   * 参数类型
   */
  private Class<?> clazz;

}
