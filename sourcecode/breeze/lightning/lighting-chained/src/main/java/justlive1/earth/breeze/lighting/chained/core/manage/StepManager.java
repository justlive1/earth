package justlive1.earth.breeze.lighting.chained.core.manage;

import java.util.Arrays;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.base.MoreObjects;
import justlive1.earth.breeze.lighting.chained.conf.CoreProps;
import justlive1.earth.breeze.lighting.chained.conf.ProjectProps;
import justlive1.earth.breeze.lighting.chained.conf.CoreProps.Option;
import justlive1.earth.breeze.lighting.chained.conf.CoreProps.Remote;
import justlive1.earth.breeze.lighting.chained.conf.ProjectProps.Project;
import justlive1.earth.breeze.lighting.chained.core.step.Step;
import justlive1.earth.breeze.lighting.chained.core.step.StepContext;
import justlive1.earth.breeze.snow.common.base.util.Checks;
import lombok.extern.slf4j.Slf4j;

/**
 * 步骤管理
 * 
 * @author wubo
 *
 */
@Slf4j
@Service
public class StepManager {

  @Autowired
  CoreProps coreProps;

  @Autowired
  ProjectProps projectProps;

  @Autowired
  Map<String, Step> stepMap;

  /**
   * 根据类型获取远程属性
   * 
   * @param type
   * @return
   */
  public Remote findByType(String type) {
    return Checks.notNull(coreProps.getRemotes()).get(type);
  }

  /**
   * 根据名称获取项目配置属性
   * 
   * @param name
   * @return
   */
  public Project findByName(String name) {
    return Checks.notNull(projectProps.getProjects()).get(name);
  }

  /**
   * 根据类型获取资源配置
   * 
   * @param type
   * @return
   */
  public Option findResourceByType(String type) {
    return Checks.notNull(coreProps.getResources()).get(type);
  }

  /**
   * 执行步骤
   * 
   * @param ctx
   */
  public void excute(StepContext ctx) {

    String projectName = Checks.notNull((String) ctx.get(StepContext.PROJECT_NAME));
    Project project = Checks.notNull(this.findByName(projectName));
    ctx.put(StepContext.PROJECT, project);
    Option resource = Checks.notNull(this.findResourceByType(project.getSourceType()));
    ctx.put(StepContext.RESOURCE, resource);

    String[] stepIds = MoreObjects.firstNonNull(project.getSteps(), coreProps.getDefaultSteps());

    if (log.isDebugEnabled()) {
      log.debug("excute stepIds: [{}]", Arrays.toString(stepIds));
    }

    for (String id : stepIds) {
      Step step = stepMap.get(id);
      if (step != null) {
        step.before(ctx);
        step.handle(ctx);
        step.after(ctx);
      }
    }
  }

}
