package justlive.earth.breeze.lighting.chained.core.step;

import org.springframework.stereotype.Service;
import justlive.earth.breeze.lighting.chained.conf.CoreProps.Option;
import justlive.earth.breeze.lighting.chained.conf.ProjectProps.Project;
import justlive.earth.breeze.snow.common.base.util.Checks;

/**
 * 构建
 * 
 * @author wubo
 *
 */
@Service
public class BuildStep extends BaseStep {

  @Override
  public String unqueId() {
    return beanName;
  }

  @Override
  public void before(StepContext ctx) {

    Project project = (Project) ctx.get(StepContext.PROJECT);
    String buildType = Checks.notNull(project.getBuildType());
    Option build = Checks.notNull(Checks.notNull(coreProps.getBuilds()).get(buildType),
        String.format(NOT_CONFIGURED_MSG, buildType, project));
    ctx.put(StepContext.BUILD, build);
  }

  @Override
  public void handle(StepContext ctx) {

    Option build = (Option) ctx.get(StepContext.BUILD);
    String[] handles = Checks.notNull(build.getHandles());
    this.dispatcher(handles, ctx);
  }

}
