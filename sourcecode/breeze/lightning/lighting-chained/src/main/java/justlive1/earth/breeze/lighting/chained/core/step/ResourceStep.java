package justlive1.earth.breeze.lighting.chained.core.step;

import org.springframework.stereotype.Service;
import justlive1.earth.breeze.lighting.chained.conf.CoreProps.Option;
import justlive1.earth.breeze.snow.common.base.util.Checks;

/**
 * 资源步骤
 * 
 * @author wubo
 *
 */
@Service
public class ResourceStep extends BaseStep {

  @Override
  public String unqueId() {
    return beanName;
  }

  @Override
  public void handle(StepContext ctx) {

    Option resource = (Option) ctx.get(StepContext.RESOURCE);
    String[] handles = Checks.notNull(resource.getHandles());
    this.dispatcher(handles, ctx);
  }

}
