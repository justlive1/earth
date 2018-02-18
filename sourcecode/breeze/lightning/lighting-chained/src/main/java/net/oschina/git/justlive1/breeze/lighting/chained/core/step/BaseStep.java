package net.oschina.git.justlive1.breeze.lighting.chained.core.step;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;

import net.oschina.git.justlive1.breeze.lighting.chained.conf.CoreProps;
import net.oschina.git.justlive1.breeze.lighting.chained.conf.ProjectProps;

/**
 * 基本步骤
 * 
 * @author wubo
 *
 */
public abstract class BaseStep implements Step, BeanNameAware {

    public static final String LOG_SUFFIX = "_build.log";
    public static final String ERROR_CODE = "-1";
    public static final String NOT_CONFIGURED_MSG = "%s for %s is not configured";
    public static final String BEAN_NOT_FOUND = "cannot not find bean named by %s";

    @Autowired
    protected CoreProps coreProps;

    @Autowired
    protected ProjectProps projectProps;

    protected String beanName;

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    @Override
    public String toString() {
        return String.format("[%s],%s", beanName, super.toString());
    }

}
