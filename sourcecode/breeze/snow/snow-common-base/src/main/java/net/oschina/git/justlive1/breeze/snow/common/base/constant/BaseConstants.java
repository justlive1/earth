package net.oschina.git.justlive1.breeze.snow.common.base.constant;

/**
 * 基本常量
 * 
 * @author wubo
 *
 */
public class BaseConstants {

  // common

  /**
   * 验证web启动key
   */
  public static final String CHECK_WEB_KEY = "4c30c1be81144134bb9dc766ddf7f1b6";

  /**
   * 返回实体code属性字段
   */
  public static final String RESP_CODE_FIELD = "code";

  /**
   * 返回实体message属性字段
   */
  public static final String RESP_MESSAGE_FIELD = "message";

  /**
   * 所有路径匹配
   */
  public static final String ANY_PATH = "/*";

  /**
   * 根目录
   */
  public static final String ROOT_PATH = "/";

  // 正则

  /**
   * Email校验正则
   */
  public static final String REGEX_EMAIL = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";

  /**
   * 身份证校验正则
   */
  public static final String REGEX_IDCARD = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";

  /**
   * 二代身份证校验正则
   */
  public static final String REGEX_IDCARD2ND = "[1-9]\\d{16}[a-zA-Z0-9]{1}";

  /**
   * 手机校验正则
   */
  public static final String REGEX_MOBILE = "(\\+\\d+)?1[34578]\\d{9}$";

  /**
   * 电话校验正则
   */
  public static final String REGEX_PHONE = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";

  /**
   * 整数校验正则
   */
  public static final String REGEX_DIGIT = "[\\-\\+]?\\d+";

  /**
   * 空白字符校验正则
   */
  public static final String REGEX_BLANK_SPACE = "\\s+";

  /**
   * 中文字符校验正则
   */
  public static final String REGEX_CHINESE = "^[\u4E00-\u9FA5]+$";

  /**
   * 邮政编码校验正则
   */
  public static final String REGEX_POSTCODE = "[1-9]\\d{5}";

  /**
   * ipv4校验正则
   */
  public static final String REGEX_IP =
      "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";

  // mvc相关

  /**
   * csrf默认名称
   */
  public static final String CSRF_COOKIE_NAME = "XSRF-TOKEN";

  /**
   * csrf参数名称
   */
  public static final String CSRF_PARAMETER_NAME = "_csrf";

  /**
   * csrf http Header 名称
   */
  public static final String CSRF_HEADER_NAME = "X-XSRF-TOKEN";

  private BaseConstants() {}
}
