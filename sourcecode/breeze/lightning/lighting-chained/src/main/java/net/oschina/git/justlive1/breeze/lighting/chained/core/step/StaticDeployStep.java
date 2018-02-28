package net.oschina.git.justlive1.breeze.lighting.chained.core.step;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import net.oschina.git.justlive1.breeze.lighting.chained.conf.ProjectProps.Project;
import net.oschina.git.justlive1.breeze.snow.common.base.exception.Exceptions;

/**
 * 静态部署步骤
 * 
 * @author wubo
 *
 */
@Slf4j
@Service
public class StaticDeployStep extends BaseStep {

  @Override
  public String unqueId() {
    return beanName;
  }

  @Override
  public void handle(StepContext ctx) {

    Project project = (Project) ctx.get(StepContext.PROJECT);
    String releasePath = (String) ctx.get(StepContext.RELEASE_DIR);

    try {

      Path release = Paths.get(releasePath);
      Files.walkFileTree(release, new SimpleFileVisitor<Path>() {
        @Override
        public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
          Path realPath = Paths.get(project.getDeployPath() + release.relativize(path));
          File f = realPath.toFile();
          if (!f.exists()) {
            f.mkdirs();
          }
          Files.copy(path, realPath, StandardCopyOption.REPLACE_EXISTING);
          return super.visitFile(path, attrs);
        }
      });

    } catch (IOException e) {
      log.error("", e);
      throw Exceptions.wrap(e);
    }
  }

}
