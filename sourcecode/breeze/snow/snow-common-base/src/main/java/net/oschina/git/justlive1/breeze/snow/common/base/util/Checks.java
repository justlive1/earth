package net.oschina.git.justlive1.breeze.snow.common.base.util;

import static net.oschina.git.justlive1.breeze.snow.common.base.constant.BaseConstants.*;
import static net.oschina.git.justlive1.breeze.snow.common.base.exception.Exceptions.errorMessage;
import static net.oschina.git.justlive1.breeze.snow.common.base.exception.Exceptions.fail;

import java.util.regex.Pattern;

import net.oschina.git.justlive1.breeze.snow.common.base.exception.ErrorCode;
import net.oschina.git.justlive1.breeze.snow.common.base.exception.Exceptions;

/**
 * 校验工具类
 * 
 * @author wubo
 *
 */
public class Checks {

    public static final String MODULE_VALID = "VALID";
    public static final String NULL_VALUE = "-00001";
    public static final String INVAID_EMAIL = "-00002";
    public static final String INVAID_IDCARD = "-00003";

    private Checks() {
    }

    /**
     * 非空检查
     */
    public static void notNull(Object obj) {
        notNull(obj, "can not be null");
    }

    public static void notNull(Object obj, String msg) {
        notNull(obj, errorMessage(MODULE_VALID, NULL_VALUE, msg));
    }

    public static void notNull(Object obj, ErrorCode errCode, Object... params) {
        if (obj == null) {
            throw fail(errCode, params);
        }
    }

    /**
     * 验证Email
     * 
     * @param email
     *            email地址
     */
    public static void email(String email) {
        email(email, "email is invalid");
    }

    public static void email(String email, String msg) {
        email(email, errorMessage(MODULE_VALID, INVAID_EMAIL, msg));
    }

    public static void email(String email, ErrorCode errCode, Object... params) {
        if (!Pattern.matches(REGEX_EMAIL, email)) {
            throw Exceptions.fail(errCode, params);
        }
    }

    /**
     * 验证身份证号码
     * 
     * @param idCard
     *            居民身份证号码15位或18位，最后一位可能是数字或字母
     */
    public static void idCard(String idCard) {
        idCard(idCard, "idcard is invalid");
    }

    public static void idCard(String idCard, String msg) {
        idCard(idCard, errorMessage(MODULE_VALID, INVAID_IDCARD, msg));
    }

    public static void idCard(String idCard, ErrorCode errCode, Object... params) {
        if (!Pattern.matches(REGEX_IDCARD, idCard)) {
            throw Exceptions.fail(errCode, params);
        }
    }

    /**
     * 验证第二代身份证号码
     * 
     * @param idCard
     *            居民身份证号码18位，最后一位可能是数字或字母
     */
    public static void idCard2nd(String idCard) {
        idCard(idCard, "idcard is invalid");
    }

    public static void idCard2nd(String idCard, String msg) {
        idCard(idCard, errorMessage(MODULE_VALID, INVAID_IDCARD, msg));
    }

    public static void idCard2nd(String idCard, ErrorCode errCode, Object... params) {
        if (!Pattern.matches(REGEX_IDCARD2ND, idCard)) {
            throw Exceptions.fail(errCode, params);
        }
    }

}
