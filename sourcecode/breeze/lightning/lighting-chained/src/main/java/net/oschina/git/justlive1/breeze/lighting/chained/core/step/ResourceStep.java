package net.oschina.git.justlive1.breeze.lighting.chained.core.step;

import org.springframework.stereotype.Service;

import net.oschina.git.justlive1.breeze.lighting.chained.conf.CoreProps.Option;
import net.oschina.git.justlive1.breeze.snow.common.base.util.Checks;

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
