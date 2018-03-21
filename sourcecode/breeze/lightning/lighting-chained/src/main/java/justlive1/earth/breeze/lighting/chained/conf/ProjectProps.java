package justlive1.earth.breeze.lighting.chained.conf;

import java.util.Map;
import lombok.Data;

/**
 * 项目属性
 * 
 * @author wubo
 *
 */
@Data
public class ProjectProps {

  private Map<String, Project> projects;

  @Data
  public static class Project {

    private String id;

    private String sourceType;

    private String buildType;

    private String deployType;

    private String deployPath;

    private String[] steps;
  }
}
