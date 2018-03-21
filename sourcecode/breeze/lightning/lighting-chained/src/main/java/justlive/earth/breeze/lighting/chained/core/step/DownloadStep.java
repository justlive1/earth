package justlive.earth.breeze.lighting.chained.core.step;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import justlive.earth.breeze.lighting.chained.conf.CoreProps.Remote;
import justlive.earth.breeze.lighting.chained.conf.ProjectProps.Project;
import justlive.earth.breeze.snow.common.base.exception.Exceptions;
import justlive.earth.breeze.snow.common.base.util.Checks;
import lombok.extern.slf4j.Slf4j;

/**
 * 下载
 * 
 * @author wubo
 *
 */
@Service
@Slf4j
public class DownloadStep extends BaseStep {

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

    try {

      File dir = new File(coreProps.getTmpPath());
      if (!dir.exists()) {
        dir.mkdirs();
      }

      Remote remote = (Remote) ctx.get(StepContext.REMOTE);
      String filePath = (String) ctx.get(StepContext.REMOTE_FILE);
      String md5 = DigestUtils.md5DigestAsHex(filePath.getBytes(StandardCharsets.UTF_8));
      File target = new File(dir, md5 + remote.getFileSuffix());
      Resources.asByteSource(new URL(filePath)).copyTo(Files.asByteSink(target));

      ctx.put(StepContext.TARGET_FILE_NAME, md5);
      ctx.put(StepContext.TARGET_FILE, target.getAbsolutePath());

    } catch (IOException e) {
      log.error("", e);
      throw Exceptions.wrap(e);
    }

  }

}
