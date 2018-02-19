package net.oschina.git.justlive1.breeze.lighting.chained.core.step;

import org.springframework.stereotype.Service;

import net.oschina.git.justlive1.breeze.lighting.chained.conf.CoreProps.Option;
import net.oschina.git.justlive1.breeze.lighting.chained.conf.ProjectProps.Project;
import net.oschina.git.justlive1.breeze.snow.common.base.util.Checks;

/**
 * 部署部署，分发不同类型
 * 
 * @author wubo
 *
 */
@Service
public class DeployStep extends BaseStep {

    @Override
    public String unqueId() {
        return beanName;
    }

    @Override
    public void before(StepContext ctx) {

        Project project = (Project) ctx.get(StepContext.PROJECT);
        String deployType = Checks.notNull(project.getDeployType());
        Option deploy = Checks.notNull(Checks.notNull(coreProps.getDeploys()).get(deployType),
                String.format(NOT_CONFIGURED_MSG, deployType, project));
        ctx.put(StepContext.DEPLOY, deploy);
    }

    @Override
    public void handle(StepContext ctx) {

        Option deploy = (Option) ctx.get(StepContext.DEPLOY);
        String[] handles = Checks.notNull(deploy.getHandles());
        this.dispatcher(handles, ctx);
    }

}
