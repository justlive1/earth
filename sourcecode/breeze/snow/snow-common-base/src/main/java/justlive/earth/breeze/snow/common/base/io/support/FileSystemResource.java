package justlive.earth.breeze.snow.common.base.io.support;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import justlive.earth.breeze.snow.common.base.io.SourceResource;
import justlive.earth.breeze.snow.common.base.util.Checks;
import justlive.earth.breeze.snow.common.base.util.ResourceUtils;

/**
 * 文件系统资源，包括File，Path
 * 
 * @author wubo
 *
 */
public class FileSystemResource implements SourceResource {

  private File file;
  private String filePath;
  private Path path;

  /**
   * 通过文件路径创建一个 {@code FileSystemResource}
   * 
   * @param filePath
   */
  public FileSystemResource(String filePath) {
    Checks.notNull(filePath);
    this.file = new File(filePath);
    this.filePath = file.toPath().normalize().toString();
  }

  /**
   * 通过{@link File}创建一个 {@code FileSystemResource}
   * 
   * @param file
   */
  public FileSystemResource(File file) {
    Checks.notNull(file);
    this.file = file;
  }

  /**
   * 通过{@link Path}创建一个 {@code FileSystemResource}
   * 
   * @param path
   */
  public FileSystemResource(Path path) {
    Checks.notNull(path);
    this.path = path;
    this.file = path.toFile();
  }

  @Override
  public String path() {
    if (filePath == null) {
      filePath = file.toPath().normalize().toString();
    }
    return filePath;
  }

  @Override
  public InputStream getInputStream() throws IOException {
    if (path == null) {
      path = file.toPath();
    }
    return Files.newInputStream(path);
  }

  @Override
  public boolean isFile() {
    return file.isFile();
  }

  @Override
  public File getFile() throws IOException {
    return file;
  }

  @Override
  public URL getURL() throws IOException {
    return file.toURI().toURL();
  }

  /**
   * 获取文件Path
   * 
   * @return
   */
  public Path getPath() {
    if (path == null) {
      path = file.toPath();
    }
    return path;
  }

  @Override
  public SourceResource createRelative(String path) {
    return new FileSystemResource(ResourceUtils.relativePath(this.path(), path));
  }

}
