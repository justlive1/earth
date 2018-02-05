package net.oschina.git.justlive1.breeze.snow.common.web.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Properties;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.oschina.git.justlive1.breeze.snow.common.base.constant.BaseConstants;
import net.oschina.git.justlive1.breeze.snow.common.base.domain.Response;

/**
 * 公共web请求
 * 
 * @author wubo
 *
 */
@RestController
@RequestMapping("/common")
public class CommonWebController {

    /**
     * 验证服务是否启动
     * 
     * @return
     */
    @GetMapping("/checkWeb")
    public String checkWeb() {
        return "checkWeb:" + BaseConstants.CHECK_WEB_KEY;
    }

    /**
     * 当前服务器毫秒值
     * 
     * @return
     */
    @GetMapping(value = "/currentTime", produces = "application/json; charset=UTF-8")
    public Response<Long> currentTime() {
        return Response.success(System.currentTimeMillis());
    }

    /**
     * 获取服务器日期 无时分秒
     * 
     * @param offset
     */
    @GetMapping(value = "/localDate", produces = "application/json; charset=UTF-8")
    public Response<Long> localDate(@RequestParam(defaultValue = "0") Integer offset) {
        return Response.success(
                LocalDate.now().plusDays(offset).atStartOfDay(ZoneOffset.systemDefault()).toInstant().toEpochMilli());
    }

    /**
     * 获取服务器日期时间
     * 
     * @param offset
     */
    @GetMapping(value = "/localDateTime", produces = "application/json; charset=UTF-8")
    public Response<Long> localDateTime(@RequestParam(defaultValue = "0") Integer offset) {
        return Response.success(
                LocalDateTime.now().plusDays(offset).atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli());
    }

    /**
     * 获取系统配置参数
     * 
     * @return
     */
    @GetMapping(value = "/systemProperties", produces = "application/json; charset=UTF-8")
    public Response<Properties> systemProperties() {
        return Response.success(System.getProperties());
    }
}
