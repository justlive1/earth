package justlive.earth.breeze.lighting.chained.core.step;

import java.io.File;
import java.io.InputStream;
import org.springframework.stereotype.Service;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import justlive.earth.breeze.lighting.chained.conf.CoreProps.Option;
import justlive.earth.breeze.snow.common.base.exception.Exceptions;
import lombok.extern.slf4j.Slf4j;

/**
 * NODE构建
 * 
 * @author wubo
 *
 */
@Slf4j
@Service
public class NodeBuildStep extends BaseStep {

  public static final String NODE_BUILD = "/dist/";

  @Override
  public String unqueId() {
    return beanName;
  }

  @Override
  public void before(StepContext ctx) {

    File logDir = new File(coreProps.getLogPath());
    if (!logDir.exists()) {
      logDir.mkdirs();
    }
  }

  @Override
  public void handle(StepContext ctx) {

    String fileName = (String) ctx.get(StepContext.TARGET_FILE_NAME);
    String buildDir = (String) ctx.get(StepContext.BUILD_DIR);
    String projectName = (String) ctx.get(StepContext.PROJECT_NAME);
    Option build = (Option) ctx.get(StepContext.BUILD);

    File dir = new File(buildDir, projectName);
    File logFile = new File(coreProps.getLogPath(), fileName + LOG_SUFFIX);

    try {
      for (String cmd : build.getCmds()) {
        Process process = Runtime.getRuntime().exec(cmd, null, dir);
        process.waitFor();
        InputStream in = process.getInputStream();
        if (in != null) {
          Files.asByteSink(logFile, FileWriteMode.APPEND).writeFrom(in);
          in.close();
        }
      }
    } catch (Exception e) {
      log.error("", e);
      throw Exceptions.wrap(e);
    }

    ctx.put(StepContext.RELEASE_DIR, dir.getAbsolutePath() + NODE_BUILD);
  }

}
