package justlive.earth.breeze.snow.common.base.util;

import static justlive.earth.breeze.snow.common.base.constant.BaseConstants.*;
import static justlive.earth.breeze.snow.common.base.exception.Exceptions.errorMessage;
import static justlive.earth.breeze.snow.common.base.exception.Exceptions.fail;
import java.util.regex.Pattern;
import justlive.earth.breeze.snow.common.base.exception.ErrorCode;
import justlive.earth.breeze.snow.common.base.exception.Exceptions;

/**
 * 校验工具类
 * 
 * @author wubo
 *
 */
public class Checks {

  public static final String MODULE_VALID = "VALID";
  public static final String NULL_VALUE = "00001";
  public static final String INVALID_EMAIL = "00002";
  public static final String INVALID_IDCARD = "00003";
  public static final String INVALID_MOBILE = "00004";
  public static final String INVALID_PHONE = "00005";
  public static final String INVALID_DIGIT = "00006";
  public static final String INVALID_DECIMAL = "00007";
  public static final String INVALID_BLACK_SPACE = "00008";
  public static final String INVALID_CHINESE = "00009";
  public static final String INVALID_POSTCODE = "00010";
  public static final String INVALID_IP = "00011";

  private Checks() {}

  /**
   * 非空检查
   */
  public static <T> T notNull(T obj) {
    return notNull(obj, "can not be null");
  }

  public static <T> T notNull(T obj, String msg) {
    return notNull(obj, errorMessage(MODULE_VALID, NULL_VALUE, msg));
  }

  public static <T> T notNull(T obj, ErrorCode errCode, Object... params) {
    if (obj == null) {
      throw fail(errCode, params);
    }
    return obj;
  }

  /**
   * 验证Email
   * 
   * @param email email地址
   */
  public static void email(String email) {
    email(email, "email is invalid");
  }

  public static void email(String email, String msg) {
    email(email, errorMessage(MODULE_VALID, INVALID_EMAIL, msg));
  }

  public static void email(String email, ErrorCode errCode, Object... params) {
    if (!Pattern.matches(REGEX_EMAIL, email)) {
      throw Exceptions.fail(errCode, params);
    }
  }

  /**
   * 验证身份证号码
   * 
   * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
   */
  public static void idCard(String idCard) {
    idCard(idCard, "idcard is invalid");
  }

  public static void idCard(String idCard, String msg) {
    idCard(idCard, errorMessage(MODULE_VALID, INVALID_IDCARD, msg));
  }

  public static void idCard(String idCard, ErrorCode errCode, Object... params) {
    if (!Pattern.matches(REGEX_IDCARD, idCard)) {
      throw Exceptions.fail(errCode, params);
    }
  }

  /**
   * 验证第二代身份证号码
   * 
   * @param idCard 居民身份证号码18位，最后一位可能是数字或字母
   */
  public static void idCard2nd(String idCard) {
    idCard(idCard, "idcard is invalid");
  }

  public static void idCard2nd(String idCard, String msg) {
    idCard(idCard, errorMessage(MODULE_VALID, INVALID_IDCARD, msg));
  }

  public static void idCard2nd(String idCard, ErrorCode errCode, Object... params) {
    if (!Pattern.matches(REGEX_IDCARD2ND, idCard)) {
      throw Exceptions.fail(errCode, params);
    }
  }

  /**
   * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
   * 
   * @param mobile 移动、联通、电信运营商的号码段 中国移动：134（不含1349）、135、136、137、138、139、147、150、151、152、157、158、
   *        159、182、183、184、187、188、178 中国联通：130、131、132、145（上网卡）、155、156、185、186、176
   *        中国电信：133、1349（卫星通信）、153、180、181、189、177、173 4G号段：176(联通)、173/177(电信)、178(移动)
   *        虚拟运营商：170[1700/1701/1702(电信)、1703/1705/1706(移动)、1704/1707/1708/1709(联通)]、171（联通）
   *        未知号段：140、141、142、143、144、146、148、149、154
   */
  public static void mobile(String mobile) {
    mobile(mobile, "mobile is invalid");
  }

  public static void mobile(String mobile, String msg) {
    mobile(mobile, errorMessage(MODULE_VALID, INVALID_MOBILE, msg));
  }

  public static void mobile(String mobile, ErrorCode errCode, Object... params) {
    if (!Pattern.matches(REGEX_MOBILE, mobile)) {
      throw Exceptions.fail(errCode, params);
    }
  }

  /**
   * 验证固定电话号码
   * 
   * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
   *        <p>
   *        <b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字， 数字之后是空格分隔的国家（地区）代码。
   *        </p>
   *        <p>
   *        <b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号—— 对不使用地区或城市代码的国家（地区），则省略该组件。
   *        </p>
   *        <p>
   *        <b>电话号码：</b>这包含从 0 到 9 的一个或多个数字
   *        </p>
   * 
   */
  public static void phone(String phone) {
    phone(phone, "phone is invalid");
  }

