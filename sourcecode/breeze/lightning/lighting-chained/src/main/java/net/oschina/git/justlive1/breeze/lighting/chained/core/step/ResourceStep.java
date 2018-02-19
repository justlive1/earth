package net.oschina.git.justlive1.breeze.lighting.chained.core.step;

import org.springframework.stereotype.Service;

import net.oschina.git.justlive1.breeze.lighting.chained.conf.CoreProps.Option;
import net.oschina.git.justlive1.breeze.lighting.chained.conf.ProjectProps.Project;
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
    public void before(StepContext ctx) {

        String projectName = Checks.notNull((String) ctx.get(StepContext.PROJECT_NAME));
        Project project = Checks.notNull(Checks.notNull(projectProps.getProjects()).get(projectName));
        ctx.put(StepContext.PROJECT, project);
    }

    @Override
    public void handle(StepContext ctx) {

        Option build = (Option) ctx.get(StepContext.RESOURCE);
        String[] handles = Checks.notNull(build.getHandles());
        this.dispatcher(handles, ctx);
    }

}
