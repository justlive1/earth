package justlive1.earth.breeze.lighting.chained.conf;

import java.util.List;
import java.util.Map;
import lombok.Data;

/**
 * 核心配置属性
 * 
 * @author wubo
 *
 */
@Data
public class CoreProps {

  public enum REMOTE_TYPE {
    GITHUB
  }

  public enum RESOURCE_TYPE {
    ZIP, GIT, SVN
  }

  public enum BUILD_TYPE {
    NODE
  }

  public enum DEPLOY_TYPE {
    STATIC
  }

  /**
   * 临时路径
   * 
   */
  private String tmpPath;

  /**
   * 日志路径
   */
  private String logPath;

  /**
   * 默认执行步骤
   */
  private String[] defaultSteps;

  /**
   * 远程配置
   */
  private Map<String, Remote> remotes;

  /**
   * 资源配置
   */
  private Map<String, Option> resources;

  /**
   * 构建配置
   */
  private Map<String, Option> builds;

  /**
   * 部署配置
   */
  private Map<String, Option> deploys;

  /**
   * 远程仓库
   * 
   * @author wubo
   *
   */
  @Data
  public static class Remote {

    /**
     * 远程类型
     */
    private String type;

    /**
     * 代理
     */
    private String agent;

    /**
     * 触发事件
     */
    private List<String> events;

    /**
     * 分支前缀
     */
    private String refPrefix;

    /**
     * 文件前缀
     */
    private String filePrefix;

    /**
     * 文件后缀
     */
    private String fileSuffix;

  }

  /**
   * 配置选项
   * 
   * @author wubo
   *
   */
  @Data
  public static class Option {

    private String type;

    private String[] handles;

    private String envPath;

    private String[] cmds;
  }
}