  public static void phone(String phone, String msg) {
    phone(phone, errorMessage(MODULE_VALID, INVALID_PHONE, msg));
  }

  public static void phone(String phone, ErrorCode errCode, Object... params) {
    if (!Pattern.matches(REGEX_PHONE, phone)) {
      throw Exceptions.fail(errCode, params);
    }
  }

  /**
   * 验证整数（正整数和负整数）
   * 
   * @param digit 一位或多位0-9之间的整数
   * 
   */
  public static void digit(String digit) {
    digit(digit, "not digit");
  }

  public static void digit(String digit, String msg) {
    digit(digit, errorMessage(MODULE_VALID, INVALID_DIGIT, msg));
  }

  public static void digit(String digit, ErrorCode errCode, Object... params) {
    if (!Pattern.matches(REGEX_DIGIT, digit)) {
      throw Exceptions.fail(errCode, params);
    }
  }

  /**
   * 验证整数和浮点数（正负整数和正负浮点数）
   * 
   * @param decimals 一位或多位0-9之间的浮点数，如：1.23，233.30
   * 
   */
  public static void decimals(String decimals) {
    decimals(decimals, (Integer) null);
  }

  public static void decimals(String decimals, String msg) {
    decimals(decimals, null, msg);
  }

  public static void decimals(String decimals, ErrorCode errCode, Object... params) {
    decimals(decimals, null, errCode, params);
  }

  /**
   * 验证整数和浮点数（正负整数和正负浮点数）
   * 
   * @param decimals 要验证的数
   * @param decimal 小数部分的最大长度
   */
  public static void decimals(String decimals, Integer decimal) {
    decimals(decimals, decimal, "not decimial");
  }

  public static void decimals(String decimals, Integer decimal, String msg) {
    decimals(decimals, decimal, errorMessage(MODULE_VALID, INVALID_DECIMAL, msg));
  }

  public static void decimals(String decimals, Integer decimal, ErrorCode errCode,
      Object... params) {
    notNull(decimals, errCode, params);
    String regex = null;
    if (decimal == null) {
      regex = "^[+-]?(\\d*\\.)?\\d+$";
    } else if (decimal > 0) {
      regex = "^[+-]?(\\d*)?(\\.)?\\d{0," + decimal + "}$";
    } else {
      throw Exceptions.fail(errCode, params);
    }
    if (".".equals(decimals) || !Pattern.matches(regex, decimals)) {
      throw Exceptions.fail(errCode, params);
    }
  }

  /**
   * 验证空白字符
   * 
   * @param blankSpace 空白字符，包括：空格、\t、\n、\r、\f、\x0B
   * 
   */
  public static void blankSpace(String blankSpace) {
    blankSpace(blankSpace, "not blank space");
  }

  public static void blankSpace(String blankSpace, String msg) {
    blankSpace(blankSpace, errorMessage(MODULE_VALID, INVALID_BLACK_SPACE, msg));
  }

  public static void blankSpace(String blankSpace, ErrorCode errCode, Object... params) {
    if (!Pattern.matches(REGEX_BLANK_SPACE, blankSpace)) {
      throw Exceptions.fail(errCode, params);
    }
  }

  /**
   * 验证中文
   * 
   * @param chinese 中文字符
   * 
   */
  public static void chinese(String chinese) {
    chinese(chinese, "not chinese");
  }

  public static void chinese(String chinese, String msg) {
    chinese(chinese, errorMessage(MODULE_VALID, INVALID_CHINESE, msg));
  }

  public static void chinese(String chinese, ErrorCode errCode, Object... params) {
    if (!Pattern.matches(REGEX_CHINESE, chinese)) {
      throw Exceptions.fail(errCode, params);
    }
  }

  /**
   * 匹配中国邮政编码
   * 
   * @param postcode 邮政编码
   * 
   */
  public void postcode(String postcode) {
    postcode(postcode, "not postcode");
  }

  public void postcode(String postcode, String msg) {
    postcode(postcode, errorMessage(MODULE_VALID, INVALID_POSTCODE, msg));
  }

  public static void postcode(String postcode, ErrorCode errCode, Object... params) {
    if (!Pattern.matches(REGEX_POSTCODE, postcode)) {
      throw Exceptions.fail(errCode, params);
    }
  }

  /**
   * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
   * 
   * @param ip IPv4标准地址
   * 
   */
  public static void ip(String ip) {
    ip(ip, "not ip");
  }

  public static void ip(String ip, String msg) {
    ip(ip, errorMessage(MODULE_VALID, INVALID_IP, msg));
  }

  public static void ip(String ip, ErrorCode errCode, Object... params) {
    if (!Pattern.matches(REGEX_IP, ip)) {
      throw Exceptions.fail(errCode, params);
    }
  }

}
