package net.oschina.git.justlive1.breeze.lighting.chained.core.step;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.oschina.git.justlive1.breeze.lighting.chained.conf.CoreProps.Deploy;
import net.oschina.git.justlive1.breeze.lighting.chained.conf.ProjectProps.Project;
import net.oschina.git.justlive1.breeze.snow.common.base.exception.Exceptions;
import net.oschina.git.justlive1.breeze.snow.common.base.util.Checks;

/**
 * 部署部署，分发不同类型
 * 
 * @author wubo
 *
 */
@Slf4j
@Service
public class DeployStep extends BaseStep implements ApplicationContextAware {

    private ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.ctx = applicationContext;
    }

    @Override
    public String unqueId() {
        return beanName;
    }

    @Override
    public void before(StepContext ctx) {

        Project project = (Project) ctx.get(StepContext.PROJECT);
        String deployType = Checks.notNull(project.getDeployType());
        Deploy deploy = Checks.notNull(Checks.notNull(coreProps.getDeploys()).get(deployType),
                String.format(NOT_CONFIGURED_MSG, deployType, project));
        ctx.put(StepContext.DEPLOY, deploy);
    }

    @Override
    public void handle(StepContext ctx) {

        Deploy deploy = (Deploy) ctx.get(StepContext.DEPLOY);

        Step step = this.ctx.getBean(Checks.notNull(deploy.getHandle()), Step.class);

        if (step == null) {
            String msg = String.format(BEAN_NOT_FOUND, deploy.getHandle());
            log.error(msg);
            throw Exceptions.fail(ERROR_CODE, msg);
        }

        step.before(ctx);
        step.handle(ctx);
        step.after(ctx);
    }

}
