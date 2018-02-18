package net.oschina.git.justlive1.breeze.lighting.chained.core.step;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.oschina.git.justlive1.breeze.lighting.chained.conf.CoreProps.Build;
import net.oschina.git.justlive1.breeze.lighting.chained.conf.ProjectProps.Project;
import net.oschina.git.justlive1.breeze.snow.common.base.exception.Exceptions;
import net.oschina.git.justlive1.breeze.snow.common.base.util.Checks;

/**
 * 构建
 * 
 * @author wubo
 *
 */
@Slf4j
@Service
public class BuildStep extends BaseStep implements ApplicationContextAware {

    private ApplicationContext ctx;

    @Override
    public String unqueId() {
        return beanName;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.ctx = applicationContext;
    }

    @Override
    public void before(StepContext ctx) {

        Project project = (Project) ctx.get(StepContext.PROJECT);
        String buildType = Checks.notNull(project.getBuildType());
        Build build = Checks.notNull(Checks.notNull(coreProps.getBuilds()).get(buildType),
                String.format(NOT_CONFIGURED_MSG, buildType, project));
        ctx.put(StepContext.BUILD, build);
    }

    @Override
    public void handle(StepContext ctx) {

        Build build = (Build) ctx.get(StepContext.BUILD);
        Step step = this.ctx.getBean(Checks.notNull(build.getHandle()), Step.class);

        if (step == null) {
            String msg = String.format(BEAN_NOT_FOUND, build.getHandle());
            log.error(msg);
            throw Exceptions.fail(ERROR_CODE, msg);
        }

        step.before(ctx);
        step.handle(ctx);
        step.after(ctx);

    }

}
