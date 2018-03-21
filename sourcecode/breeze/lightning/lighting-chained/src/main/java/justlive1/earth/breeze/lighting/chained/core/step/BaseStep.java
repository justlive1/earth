package justlive1.earth.breeze.lighting.chained.core.step;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import justlive1.earth.breeze.lighting.chained.conf.CoreProps;
import justlive1.earth.breeze.lighting.chained.conf.ProjectProps;
import justlive1.earth.breeze.snow.common.base.exception.Exceptions;
import lombok.extern.slf4j.Slf4j;

/**
 * 基本步骤
 * 
 * @author wubo
 *
 */
@Slf4j
public abstract class BaseStep implements Step, BeanNameAware, ApplicationContextAware {

  public static final String LOG_SUFFIX = "_build.log";
  public static final String ERROR_CODE = "-1";
  public static final String NOT_CONFIGURED_MSG = "%s for %s is not configured";
  public static final String BEAN_NOT_FOUND = "cannot not find bean named by %s";

  @Autowired
  protected CoreProps coreProps;

  @Autowired
  protected ProjectProps projectProps;

  protected String beanName;

  protected ApplicationContext appCtx;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    this.appCtx = applicationContext;
  }

  @Override
  public void setBeanName(String name) {
    this.beanName = name;
  }

  @Override
  public String toString() {
    return String.format("[%s],%s", beanName, super.toString());
  }

  protected void dispatcher(String[] handles, StepContext ctx) {

    for (String handle : handles) {

      Step step = this.appCtx.getBean(handle, Step.class);
      if (step == null) {
        String msg = String.format(BEAN_NOT_FOUND, handle);
        log.error(msg);
        throw Exceptions.fail(ERROR_CODE, msg);
      }

      step.before(ctx);
      step.handle(ctx);
      step.after(ctx);
    }
  }

}
